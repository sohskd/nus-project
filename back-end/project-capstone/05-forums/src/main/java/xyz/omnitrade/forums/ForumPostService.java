
package xyz.omnitrade.forums;

import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.helidon.common.http.Http;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;
import io.helidon.webserver.Handler;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

public class ForumPostService implements Service {

    private static final Logger LOGGER = Logger.getLogger(ForumPostService.class.getName());

    private final DbClient dbClient;

    ForumPostService(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/posts", this::listAllPosts);
                // .get("/posts/{ticker}", this::getPostsByTicker)
                // .post("/posts", Handler.create(Pokemon.class, this::insertPost));
    }

    private void listAllPosts(ServerRequest request, ServerResponse response) {
        dbClient.execute(exec -> exec.namedQuery("select-all-posts"))
                .map(row -> row.as(ForumPost.class))
                .collectList()
                .forSingle(response::send);
    }

    // private void getPostsByTicker(ServerRequest request, ServerResponse response) {
    //     try {
    //         int pokemonId = Integer.parseInt(request.path().param("id"));
    //         dbClient.execute(exec -> exec
    //                 .createNamedGet("select-pokemon-by-id")
    //                 .addParam("id", pokemonId)
    //                 .execute())
    //                 .thenAccept(maybeRow -> maybeRow
    //                         .ifPresentOrElse(
    //                                 row -> response.send(row.as(Pokemon.class)),
    //                                 () -> sendNotFound(response, "Pokemon " + pokemonId + " not found")))
    //                 .exceptionally(throwable -> sendError(throwable, response));
    //     } catch (NumberFormatException ex) {
    //         sendError(ex, response);
    //     }
    // }

    // private void insertPost(ServerRequest request, ServerResponse response, Pokemon pokemon) {
    //     dbClient.execute(exec -> exec
    //             .createNamedInsert("insert-pokemon")
    //             .indexedParam(pokemon)
    //             .execute())
    //             .thenAccept(count -> response.send("Inserted: " + count + " values\n"))
    //             .exceptionally(throwable -> sendError(throwable, response));
    // }

    private void sendNotFound(ServerResponse response, String message) {
        response.status(Http.Status.NOT_FOUND_404);
        response.send(message);
    }

    private <T> T sendError(Throwable throwable, ServerResponse response) {
        Throwable realCause = throwable;
        if (throwable instanceof CompletionException) {
            realCause = throwable.getCause();
        }
        response.status(Http.Status.INTERNAL_SERVER_ERROR_500);
        response.send(
                "Failed to process request: " + realCause.getClass().getName() + "(" + realCause.getMessage() + ")");
        LOGGER.log(Level.WARNING, "Failed to process request", throwable);
        return null;
    }
}

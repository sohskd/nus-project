
package xyz.omnitrade.forums;

import java.sql.Date;
import java.time.LocalDate;

public class ForumPost {

    private int id;
    private String ticker;
    private String username;
    private String post;
    // private Date dateCreated;

    public ForumPost() {
        // JSON-B
    }

    public ForumPost(int id, String ticker, String username, String post) {
        this.id = id;
        this.ticker = ticker;
        this.username = username;
        this.post = post;
        // this.dateCreated = Date.valueOf(LocalDate.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    // public Date getDateCreated() {
    //     return dateCreated;
    // }

    // public void setDateCreated(LocalDate date) {
    //     this.dateCreated = Date.valueOf(date);
    // }
}

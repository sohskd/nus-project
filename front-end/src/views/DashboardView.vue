<template>
  <div class="about">
    <v-container fluid>
      <v-row dense>
        <v-col v-for="card in cards" :key="card.title" :cols="card.flex">
          <v-card>
            <v-img
              :src="card.src"
              class="white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
            </v-img>

            <v-card-actions>
              <v-spacer></v-spacer>

              <v-btn color="green" dark> Buy </v-btn>

              <v-btn color="red" dark> Sell </v-btn>

              <v-btn color="blue" icon>
                <v-icon>mdi-forum</v-icon>
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => ({
    cards: [
      {
        src: "https://via.placeholder.com/500x500/000000/FFFFFF/?text=SE",
        flex: 6,
      },
      {
        src: "https://via.placeholder.com/500x500/000000/FFFFFF/?text=MSFT",
        flex: 6,
      },
    ],
  }),
};
</script>

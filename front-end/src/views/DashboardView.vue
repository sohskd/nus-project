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
              <v-card-title v-text="card.title"></v-card-title>
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
import axios from "axios";

export default {
  name: "LoginView",
  components: {
    // HelloWorld,
  },
  mounted() {
    this.getStocksList();
  },
  methods: {
    async getStocksList() {
      let result = await axios.get(
        "https://orders.omni-trade.xyz/api/stocklive"
      );
      if (result.status === 200) {
        this.stocksList = result.data.data;
        this.stocksList.forEach((stock) => {
          this.cards.push({
            title: Number(stock.priceLive).toFixed(2),
            src: this.getPlaceholderImage(stock.stockTicker),
            flex: 6,
          });
        });
      } else {
        return null;
      }
    },
    getPlaceholderImage(text) {
      let bgColor = this.randomColor().slice(-6);
      let fgColor = this.randomColor().slice(-6);
      return `https://via.placeholder.com/500x500/${bgColor}/${fgColor}/?text=${text}`;
    },
    randomColor() {
      let r = ("0" + Math.round(Math.random() * 256).toString(16)).slice(-2),
        g = ("0" + Math.round(Math.random() * 256).toString(16)).slice(-2),
        b = ("0" + Math.round(Math.random() * 256).toString(16)).slice(-2);
      // console.log(r, g, b);
      return `#${r}${g}${b}`;
    },
  },
  data: () => ({
    stocksList: [],
    cards: [],
  }),
};
</script>

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

              <v-btn
                @click="
                  buyStock(
                    'BUY',
                    null,
                    card.data.stockTicker,
                    card.data.priceLive
                  )
                "
                color="green"
                class="white--text"
                :loading="isLoading"
                :disabled="isLoading"
              >
                Buy
              </v-btn>

              <v-btn
                @click="
                  buyStock(
                    'SELL',
                    null,
                    card.data.stockTicker,
                    card.data.priceLive
                  )
                "
                color="red"
                class="white--text"
                :loading="isLoading"
                :disabled="isLoading"
              >
                Sell
              </v-btn>

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
            data: stock,
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
    async buyStock(side, amount, ticker, price) {
      let userId = this.$store.getters.userData.userId;
      this.isLoading = true;

      amount = Math.round(Math.random() * 100);

      console.log(`[buyStock] ${side} ${amount}x${ticker} @ ${price}...`);

      let result = await axios.post(
        "https://orders.omni-trade.xyz/ordermatching/order",
        `${side}#${ticker}#${amount}#${price}#${userId}`,
        {
          headers: {
            "Content-Type": "text/plain",
          },
        }
      );

      this.isLoading = false;

      if (result.status === 200) {
        return true;
      } else {
        return false;
      }
    },
  },
  data: () => ({
    stocksList: [],
    cards: [],
    isLoading: false,
  }),
};
</script>

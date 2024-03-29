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
                @click="openTradeDialog('BUY', card.data)"
                color="green"
                class="white--text"
              >
                Buy
              </v-btn>

              <v-btn
                @click="openTradeDialog('SELL', card.data)"
                color="red"
                class="white--text"
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
    <v-dialog v-model="isTradeDialogVisible" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="text-h5"
            >{{ tradeSide }}
            {{ selectedStock ? selectedStock.stockTicker : "" }}</span
          >
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  label="Amount*"
                  outlined
                  required
                  v-model="selectedStock.amount"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  label="Price*"
                  outlined
                  required
                  v-model="selectedStock.price"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
          <small>*indicates required field</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn secondary text @click="isTradeDialogVisible = false">
            Cancel
          </v-btn>
          <v-btn
            @click="submitOrder(tradeSide)"
            :loading="isLoading"
            :disabled="isLoading"
            color="white--text blue darken-1"
          >
            Submit
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "DashboardView",
  components: {
    // HelloWorld,
  },
  data: () => ({
    stocksList: [],
    cards: [],
    isLoading: false,
    isTradeDialogVisible: false,
    tradeSide: "",
    selectedStock: {},
  }),
  mounted() {
    this.getStocksList();
  },
  methods: {
    async getStocksList() {
      let result = await axios.get(
        `${process.env.VUE_APP_ENDPOINT_ORDERS}/api/stocklive`
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
    async submitOrder(side) {
      let ticker = this.selectedStock.ticker;
      let amount = this.selectedStock.amount;
      let price = this.selectedStock.price;
      let userId = this.$store.getters.userData.userId;

      this.isLoading = true;

      console.log(`[submitOrder] ${side} ${amount}x${ticker} @ ${price}...`);

      let result = await axios.post(
        `${process.env.VUE_APP_ENDPOINT_ORDERS}/ordermatching/order`,
        `${side}#${ticker}#${amount}#${price}#${userId}`,
        {
          headers: {
            "Content-Type": "text/plain",
          },
        }
      );

      this.isLoading = false;
      this.isTradeDialogVisible = false;

      if (result.status === 200) {
        return true;
      } else {
        return false;
      }
    },
    openTradeDialog(side, stockData) {
      this.tradeSide = side;
      this.selectedStock = {
        amount: Math.round(Math.random() * 10),
        price: stockData.priceLive.toFixed(2),
        ticker: stockData.stockTicker,
      };
      this.isTradeDialogVisible = true;
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
};
</script>

<template>
  <div class="orderbook">
    <v-container>
      <h1 class="text-center">Order Book</h1>
      <v-data-table
        :headers="headers"
        :items="trades"
        :items-per-page="5"
        class="elevation-1"
      ></v-data-table>
    </v-container>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "OrderbookView",
  components: {},
  mounted() {
    this.getOrderHistory();
  },
  data() {
    return {
      headers: [
        {
          text: "Stock",
          align: "start",
          sortable: false,
          value: "stockTicker",
        },
        { text: "Order Type", value: "side" },
        { text: "Quantity", value: "quantity" },
        { text: "Price Done", value: "price" },
        { text: "Status", value: "status" },
        { text: "P&L", value: "profitOrLoss" },
      ],
      trades: [],
    };
  },
  methods: {
    async getOrderHistory() {
      let userId = this.$store.getters.userData.userId;
      let result = await axios.get(
        `https://orders.omni-trade.xyz/api/position?userId=${userId}`
      );
      if (result.status === 200) {
        this.trades = result.data.data;
      } else {
        return null;
      }
    },
  },
};
</script>

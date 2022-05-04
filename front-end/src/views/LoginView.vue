<template>
  <v-container fill-height fluid>
    <v-row align="center" justify="center">
      <v-col>
        <v-card class="mx-auto" max-width="344">
          <v-card-text>
            <p class="text-h4 text--primary">Login</p>
            <v-text-field
              label="Username"
              prepend-inner-icon="mdi-account"
              dense
              filled
              v-model="username"
            ></v-text-field>
            <v-text-field
              label="Password"
              type="password"
              prepend-inner-icon="mdi-key"
              dense
              filled
              v-model="password"
            ></v-text-field>
            <v-divider />
          </v-card-text>
          <v-card-actions>
            <v-col class="text-center">
              <v-btn color="cyan accent-4" dark @click="signUp">Sign Up</v-btn>
            </v-col>
            <v-col class="text-center">
              <v-btn color="teal accent-4" dark @click="login">Login</v-btn>
            </v-col>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import store from "../store";

export default {
  name: "LoginView",
  components: {
    // HelloWorld,
  },
  data() {
    return {
      username: "wallstreetwolf",
      password: "game$top",
    };
  },
  mounted() {},
  methods: {
    login() {
      console.log(`LOGIN with ${this.username}, ${this.password}`);
      axios
        .post(
          "https://accounts.omni-trade.xyz/account/userLogon",
          `${this.username}#${this.password}`,
          {
            headers: {
              "Content-Type": "text/plain",
            },
          }
        )
        .then((response) => console.log(response));
    },
    signUp() {
      console.log(`SIGN UP with ${this.username}, ${this.password}`);

      store.commit("increment");
      console.log(store.state.count); // -> 1
    },
  },
};
</script>

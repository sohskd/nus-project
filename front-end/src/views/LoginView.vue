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
import { mapMutations } from "vuex";

export default {
  name: "LoginView",
  components: {
    // HelloWorld,
  },
  data() {
    return {
      username: "",
      password: "",
    };
  },
  mounted() {},
  methods: {
    ...mapMutations({
      saveUserData: "saveUserState",
    }),
    async login() {
      console.log(`LOGIN with ${this.username}, ${this.password}`);
      let result = await axios.post(
        `${process.env.VUE_APP_ENDPOINT_ACCOUNTS}/account/userLogon`,
        `${this.username}#${this.password}`,
        {
          headers: {
            "Content-Type": "text/plain",
          },
        }
      );
      if (result.status === 200 && result.data.success === true) {
        console.log("LOGIN SUCCESS");
        this.saveUserData(result.data.data);
        this.$router.push("/dashboard");
      } else {
        console.log("LOGIN FAILED");
      }
    },
    signUp() {
      this.$router.push("/signup");
    },
  },
};
</script>

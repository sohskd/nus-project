<template>
  <v-container fill-height fluid>
    <v-row align="center" justify="center">
      <v-col>
        <v-card class="mx-auto" max-width="344">
          <v-card-text>
            <p class="text-h4 text--primary">Sign Up</p>
            <v-text-field
              label="E-mail"
              prepend-inner-icon="mdi-email"
              dense
              filled
              v-model="email"
            ></v-text-field>
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
              <v-btn color="cyan accent-4" dark @click="login">Login</v-btn>
            </v-col>
            <v-col class="text-center">
              <v-btn color="teal accent-4" dark @click="signUp">Sign Up</v-btn>
            </v-col>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "SignUpView",
  components: {
    // HelloWorld,
  },
  data() {
    return {
      username: "",
      password: "",
      email: "",
    };
  },
  mounted() {},
  methods: {
    login() {
      this.$router.push("/login");
    },
    async signUp() {
      console.log(
        `SIGN UP with ${this.email}, ${this.username}, ${this.password}`
      );
      let result = await axios.post(
        `${process.env.VUE_APP_ENDPOINT_ACCOUNTS}/account/createNewAccount`,
        {
          email: this.email,
          username: this.username,
          password: this.password,
        }
      );
      if (result.status === 200) {
        console.log("SIGN UP SUCCESS");
        this.$router.push("/");
      }
    },
  },
};
</script>

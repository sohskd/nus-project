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
            ></v-text-field>
            <v-text-field
              label="Password"
              type="password"
              prepend-inner-icon="mdi-key"
              dense
              filled
            ></v-text-field>
            <v-divider />
          </v-card-text>
          <v-card-actions>
            <v-col class="text-right">
              <v-btn color="teal accent-4" dark @click="login">Login</v-btn>
            </v-col>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
// import HelloWorld from "../components/HelloWorld";

export default {
  name: "LoginView",

  components: {
    // HelloWorld,
  },

  methods: {
    login() {
      console.log("LOGIN");
    },
  },
};
</script>

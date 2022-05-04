import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    userData: {
      username: null,
    },
  },
  mutations: {
    saveUserState(state, username) {
      state.userData.username = username;
    },
  },
});

export default store;

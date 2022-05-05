import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    userData: null,
  },
  getters: {
    isLoggedIn: (state) => {
      return state.userData !== null;
    },
    userData: (state) => {
      return state.userData;
    },
  },
  mutations: {
    saveUserState(state, userData) {
      state.userData = userData;
    },
  },
});

export default store;

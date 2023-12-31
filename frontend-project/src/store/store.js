import Vue from "vue";
import Vuex from "vuex";
// jwt_decode is an external library that decodes the JWT token.
import jwtDecode from "jwt-decode";
import axios from 'axios';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userData: null, //store user data decode from token,include userId,username,avatar
    unreadCount: 0, //store unread message count

  },
  mutations: {
    SET_USER_DATA(state, userData) {
      state.userData = userData;
    },
    SET_AVATAR(state, avatarUrl) {
      if (state.userData) {//ensure userData is not null or undefined
        state.userData.avatar = avatarUrl;
      }
    },
    SET_UNREAD_COUNT(state, count) {
      state.unreadCount = count;
    }
  },
  actions: {
    loadUserFromToken({ commit }, token) {
      try {
        const decoded = jwtDecode(token);
        commit("SET_USER_DATA", decoded);
      } catch (e) {
        console.error("Failed to decode token", e);
      }
    },
    logout({ commit }) {
      commit("SET_USER_DATA", null);
    },
    loadUnreadCount({ commit }) {
      axios.get('/notifications/unread_count')
        .then(response => {
          if (response.data.code === 1) {
            commit('SET_UNREAD_COUNT', response.data.data);
          } else {
            console.error(response.data.msg);
          }
        })
        .catch(error => {
          console.error(error);
        });
    }
  },
  getters: {},
});

import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    photos: [],
    filteredPhotos: [],
    favoritePhotos: []
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_PHOTOS(state, data) {
      state.photos = data;
    },
    ADD_PHOTO(state, data) {
      state.photos.unshift(data);
    },
    REMOVE_PHOTO(state, data) {
      let newPhotos = state.photos.filter(photo => photo.id != data.id);
      this.commit("SET_PHOTOS", newPhotos);
      this.commit("SET_FILTERED_PHOTOS", state.user.id)
    },
    SET_FILTERED_PHOTOS(state, user_id) {
      state.filteredPhotos = state.photos.filter(photo => {
        return photo.user_id == user_id;
      });
    },
    UPDATE_USERNAME(state, username) {
      state.user.username = username;
    },
    SET_FAVORITE_PHOTOS(state, data) {
      state.favoritePhotos = data;
    },
    ADD_FAVORITE_PHOTO(state, data) {
      let favoritePhoto = state.photos.find(photo => {
        return photo.id == data;
      });
      state.favoritePhotos.push(favoritePhoto);
    },
    REMOVE_FAVORITE_PHOTO(state, data) {
      let newPhotos = state.favoritePhotos.filter(photo => {
        return photo.id != data
      });
      this.commit("SET_FAVORITE_PHOTOS", newPhotos);
    }
  }
})

<template>
  <div>
    <div 
      class="card" 
      v-for="photo in this.$store.state.favoritePhotos" 
      v-bind:key="photo.photo_id">
      <img 
        class="cardImg"
        v-bind:src="photo.photo_url"
        v-bind:alt="photo.description"
      />
      <div class="container">
        <h3>
          <b class="description">{{ photo.description }}</b>
        </h3>
        <div class="details">
          <p class="username">Posted By: {{ photo.username }}</p>
          <p class="date-posted">Date: {{ photo.date_added }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FavoritesService from '../services/FavoritesService';

export default {
  name: 'favorite-feed',
  created() {
    this.retrieveFavorites();
  },
  methods: {
    retrieveFavorites() {
      FavoritesService.getFavorites(this.$store.state.user.id).then((response) => {
        this.$store.commit("SET_FAVORITE_PHOTOS", response.data)
      })
    }
  }
}
</script>

<style scoped>
.date-posted {
  color: rgba(0, 0, 0, 0.4);
  font-size: 11px;
  margin-top: 0;
  margin-bottom: 0;
}
.username {
  color: rgba(0, 0, 0, 0.9);
  font-size: 14px;
  margin-top: 0;
  margin-bottom: 0;
}
.details {
  background-color: rgba(0, 120, 163, 0.1);
  padding: 2px 16px;
  margin-top: -4px;
}
.description {
  padding: 2px 16px;
  font-size: 21px;
}
.card {
  background-color: white;
}
.details {
  background-color: white;
}
.username {
  font-size: 20px;
}
.date-posted {
  font-size: 18px;
}
</style>
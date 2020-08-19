<template>
  <div>
    <div class="card" v-for="photo in this.$store.state.photos" v-bind:key="photo.photo_id">
      <img v-bind:src="photo.photo_url" v-bind:alt="photo.description" />
      <div class="container">
        <h4>
          <b class="description">{{ photo.description }}</b>
        </h4>
        <div class="details">
          <p class="username">Posted By: {{ photo.username }}</p>
          <p class="date-posted">Date: {{ photo.date_added }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserPhotosService from "@/services/UserPhotosService";

export default {
  name: "login-feed",
  data() {
    return {};
  },
  created() {
    this.retrievePhotos();
  },
  methods: {
    retrievePhotos() {
      UserPhotosService.getPhotos()
        .then((response) => {
          this.$store.commit("SET_PHOTOS", response.data);
        })
        .catch((error) => {
          if (error.response) {
            this.errorMsg =
              "Error. Response received was '" +
              error.response.statusText +
              "'.";
          } else if (error.request) {
            this.errorMsg = "Error. Server could not be reached.";
          } else {
            this.errorMsg = "Error. Request could not be created.";
          }
        });
    },
  },
};
</script>

<style>
.main {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  grid-template-areas:
    ".    .       .          ."
    ". main-cards main-login ."
    ". footer     footer     .";
  grid-gap: 12px;
  margin-top: 50px;
}
.main #login {
  grid-area: main-login;
}
.card {
  
}
.card-container {
  grid-area: main-cards;
  
}
#login form h1 {
  font: 25px;
  text-align: center;
}
footer {
  grid-area: footer;
  text-align: center;
}
.card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  transition: 0.3s;
  border-radius: 5px;
  height: 400px;
  width: 500px;
  margin-bottom: 50px;
  background-color: white;
}
.card:hover {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
}
.card img {
  border-radius: 5px 5px 0 0;
  margin: auto;
  height: 75%;
  width: 100%;
}
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
.details {
  background-color: white;
}
.username {
  font-size: 18px;
}
.date-posted {
  font-size: 18px;
}
</style>
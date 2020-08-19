<template>
  <div class="feed">
    <div class="card" v-for="photo in this.$store.state.filteredPhotos" v-bind:key="photo.photo_id">
      <img v-bind:src="photo.photo_url" v-bind:alt="photo.description" />
      <div class="container">
        <h4>
          <b>{{ photo.description }}</b>
        </h4>
        <input
        class="btn"
          type="button"
          value="Remove Photo"
          v-if="ensureDelete != photo.id "
          v-on:click="ensureDelete = photo.id"
        />
        <div v-if="ensureDelete === photo.id">
          <p class="sureQuestion">Are you sure? This is permanent!</p>
          <input class="btn" type="button" value="Yes" v-on:click="deletePhoto(photo.id)" />
          <input class="btn" type="button" value="Cancel" v-on:click="resetForm" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserPhotosService from "../services/UserPhotosService";

export default {
  name: "user-photo-feed",
  components: {},
  data() {
    return {
      ensureDelete: 0,
    };
  },
  created() {
    this.$store.commit("SET_FILTERED_PHOTOS", this.$store.state.user.id);
  },
  methods: {
    resetForm() {
      this.ensureDelete = 0;
    },
    deletePhoto(photoID) {
      UserPhotosService.deletePhoto(photoID).then((response) => {
        if (response.status === 200) {
          alert("Photo succesfully deleted");
          this.$store.commit("REMOVE_PHOTO", { id: photoID });
        }
      });
      this.ensureDelete = 0;
    },
  },
};
</script>

<style scoped>
.card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  transition: 0.3s;
  border-radius: 5px;
  height: 614px;
  width: 700px;
  margin-bottom: 50px;
}
.card:hover {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
}
.container {
  padding: 2px 16px;
}
.card img {
  border-radius: 5px 5px 0 0;
  margin: auto;
  height: 75%;
  width: 100%;
}
.feed {
  margin-top: 250px;
}
.btn {
  width: 150px;
  height: 40px;
  border: none;
  outline: none;
  box-shadow: -4px 4px 5px 0 #feb361;
  color: #fff;
  font-size: 14px;
  text-shadow: 0 1px rgba(0, 0, 0, 0.4);
  background-color: #00adee;
  border-radius: 3px;
  font-weight: 700;
}
.sureQuestion {
  font-size: 17px;
}
</style>
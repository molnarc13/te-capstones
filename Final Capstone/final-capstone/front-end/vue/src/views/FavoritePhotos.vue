<template>
  <div>
    <div class="main">
      <nav-bar class="nav-bar" />

      <favorite-feed class="feed" />
      <footer>&copy; TE-Gram 2020 - All Rights Reservered.</footer>
    </div>
  </div>
</template>

<script>
import NavBar from "../components/Header";
import FavoriteFeed from "../components/FavoritePhotoFeed";

export default {
  name: "favorites",
  components: {
    NavBar,
    FavoriteFeed,
  },
};
</script>

<style scoped>
.main {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  grid-template-areas:
    "header header header"
    ".      main   ."
    ".      footer    .";
  height: 100vh;
  width: 100vw;
  grid-gap: 12px;
}
.nav-bar {
  grid-area: header;
}

.feed {
  margin-top: 100px;
  grid-area: main;
  display: flex;
  flex-wrap: wrap;
  margin-left: 40px;
 
}
footer {
  grid-area: footer;
  margin-right: 110px;
}
.heartBtn {
  padding: 5px;
  box-shadow: 0 1px 3px 2px;
  border-radius: 25%;
}
</style>
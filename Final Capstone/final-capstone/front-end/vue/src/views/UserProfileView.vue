<template>
    <div class="main">
      <nav-bar class="nav-bar" />
      <update-username class="button" />
      <user-feed class="feed" />
    </div>
</template>

<script>
import NavBar from "../components/Header";
import UpdateUsername from "../components/UpdateUsername";
import UserFeed from "../components/UserPhotoFeed";

export default {
  components: {
    NavBar,
    UpdateUsername,
    UserFeed,
  },
};
</script>

<style scoped>
body {
  margin: 0, 0, 0, 0;
}
.main {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  grid-template-areas:
    "header header header"
    ". username ."
    ". feed .";
  height: 100vh;
  width: 100vw;
  grid-gap: 12px;
}
.nav-bar {
  grid-area: header;
}
.button {
  grid-area: username;
  display: flex;
  justify-content: center;
}
.feed {
  grid-area: feed;
}
.userBtn {
  padding: 5px;
  box-shadow: 0 1px 3px 2px;
  border-radius: 25%;
}
</style>


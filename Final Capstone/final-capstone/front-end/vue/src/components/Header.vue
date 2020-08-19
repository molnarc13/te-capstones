<template>
  <div class="header">
    <router-link v-bind:to="{ name: 'home' }">
      <img id="logo" src="../assets/TeGram-blue-logo.png" alt="TE-Gram Logo" />
    </router-link>
    <!--<input type="text" id="searchUsers" placeholder="search" />-->
    <nav>
      <ul>
        <li>
          <a href="#" v-on:click="(e) => changeView(e, {name: 'home'})">
            <img class="benZona" src="../assets/home.png" alt="home link" width="35" height="35" />
          </a>
        </li>
        <li>
         <a href="#" v-on:click="(e) => changeView(e, {name: 'favorites'})">
            <img class="benZona" src="../assets/heart.png" alt="favorite photos link" width="35" height="35" />
          </a>
        </li>
        <li>
          <a href="#" v-on:click="(e) => changeView(e, {name: 'user-profile'})">
            <img class="benZona" src="../assets/account.png" alt="user profile" width="35" height="35" />
          </a>
        </li>
        <li>
          <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">
            <img class="benZona" src="../assets/logout.png" alt="user profile" width="35" height="35" />
          </router-link>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
export default {
  name: "header",
  data() {
    return {
      clickHome: false, // boolean to hold whether photo was liked or not
      homeImg: "home-empty.png",
    };
  },
  methods: {
    toggleLike() {
      this.clickHome = !this.clickHome;
      this.homeImg = this.clickHome ? "home-filled.png" : "home-empty.png";
    },
    changeView(e, Route) {
      document.querySelectorAll(".benZona").forEach(anon =>  {
          anon.classList.remove("selected")
      })
      e.currentTarget.querySelector(".benZona").classList.add("selected")
      this.$router.push(Route)
    }
  },
};
</script>

<style scoped>
.header {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas: "header-logo header-search header-nav";
  
  margin-bottom: 150px;
  width: 100%;
  background-color: white;
  opacity: 0.7;
  
  border-bottom: .5px solid grey;
  position:fixed; /* fixing the position takes it out of html flow - knows
                   nothing about where to locate itself except by browser
                   coordinates */
  left:0;           /* top left corner should start at leftmost spot */
  top:0;            /* top left corner should start at topmost spot */
  width:100vw;      /* take up the full browser width */
  z-index:200;  /* high z index so other content scrolls underneath */
  height:110px;
}
.header #logo {
  grid-area: header-logo;
  height: 150px;
  width: 150px;
}
.header #searchUsers {
  grid-area: header-search;
  display: flex;
  justify-content: center;
  flex-direction: column;
  margin-left: 75px;
  margin-top: 62px;
  height: 20px;
  width: 200px;
  border-radius: 5px #00adee;
}
nav {
  grid-area: header-nav;
  display: flex;
  justify-content: center;
  flex-direction: column;
}
nav ul {
  display: flex;
  justify-content: space-evenly;
}
nav ul li {
  list-style: none;
}
.selected {
  
  padding: 5px;
  box-shadow: 0 1px 3px 2px;
  border-radius: 25%;
}
/*.heartBtn {
  padding: 5px;
  border-radius: 25%;
  background-color: white;
  box-shadow: 0 1px 3px 2px;
}
.userBtn {
  padding: 5px;
  border-radius: 25%;
  background-color: white;
  box-shadow: 0 1px 3px 2px;
  border-color: #00adee;
}
.logoutBtn {
  padding: 5px;
  border-radius: 25%;
  background-color: white;
  box-shadow: 0 1px 3px 2px;
}*/
</style>
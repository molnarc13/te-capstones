<template>
  <div class="background">
    <div class="home">
      <div class="header">
        <router-link v-bind:to="{ name: 'home' }">
          <img id="logo" src="../assets/TeGram-blue-logo.png" alt="TE-Gram Logo" />
        </router-link>
       <!-- <input type="text" id="searchUsers" placeholder="search" />-->
        <nav>
          <ul>
            <li>
               <a href="#" v-on:click="(e) => changeView(e, {name: 'home'})">
                <img class="benZona" src="../assets/home.png" alt="home link" width="35" height="35" />
               </a>
            </li>
            <li>
              <!-- component to be linked to -->
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
                <img class="logoutBtn" src="../assets/logout.png" alt="user profile" width="35" height="35" />
              </router-link>
            </li>
          </ul>
        </nav>
      </div>

      <photo-feed class="feed" />

      <div>
        <transition name="modal">
          <div v-if="isOpen">
            <div class="overlay" @click.self="isOpen = false;">
              <div class="modal">
                <h1 id="upload-photo">Upload Photo</h1>
                <upload-photo v-on:image-upload="saveUrl" />
                <br />
                <textarea
                  name="description"
                  id="description"
                  placeholder="Description"
                  cols="60"
                  rows="5"
                  v-model="picDesc"
                ></textarea>
                <button
                class="upload-photo-btn"
                  v-bind:disabled="(this.imgUrl.length == 0)"
                  @click="savePhotoTo"
                >Upload Photo</button>
              </div>
            </div>
          </div>
        </transition>
        <button class="uploadBtn" @click="isOpen=!isOpen">
          <img src="../assets/upload-img.png" alt />
        </button>
      </div>

      <footer>&copy; TE-Gram 2020 - All Rights Reservered.</footer>
    </div>
  </div>
</template>

<script>
import UploadPhoto from "./UploadPhoto";
import PhotoFeed from "./PhotoFeed";
import UserPhotosService from "../services/UserPhotosService";
export default {
  name: "home",
  components: {
    UploadPhoto,
    PhotoFeed,
  },
  data: function () {
    return {
      isOpen: false,
      imgUrl: "",
      picDesc: "",
    };
  },

  methods: {
    savePhotoTo() {
      UserPhotosService.uploadPhoto(
        this.$store.state.user.id,
        this.$store.state.user.username,
        this.imgUrl,
        this.picDesc
      )
        .then((response) => {
          this.$store.commit("ADD_PHOTO", response.data);
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
        })
        .finally( () => {
            this.isOpen =false
            this.picDesc = ""
        });
    },
    changeView(e, Route) {
      document.querySelectorAll(".benZona").forEach(anon =>  {
          anon.classList.remove("selected")
      })
      e.currentTarget.querySelector(".benZona").classList.add("selected")
      this.$router.push(Route)
    },
    saveUrl(imgUrl) {
      this.imgUrl = imgUrl;
    },
    // New service method for adding to $store
  },

  imgToBeUploaded: function () {
    console.log({ picDesc: this.picDesc });
  },
};
</script>

<style>
.backgound {
  background-color: #f4f4f4;
  /*background-image: initial;
    background-position-x: initial;
    background-position-y: initial;
    background-size: initial;
    background-repeat-x: initial;
    background-repeat-y: initial;
    background-attachment: initial;
    background-origin: initial;
    background-clip: initial;
    background-color: transparent;
    background-color: #000000E6;*/
}
.home {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  grid-template-areas:
    "header header header"
    ".   main   ."
    ".  footer .";
  height: 100vh;
  width: 100vw;
  grid-gap: 12px;
  padding-top: 80px;
}

.header {
  grid-area: header;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas: "header-logo header-search header-nav";
  margin-bottom: 20px;
  opacity: 0.7;
  position:fixed; /* fixing the position takes it out of html flow - knows
                   nothing about where to locate itself except by browser
                   coordinates */
  left:0;           /* top left corner should start at leftmost spot */
  top:0;            /* top left corner should start at topmost spot */
  width:100vw;      /* take up the full browser width */
  z-index:200;  /* high z index so other content scrolls underneath */
  height:110px;
  background-color: white;
  border-bottom: .5px solid gray;
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

footer {
  grid-area: footer;
  margin-bottom: 20px;
}
.modal {
  width: 500px;
  margin: 0px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 8px 3px;
  transition: all 0.2s ease-in;
  font-family: Arial, Helvetica, sans-serif;
}
.fadeIn-enter {
  opacity: 0;
}
.fadeIn-leave-active {
  opacity: 0;
  transition: all 0.2s step-end;
}
.fadeIn-enter .modal,
.fadeIn-leave-active .modal {
  transform: scale(1.1);
}
.uploadBtn {
  height: 87px;
  width: 87px;
  margin: 10px;
  font-size: 1.2rem;
  background-color: whitesmoke;
  border-radius: 50%;
  position: fixed;
  right: 10px;
  bottom: -4px;
  box-shadow: 0 1px 3px 2px;
}
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: #00000094;
  z-index: 999;
  transition: opacity 0.2s ease;
}
#upload-photo {
  text-align: center;
}
/*.submitBtn {
  width: 250px;
  height: 45px;
  border: none;
  outline: none;
  box-shadow: -4px 4px 5px 0 #feb361;
  color: #fff;
  font-size: 14px;
  text-shadow: 0 1px rgba(0, 0, 0, 0.4);
  background-color: #00adee;
  border-radius: 3px;
  font-weight: 700;
  margin-left: 25%;
  margin-top: 13px;
}*/
.upload-photo-btn {
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
.submitBtn:hover {
  background-color: #0b7ca5;
  cursor: pointer;
}
.submitBtn:active {
  margin-left: -4px;
  margin-bottom: -4px;
  padding-top: 2px;
  box-shadow: none;
}
.feed {
  grid-area: main;
  margin-top: 100px;
}
.selected {
  
  padding: 5px;
  box-shadow: 0 1px 3px 2px;
  border-radius: 25%;
}

</style>
<template>
  <div class="main">
    <div id="login" class="text-center">
      <form class="form-signin" @submit.prevent="login">
        <img id="logo" src="../assets/TeGram-blue-logo.png" alt="TE-Gram Logo" />
        <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
        <div
          class="alert alert-danger"
          role="alert"
          v-if="invalidCredentials"
        >Invalid username and password!</div>
        <div
          class="alert alert-success"
          role="alert"
          v-if="this.$route.query.registration"
        >Thank you for registering, please sign in.</div> <br>
        <label for="username" class="sr-only"></label>
        <input
          type="text"
          id="username"
          class="form-control"
          placeholder="Username"
          v-model="user.username"
          required
          autofocus
        />
        <br />
        <br />
        <label for="password" class="sr-only"></label>
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="user.password"
          required
        />
        <br />
        <br />
        <button type="submit">Sign in</button>
        <br />
        <br />
      </form>
      <div class="need-account">
        <router-link :to="{ name: 'register' }">Need an account?</router-link>
      </div>
    </div>
    <div class="card-container">
      <login-feed />
    </div>
    <footer>&copy; TE-Gram 2020 - All Rights Reservered.</footer>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import LoginFeed from "../components/LoginFeed";

export default {
  name: "login",
  components: {
    LoginFeed,
  },
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>
<style  scoped>
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
.card-container {
  flex-direction: column;
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
  flex-wrap: nowrap;
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
#login {
  padding-bottom: 110px;
  height: 350px;
  width: 350px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  text-align: center;
  margin-top: 100px;
  margin-left: 30px;
  position: sticky;
  top: 50px;
  background-color: white;
}
.form-signin {
  background-color: white;
}
.need-account {
  background-color: white;
}
</style>

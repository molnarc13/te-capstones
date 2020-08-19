<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <img id="logo" src="../assets/TeGram-blue-logo.png" alt="TE-Gram Logo" />
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="registrationErrors"
      >{{ registrationErrorMsg }}</div>
      <!--<label for="username" class="sr-only">Username</label><br>-->
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
      <!--<label for="password" class="sr-only">Password</label><br>-->
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
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <br />
      <br />

      <button id="btn" class="btn btn-lg btn-primary btn-block" type="submit">Create Account</button>
      <br />
      <br />
      <router-link :to="{ name: 'login' }">Have an account?</router-link>
    </form>
    <!-- <footer>
      &copy; TE-Gram 2020 - All Rights Reservered.
    </footer>-->
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style scoped>
.register {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  grid-template-areas:
    ". form-register ."
    ". footer        .";
  grid-gap: 12px;
  margin-top: 50px;
}
.form-register {
  grid-area: form-register;
  padding-bottom: 110px;
  height: 550px;
  width: 350px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  text-align: center;
  position: absolute;
  left: 50%;
  margin-left: -175px;
  margin-top: 20px;
  background-color: white;
}
input {
  height: 30px;
  width: 275px;
  text-align: left;
  background: rgb(245, 245, 245);
  border-radius: 3px;
}
#btn {
  margin-top: 27px;
  margin-bottom: 30px;
}
/*.footer {
  grid-area: footer;
  text-align: center;  
}*/
</style>

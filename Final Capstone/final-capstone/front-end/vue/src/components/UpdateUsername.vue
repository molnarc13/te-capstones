<template>
  <div class="feed">
    <button
      v-if="showField === false"
      v-on:click.prevent="showField = true"
      class="changeUserName"
    >Change Username</button>
    <div class="username-form" v-if="showField === true"> 
     <!-- <p>Current username: {{ this.$store.state.user.username }}</p>-->
      <label class="label" for="updated_username">Current username: {{ this.$store.state.user.username }}</label> 
      <input
        type="text"
        name="new username"
        id="updated_username"
        placeholder="New Username"
        v-model="newUsername.username"
        class="userInput"
      /> <br>
      <input
        type="button"
        value="Submit Change"
        v-if="(newUsername.username.length != 0)"
        v-on:click="saveUsername"
        class="changeUserName"
      />
      <!--<button v-bind:disabled="(newUsername.length === 0)" value="Submit Change"  @click="changeUsername()">Submit Change</button>-->
      <input type="button" value="Cancel" v-on:click="resetForm" class="changeUserName" />
    </div>
  </div>
</template>

<script>
import UserService from "../services/UserService";

export default {
  name: "update-username",
  data() {
    return {
      showField: false,
      newUsername: {
        username: ""
      }
    };
  },
  methods: {
    resetForm() {
      this.showField = false;
      this.newUsername = "";
    },
    saveUsername() {
      UserService.updateUsername(this.$store.state.user.id, this.newUsername).then(response => {
        this.$store.commit("UPDATE_USERNAME", response.data);
      });
      this.resetForm();
    },
    changeUsername() {
      this.$emit("changeUsername", this.newUsername);
    },
  },
};
</script>

<style scoped>
.changeUserName {
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
  /*left: 50%;
  transform: translate(-50%);
  margin-top: 80px;*/
  margin-right: 15px;
  margin-bottom: 20px;
}
.label{
  padding-top: 15px;
  font-size: x-large;
 /* left: 50%;
  transform: translate(-50%);*/
}
.changeUserName:hover {
  background-color: #0b7ca5;
  cursor: pointer;
}
.changeUserName:active {
  padding-top: 2px;
  box-shadow: none;
}
.userInput {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 30px;
  width: 306px;
  text-align: left;
  background: rgb(245, 245, 245);
  border-radius: 3px;
  /*left: 50%;
  transform: translate(-50%);*/
}

.feed {
  margin-top: 100px;
}
</style>
<template>
  <div id="app">
    <!--
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>-->
    <router-view />
  </div>
</template>
<script>
export default {
  name: "app",
  components: {},
};
</script>
<style>
body {
  font-family: Arial, Helvetica, sans-serif;
  display: inline-block;
  background-color: #F6F6F6;
}
</style>
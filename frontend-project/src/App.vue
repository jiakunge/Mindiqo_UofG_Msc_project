<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
    };
  },
  methods: {
  },
  created() {
    const userToken = localStorage.getItem('userToken');
    if(userToken){
      this.$store.dispatch('loadUserFromToken', userToken);
    }
  },
};
</script>

<style>
body {
  padding: 0 !important;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

</style>

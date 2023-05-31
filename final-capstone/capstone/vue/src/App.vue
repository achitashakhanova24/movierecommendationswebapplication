<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>

    </div>
    <router-view />
    <footer>
        © COPYRIGHT 2023 - CinemaCrush.com
    </footer>
  </div>
</template>

<style scoped>
  #app{
    background-image: url('../images/Posters.jpg');
    background-size: cover;
    display: grid;
    grid-template-areas: 
    "nav"
    "router"
    'footer';
  }

  #nav{
    grid-area: 'nav';
  }

  router-view{
    grid-area: 'router';
  }

  footer{
    grid-area: 'footer';
  }

  footer{
    text-align: center;
    margin-top: 65vh;
    
  }
  

</style>

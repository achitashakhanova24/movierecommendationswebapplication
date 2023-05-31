<template>
  <div id="app">
    <head>
      <link rel="preconnect" href="https://fonts.googleapis.com">
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
      <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@600&family=Lobster&family=Paytone+One&display=swap" rel="stylesheet">
    </head>
    <div id="nav">
      <router-link id="home-link" v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
    <footer>
      Copyright 2023
    </footer>
  </div>
</template>

<style>
  html {
    background-image: url('../images/poster.jpg');
    background-size: 100%;
    font-family: 'Paytone One', sans-serif;
  }

  #nav {
    background-color: rgba(0, 0, 0, 0.9);
    height: 2.5rem;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    font-size: 2em;
  }

  footer {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 1.5rem;
    background-color: rgba(0, 0, 0, 0.9);
    color: whitesmoke;
  }

  router-view {
    background-color: rgba(112, 128, 144, .8);
  }

  #home-link {
    text-decoration: none;
    color: rgb(245,245,245);
  }
  #home-link:hover {
    color: rgba(245, 245, 245, 0.788);
  }
</style>

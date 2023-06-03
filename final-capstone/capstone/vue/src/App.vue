<template>
  <div id="app">
    <head>
      <link rel="preconnect" href="https://fonts.googleapis.com">
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
      <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@600&family=Lobster&family=Paytone+One&display=swap" rel="stylesheet">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
    </head>
    <div id="nav">
      
<div class="ellipsis-menu">
    <div class="menu-icon" @click="toggleMenu"><i class="fas fa-ellipsis-h"></i></div>
    <ul class="menu" v-if="isMenuOpen">
      <li><router-link id="profile-link" v-bind:to="{ name: 'profile'}">Profile</router-link></li>
      <li><router-link id="favorites-link" v-bind:to="{ name: 'favorites'}">Favorites</router-link></li>
      <li><router-link id="recommendations-link" v-bind:to="{ name: 'recommendations'}">Recommendations</router-link></li> 
      <li><router-link id="about-link" v-bind:to="{ name: 'about-us'}">About Us</router-link> </li>
      <li><router-link id="home-link" v-bind:to="{ name: 'home' }"><i class="fas fa-home"></i></router-link> </li>
      
    </ul>
  </div>

      <router-link id="home-link" v-bind:to="{ name: 'home' }"><i class="fas fa-home"></i></router-link>&nbsp;|&nbsp;
      <router-link id="logout-link" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>

    </div>
    <router-view />
    <footer>
      &copy; 2023 - CinemaCrush.com
    </footer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isMenuOpen: false
    };
  },
  methods: {
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
    }
  }
};
</script>


<style>
.ellipsis-menu {
  position: relative;
  display: inline-block;
}

.menu-icon {
  width: 50px;
  height: 20px;
  margin-right: 25px;
  margin-left: 10px;
  border-radius: 50%;
  padding: 0.2px;
  cursor: pointer;
  color: white;
}


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
    font-family: 'Paytone One', sans-serif;

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

  .menu {
  display: none;
  /* position: absolute; */
  top: 100%;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 10px;
  list-style-type: none;
  margin: 0;
  border-radius: 5px;
  font-size: 20px;
}
.menu li {
  margin-bottom: 10px;
}
.menu a {
  text-decoration: none;
  color: rgb(243, 243, 243);
}
.ellipsis-menu:hover .menu {
  display: block;
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
 #logout-link{
   text-decoration: none;
 }
</style>
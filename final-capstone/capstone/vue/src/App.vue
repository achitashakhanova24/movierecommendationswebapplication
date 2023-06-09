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
      <br>
      <li><router-link id="home-link" v-bind:to="{ name: 'home' }"><i class="fas fa-home"></i></router-link> </li>
      <li><router-link id="browse-link" v-bind:to="{name: 'movie-list'}" v-if="$store.state.token != ''">Browse Movies</router-link></li>
      <li><router-link id="favorites-link" v-bind:to="{ name: 'my-lists'}" v-if="$store.state.token != ''">My Lists</router-link></li>
      <li><router-link id="recommendations-link" v-bind:to="{ name: 'recommendations'}" v-if="$store.state.token != ''">Recommendations</router-link></li> 
      <li><router-link id="profile-link" v-bind:to="{ name: 'profile'}" v-if="$store.state.token != ''">Profile</router-link></li>
      <li><router-link id="about-link" v-bind:to="{ name: 'about-us'}">About Us</router-link> </li>
      <li><router-link id="logout-link" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link></li>

    </ul>
  </div>
  <div class="title">CinemaCrush</div>

          <div class="vignette"> </div>

      <!-- <router-link id="home-link" v-bind:to="{ name: 'home' }"><i class="fas fa-home"></i></router-link>&nbsp;|&nbsp; -->
      <!-- <router-link id="logout-link" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link> -->

    </div>
    <LoadingScreen v-if="loading" />
    <div v-else>
    <router-view/>
    <footer>
      &copy; 2023 - CinemaCrush.com
    </footer>
  </div>
  </div>
</template>

<script>

import LoadingScreen from './components/Loading.vue'

export default {
  data() {
    return {
      isMenuOpen: false,
      loading: true
    };
  },
  methods: {
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
    }
  },
mounted() {
    setTimeout(() => {
      this.loading = false;
    }, 2000); // Display loading screen for 2 seconds
  },
  components: {
    LoadingScreen
  }
};

</script>


<style>
.title {
  position: absolute;
    top: 0;
    right: 10px;
    height: 100%;
    display: flex;
    align-items: center;
    color: whitesmoke;
    font-weight: bold;
    font-size: 20px;
    z-index: 9999;
}
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
    z-index: 9999;
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
  background-color: black;
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
 #about-link {
   text-decoration: none;
 }
 #about-link:hover {
   color: rgba(245, 245, 245, 0.788);
 }

 #profile-link {
   text-decoration: none;
 }
 #profile-link:hover {
   color: rgba(245, 245, 245, 0.788);
 }
 #favorites-link {
   text-decoration: none;
 }
 #favorites-link:hover {
   color: rgba(245, 245, 245, 0.788);
 }
 #recommendations-link {
   text-decoration: none;
 }
 #recommendations-link:hover {
   color: rgba(245, 245, 245, 0.788);
 }
 #browse-link {
   text-decoration: none;
 }
 #browse-link:hover {
   color: rgba(245, 245, 245, 0.788);
 }
 #logout-link{
   text-decoration: none;
 }
 #logout-link:hover {
   color: red
 }

 router-view {
   background-color: transparent;
 }
 .vignette{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 7px;
  background-image: radial-gradient(
    circle at center,
    transparent 0%,
    rgba(0, 0, 0, 0.9) 60%
  );
  z-index: -1;
}
</style>
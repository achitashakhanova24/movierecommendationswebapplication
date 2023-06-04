<template>
  <div class="home">
    <head>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <h1>CinemaCrush</h1>
    <router-link v-bind:to="{name: 'movie-list'}">Browse Movies</router-link>
    <p> Swipe Right for Your Movie Night!</p>
    <div class="poster-container">
      <div>
        <b-carousel
          id="carousel"
          v-model="slide"
          :interval="4000"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333;"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
        >
          <b-carousel-slide v-for="movie in newReleases" v-bind:key="movie.id" class="carousel-item" :img-src="movie.posterPath" :img-alt="movie.title">
          </b-carousel-slide>
        </b-carousel>
      </div>   
    </div>  
  </div>
</template>
<script src="js/jquery-1.7.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script>
import movieService from "../services/MovieService";

export default {
  name: "home",
  data() {
    return {
      newReleases: [],
      slide: 0,
      sliding: null
      
    }
  },
  methods: {
    goToAbout(){

    },
    onSlideStart(slide) {
        this.sliding = true
      },
    onSlideEnd(slide) {
        this.sliding = false
    }
  },
  created() {
    movieService.home().then(response => {
      this.newReleases = response.data.filter(movie => {
        return movie.posterPath != "null";
      });
      this.newReleases.forEach(movie => {
        movie.posterPath = "https://image.tmdb.org/t/p/w342/" + movie.posterPath;
      })
    })
  }
};
</script>

<style scoped>
.home{
  display: flex;
  flex-direction: column;
  align-items: center;
  position: fixed;
  z-index: -1;
  background-color: rgba(0, 0, 0, 0.5);
  width: 100%;
  height: 100%;
  border-radius: 7px;
  margin-top: 30px;
  margin-left: -8px;
  font-family: 'Paytone One', sans-serif;
}
.content{
  color: #ffffff;
  text-align: center;
} 
p{
  color:#ffffff;
}
  h1{
    color: #ffffff;
    font-size: 48px;
  }

  /* .poster-container {
    display: flex;
    width: 300px;
  } */

  .poster1 {
    width: 300px;
    height: auto;
    margin-right: 20px;
  }

  .poster2 {
    width: 300px;
    height: auto;
    margin-right: 20px;
  }

  .poster3 {
    width: 300px;
    height: auto;
  }
  b-carousel {
    font-family: 'Paytone One', sans-serif;
  }

</style>
<template>
  <html>
    <div class="listPage" @click="handleClick">
      <head>
        <link
          rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous"
        />
      </head>
      <br>
      <h1>My_Lists</h1>
      <h3 class="favoritesHeader">
        FAVORITE MOVIES
      </h3>
      <div class="favorites">
      <b-card
        v-for="movie in favoriteMovies"
        :key="movie.movieId"
        :title="'#' + movie.rank + ' - ' + movie.title"
        :img-src="movie.backdropPath"
        img-onerror="this.src ='images/default-backdrop.jpg'"
        img-alt="Image unavailable"
        img-top
        tag="article"
        style="max-width: 20rem;"
        class="mb-2"
      >
      <b-card-text>
        <b-button id="details-button" v-on:click="getMovieDetails(movie)" variant="primary">Details</b-button>
        <b-button v-on:click="updateWatchedStatus(movie.movieId)" v-if="!movie.watched" id="not-watched-button"><b-icon style="color: black; border-radius: 10px;" icon="binoculars" aria-label="Help"></b-icon></b-button>
        <b-button v-on:click="updateWatchedStatus(movie.movieId)" v-if="movie.watched" id="watched-button"><b-icon style="color: black; border-radius: 10px;" icon="binoculars-fill" aria-label="Help"></b-icon></b-button>
        <br><br><br>
        {{movie.description}}
      </b-card-text>
      <span class="favorites-buttons">
          <b-button id="update-rank-button" v-on:click="updateWatchedStatus(movie.movieId)" variant="primary">Change Rank</b-button>
      </span>
  </b-card>
  
</div>
      <h3 class="watchListHeader">
          WATCH LIST
        </h3>
      <div class="watchListCarousel">
        
        <b-carousel
          id="carousel-1"
          v-model="slide1"
          :interval="4000"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333"
          @sliding-start="onSlideStart1"
          @sliding-end="onSlideEnd1"
        >
          <b-carousel-slide
            v-for="movie in watchList"
            v-bind:key="movie.movieId"
            class="carousel-item"
            :img-src="movie.posterPath"
            :img-alt="movie.title"
          >
          </b-carousel-slide>
        </b-carousel>
      </div>
      <h3 class="seenListHeader">
        SEEN LIST
      </h3>
      <div class="seenListCarousel">
        <b-carousel
          id="carousel-2"
          v-model="slide2"
          :interval="4000"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333"
          @sliding-start="onSlideStart2"
          @sliding-end="onSlideEnd2"
        >
          <b-carousel-slide
            v-for="movie in seenList"
            v-bind:key="movie.id"
            class="carousel-item"
            :img-src="movie.posterPath"
            :img-alt="movie.title"
          >
          </b-carousel-slide>
        </b-carousel>
      </div>
    </div>
  </html>
</template>
<script src="js/jquery-1.7.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script>
import movieService from '../services/MovieService'
export default {
    data(){
        return {
            favoriteMovies: [],
            watchList: [],
            seenList: [],
            slide1: 0,
            sliding1: null,
            slide2: 0,
            sliding2: null
        }
    },
    methods: {
      handleClick(){

      },
      onSlideStart1(slide) {
        this.sliding = true
      },
      onSlideEnd1(slide) {
        this.sliding = false
      },
      onSlideStart2(slide) {
        this.sliding = true
      },
      onSlideEnd2(slide) {
        this.sliding = false
      },
      updateWatchedStatus(movieId){
        movieService.editWatchedStatus(movieId).then(response => {
          this.favoriteMovies.forEach(movie => {
            if(movie.movieId === response.data.movieId) {
              movie.watched = response.data.watched;
              if(movie.posterPath != "null") {
                movie.posterPath = "https://image.tmdb.org/t/p/w780/" + movie.posterPath;
              }
              if(movie.watched) {
                this.seenList.push(movie);
                this.watchList = this.watchList.filter(currentMovie => {
                  return currentMovie.movieId != movie.movieId;
                })
              }
              else {
                this.watchList.push(movie);
                this.seenList = this.seenList.filter(currentMovie => {
                  return currentMovie.movieId != movie.movieId;
                })
              }
              console.log(this.watchList);
            }
          })
        })
      },
      getMovieDetails(item) {
        this.$router.push({
          name:'movie-details', params:{
            id: item.movieId,
            rating: item.rating,
            genre: item.genres,
            releaseDate: item.releaseDate,
            description: item.description,
          }
        });
      }
    },
    created(){
      movieService.getWatchList().then(response => {
        response.data.forEach(movie => {
          if(movie.posterPath != "null") {
            movie.posterPath = "https://image.tmdb.org/t/p/w780/" + movie.posterPath;
          }
          this.watchList.push(movie);
      })
    }),
      movieService.getSeenList().then(response => {
        response.data.forEach(movie => {
          if(movie.posterPath != "null") {
            movie.posterPath = "https://image.tmdb.org/t/p/w780/" + movie.posterPath;
          }
          this.seenList.push(movie);
      })
    }),
    movieService.getFavorites().then(response => {
      const size = response.data.length > 10 ? 10 : response.data.length;
      console.log(response.data.length)
      this.favoriteMovies = response.data.filter(movie => {
          return movie.rank != 0;
      });
      this.favoriteMovies.sort((a, b) => {
        return a.rank < b.rank && a.rank != 0 ? -1 : 1;
      })
      this.favoriteMovies.forEach(movie => {
        if(movie.backdropPath != "null") {
           movie.backdropPath = "https://image.tmdb.org/t/p/w780/" + movie.backdropPath;
        }
      })
      console.log(this.favoriteMovies)
    })
  }
}
</script>

<style scoped>
.listPage {
  display: grid;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: -1;
  background-color: rgba(0, 0, 0, 0.5);
  width: 100%;
  height: 100%;
  border-radius: 7px;
  margin-top: 30px;
  margin-left: -8px;
  grid-template-areas:
    "header"
    "description"
    "favoriteMovies"
    "favoriteMovies"
    "watchList"
    "seenList";
  grid-template-rows: 5fr 0fr 3fr 3fr 3fr;
  font-family: 'Paytone One', sans-serif;
  overflow-y: auto;
}
/* .content{
    color: white; */

h3 {
  grid-area: "description";
  color: white;
  text-align: center;
  background-color: rgba(255, 49, 49, 0.644);
  margin-top: 70px;
  margin-bottom: 70px;
  height: 65px;
  /* width: 500px; */
  font-size: 3rem;
}
h1 {
  grid-area: "header";
  color: rgb(255, 49, 49);
  text-align: center;
  font-family: 'Showtime';
  font-size: 5rem;
  margin-top: 20px;
  
}

.watchListHeader {
  margin-top: 60px;
}

.favorites {
  grid-area: "favoriteMovies";
  display: flex;
  gap: 10px;
  height: 100%;
  color: black;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-evenly;
}

/* .favorites-buttons > button {
  position: absolute;
  bottom: 0;
  left: 5px;
} */

.card-text {
  color: black;
}

article {
  height: 700px;
}

.watchList {
  grid-area: "watchList";
}

.seenList {
  grid-area: "seenList";
}

.favorites-buttons {
  display: flex;
  position: absolute;
  bottom: 0;
  gap: 5px;
}

#carousel-1, #carousel-2 {
  height: 700px;
  width: 500px;
}

.watchListCarousel, .seenListCarousel{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
img:before {
  background-image: "../../images/default-backdrop.jpg";
}

b-button {
  width: 10px;
}

#not-watched-button {
  background-color: white;
  border-color: white;
}

#not-watched-button:hover {
  background-color: rgba(255, 0, 0, 0.315);
}
#watched-button {
  background-color: rgba(255, 0, 0, 0.315);
  border-color: white;
}

#watched-button:hover {
  background-color: white;
}
</style>
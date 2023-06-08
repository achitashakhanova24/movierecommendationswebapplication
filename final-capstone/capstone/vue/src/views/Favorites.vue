<template>
  <html>
    <div class="favoritePage" @click="handleClick">
      <head>
        <link
          rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous"
        />
      </head>
      <br>
      <h1>My Lists</h1>
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
        blank-color="#777"
      >
      <b-card-text>
        <b-button id="details-button" v-on:click="getMovieDetails(movie)" variant="primary">Details</b-button>
        <br><br><br>
        {{movie.description}}
        <br>
        Watched: {{movie.watched}}
      </b-card-text>
      <span class="favorites-buttons">
          <b-button id="update-watched-button" v-on:click="updateWatchedStatus(movie.movieId)" variant="primary">Mark Watched</b-button>
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
          v-model="slide"
          :interval="4000"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
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
          v-model="slide"
          :interval="4000"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
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
            slide: 0,
            sliding: null
        }
    },
    methods: {
      handleClick(){

      },
      onSlideStart(slide) {
        this.sliding = true
      },
      onSlideEnd(slide) {
        this.sliding = false
      },
      updateWatchedStatus(movieId){
        movieService.editWatchedStatus(movieId).then(response => {
          this.favoriteMovies.forEach(movie => {
            if(movie.movieId === response.data.movieId) {
              movie.watched = response.data.watched;
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
      const size = response.data.length > 10 ? 10 : response.data.size();
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

      //  response.data.forEach(movie => {
      //    if(movie.backdropPath != "null") {
      //      movie.backdropPath = "https://image.tmdb.org/t/p/w780/" + movie.backdropPath;
      //   }
      //   this.favoriteMovies.push(movie);
      // })
      // this.favoriteMovies.sort((a, b) => {
      //   return a.rank < b.rank ? -1 : 1;
      // })
      console.log(this.favoriteMovies)
    })
  }
}
</script>

<style scoped>
.favoritePage {
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
</style>
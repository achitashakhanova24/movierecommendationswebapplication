<template>
  <html>
    <div>
      <Loading v-if="loading" />
    
    <!-- <div class="vignette"> -->
    <div class="detailsPage">
      <head>
        <link
          rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous"
        />
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16">
  <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
</svg>
      </head>
      
        <b-card 
        :img-src="movie.posterPath" 
        img-left class="mb-3"
        :title="movie.title"
        :caption="movie.rank"
        >
          <b-card-text>
            <b-button variant="light" v-on:click="changeFavoritedStatus(movie.movieId)" v-if="!movie.favorited" id="not-favorited-button"><b-icon style="color: red; border-radius: 10px;" icon="emoji-heart-eyes" aria-label="Help"></b-icon></b-button>	
            <b-button variant="danger" v-on:click="changeFavoritedStatus(movie.movieId)" v-if="movie.favorited" id="favorited-button"><b-icon style="color: red; border-radius: 10px;" icon="emoji-heart-eyes-fill" aria-label="Help"></b-icon></b-button>	
            <span style="font-size: 1.25rem;">&emsp;&emsp;&emsp;Already seen?</span>&nbsp;
              <b-button v-on:click="changeWatchedStatus(movie.movieId)" v-if="!movie.watched" id="not-watched-button"><b-icon style="color: black; border-radius: 10px;" icon="binoculars" aria-label="Help"></b-icon></b-button>
              <b-button v-on:click="changeWatchedStatus(movie.movieId)" v-if="movie.watched" id="watched-button"><b-icon style="color: black; border-radius: 10px;" icon="binoculars-fill" aria-label="Help"></b-icon></b-button>
            <br><br>
            <i style="font-size: 1.25rem; color: gray"> Favorites Rank: {{movie.rank}} &emsp; Average Review Score: {{movie.rating}}</i>
            <br><br><br>
            Release date: {{movie.releaseDate}}
            <br><br>
            {{movie.description}}
          </b-card-text>
        </b-card>

   <!-- <h1>{{}}</h1>
   <h2>{{movie.rating}}/10</h2>
   <h3>Release Date: {{this.$route.params.releaseDate}}</h3>
   <p>{{this.$route.params.description}}</p>
   <span>{{movie.rank}}</span> -->
   
        </div>
    </div>
    <!-- </div> -->
  </html>
</template>

<script>
import Loading from "../components/Loading.vue"
import movieService from "../services/MovieService";

export default {
  name: "movie-details",
  data() {
    return {
      movie: null,
      loading: true

    };
  },
  created() {
    const movieId = this.$route.params.id;
    console.log(movieId);
    movieService.getMovie(movieId).then(response => {
      this.movie = response.data;
      if(this.movie.rank === 0) {
        this.movie.rank = "Unranked"
      }
      if(this.movie.posterPath != "null") {
            this.movie.posterPath = "https://image.tmdb.org/t/p/w500/" + this.movie.posterPath;
      }
    })
    .catch(error => {
      console.error(error);
    });
    console.log(this.movie)
  },
  methods: {
    changeFavoritedStatus(movieId){
      movieService.editFavoritedStatus(movieId).then(response => {
        this.movie = response.data;
        if(this.movie.rank === 0) {
          this.movie.rank = "Unranked"
        }
        if(this.movie.posterPath != "null") {
          this.movie.posterPath = "https://image.tmdb.org/t/p/w500/" + this.movie.posterPath;
        }
      })
      .catch(error => {
        console.error(error);
      });
    },
    changeWatchedStatus(movieId) {
      movieService.editWatchedStatus(movieId).then(response => {
        this.movie = response.data;
        if(this.movie.rank === 0) {
          this.movie.rank = "Unranked"
        }
        if(this.movie.posterPath != "null") {
          this.movie.posterPath = "https://image.tmdb.org/t/p/w500/" + this.movie.posterPath;
        }
      });
    }
  },
  mounted() {
    setTimeout(() => {
      this.loading = false;
    }, 1500);
  },
  components: {
    Loading
  },
};
</script>

<style scoped>
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

.detailsPage{
  margin-top: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  /* background-color: rgba(0, 0, 0, 0.7); */
  border-radius: 7px;
  font-family: 'Paytone One', sans-serif;
}

.card-text {
  font-size: 1.75rem;
  color: #000;
}

h1{
    color: white;
}
h2{
  color: white;
}
h3 {
  color: white
}
p {
    color: white;
}


#favorited-button {
  background-color: rgba(255, 0, 0, 0.315);
}

#favorited-button:hover {
  background-color: white;
}
#not-favorited-button {
  background-color: white;
}

#not-favorited-button:hover {
  background-color: rgba(255, 0, 0, 0.315);
}
#not-watched-button {
  background-color: white;
}

#not-watched-button:hover {
  background-color: rgba(255, 0, 0, 0.315);
}
#watched-button {
  background-color: rgba(255, 0, 0, 0.315);
}

#watched-button:hover {
  background-color: white;
}
</style>
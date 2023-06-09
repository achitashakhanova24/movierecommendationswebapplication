<template>
    <html>
        <div class="main-div">
            
            <head>
                <link
                rel="stylesheet"
                href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
                integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
                crossorigin="anonymous"
                />
            </head>
            <h1>My_Favorites</h1>
            <div class="favorites">
                <div class="vignette"></div>
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
                        <div>
                            <b-button :class="`${movie.movieId}`" id="show-btn" @click="$bvModal.show(`${movie.movieId}`)" v-on:click="setTargetFavorite(movie.movieId)">Change Rank</b-button>

                            <b-modal hide-backdrop :id="`${movie.movieId}`" hide-footer >
                            <template #modal-title>
                                Choose a movie to swap ranks with #{{movie.rank}}&nbsp;{{movie.title}}: 
                            </template>
                            <div class="d-block text-center">
                                <div class="modal-movie-info" v-for="movie in favoriteMovies" :key="movie.movieId">
                                <a variant="light" id="update-rank-button" >{{movie.title}}</a>&emsp;&emsp;<b-button id="update-rank-button" v-on:click="updateRank(targetFavorite, movie.rank)" variant="secondary">{{movie.rank}}</b-button>
                                </div>
                            </div>
                            <b-button class="mt-3" block @click="$bvModal.hide(`${movie.movieId}`)">Close Me</b-button>
                            </b-modal>
                        </div>
                    </span>
                </b-card>
            
            </div>
        </div>
    </html>
</template>
<script src="js/jquery-1.7.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script>
import movieService from '../services/MovieService';
export default {
    data() {
        return {
            favoriteMovies: []
        }
    },
    created() {
        movieService.getFavorites().then(response => {
            this.favoriteMovies = response.data;
            let rankedMovies = response.data.filter(movie => {
                return movie.rank != 0;
            })
            let unrankedMovies = response.data.filter(movie => {
                return movie.rank === 0;
            })
            rankedMovies.sort((a, b) => {
                return a.rank < b.rank ? -1 : 1;
            })
            this.favoriteMovies = rankedMovies;
            unrankedMovies.forEach(movie => {
                this.favoriteMovies.push(movie);
            })
            this.favoriteMovies.forEach(movie => {
                if(movie.backdropPath != "null") {
                    movie.backdropPath = "https://image.tmdb.org/t/p/w780/" + movie.backdropPath;
                }
                if(movie.rank === 0) {
                    movie.rank = "Unranked";
                }
            })
            console.log(this.favoriteMovies)
        })
    },
    methods: {
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
      },
      updateWatchedStatus(movieId){
        movieService.editWatchedStatus(movieId).then(response => {
          this.favoriteMovies.forEach(movie => {
            if(movie.movieId === response.data.movieId) {
              movie.watched = response.data.watched;
              if(movie.backdropPath != "null") {
                    movie.backdropPath = "https://image.tmdb.org/t/p/w780/" + movie.backdropPath;
                }
            }
          })
        })
      },
      updateRank(movieId, rank) {
          if(rank === "Unranked"){         
          rank = 0;
        }
        movieService.updateRank(movieId, rank).then(response => {
          this.favoriteMovies = response.data;
            let rankedMovies = response.data.filter(movie => {
                return movie.rank != 0;
            })
            let unrankedMovies = response.data.filter(movie => {
                return movie.rank === 0;
            })
            rankedMovies.sort((a, b) => {
                return a.rank < b.rank ? -1 : 1;
            })
            this.favoriteMovies = rankedMovies;
            unrankedMovies.forEach(movie => {
                this.favoriteMovies.push(movie);
            })
            this.favoriteMovies.forEach(movie => {
                if(movie.backdropPath != "null") {
                    movie.backdropPath = "https://image.tmdb.org/t/p/w780/" + movie.backdropPath;
                }
                if(movie.rank === 0) {
                    movie.rank = "Unranked";
                }
            })
        })
      },
      setTargetFavorite(movieId) {
        this.targetFavorite = movieId;
      }
    }
}
</script>

<style scoped>
    /* .vignette{
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100vh;
        border-radius: 7px;
        background-image: radial-gradient(
            circle at center,
            transparent 0%,
            rgba(0, 0, 0, 0.9) 60%
        );
        z-index: -1;
        overflow-y: auto;
    } */
    h1{
        font-family: 'Showtime';
        font-size: 4rem;
        color: whitesmoke;
        padding-bottom: 20px;
        height: 100px;
        text-align: center;
        align-items: center;
    }
    .favorites {
        display: flex;
        justify-content: space-evenly;
        flex-wrap: wrap;
        align-items: center;
        font-family: 'Paytone One', sans-serif;
        
    }
    .main-div {
        padding-top: 50px;
        padding-bottom: 50px;
        overflow-y: auto;
        display: flex;
        flex-direction: column;
        position: fixed;
        width: 100%;
        height: 100%;
        background-image: radial-gradient(
            circle at center,
            transparent 0%,
            rgba(0, 0, 0, 0.9) 60%
        );
    }
    .card-text {
        color: black;
    }

    article {
        height: 700px;
    }
    .favorites-buttons {
        display: flex;
        position: absolute;
        bottom: 0;
        gap: 5px;
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

    .modal-movie-info {
  display: flex;
  justify-content: space-between;
  font-family: 'Paytone One', sans-serif;
}
</style>
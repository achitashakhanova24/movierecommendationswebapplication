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
      <p class="description">
        FAVORITE MOVIES - WATCH LIST - SEEN LIST
      </p>
      <!-- <div class='favoriteMovies'>
        <ul>
            <li v-for="movie in favoriteMovies" :key="movie.movieId">
               {{movie.rank}} {{movie.title}} {{movie.genres}} 
            </li>
        </ul>
    </div>
    <div class='watchList'>
        <ul>
            <li v-for="movie in favoriteMovies" :key="movie.movieId">
               {{movie.rank}} {{movie.title}} {{movie.genres}} 
            </li>
        </ul>
    </div>
    <div class='seenList'>
        <ul>
            <li v-for="movie in seenList" :key="movie.movieId">
               {{movie.rank}} {{movie.title}} {{movie.genres}} 
            </li>
        </ul>
    </div> -->
      <div class="favoriteCarousel">
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
          <!-- Text slides with image -->
          <b-carousel-slide
            v-for="movie in favoriteMovies"
            v-bind:key="movie.id"
            class="carousel-item"
            :img-src="movie.posterPath"
            :img-alt="movie.title"
          >
          </b-carousel-slide>

          <!-- Slides with custom text -->
          <b-carousel-slide
            v-for="movie in newReleases"
            v-bind:key="movie.id"
            class="carousel-item"
            :img-src="movie.posterPath"
            :img-alt="movie.title"
          >
          </b-carousel-slide>

          <!-- Slides with image only -->
          <b-carousel-slide
            v-for="movie in newReleases"
            v-bind:key="movie.id"
            class="carousel-item"
            :img-src="movie.posterPath"
            :img-alt="movie.title"
          >
          </b-carousel-slide>

          <!-- Slides with img slot -->
          <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
          <b-carousel-slide>
            <template #img>
              <img
                class="d-block img-fluid w-100"
                width="1024"
                height="480"
                src="https://picsum.photos/1024/480/?image=55"
                alt="image slot"
              />
            </template>
          </b-carousel-slide>

          <!-- Slide with blank fluid image to maintain slide aspect ratio -->
          <b-carousel-slide
            caption="Blank Image"
            img-blank
            img-alt="Blank image"
          >
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit.
              Suspendisse eros felis, tincidunt a tincidunt eget, convallis vel
              est. Ut pellentesque ut lacus vel interdum.
            </p>
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
      }
    },
    created(){
        movieService.getFavorites()
        .then(response => {
            response.data.forEach((currentMovie) => {
        this.favoriteMovies.push(currentMovie);
      });
        }),
       movieService.getWatchList()
       .then(response => {
           response.data.forEach(currentMovie => {
               this.watchList.push(currentMovie)
           })
       }),
       movieService.getSeenList()
       .then(response => {
           response.data.forEach(currentMovie => {
               this.seenList.push(currentMovie)
           })
       }),
        movieService.getFavorites.then(response => {
      this.favoriteMovies = response.data.filter(movie => {
        return movie.posterPath != "null";
      });
      this.favoriteMovies.forEach(movie => {
        movie.posterPath = "https://image.tmdb.org/t/p/w500/" + movie.posterPath;
      })
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
    "watchList"
    "seenList";
  grid-template-rows: 0fr 0fr 3fr 3fr 3fr;
}
/* .content{
    color: white; */

p {
  grid-area: "description";
  color: white;
}
h1 {
  grid-area: "header";
  color: white;
}

.favoriteMovies {
  grid-area: "favoriteMovies";
}

.watchList {
  grid-area: "watchList";
}

.seenList {
  grid-area: "seenList";
}
</style>
<template>
  <html>
    
    <div class="recommendationsPage">
      <head>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
      </head>
      <div class="vignette"></div>
      <div class="table-div">
        <h1>Recommendations Based On Your Top 10 Favorites</h1>
        <b-table
        striped hover
        id="my-table"
        :items="movies"
        :per-page="10"
        :current-page="currentPage"
        small
        @row-clicked="handleRowClick"
        ></b-table>
        <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        :per-page="10"
        aria-controls="my-table"
        ></b-pagination>
      </div>
    </div>
  </html>
</template>
<script src="js/jquery-1.7.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script>
import movieService from "../services/MovieService";
export default {
  data() {
    return {
      currentPage: 1,
      movies: [],
      movieIds: [],
      rows: 0
    }
  },
    methods: {
        handleRowClick(item){
         this.$router.push({
           name:'movie-details', params:{
                id: this.movieIds[this.movies.indexOf(item)],
                title: item.title,
                rating: item.rating,
                genre: item.genres,
                releaseDate: item.releaseDate,
                description: item.description,
            }
          });
        }
    },
    created() {
      movieService.getRecommendations().then(response => {
        console.log(response.data);
        response.data.sort((a,b) => {
          return a.rating > b.rating ? -1 : 1;
        });
        response.data.forEach(currentMovie => {
          this.movies.push({
              title: currentMovie.title,
              rating: currentMovie.rating,
              releaseDate: currentMovie.releaseDate,
              genres: currentMovie.genres,
              description: currentMovie.description,
              language: currentMovie.language
          });
          this.movieIds.push(currentMovie.movieId);
        })
        this.rows = this.movies.length;
        console.log(this.rows);
      })
    }
}
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
.recommendationsPage {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-family: 'Paytone One', sans-serif;
  border-radius: 7px;
  margin-top: 100px;
}

  p{
      color: white;
  }

  h1 {
      color: white;
  }
  table {
    font-size: 70%;
    height: 600px;
    background-color: whitesmoke;
  }
  .table-div {
      width: 90vw;
      display: flex;
      justify-content: center;
      flex-direction: column;
      align-items: center;
      border-color: rgba(0, 0, 0, 0.76);
      border: solid;
      border-width: 5px;
      border-radius: 7px;
      background-color: rgb(24, 23, 23);
  }
  td {
      height: 50vh;
  }
</style>
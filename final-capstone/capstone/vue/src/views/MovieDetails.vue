<template>
  <div class="detailsPage">
      <div class="vignette"></div>
   <h1>{{this.$route.params.id}}</h1>
   <h2>{{this.$route.params.rating}}/10</h2>
   <h3>Release Date: {{this.$route.params.releaseDate}}</h3>
   <p>{{this.$route.params.description}}</p>
  
   <p></p>
  </div>
</template>

<script>
import movieService from "../services/MovieService";

export default {
  name: "movie-details",
  data() {
    return {
      movie: null

    };
  },
  created() {
    const movieId = this.$route.params.id;
    console.log(movieId);
    movieService.getMovieDetails(movieId)
    .then(response => {
      this.movie = response.data;
    })
    .catch(error => {
      console.error(error);
    });
  }
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
  display: flex;
  flex-direction: column;
  align-items: center;
  position: fixed;
  z-index: -1;
  /* background-color: rgba(0, 0, 0, 0.7); */
  width: 100%;
  height: 100%;
  border-radius: 7px;
  margin-top: 30px;
  margin-left: -8px;
  font-family: 'Paytone One', sans-serif;
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
</style>
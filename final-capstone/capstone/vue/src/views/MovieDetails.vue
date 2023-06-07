<template>
  <div class="detailsPage">
      <div class="vignette"></div>
   <h1>Movie Details</h1>
   <p>Spider-Man: Across the Spider-Verse</p>
   <p>Description: After reuniting with Gwen Stacy, Brooklyn’s full-time, friendly neighborhood Spider-Man is catapulted across the Multiverse, where he encounters the Spider Society, a team of Spider-People charged with protecting the Multiverse’s very existence. But when the heroes clash on how to handle a new threat, Miles finds himself pitted against the other Spiders and must set out on his own to save those he loves most.</p>
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
    color: white
}
p {
    color: white
}
</style>
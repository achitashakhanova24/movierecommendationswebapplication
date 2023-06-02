<template>
  <div class="home">
    <h1>CinemaCrush</h1>
    <p> Swipe Right for Your Movie Night!</p>
    <div class="poster-container">
    <img class="poster1" src="https://m.media-amazon.com/images/M/MV5BNzQ1ODUzYjktMzRiMS00ODNiLWI4NzQtOTRiN2VlNTNmODFjXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_FMjpg_UX1000_.jpg" alt="Spider-Man">
    <img class="poster2" src="https://m.media-amazon.com/images/M/MV5BYTUxYjczMWUtYzlkZC00NTcwLWE3ODQtN2I2YTIxOTU0ZTljXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_FMjpg_UX1000_.jpg" alt="The Little Mermaid">
    <img class="poster3" src="https://m.media-amazon.com/images/M/MV5BMDgxOTdjMzYtZGQxMS00ZTAzLWI4Y2UtMTQzN2VlYjYyZWRiXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_FMjpg_UX1000_.jpg" alt="Guardians vol. 3">
  </div>  
  </div>
</template>

<script>
export default {
  name: "home",
  methods: {
    goToAbout(){

    }
  }
};
</script>

<style scoped>
.home{
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
  position: fixed;
  z-index: -1;
  background-color: rgba(0, 0, 0, 0.5);
  width: 100%;
  height: 100%;
  border-radius: 7px;
  margin-top: 30px;
  margin-left: -8px;
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

</style>
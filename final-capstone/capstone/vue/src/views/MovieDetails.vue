<template>
  <div class="detailsPage">
      <div class="vignette"></div>
   <h1>Details</h1>
   <p>**PLACEHOLDER**</p>
  </div>
</template>

<script>
export default {

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
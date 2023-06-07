<template>
  <div class="loading">
      <div class="vignette"></div>
    <img src="/loading.gif" alt="Loading..." />
    <br>
    <h2>Loading...</h2>
  </div>
</template>

<style scoped>
.loading {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #ffffff;
}

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
    rgba(0, 0, 0, 0.7) 25%
  );
  z-index: 9999;
}

h2 {
  font-size: 24px;
}
</style>
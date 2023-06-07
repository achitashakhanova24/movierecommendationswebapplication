<template>
  <div class="recommendationsPage" @click="handleClick">
    <div class="vignette"></div>
<h1>Recommendations</h1>
<p>Here are movies you should check out based on your previous selections!</p>
  </div>
</template>

<script>
export default {
    methods: {
        handleClick(){

        }
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
  justify-content: flex-start;
  align-items: center;
  position: fixed;
  z-index: -1;
  /* background-color: rgba(0, 0, 0, 0.5); */
  width: 100%;
  height: 100%;
  border-radius: 7px;
  margin-top: 30px;
  margin-left: -8px;
}

p{
    color: white;
}

h1 {
    color: white;
}
</style>
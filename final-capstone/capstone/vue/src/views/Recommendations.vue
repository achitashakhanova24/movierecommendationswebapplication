<template>
  <div class="recommendationsPage" @click="handleClick">
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

<style>
.recommendationsPage {
display: flex;
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
}

p{
    color: white;
}

h1 {
    color: white;
}
</style>
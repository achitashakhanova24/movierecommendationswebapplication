<template>
  <div class="favoritePage" @click="handleClick">
      <h1>Top 10 Movies (Can Change)</h1>
      <p>These are my favorite movies that I have seen. (CHANGE)</p>

  </div>
</template>

<script>
export default {
    methods: {
        handleClick(){

        }
    }

};
</script>

<style scoped>
.favoritePage {
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
/* .content{
    color: white; */


p{
    color: white;
}
h1{
    color: white;
}
</style>
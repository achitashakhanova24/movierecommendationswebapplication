<template>
  <div class="home">
    <h1>CinemaCrush</h1>
    <p> Swipe Right for Your Movie Night!</p>
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
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  width: 100%;
  height: 100%;
  border-radius: 7px;
  margin-top: 120px;
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

</style>
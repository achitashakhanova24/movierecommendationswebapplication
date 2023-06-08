<template>
<html>
  <div>
    <Loading v-if="loading" />
  <div class="vignette"> </div>
  <div class="AboutUs" @click="handleClick">
    <head>
            
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        </head>
    
    <h1 >About Us</h1>
     <h2> Introducing CinemaCrush: Where Movie Matches Are Made! </h2>
    <p>
      <!-- Are you tired of endlessly swiping through potential "soulmates" on dating apps, only to end up disappointed? 
      What if we told you there's an app that can match you with your perfect movie instead? Get ready for a cinematic journey like no other! -->
      CinemaCrush is here to revolutionize the way you discover movies. We believe in the power of connection and the thrill of finding your perfect movie match. 
      Say goodbye to scrolling through endless movie recommendations that don't quite hit the mark. With CinemaCrush, your movie preferences take center stage!


      Our innovative "yes" or "no" swiping feature adds an exciting twist to your movie exploration. 
<!-- Just like in the dating world, you'll embark on a thrilling adventure as you swipe through a vast array of films tailored to your unique tastes. Each swipe brings you one step closer to finding your cinematic soulmate. -->
      But what makes CinemaCrush truly special is the experience it offers. We've created a fun and engaging platform where movie lovers like you can connect with your perfect match. Explore movie genres, dive into captivating plot summaries, and enjoy vibrant visuals that will make your movie browsing a delightful experience.
      So, are you ready to ignite your cinematic journey? Let CinemaCrush be your guide to unforgettable movie experiences. Get ready to fall in love with cinema all over again!
    </p>
    <div class="portraits">
      <div class="portrait-div">
        <label for="portrait">Carter Akins</label>
        <b-img v-bind="mainProps" id="portrait" rounded="circle" :src="require('../../images/carter2.jpg')" alt=""></b-img></div>
      <div class="portrait-div">
        <label for="portrait">Marco Brooks</label>
        <b-img v-bind="mainProps" id="portrait" rounded="circle" :src="require('../../images/marco.jpg')" alt=""></b-img>
      </div>
      <div class="portrait-div">
        <label for="portrait">Achita Shakhanova</label>
        <b-img v-bind="mainProps" id="portrait" rounded="circle" :src="require('../../images/achita.png')" alt=""></b-img>
      </div>
      <div class="portrait-div">
        <label for="portrait">Bobby Short</label>
        <b-img v-bind="mainProps" id="portrait" rounded="circle" :src="require('../../images/bobby.jpg')" alt=""></b-img>
      </div>
      
      
      
      
    </div>
    
 </div>
 </div>
 </html>
</template>

<script>
import Loading from "../components/Loading.vue"

export default {
  data() {
    return {
      mainProps: { width: 150, height: 150, class: 'm1' },
      loading: true
    }
  },
  methods: {
    handleClick() {},
  },
  mounted() {
    setTimeout(() => {
      this.loading = false;
    }, 1500);
  },
  components: {
    Loading
  },
};
</script>

<style scoped>
.portrait-div {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}
.portraits {
  display: flex;
  margin-top: 200px;
  gap: 50px;
}
label {
  color: whitesmoke;
}
.AboutUs {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  position: fixed;
  /* background-color: rgba(0, 0, 0, 0.5); */
  width: 100%;
  height: 100%;
  border-radius: 7px;
  margin-top: 50px;
  margin-left: -8px;
  
  font-family: 'Paytone One', sans-serif;
}

p{
    color:white;
    text-indent: 2em;
    max-width: 1500px;
    background-color: rgba(0, 0, 0, 0.7);
    font-size: 25px;
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
    rgba(0, 0, 0, 0.9) 60%
  );
  z-index: -1;
}
h1{
  color: whitesmoke;
  font-size: 27px;
}
 h2{
   color: whitesmoke;
   font-size: 27px;
 }

 #portrait {
   width: 75;
   height: 50;
 }

</style>





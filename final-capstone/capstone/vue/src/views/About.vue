<template>
  <div class="AboutUs" @click="handleClick">
    <div class="vignette"> </div>
    <h1>About Us</h1>
    <h2> Introducting CinemaCrush: Where Movie Matches Are Made! </h2>
    <p>
      Are you tired of endlessly swiping through potential "soulmates" on dating apps, only to end up disappointed? 
      What if we told you there's an app that can match you with your perfect movie instead? Get ready for a cinematic journey like no other!
CinemaCrush is here to revolutionize the way you discover movies. We believe in the power of connection and the thrill of finding your perfect movie match. 
Say goodbye to scrolling through endless movie recommendations that don't quite hit the mark. With CinemaCrush, your movie preferences take center stage!
Our innovative "yes" or "no" swiping feature adds an exciting twist to your movie exploration. 
Just like in the dating world, you'll embark on a thrilling adventure as you swipe through a vast array of films tailored to your unique tastes. Each swipe brings you one step closer to finding your cinematic soulmate.
But what makes CinemaCrush truly special is the experience it offers. We've created a fun and engaging platform where movie lovers like you can connect with your perfect match. Explore movie genres, dive into captivating plot summaries, and enjoy vibrant visuals that will make your movie browsing a delightful experience.
So, are you ready to ignite your cinematic journey? Let CinemaCrush be your guide to unforgettable movie experiences. Get ready to fall in love with cinema all over again!
</p>
 </div>
</template>

<script>
export default {
  methods: {
    handleClick() {},
  },
};
</script>

<style scoped>
.AboutUs {
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
    color:white;
    border: solid  black 2px;
    
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
 h2{
   color: white;
   font-size: 27px;
 }

</style>





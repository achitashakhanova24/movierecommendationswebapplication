<template>
  <div class="AboutUs" @click="handleClick">
    <h1>About Us</h1>
    <p>
      Picture this: you've swiped on countless potential "soulmates" on Tinder,
      but what if there was an app that matched you with your perfect movie
      instead? Are you tired of endlessly scrolling through movie
      recommendations that don't quite hit the mark? Look no further!
      CinemaCrush is here to ignite your cinematic journey with a unique twist!
      At CinemaCrush, we believe in the power of connection and the thrill of
      discovering your perfect movie match. With our innovative "yes" or "no"
      swiping feature, you'll embark on a thrilling adventure, exploring a vast
      array of films tailored to your preferences. Say goodbye to wasting time
      on movies that don't resonate with you and embrace the excitement of
      finding your cinematic soulmate.
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

<style >
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
</style>





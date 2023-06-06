<template>
  <div class="profilePage" @click="handleClick">
      <h1>Profile</h1>
      <div class="profileInfo">
            <div class="vignette"></div>
          <div>
              <h3>Username: {{ username }}      </h3>
              <!-- <p>{{ username }}</p> -->
          </div>
          <div>
              <h3>Email: {{email}}</h3>
              <!-- <p>{{ email }}</p> -->
          </div>
      </div>
  </div>
</template>

<script>
export default {
    data() {
        return {
            username: 'Kelvin',
            email: 'kelvinTE@example.com',
        };
    },
    methods: {
        handleClick(){

        }
    }
}
</script>

<style>

h3 {
    font-style: normal;
}

.profilePage {
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

.profileInfo{
    color: white;
    border: solid black;
    background-color: rgba(0, 0, 0, 0.8);
    font-weight: 0;
}
.vignette {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 7px;
  background-image: radial-gradient(
    circle at center,
    transparent 0%,
    rgba(0, 0, 0, 0.7) 60%
  );
  z-index: -1;
}
</style>
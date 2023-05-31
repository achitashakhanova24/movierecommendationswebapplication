<template>
  <div id="login">
    <form @submit.prevent="login" class="form-container">
      <h1 >Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="button-container">
      <button type="submit">About Us</button>
      <router-link :to='{ name: "register" }'>
        <button type="submit">Register</button>
      </router-link>
      
      <button type="submit">Login</button>
      </div>

    </form>
    <div class='about-us'>
      <p>
       Picture this: You've swiped on countless potential "soulmates" on Tinder, but what 
       if there was an app that matched you with your perfect movie instead? Are you tired of 
       endlessly scrolling through movie recommendations that don't quite hit the mark? Look no further! 
       CinemaCrush is here to ignite your cinematic journey with a unique twist! At CinemaCrush, we believe in 
       the power of connection and the thrill of discovering your perfect movie match. With our innovative "yes" or 
       "no" swiping feature, you'll embark on a thrilling adventure, exploring a vast array of films tailored to your preferences. 
       Say goodbye to wasting time on movies that don't resonate with you and embrace the excitement of finding your cinematic soulmate.
      </p>
    </div>
    
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1rem;
}
label {
  margin-right: 0.5rem;
}

.form-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.button-container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 10px;
}

.button-container button {
  margin: 0 10px;
}
footer{
  text-align: center;
}
.about-us{
  text-align: center;
  font-weight: bold;
  font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
  letter-spacing: 1px;
  margin-left: 300px;
  margin-right: 300px;
  font-size: 25px;
}
</style>
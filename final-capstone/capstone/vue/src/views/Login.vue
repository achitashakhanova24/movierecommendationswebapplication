<template>
  <div id="login">
    <div class="vignette">
      </div>
    <form @submit.prevent="login">
      <!-- <h1 class="headline">CinemaCrush</h1> -->
      <h1 >Sign_In</h1>
      <div class="alert" role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div class="alert" role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input placeholder="Enter your username here" type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input placeholder="Enter your password here" type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Sign in</button>
      <p>
      Need an account?<router-link class="register-link" :to="{ name: 'register' }"> Sign up now</router-link></p>
    </form>
    <!-- <div class="about">
      <p>Cinema Crush</p>
    </div> -->
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
.headline {
  color: white;
}
.form-input-group {
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
}
label {
  margin-right: 0.5rem;
}
form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.9);
  width: 30rem;
  height: 35rem;
  /* text-align: center; */
  border-radius: 7px;
}
button {
  font-family: 'Paytone One', sans-serif;
  width: 15rem;
  height: 3rem;
  border-radius: 5px;
  background-color: red;
  color: whitesmoke;
  font-size: 1.25rem;
}
#login {
  margin-top: 100px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.about {
  margin-top: 50px;
  color: rgb(245,245,245);
  font-size: 1.5em;
  width: 100vw;
  height: 5rem;
  background-color: rgba(0, 0, 0, 0.9);
}

.about > p {
  color: whitesmoke;
  text-align: center;
}

input {
  border-radius: 5px;
  background-color: #b1b1b1;
  height: 3rem;
  width: 15rem;
  color: #000;
  font-family: 'Heebo', sans-serif;
}

h1 {
  color: whitesmoke;
}
label {
  color: whitesmoke;
}

.register-link {
  text-decoration: none;
  color: whitesmoke;
}

p {
  color: #696969;
}
.alert{
  color: tomato;
}

::placeholder {
  color: rgba(32, 32, 32, 0.774);
  font-family: 'Paytone One', sans-serif;
  font-size: .7rem;
  font-style: italic;
  position: absolute;
  left: 5;
}

input:not(:placeholder-shown) {
  background-color: #F5F5F5;
}

button:hover {
  background-color: firebrick;
}
</style>
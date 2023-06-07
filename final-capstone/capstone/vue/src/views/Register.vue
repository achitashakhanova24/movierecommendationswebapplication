<template>

<div id="app">
  <div class="vignette"></div>
  <div id="register" class="text-center">
    
    <form @submit.prevent="register">
      <h1>Create Account</h1>
      <div class="alert" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input placeholder="Enter your username" type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input placeholder="Enter your password here" type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password</label>
        <input placeholder="Re-enter password" type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit">Create Account</button>
      <p>Already have an account?<router-link :to="{ name: 'login' }">  Log in</router-link></p>
    </form>
  </div>
</div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'User created successfully.', 
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                name: 'login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
  
}

/* #app{
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  position: absolute;
} */


label {
  margin-right: 0.5rem;
}
#register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
  position: relative;
  margin-top: 160px;
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
  height: 13rem;
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

a{
  color: white;
  text-decoration: none;
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

/* footer{
  margin-top: auto;
} */
</style>
<template>
  <div id="register" class="text-center">
    <form @submit.prevent="register">
      <h1>Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password</label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit">Create Account</button>
      <p><router-link :to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    </form>
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
      registrationErrorMsg: 'There were problems registering this user.',
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
                path: '/login',
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
label {
  margin-right: 0.5rem;
}
#register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  position: relative;
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
  background-color: #B1B1B1;
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
</style>
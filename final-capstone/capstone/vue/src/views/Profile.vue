<template>
<div>
  <Loading v-if="loading" />
  <div class="profilePage">
      <h1>Profile</h1>
      <div class="profileInfo">
            <div class="vignette"></div>
          <div>
              
              <!-- <p>{{ username }}</p> -->
          </div>
          <div class="info-section">
           
              <form @submit.prevent="updateAccount()">
                <div class="form-section">
                  <h3>Username:  </h3>
                  <input v-model="username" disabled placeholder="Enter email address" type="text">
                </div>
                <div class="form-section">
                  
                   
                  <h3>Email: </h3>
                  <input v-model="email" placeholder="Enter email address" type="text">
                  
                </div>
                <div class="submit-button">
                  <input type="submit">
                </div>
                
              </form>
              
              <!-- <p>{{ email }}</p> -->
          </div>
      </div>
  </div>
  </div>
</template>

<script>
import accountService from '../services/AccountService';
import Loading from "../components/Loading.vue"
import movieService from '../services/MovieService';
export default {
    data() {
        return {
            username: '',
            email: '',
            loading: true
        };
    },
   
    created(){
            movieService.getProfile() 
      .then(response => {
        this.username = response.data.username;
        this.email = response.data.email;
      })
      .catch(error => {
        console.log(error);
      });
        },
    methods: {
      updateAccount(){
        accountService.updateAccount(this.email)
      }
    },
        mounted() {
          setTimeout(() => {
            this.loading = false;
          }, 1500);
        },
        components: {
          Loading
        },
      
    
}
</script>

<style>

h1 {
  grid-area: "header";
  color: rgb(255, 49, 49);
  text-align: center;
  font-family: 'Showtime';
  font-size: 5rem;
  margin-top: 20px;
}

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
    /* border: solid black;
    background-color: rgba(0, 0, 0, 0.8);
    font-weight: 0;
    width: 275px;
    height: 100px */

    
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
.info-section{
  display: flex;
  flex-direction: column;
}
.submit-button{
  margin-top: 20px;
}
</style>
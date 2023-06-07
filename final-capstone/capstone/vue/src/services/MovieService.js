import axios from 'axios';

export default {

  home() {
    return axios.get('/movie/newReleases')
  },

  getMovie(movieId) {
    return axios.get(`/movie/${movieId}`)
  },

  getProfile(){
    return axios.get('/account')
  },

  getFavorites(){
    return axios.get('/movie/myFavorites')
  }, 

  getWatchList(){
    return axios.get('/movie/myWatchList')
  },

  getSeenList(){
    return axios.get('/movie/mySeenList')
  },

     getPageOfMovies() {

    // const params = {
    //   page: page,
    //   title: title,
    //   genre: genre,
    //   data: date,
    //   language: language
    // };

    // return axios.get('/api/movies', { params });
    return axios.get(`/movie/list`);
  }

}

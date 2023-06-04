import axios from 'axios';

export default {

  home() {
    return axios.get('/movie/newReleases')
  },

  getMovie(movieId) {
    return axios.get(`/movie/${movieId}`)
  },

  getPageOfMovies(page) {
    return axios.get(`/movie/list/${page}`)
  }

}

import axios from 'axios';

export default {

  home() {
    return axios.get('/movie/newReleases')
  },

  getMovie(movieId) {
    return axios.get(`/movie/${movieId}`)
  },

  // getPageOfMovies(page) {
    getPageOfMovies(page, title, genre, date, language) {

    const params = {
      page: page,
      title: title,
      genre: genre,
      data: date,
      language: language
    };

    // return axios.get('/api/movies', { params });
    return axios.get(`/movie/list/${page}`, { params});
  }

}

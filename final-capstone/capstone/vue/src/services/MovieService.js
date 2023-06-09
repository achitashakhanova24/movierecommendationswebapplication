import axios from 'axios';

export default {

  home() {
    return axios.get('/movie/newReleases')
  },

  getMovie(movieId) {
    return axios.get(`/movie/${movieId}`)
  },

  getMovieByTitle(title) {
    return axios.get(`/movie/title/${title}`)
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
  },

  editWatchedStatus(movieId) {
    return axios.put(`/movie/editWatched/${movieId}`);
  },

  searchMovies(title, genre, date) {
     const queryParams = {
      title: title,
      genre: genre,
      releaseDate: date,
    };
    const params = new URLSearchParams(queryParams);

    return axios.get('/movie/search', {params});
  },

  editFavoritedStatus(movieId) {
    return axios.post(`/movie/favorite/${movieId}`);
  },

  updateRank(movieId, rank) {
    return axios.put(`/movie/updateRank/${movieId}/${rank}`);
  },

  getRecommendations() {
    return axios.get('/movie/recommendations');
  }

}

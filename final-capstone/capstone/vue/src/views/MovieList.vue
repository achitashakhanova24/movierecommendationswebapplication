
<template>

<html lang="en">
    <div class="movie-list">
        <div class="vignette"></div>
        <head>
            
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        </head>
        <div class="table-div">
            <h2>Browse Movies</h2>
            <form @submit.prevent="getPage(1)">
                <div>
                    <label for="title">Title </label>
                    <input id="title" type="text" placeholder="Title" v-model="filterTitle">
                </div>
                <div>
                    <label for="title">Genre </label>
                    <input id="genre" type="text" placeholder="Genre" v-model="filterGenre">
                </div>
                <div>
                    <label for="title">Release Date</label>
                    <input id="date" type="date" v-model="filterDate">
                </div>
                <div>
                    <label for="title">Language</label>
                    <input id="language" type="text" placeholder="Language" v-model="filterLanguage">
                </div>
                <div class="filterButton">
                    <button type="submit">Submit</button>
                </div>

            </form>
            <b-table
            striped hover
            id="my-table"
            :items="movies"
            :per-page="10"
            :current-page="currentPage"
            small
            ></b-table>
            <b-pagination
            v-model="currentPage"
            :total-rows="400"
            :per-page="10"
            aria-controls="my-table"
            ></b-pagination>
        </div>
        
    </div>
    </html>
</template>

<script>
import movieService from "../services/MovieService";

export default {
    name: "movie-list",
    data() {
        return {
            currentPage: 1,
            rows: 10,
            movies: [],
            filterTitle: '',
            filterGenre: '',
            filterDate: '',
            filterLanguage: ''
        };
    },
    created() {
        for(let i = 1; i <= 20; i++) {
            movieService.getPageOfMovies(i).then(response => {
                console.log(response.data)
                response.data.forEach(currentMovie => {
                    this.movies.push(currentMovie);
                })
            })
        }
        console.log(this.movies);
        // movieService.getPageOfMovies(1).then(response => {
        //     console.log(response.data);
        //     return this.movies = response.data;
        // })
    },
    methods: {
        getPage(page) {
            if (!this.isAuthenticated) {
                // redirect to login page 
                return;
            }

            movieService.getPageOfMovies(page, this.filterTitle, this.filterGenre, this.filterDate, this.filterLanguage)
            .then(response => {
            console.log(response.data);
            return response.data;
        })
        .catch(error => {
            console.error(error);
        });
        }
    }

}
</script>


<style scoped>
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


    form {
        display: flex;
        color: whitesmoke;
        gap: 10px;
        margin-bottom: 10px;
    }
    form > div {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    form > div > input {
        border-radius: 5px;
    }
    table {
        font-size: 70%;
        height: 600px;
        background-color: whitesmoke;
    }

    h2{
        color: whitesmoke;
    }

    .movie-list {
        display: flex;
        justify-content: center;
        flex-direction: column;
        align-items: center;
        margin-top: 100px;
        background-color: transparent;
        box-sizing: content-box;
        font-family: 'Paytone One', sans-serif;
        z-index: -1;
    }
    .table-div {
        width: 90vw;
        display: flex;
        justify-content: center;
        flex-direction: column;
        align-items: center;
        border-color: rgba(0, 0, 0, 0.76);
        border: solid;
        border-width: 5px;
        border-radius: 7px;
        background-color: rgb(24, 23, 23);
    }
    td {
        height: 50vh;
    }
    ul {
        background-color: rgb(0, 0, 0);
    }
</style>
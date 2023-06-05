
<template>

<html lang="en">
    <div class="movie-list">
        <head>
            
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        </head>
        <div class="table-div">
            <h2>Browse Movies</h2>
            <form @submit.prevent="login">
                <div>
                    <label for="title">Title </label>
                    <input id="title" type="text" placeholder="Title">
                </div>
                <div>
                    <label for="title">Genre </label>
                    <input type="text" placeholder="Genre">
                </div>
                <div>
                    <label for="title">Release Date</label>
                    <input type="date">
                </div>
                <div>
                    <label for="title">Language</label>
                    <input type="text" placeholder="Language">
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
            movies: []
        }
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
            movieService.getPageOfMovies(page).then(response => {
            console.log(response.data);
            return response.data;
        })
        }
    }

}
</script>


<style scoped>
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
        background-color: rgba(122, 118, 118, 0.89);
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
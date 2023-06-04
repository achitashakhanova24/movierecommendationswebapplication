<template>
    <div class="movie-list">
        <head>
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        </head>
        <b-pagination
        v-model="currentPage"
        :total-rows="400"
        :per-page="20"
        aria-controls="my-table"
        ></b-pagination>

        <p class="mt-3">Current Page: {{ currentPage }}</p>

        <b-table
        id="my-table"
        :items="movies"
        :per-page="20"
        :current-page="currentPage"
        small
        ></b-table>
    </div>
</template>
<script src="js/jquery-1.7.1.min.js"></script>
<script src="js/bootstrap.js"></script>
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
    b-table {
        /* display: flex;
        justify-content: center;
        align-items: center;
        position: fixed;
        top: 50%; */
        /* left: 50%; */
        /* background-color: rgba(0, 0, 0, 0.5); */
        height: 100px;
        width: 50vh;
    }
</style>
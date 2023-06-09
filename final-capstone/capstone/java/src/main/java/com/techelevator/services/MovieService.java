package com.techelevator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.techelevator.model.Movie;
import com.techelevator.model.MovieDto;
import com.techelevator.model.MovieTableDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


@Service
public class MovieService {

    HttpHeaders headers = new HttpHeaders();
    private Map<Integer, String> genreIdToName = new HashMap<>();
    private Map<String, String> langAbbrevToFull = new HashMap<>();

    public MovieService () {
        genreIdToName.put(28,"Action");
        genreIdToName.put(12,"Adventure");
        genreIdToName.put(16,"Animation");
        genreIdToName.put(35,"Comedy");
        genreIdToName.put(80,"Crime");
        genreIdToName.put(99,"Documentary");
        genreIdToName.put(10751,"Family");
        genreIdToName.put(14,"Fantasy");
        genreIdToName.put(36,"History");
        genreIdToName.put(27,"Horror");
        genreIdToName.put(10402,"Music");
        genreIdToName.put(9648,"Mystery");
        genreIdToName.put(878,"Science Fiction");
        genreIdToName.put(10749,"Romance");
        genreIdToName.put(10770,"TV Movie");
        genreIdToName.put(53,"Thriller");
        genreIdToName.put(10752,"War");
        genreIdToName.put(37,"Western");
        genreIdToName.put(18, "Drama");
    }

    @Value("${MOVIE_API}")
    private String MOVIE_API;

    @Value("${KEY}")
    private String KEY;

    RestTemplate restTemplate = new RestTemplate();

    public MovieDto getMovie(int movieId) {
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        ResponseEntity<String> responseEntity = restTemplate.exchange(MOVIE_API + "/movie/" + movieId + "?api_key=" + KEY, HttpMethod.GET, entity, String.class);
        MovieDto movie = new MovieDto();

        try{
            jsonNode = mapper.readTree(responseEntity.getBody());
            movie.setTitle(jsonNode.path("original_title").asText());
            movie.setLanguage(jsonNode.path("original_language").asText());
            JsonNode array = jsonNode.path("genres");

            String genreList = "";
            for (int i = 0; i < array.size(); i++){
                String genre = array.get(i).path("name").asText();
                genreList += genre;
            }
            String release = jsonNode.path("release_date").asText().equals("") ? "Unreleased" : jsonNode.path("release_date").asText().substring(5,10) + "-" + jsonNode.path("release_date").asText().substring(0,4);
            movie.setReleaseDate(release);
            movie.setDescription(jsonNode.path("overview").asText());
            movie.setGenres(genreList);
            movie.setMovieId(jsonNode.path("id").asInt());
            movie.setPosterPath(jsonNode.path("poster_path").asText());
            movie.setBackdropPath(jsonNode.path("backdrop_path").asText());
            movie.setRating(jsonNode.path("vote_average").asDouble());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public MovieDto getMovieByTitle(String title) {
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        ResponseEntity<String> responseEntity = restTemplate.exchange(MOVIE_API + "/search/movie" + "?api_key=" + KEY + "&query=" + title.replace("%20", ""), HttpMethod.GET, entity, String.class);

        MovieDto movie = new MovieDto();

        try{
            jsonNode = mapper.readTree(responseEntity.getBody());
            movie.setTitle(jsonNode.path("original_title").asText());
            movie.setLanguage(jsonNode.path("original_language").asText());
            JsonNode array = jsonNode.path("genres");

            String genreList = "";
            for (int i = 0; i < array.size(); i++){
                String genre = array.get(i).path("name").asText();
                genreList += genre;
            }

            movie.setReleaseDate(jsonNode.path("release_date").asText());
            movie.setDescription(jsonNode.path("overview").asText());
            movie.setGenres(genreList);
            movie.setMovieId(jsonNode.path("id").asInt());
            movie.setPosterPath(jsonNode.path("poster_path").asText());
            movie.setBackdropPath(jsonNode.path("backdrop_path").asText());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public List<MovieTableDto> getPageOfMovies() {
        List<MovieTableDto> movies = new ArrayList<>();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        for(int i =1; i <= 20; i++) {
            ResponseEntity<String> responseEntity = restTemplate.exchange(MOVIE_API + "/movie/top_rated" + "?api_key=" + KEY + "&page=" + i, HttpMethod.GET, entity, String.class);

            try {
                jsonNode = mapper.readTree(responseEntity.getBody()).path("results");
                movies.addAll(mapResultSetToDto(jsonNode));
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return movies;
    }

    public Movie getRandomMovie(){
        //headers.setBearerAuth(KEY);
        int page = (int)Math.floor(Math.random() * 500);
        int index = (int)Math.floor(Math.random()*19);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        ResponseEntity<String> responseEntity = restTemplate.exchange(MOVIE_API + "/discover/movie" + "?api_key=" + KEY + "&page=" + page + "&language=en-US", HttpMethod.GET, entity, String.class);
        Movie movie = new Movie();


        try{
            jsonNode = mapper.readTree(responseEntity.getBody()).path("results").get(index);
            movie.setTitle(jsonNode.path("original_title").asText());
            movie.setLanguage(jsonNode.path("original_language").asText());
            JsonNode array = jsonNode.path("genre_ids");

            List<String> genreList = new ArrayList<>();
            for (int i = 0; i < array.size(); i++){
                int genre = array.get(i).asInt();
                genreList.add(genreIdToName.get(genre));
            }

            movie.setRuntime(jsonNode.path("runtime").asInt());
            movie.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(jsonNode.path("release_date").asText()));
            movie.setDescription(jsonNode.path("overview").asText());
            movie.setListOfGenres(genreList);
            movie.setMovieId(jsonNode.path("id").asInt());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return movie;
    }

    public List<Movie> displayLatestMovies() {
        List<Movie> movies = new ArrayList<>();
        String startDate = LocalDate.now().minusDays(10).toString();
        String endDate = LocalDate.now().plusDays(5).toString();

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        String releaseYear = String.valueOf(LocalDate.now().getYear());
        ResponseEntity<String> responseEntity = restTemplate.exchange(MOVIE_API + "/discover/movie" + "?api_key=" + KEY +
                "&primary_release_date.gte=" + startDate + "&primary_release_date.lte=" + endDate + "&with_release_type=3|2|1" + "&primary_release_year=" + releaseYear, HttpMethod.GET, entity, String.class);
        try {
            jsonNode = mapper.readTree(responseEntity.getBody()).path("results");


            for (int i = 0; i < jsonNode.size(); i++) {
                List<String> genreList = new ArrayList<>();
                Movie movie = new Movie();
                movie.setTitle(jsonNode.get(i).path("original_title").asText());
                movie.setLanguage(jsonNode.get(i).path("original_language").asText());
                JsonNode array = jsonNode.get(i).path("genre_ids");
                movie.setRuntime(jsonNode.get(i).path("runtime").asInt());
                movie.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(jsonNode.get(i).path("release_date").asText()));
                movie.setDescription(jsonNode.get(i).path("overview").asText());
                movie.setPosterPath(jsonNode.get(i).path("poster_path").asText());

                movie.setMovieId(jsonNode.get(i).path("id").asInt());
                for (int j = 0; j < array.size(); j++) {
                    int genre = array.get(j).asInt();
                    genreList.add(genreIdToName.get(genre));
                }
                movie.setListOfGenres(genreList);
                movies.add(movie);
            }

            return movies;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<MovieTableDto> searchMovies(String searchTitle, String searchGenre, String releaseDate, String language) {
        List<MovieTableDto> movies = new ArrayList<>();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        ResponseEntity<String> responseEntity = null;
        for(int i = 1; i <= 20; i++) {
            if (searchTitle != "") {
                responseEntity = restTemplate.exchange(MOVIE_API + "/search/movie" + "?api_key=" + KEY + "&query=" + searchTitle + "&sort_by=vote_average.desc" + "&page=" + i, HttpMethod.GET, entity, String.class);
            } else if (!searchGenre.equals("") && releaseDate != null && language != "") {
                int genreId = 0;
                for (Integer key : genreIdToName.keySet()) {
                    if (genreIdToName.get(key).equalsIgnoreCase(searchGenre)) {
                        genreId = key;
                    }
                }

                responseEntity = restTemplate.exchange(MOVIE_API + "/discover/movie" + "?api_key=" + KEY + "&with_genres=" + genreId + "&primary_release_date.gte=" + releaseDate + "&language=" + language + "&sort_by=vote_average.desc" + "&page=" + i, HttpMethod.GET, entity, String.class);
            } else if (!searchGenre.equals("") && releaseDate != null) {
                int genreId = 0;
                for (Integer key : genreIdToName.keySet()) {
                    if (genreIdToName.get(key).equalsIgnoreCase(searchGenre)) {
                        genreId = key;
                    }
                }

                responseEntity = restTemplate.exchange(MOVIE_API + "/discover/movie" + "?api_key=" + KEY + "&with_genres=" + genreId + "&primary_release_date.gte=" + releaseDate + "&sort_by=vote_average.desc" + "&page=" + i, HttpMethod.GET, entity, String.class);
            } else if (releaseDate != null && language != "") {
                responseEntity = restTemplate.exchange(MOVIE_API + "/discover/movie" + "?api_key=" + KEY + "&primary_release_date.gte=" + releaseDate + "&language=" + language + "&sort_by=vote_average.desc" + "&page=" + i, HttpMethod.GET, entity, String.class);
            } else if (!searchGenre.equals("") && language != "") {
                int genreId = 0;
                for (Integer key : genreIdToName.keySet()) {
                    if (genreIdToName.get(key).equalsIgnoreCase(searchGenre)) {
                        genreId = key;
                    }
                }

                responseEntity = restTemplate.exchange(MOVIE_API + "/discover/movie" + "?api_key=" + KEY +  "&with_genres=" + genreId + "&language=" + language + "&sort_by=vote_average.desc" + "&page=" + i, HttpMethod.GET, entity, String.class);
            } else if (releaseDate != null) {
                responseEntity = restTemplate.exchange(MOVIE_API + "/discover/movie" + "?api_key=" + KEY + "&primary_release_date.gte=" + releaseDate + "&sort_by=vote_average.desc" + "&page=" + i, HttpMethod.GET, entity, String.class);
            } else if (searchGenre != "") {
                int genreId = 0;
                for (Integer key : genreIdToName.keySet()) {
                    if (genreIdToName.get(key).equalsIgnoreCase(searchGenre)) {
                        genreId = key;
                    }
                }
                responseEntity = restTemplate.exchange(MOVIE_API + "/discover/movie" + "?api_key=" + KEY + "&with_genres=" + genreId + "&sort_by=vote_average.desc" + "&page=" + i, HttpMethod.GET, entity, String.class);
            } else if (language != "") {
                responseEntity = restTemplate.exchange(MOVIE_API + "/discover/movie" + "?api_key=" + KEY + "&language=" + language + "&sort_by=vote_average.desc" + "&page=" + i, HttpMethod.GET, entity, String.class);
            }
            try {
                jsonNode = mapper.readTree(responseEntity.getBody()).path("results");
                movies.addAll(mapResultSetToDto(jsonNode));
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }

    public List<MovieDto> getRecommendations(List<Integer> movieIds) {
        List<MovieDto> movies = new ArrayList<>();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        int size = movieIds.size() >= 10 ? 10 : movieIds.size();
        for(int i = 0; i <= size; i++) {
            ResponseEntity<String> responseEntity = restTemplate.exchange(MOVIE_API + "/movie/" + movieIds.get(i) + "/recommendations" + "?api_key=" + KEY, HttpMethod.GET, entity, String.class);

            try {
                jsonNode = mapper.readTree(responseEntity.getBody()).path("results");
                movies.addAll(mapNodeArrayToDto(jsonNode));

            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return movies;
    }

    public List<MovieDto> mapNodeArrayToDto(JsonNode jsonNode) {
        List<MovieDto> movies = new ArrayList<>();
        int size = jsonNode.size() > 10 ? 10 : jsonNode.size();
        for (int i = 0; i < size; i++){
            MovieDto movie = new MovieDto();
            movie.setTitle(jsonNode.get(i).path("original_title").asText());
            movie.setLanguage(jsonNode.get(i).path("original_language").asText());

            JsonNode array = jsonNode.get(i).path("genre_ids");
            String genreList = "";
            for (int j = 0; j < array.size(); j++){
                int genre = array.get(j).asInt();
                if(j == (array.size()-1)){
                    genreList += genreIdToName.get(genre);
                }
                else {
                    genreList += genreIdToName.get(genre) + ", ";
                }
            }
            movie.setGenres(genreList);
            String release = jsonNode.get(i).path("release_date").asText().equals("") ? "Unreleased" : jsonNode.get(i).path("release_date").asText().substring(5,10) + "-" + jsonNode.get(i).path("release_date").asText().substring(0,4);
            movie.setReleaseDate(release);
            movie.setDescription(jsonNode.get(i).path("overview").asText());
            movie.setMovieId(jsonNode.get(i).path("id").asInt());
            movie.setPosterPath(jsonNode.get(i).path("poster_path").asText());
            movie.setBackdropPath(jsonNode.get(i).path("backdrop_path").asText());
            movie.setRating(jsonNode.get(i).path("vote_average").asDouble());
            movies.add(movie);
        }
        return movies;
    }

    public List<MovieTableDto> mapResultSetToDto(JsonNode jsonNode){
        List<MovieTableDto> movies = new ArrayList<>();
        for (int i = 0; i < jsonNode.size(); i++) {
            String genreList = "";
            MovieTableDto movie = new MovieTableDto();
            movie.setTitle(jsonNode.get(i).path("original_title").asText());
            movie.setLanguage(jsonNode.get(i).path("original_language").asText());
            JsonNode array = jsonNode.get(i).path("genre_ids");
            String release = jsonNode.get(i).path("release_date").asText().equals("") ? "Unreleased" : jsonNode.get(i).path("release_date").asText().substring(5,10) + "-" + jsonNode.get(i).path("release_date").asText().substring(0,4);
            movie.setReleaseDate(release);
            movie.setDescription(jsonNode.get(i).path("overview").asText());
            movie.setRating(jsonNode.get(i).path("vote_average").asDouble());
            movie.setMovieId(jsonNode.get(i).path("id").asInt());
            for (int j = 0; j < array.size(); j++) {
                int genre = array.get(j).asInt();
                if(j == (array.size()-1)){
                    genreList += genreIdToName.get(genre);
                }
                else {
                    genreList += genreIdToName.get(genre) + ", ";
                }
            }
            movie.setGenres(genreList);
            movies.add(movie);
        }

        return movies;
    }
    /*public List<Movie> getRandomMovies(int count) {
        List<Movie> randomMovies = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // Generate a random movie_id
            int randomMovieId = generateRandomMovieId();

            String apiKey = "your_api_key";
            String apiUrl = "https://api.themoviedb.org/3/movie/" + randomMovieId + "?api_key=" + apiKey;

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    Movie movie = parseMovie(jsonResponse);
                    randomMovies.add(movie);
                } else {
                    // Handle error case
                    // ...
                }
            } catch (IOException | JSONException e) {
                // Handle exception
                // ...
            }
        }

        return randomMovies;
    }


    private int generateRandomMovieId() {
            // Define the range of movie IDs
            int minMovieId = 1;
            int maxMovieId = 10000;

            // Generate a random movie ID within the range
            int randomMovieId = (int) (Math.random() * (maxMovieId - minMovieId + 1)) + minMovieId;

            return randomMovieId;
    }


    private Movie parseMovie(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String title = jsonObject.getString("title");
        // Parse other movie properties as needed

        return new Movie(id, title);
    }*/
}

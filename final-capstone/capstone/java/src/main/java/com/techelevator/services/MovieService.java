package com.techelevator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Movie;
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

    public Movie getMovie(int movieId) {
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        ResponseEntity<String> responseEntity = restTemplate.exchange(MOVIE_API + "/movie/" + movieId + "?api_key=" + KEY, HttpMethod.GET, entity, String.class);
        Movie movie = new Movie();

        try{
            jsonNode = mapper.readTree(responseEntity.getBody());
            movie.setTitle(jsonNode.path("original_title").asText());
            movie.setLanguage(jsonNode.path("original_language").asText());
            JsonNode array = jsonNode.path("genres");

            List<String> genreList = new ArrayList<>();
            for (int i = 0; i < array.size(); i++){
                String genre = array.get(i).path("name").asText();
                genreList.add(genre);
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

    public List<Movie> getPageOfMovies(int page) {
        List<Movie> movies = new ArrayList<>();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        ResponseEntity<String> responseEntity = restTemplate.exchange(MOVIE_API + "/discover/movie" + "?api_key=" + KEY + "&page=" + page + "&language=en-US", HttpMethod.GET, entity, String.class);

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

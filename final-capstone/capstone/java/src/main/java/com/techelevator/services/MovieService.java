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
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class MovieService {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM uuu");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
    }

    @Value("${MOVIE_API}")
    private String MOVIE_API;

    @Value("${KEY}")
    private String KEY;

    RestTemplate restTemplate = new RestTemplate();

    public Movie getMovie(){
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
}

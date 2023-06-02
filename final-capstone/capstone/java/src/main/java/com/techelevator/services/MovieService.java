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
import java.util.ArrayList;
import java.util.List;


@Service
public class MovieService {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM uuu");
    HttpHeaders headers = new HttpHeaders();


    @Value("${MOVIE_API}")
    private String MOVIE_API;

    @Value("${KEY}")
    private String KEY;

    RestTemplate restTemplate = new RestTemplate();

    public Movie getMovie(){
        //headers.setBearerAuth(KEY);
        int id = (int)Math.floor(Math.random() * 1000);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        ResponseEntity<String> responseEntity = restTemplate.exchange(MOVIE_API + "/" + id + "?api_key=" + KEY, HttpMethod.GET, entity, String.class);
        Movie movie = new Movie();

        try{
            jsonNode = mapper.readTree(responseEntity.getBody());
            movie.setTitle(jsonNode.path("original_title").asText());
            movie.setLanguage(jsonNode.path("original_language").asText());
            JsonNode array = jsonNode.path("genres");

            List<String> genreList = new ArrayList<>();
            for (int i = 0; i < array.size(); i++){
                JsonNode genreNode = array.get(i);
                genreList.add(genreNode.get("name").asText());
            }

            movie.setRuntime(jsonNode.path("runtime").asInt());
            movie.setReleaseDate(new SimpleDateFormat("dd/MM/yyyy").parse(jsonNode.path("release_date").asText()));
            movie.setDescription(jsonNode.path("overview").asText());
            movie.setListOfGenres(genreList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return movie;
    }
}

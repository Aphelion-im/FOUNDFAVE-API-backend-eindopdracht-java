package online.foundfave.foundfaveapi.controllers;

import online.foundfave.foundfaveapi.dtos.output.MovieOutputDto;
import online.foundfave.foundfaveapi.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Basic CRUD methods


    // Repository methods
    @GetMapping(value = "/search")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitle(@RequestParam("title") String title) {
        List<MovieOutputDto> movies = movieService.findMoviesByTitle(title);
        return ResponseEntity.ok(movies);
    }

    // TODO: findByAgeOrderByLastnameDesc


    // Relational methods




    // Image methods

}

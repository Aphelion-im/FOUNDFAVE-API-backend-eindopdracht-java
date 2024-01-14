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
// TODO: Get all movies

    // TODO: find movie by id

    // TODO: Add movie

    // TODO: Update movie

    // TODO: Delete movie


    // Repository methods
    @GetMapping(value = "/search")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitle(@RequestParam("title") String title) {
        List<MovieOutputDto> movies = movieService.findMoviesByTitle(title);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search/asc")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitleSortedAsc(@RequestParam("title") String title) {
        List<MovieOutputDto> movies = movieService.findMovieByTitleSortedAsc(title);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search/desc")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitleSortedDesc(@RequestParam("title") String title) {
        List<MovieOutputDto> movies = movieService.findMovieByTitleSortedDesc(title);
        return ResponseEntity.ok(movies);
    }

    // TODO: Search by movieYearOfRelease


    // Relational methods
// TODO: Add movie to character or vice versa


    // Image methods
    // TODO: Add image to movie
    // TODO: Delete image from movie


}

package online.foundfave.foundfaveapi.controllers;

import jakarta.validation.Valid;
import online.foundfave.foundfaveapi.dtos.input.MovieInputDto;
import online.foundfave.foundfaveapi.dtos.output.MovieOutputDto;
import online.foundfave.foundfaveapi.services.MovieService;
import online.foundfave.foundfaveapi.utilities.FieldErrorHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Basic CRUD methods
    @GetMapping(value = "")
    public ResponseEntity<List<MovieOutputDto>> getMovies() {
        List<MovieOutputDto> movieOutputDtosCollection = movieService.getMovies();
        return ResponseEntity.ok().body(movieOutputDtosCollection);
    }

    @GetMapping(value = "/{movieId}")
    public ResponseEntity<MovieOutputDto> getMovie(@PathVariable("movieId") Long movieId) {
        MovieOutputDto optionalMovie = movieService.getMovie(movieId);
        return ResponseEntity.ok().body(optionalMovie);
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createMovie(@Valid @RequestBody MovieInputDto movieInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        String newMovie = movieService.createMovie(movieInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newMovie).toUriString());
        return ResponseEntity.created(uri).body("Movie: " + "'" + newMovie + "'" + " registered successfully!");
    }

    @PutMapping(value = "/movie/{movieId}")
    public ResponseEntity<String> updateMovie(@PathVariable("movieId") Long movieId, @RequestBody MovieInputDto movieInputDto) {
        return ResponseEntity.ok(movieService.updateMovie(movieId, movieInputDto));
    }

    @DeleteMapping(value = "/{movieId}")
    public ResponseEntity<Object> deleteMovie(@PathVariable("movieId") Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.status(HttpStatus.OK).body("Movie with id: " + movieId + " deleted!");
    }

    // Repository methods
    @GetMapping(value = "/search/starting-with")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitleStartingWith(@RequestParam("title") String title) {
        List<MovieOutputDto> movies = movieService.findMoviesByTitleStartingWith(title);
        return ResponseEntity.ok(movies);
    }

    @GetMapping(value = "/search/contains")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitleContains(@RequestParam("title") String title) {
        List<MovieOutputDto> movies = movieService.findMoviesByTitleContains(title);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search/sorted-asc")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitleSortedAsc(@RequestParam("title") String title) {
        List<MovieOutputDto> movies = movieService.findMovieByTitleSortedAsc(title);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search/sorted-desc")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitleSortedDesc(@RequestParam("title") String title) {
        List<MovieOutputDto> movies = movieService.findMovieByTitleSortedDesc(title);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search/year")
    public ResponseEntity<List<MovieOutputDto>> findMovieByYearOfRelease(@RequestParam("year") String year) {
        List<MovieOutputDto> movies = movieService.findMovieByYearOfRelease(year);
        return ResponseEntity.ok(movies);
    }

    // Relational methods
// TODO: Add movie to character or vice versa


    // Image methods
    // TODO: Add image to movie
    // TODO: Delete image from movie


}

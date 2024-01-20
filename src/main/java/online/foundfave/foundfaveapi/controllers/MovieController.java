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
    @GetMapping("")
    public ResponseEntity<List<MovieOutputDto>> getMovies() {
        List<MovieOutputDto> movieOutputDtoList = movieService.getMovies();
        return ResponseEntity.ok().body(movieOutputDtoList);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieOutputDto> getMovieById(@PathVariable("movieId") Long movieId) {
        MovieOutputDto movieOutputDto = movieService.getMovieById(movieId);
        return ResponseEntity.ok().body(movieOutputDto);
    }

    @PostMapping("")
    public ResponseEntity<Object> createMovie(@Valid @RequestBody MovieInputDto movieInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        Long newMovieId = movieService.createMovie(movieInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newMovieId).toUriString());
        return ResponseEntity.created(uri).body("New movie added with id: " + newMovieId + ".");
    }

    @PutMapping("/movie/{movieId}")
    public ResponseEntity<String> updateMovie(@PathVariable("movieId") Long movieId, @Valid @RequestBody MovieInputDto movieInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.showFieldErrors(bindingResult));
        }
        return ResponseEntity.ok(movieService.updateMovie(movieId, movieInputDto));
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Object> deleteMovie(@PathVariable("movieId") Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.status(HttpStatus.OK).body("Movie with id: " + movieId + " deleted!");
    }

    // Repository methods
    @GetMapping("/search/starting-with")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitleStartingWith(@RequestParam("title") String title) {
        List<MovieOutputDto> movieOutputDtoList = movieService.findMoviesByTitleStartingWith(title);
        return ResponseEntity.ok(movieOutputDtoList);
    }

    @GetMapping("/search/contains")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitleContains(@RequestParam("title") String title) {
        List<MovieOutputDto> movieOutputDtoList = movieService.findMoviesByTitleContains(title);
        return ResponseEntity.ok(movieOutputDtoList);
    }

    @GetMapping("/search/sorted-asc")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitleSortedAsc(@RequestParam("title") String title) {
        List<MovieOutputDto> movieOutputDtoList = movieService.findMovieByTitleSortedAsc(title);
        return ResponseEntity.ok(movieOutputDtoList);
    }

    @GetMapping("/search/sorted-desc")
    public ResponseEntity<List<MovieOutputDto>> findMovieByTitleSortedDesc(@RequestParam("title") String title) {
        List<MovieOutputDto> movieOutputDtoList = movieService.findMovieByTitleSortedDesc(title);
        return ResponseEntity.ok(movieOutputDtoList);
    }

    @GetMapping("/search/year")
    public ResponseEntity<List<MovieOutputDto>> findMovieByYearOfRelease(@RequestParam("year") String year) {
        List<MovieOutputDto> movieOutputDtoList = movieService.findMovieByYearOfRelease(year);
        return ResponseEntity.ok(movieOutputDtoList);
    }

    // Relational methods
    // TODO: Add movie to character or vice versa


    // Image methods
    // TODO: Add image to movie
    // TODO: Delete image from movie
}

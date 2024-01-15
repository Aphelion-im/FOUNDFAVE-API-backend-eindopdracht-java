package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.input.MovieInputDto;
import online.foundfave.foundfaveapi.dtos.input.UserInputDto;
import online.foundfave.foundfaveapi.dtos.output.MovieOutputDto;
import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.exceptions.MovieAlreadyExistsException;
import online.foundfave.foundfaveapi.exceptions.MovieNotFoundException;
import online.foundfave.foundfaveapi.exceptions.UsernameNotFoundException;
import online.foundfave.foundfaveapi.models.Movie;
import online.foundfave.foundfaveapi.models.User;
import online.foundfave.foundfaveapi.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Basic CRUD methods
    public List<MovieOutputDto> getMovies() {
        List<MovieOutputDto> collection = new ArrayList<>();
        List<Movie> list = movieRepository.findAll();
        for (Movie movie : list) {
            collection.add(transformMovieToMovieOutputDto(movie));
        }
        return collection;
    }

    public MovieOutputDto getMovie(Long movieId) {
        MovieOutputDto movieOutputDto;
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            movieOutputDto = transformMovieToMovieOutputDto(movie.get());
        } else {
            throw new MovieNotFoundException("Movie with id: " + movieId + " not found!");
        }
        return movieOutputDto;
    }

    public String createMovie(MovieInputDto movieInputDto) {
        Optional<Movie> movie = movieRepository.findByMovieTitleIgnoreCase(movieInputDto.movieTitle);
        if (movie.isPresent()) {
            throw new MovieAlreadyExistsException("Movie: " + "'" + movieInputDto.movieTitle + "'" + " already exists!");
        }
        Movie newMovie = movieRepository.save(transformMovieInputDtoToMovie(movieInputDto));
        return newMovie.getMovieTitle();
    }

    public String updateMovie(long movieId, MovieInputDto movieInputDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + movieId + "!"));
        if (movieInputDto.movieTitle != null) movie.setMovieTitle(movieInputDto.getMovieTitle());
        if (movieInputDto.movieSummary != null) movie.setMovieSummary(movieInputDto.getMovieSummary());
        if (movieInputDto.movieYearOfRelease != null)
            movie.setMovieYearOfRelease(movieInputDto.getMovieYearOfRelease());
        if (movieInputDto.movieSummary != null) movie.setMovieImageUrl(movieInputDto.getMovieImageUrl());
        movieRepository.save(movie);
        return "Movie with id: " + movieId + " updated successfully!";
    }

    public void deleteMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new MovieNotFoundException("Movie with id: " + movieId + " not found!");
        }
        movieRepository.deleteById(movieId);
    }

    // Repository methods
    public List<MovieOutputDto> findMoviesByTitleStartingWith(String title) {
        List<MovieOutputDto> collection = new ArrayList<>();
        List<Movie> list = movieRepository.findByMovieTitleStartingWithIgnoreCase(title);
        for (Movie movie : list) {
            collection.add(transformMovieToMovieOutputDto(movie));
        }
        if (collection.isEmpty()) {
            throw new MovieNotFoundException("0 results. No movies were found!");
        }
        return collection;
    }

    public List<MovieOutputDto> findMoviesByTitleContains(String title) {
        List<MovieOutputDto> collection = new ArrayList<>();
        List<Movie> list = movieRepository.findByMovieTitleContainsIgnoreCase(title);
        for (Movie movie : list) {
            collection.add(transformMovieToMovieOutputDto(movie));
        }
        if (collection.isEmpty()) {
            throw new MovieNotFoundException("0 results. No movies were found!");
        }
        return collection;
    }

    public List<MovieOutputDto> findMovieByTitleSortedDesc(String title) {
        List<MovieOutputDto> collection = new ArrayList<>();
        List<Movie> list = movieRepository.findByMovieTitleStartingWithIgnoreCaseOrderByMovieTitleDesc(title);
        for (Movie movie : list) {
            collection.add(transformMovieToMovieOutputDto(movie));
        }
        if (collection.isEmpty()) {
            throw new MovieNotFoundException("0 results. No movies were found!");
        }
        return collection;
    }

    public List<MovieOutputDto> findMovieByTitleSortedAsc(String title) {
        List<MovieOutputDto> collection = new ArrayList<>();
        List<Movie> list = movieRepository.findByMovieTitleStartingWithIgnoreCaseOrderByMovieTitleAsc(title);
        for (Movie movie : list) {
            collection.add(transformMovieToMovieOutputDto(movie));
        }
        if (collection.isEmpty()) {
            throw new MovieNotFoundException("0 results. No movies were found!");
        }
        return collection;
    }

    public List<MovieOutputDto> findMovieByYearOfRelease(String year) {
        List<MovieOutputDto> collection = new ArrayList<>();
        List<Movie> list = movieRepository.findByMovieYearOfRelease(year);
        for (Movie movie : list) {
            collection.add(transformMovieToMovieOutputDto(movie));
        }
        if (collection.isEmpty()) {
            throw new MovieNotFoundException("0 results. No movies were found!");
        }
        return collection;
    }


    // Relational methods


    // Image methods


    // Transformers
    // Movie to MovieOutputDto
    public static MovieOutputDto transformMovieToMovieOutputDto(Movie movie) {
        var movieOutputDto = new MovieOutputDto();
        movieOutputDto.movieId = movie.getMovieId();
        movieOutputDto.movieTitle = movie.getMovieTitle();
        movieOutputDto.movieSummary = movie.getMovieSummary();
        movieOutputDto.movieYearOfRelease = movie.getMovieYearOfRelease();
        movieOutputDto.movieImageUrl = movie.getMovieImageUrl();
        return movieOutputDto;
    }

    // From MovieInputDto to MovieUser
    public Movie transformMovieInputDtoToMovie(MovieInputDto movieInputDto) {
        var movie = new Movie();
        movie.setMovieTitle(movieInputDto.getMovieTitle());
        movie.setMovieSummary(movieInputDto.getMovieSummary());
        movie.setMovieYearOfRelease(movieInputDto.getMovieYearOfRelease());
        movie.setMovieImageUrl(movieInputDto.getMovieImageUrl());
        return movie;
    }
}

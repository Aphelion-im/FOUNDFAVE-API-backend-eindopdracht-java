package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.input.MovieInputDto;
import online.foundfave.foundfaveapi.dtos.output.MovieOutputDto;
import online.foundfave.foundfaveapi.exceptions.BadRequestException;
import online.foundfave.foundfaveapi.exceptions.MovieAlreadyExistsException;
import online.foundfave.foundfaveapi.exceptions.MovieNotFoundException;
import online.foundfave.foundfaveapi.models.Movie;
import online.foundfave.foundfaveapi.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        List<Movie> movieList = movieRepository.findAll();
        for (Movie movie : movieList) {
            collection.add(transformMovieToMovieOutputDto(movie));
        }
        return collection;
    }

    public MovieOutputDto getMovieById(Long movieId) {
        MovieOutputDto movieOutputDto;
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if (optionalMovie.isPresent()) {
            movieOutputDto = transformMovieToMovieOutputDto(optionalMovie.get());
        } else {
            throw new MovieNotFoundException("Movie with id: " + movieId + " not found!");
        }
        return movieOutputDto;
    }

    public Long createMovie(MovieInputDto movieInputDto) {
        Optional<Movie> optionalMovie = movieRepository.findByMovieTitleIgnoreCase(movieInputDto.movieTitle);
        if (optionalMovie.isPresent()) {
            throw new MovieAlreadyExistsException("Movie: " + "'" + movieInputDto.movieTitle + "'" + " already exists!");
        }
        Movie newMovie = movieRepository.save(transformMovieInputDtoToMovie(movieInputDto));
        return newMovie.getMovieId();
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
        try {
            movieRepository.deleteById(movieId);
        } catch (Exception e) {
            throw new BadRequestException("You are not allowed to delete this movie as it is still linked to a character or characters!");
        }
    }

    // Repository methods
    public List<MovieOutputDto> findMoviesByTitleStartingWith(String title) {
        List<MovieOutputDto> movieOutputDtoList = new ArrayList<>();
        List<Movie> movieList = movieRepository.findByMovieTitleStartingWithIgnoreCase(title);
        for (Movie movie : movieList) {
            movieOutputDtoList.add(transformMovieToMovieOutputDto(movie));
        }
        if (movieOutputDtoList.isEmpty()) {
            throw new MovieNotFoundException("0 results. No movies were found!");
        }
        return movieOutputDtoList;
    }

    public List<MovieOutputDto> findMoviesByTitleContains(String title) {
        List<MovieOutputDto> movieOutputDtoList = new ArrayList<>();
        List<Movie> movieList = movieRepository.findByMovieTitleContainsIgnoreCase(title);
        for (Movie movie : movieList) {
            movieOutputDtoList.add(transformMovieToMovieOutputDto(movie));
        }
        if (movieOutputDtoList.isEmpty()) {
            throw new MovieNotFoundException("0 results. No movies were found!");
        }
        return movieOutputDtoList;
    }

    public List<MovieOutputDto> findMovieByTitleSortedDesc(String title) {
        List<MovieOutputDto> movieOutputDtoList = new ArrayList<>();
        List<Movie> movieList = movieRepository.findByMovieTitleStartingWithIgnoreCaseOrderByMovieTitleDesc(title);
        for (Movie movie : movieList) {
            movieOutputDtoList.add(transformMovieToMovieOutputDto(movie));
        }
        if (movieOutputDtoList.isEmpty()) {
            throw new MovieNotFoundException("0 results. No movies were found!");
        }
        return movieOutputDtoList;
    }

    public List<MovieOutputDto> findMovieByTitleSortedAsc(String title) {
        List<MovieOutputDto> movieOutputDtoList = new ArrayList<>();
        List<Movie> movieList = movieRepository.findByMovieTitleStartingWithIgnoreCaseOrderByMovieTitleAsc(title);
        for (Movie movie : movieList) {
            movieOutputDtoList.add(transformMovieToMovieOutputDto(movie));
        }
        if (movieOutputDtoList.isEmpty()) {
            throw new MovieNotFoundException("0 results. No movies were found!");
        }
        return movieOutputDtoList;
    }

    public List<MovieOutputDto> findMovieByYearOfRelease(String year) {
        List<MovieOutputDto> movieOutputDtoList = new ArrayList<>();
        List<Movie> movieList = movieRepository.findByMovieYearOfRelease(year);
        for (Movie movie : movieList) {
            movieOutputDtoList.add(transformMovieToMovieOutputDto(movie));
        }
        if (movieOutputDtoList.isEmpty()) {
            throw new MovieNotFoundException("0 results. No movies were found!");
        }
        return movieOutputDtoList;
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
        movieOutputDto.charactersList = movie.getCharactersList();
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

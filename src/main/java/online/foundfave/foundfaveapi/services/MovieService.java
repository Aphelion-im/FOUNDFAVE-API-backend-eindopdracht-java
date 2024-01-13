package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.output.MovieOutputDto;
import online.foundfave.foundfaveapi.exceptions.MovieNotFoundException;
import online.foundfave.foundfaveapi.models.Movie;
import online.foundfave.foundfaveapi.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Basic CRUD methods








    // Repository methods
    public List<MovieOutputDto> findMoviesByTitle(String title) {
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


}

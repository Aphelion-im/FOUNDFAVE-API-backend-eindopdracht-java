package online.foundfave.foundfaveapi.services;

import online.foundfave.foundfaveapi.dtos.output.MovieOutputDto;
import online.foundfave.foundfaveapi.exceptions.NoMoviesFoundException;
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
    // Relational methods
    // Image methods

    public List<MovieOutputDto> findMoviesByTitle(String title) {
        List<MovieOutputDto> collection = new ArrayList<>();
        List<Movie> list = movieRepository.findByMovieTitleStartingWithIgnoreCase(title);
        for (Movie movie : list) {
            collection.add(transformMovieToMovieOutputDto(movie));
        }
        if (collection.isEmpty()) {
            throw new NoMoviesFoundException("0 results. No movies were found!");
        }
        return collection;
    }


    // Transformers
    // Movie to MovieOutputDto
    public static MovieOutputDto transformMovieToMovieOutputDto(Movie movie) {
        var movieOutputDto = new MovieOutputDto();
        movieOutputDto.movieId = movie.getMovieId();
        movieOutputDto.movieTitle = movie.getMovieTitle();
        return movieOutputDto;
    }


}

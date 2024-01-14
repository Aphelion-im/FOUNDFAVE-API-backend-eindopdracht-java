package online.foundfave.foundfaveapi.repositories;

import online.foundfave.foundfaveapi.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByMovieTitleStartingWithIgnoreCase(String title);
    List<Movie> findByMovieTitleContainsIgnoreCase(String title);
    List<Movie> findByMovieTitleStartingWithIgnoreCaseOrderByMovieTitleAsc(String title);
    List<Movie> findByMovieTitleStartingWithIgnoreCaseOrderByMovieTitleDesc(String title);
}

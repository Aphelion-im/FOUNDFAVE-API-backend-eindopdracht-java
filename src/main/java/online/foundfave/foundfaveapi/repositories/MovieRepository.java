package online.foundfave.foundfaveapi.repositories;

import online.foundfave.foundfaveapi.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByMovieTitleStartingWithIgnoreCase(String title);
}

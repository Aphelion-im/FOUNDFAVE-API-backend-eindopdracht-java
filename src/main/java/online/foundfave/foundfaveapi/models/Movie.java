package online.foundfave.foundfaveapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    private String movieTitle;
    private String movieSummary;
    private String movieYearOfRelease;
    private String movieImageUrl;
    private String fileName;

    @ManyToMany(mappedBy = "moviesList")
    @JsonIgnore
    private List<Character> charactersList;
}

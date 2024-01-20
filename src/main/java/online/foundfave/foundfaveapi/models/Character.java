package online.foundfave.foundfaveapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.foundfave.foundfaveapi.enums.Gender;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;

    private String characterAliasName;
    private String characterRealName;
    private String characterActorName;
    private String characterTitle;

    @Enumerated(EnumType.STRING)
    private Gender characterGender;

    private String characterSummary;
    private String characterDescription;
    private String characterImageUrl;

    // Relations
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "characters_movies",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> moviesList;






//    @ManyToOne
//    @JoinColumn(name = "users_username") // TODO: user_username of users_username?
//    @JsonIgnore
//    private User user;
}

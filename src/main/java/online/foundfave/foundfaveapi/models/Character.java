package online.foundfave.foundfaveapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.foundfave.foundfaveapi.enums.Gender;

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
//    @ManyToOne
//    @JoinColumn(name = "users_username") // TODO: user_username of users_username?
//    @JsonIgnore
//    User user;
}

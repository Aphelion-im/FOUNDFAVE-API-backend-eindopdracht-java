package online.foundfave.foundfaveapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    // TODO: Hier nog andere annotaties op de velden?
    private String characterAliasName;
    private String characterRealName;
    private String characterActorName;
    private String characterTitle;

    @Enumerated(EnumType.STRING) // TODO: Moet dat hier staan?
    private Gender characterGender; // TODO: Gender Enum instellen en testen in database. Blijkbaar alleen in model de annotatie

    private String characterSummary;
    private String characterDescription;
    private String characterImageUrl;

    // Relations
    @ManyToOne
    @JoinColumn(name = "users_username") // TODO: user_username of users_username?
    @JsonIgnore
    User user;
}

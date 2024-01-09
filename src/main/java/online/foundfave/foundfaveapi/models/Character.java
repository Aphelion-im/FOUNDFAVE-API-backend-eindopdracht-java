package online.foundfave.foundfaveapi.models;

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
    @Column(nullable = false, unique = true)
    private Long characterId;

    // TODO: Hier nog andere annotaties op de velden?
    private String characterAliasName;
    private String characterRealName;
    private String characterActorName;
    private String characterTitle;
    private String characterGender;
    private String characterSummary;
    private String characterDescription;
    private String characterImageUrl;

    // Relations
    @ManyToOne
    @JoinColumn(name = "users_username")
    User user;
}

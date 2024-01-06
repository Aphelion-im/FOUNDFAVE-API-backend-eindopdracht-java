package online.foundfave.foundfaveapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contact_forms")
public class ContactForm {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String comments;
    private Date submissionDate;
    private Time submissionTime;


    // Relations
    @ManyToOne
    @JoinColumn(name = "user_username")
    @JsonIgnore
    private User user;

}

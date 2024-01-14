package online.foundfave.foundfaveapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contact_forms")
public class ContactForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactFormId;

    private String name;
    private String email;
    private String comments;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    public LocalDateTime timeStamp;

    // Relations
//    @ManyToOne
//    @JoinColumn(name = "user_username") // TODO: user_username of users_username?
//    @JsonIgnore
//    private User user;

}

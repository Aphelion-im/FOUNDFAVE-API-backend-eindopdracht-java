package online.foundfave.foundfaveapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    public LocalDateTime timeStamp;



    // Relations
    @ManyToOne
    @JoinColumn(name = "user_username")
    @JsonIgnore
    private User user;

}

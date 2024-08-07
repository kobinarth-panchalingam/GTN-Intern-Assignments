package gtn.kobi.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String hobbyName;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private Users user;

    public Hobby() {
    }
    public Hobby(String hobbyName, Users user) {
        this.hobbyName = hobbyName;
        this.user = user;
    }


}

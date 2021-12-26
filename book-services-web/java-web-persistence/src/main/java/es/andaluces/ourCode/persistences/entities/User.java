package es.andaluces.ourCode.persistences.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @Column(unique=true)
    private String nick;

    private String email;
}

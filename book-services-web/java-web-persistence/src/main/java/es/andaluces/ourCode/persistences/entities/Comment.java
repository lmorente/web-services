package es.andaluces.ourCode.persistences.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String content;

    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
}

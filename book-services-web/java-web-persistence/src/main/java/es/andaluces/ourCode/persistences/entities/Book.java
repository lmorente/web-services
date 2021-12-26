package es.andaluces.ourCode.persistences.entities;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String summary;

    private String author;

    private String publisher;

    private LocalDate published;

    @OneToMany(mappedBy="book", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setBook(this);
    }
    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setBook(null);
    }

    public void removeAllComments() {
        this.comments.forEach(c -> removeComment(c));
    }
}

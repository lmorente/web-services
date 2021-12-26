package es.andaluces.ourCode.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class BookWithComments extends Book {

    private AtomicLong bookIdentificator = new AtomicLong(1L);

    @JsonView(ViewBook.Complete.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Comment> comments;

    public BookWithComments(Long id, String title, String summary, String author, String publisher, LocalDate published) {
        super(id, title, summary, author, publisher, published);
        this.comments = Collections.synchronizedList(new ArrayList<>());
    }

    public BookWithComments() {
        super();
        this.comments = Collections.synchronizedList(new ArrayList<>());
    }


    public void addComment(Comment comment) {
        final Long id = bookIdentificator.incrementAndGet();
        comment.setId(id);
        comments.add(comment);
    }

    public void deleteComment(Long idComment) {
        final Optional<Comment> optComment = getOptionalbyId(idComment);
        if (optComment.isPresent()) {
            comments.remove(optComment.get());
        }
    }

    public Comment getCommentById(Long idComment) {
        final Optional<Comment> optComment = getOptionalbyId(idComment);
        return optComment.isPresent() ? optComment.get() : null;
    }

    private Optional<Comment> getOptionalbyId(Long idComment) {
        return comments.stream().filter(c -> c.getId() == idComment).findFirst();
    }
}

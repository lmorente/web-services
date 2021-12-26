package es.andaluces.ourCode.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Book {

    @JsonView({ViewBook.Basic.class, ViewBook.Complete.class})
    private Long id;
    @JsonView({ViewBook.Basic.class, ViewBook.Complete.class})
    private String title;
    @JsonView(ViewBook.Complete.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String summary;
    @JsonView(ViewBook.Complete.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String author;
    @JsonView(ViewBook.Complete.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String publisher;
    @JsonView(ViewBook.Complete.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate published;

    public Book(Long id, String title, String summary, String author, String publisher, LocalDate published) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.author = author;
        this.publisher = publisher;
        this.published = published;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublished() {
        return published;
    }

    public void setPublished(LocalDate published) {
        this.published = published;
    }
}

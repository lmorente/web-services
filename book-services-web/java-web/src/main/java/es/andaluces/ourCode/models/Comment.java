package es.andaluces.ourCode.models;

public class Comment {

    private Long id;
    private String comment;
    private String user;
    private Integer score;

    public Comment(Long id, String comment, String user, Integer score) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}

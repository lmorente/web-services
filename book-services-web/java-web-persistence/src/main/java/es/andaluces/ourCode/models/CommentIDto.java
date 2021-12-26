package es.andaluces.ourCode.models;

import lombok.Data;

@Data
public class CommentIDto {

    private Long id;
    private String user;
    private String content;
    private Integer score;
}

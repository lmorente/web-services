package es.andaluces.ourCode.models;

import lombok.Data;

@Data
public class CommentODto {

    private Long id;
    private Long idBook;
    private UserDto user;
    private String content;
    private Integer score;
}

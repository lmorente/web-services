package es.andaluces.ourCode.service;

import es.andaluces.ourCode.models.BookDto;
import es.andaluces.ourCode.models.CommentIDto;
import es.andaluces.ourCode.models.CommentODto;
import java.util.Collection;

public interface CommentService {

    Collection<CommentODto> getAllCommentsByUser(String nick);

    CommentODto getComment(Long idBook, Long idComment);

    void deleteComment(Long idBook, Long idComment);

    BookDto saveComment(Long idBook, CommentIDto comment);
}

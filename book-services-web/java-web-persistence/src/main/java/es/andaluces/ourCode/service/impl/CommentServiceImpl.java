package es.andaluces.ourCode.service.impl;

import es.andaluces.ourCode.models.BookDto;
import es.andaluces.ourCode.models.CommentIDto;
import es.andaluces.ourCode.models.CommentODto;
import es.andaluces.ourCode.persistences.entities.Book;
import es.andaluces.ourCode.persistences.entities.Comment;
import es.andaluces.ourCode.persistences.entities.User;
import es.andaluces.ourCode.persistences.repositories.BookRepository;
import es.andaluces.ourCode.persistences.repositories.CommentRepository;
import es.andaluces.ourCode.persistences.repositories.UserRepository;
import es.andaluces.ourCode.service.CommentService;
import es.andaluces.ourCode.transformer.BookMapper;
import es.andaluces.ourCode.transformer.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Collection<CommentODto> getAllCommentsByUser(String nick) {
        return this.commentMapper.toDTOS(this.commentRepository.findAllByNick(nick));
    }

    @Override
    public void deleteComment(Long idBook, Long idComment) {
        final Comment existingComment = this.commentRepository.findByIdAndIdBook(idComment, idBook).orElseThrow();
        this.commentRepository.delete(existingComment);
    }

    @Override
    public BookDto saveComment(Long idBook, CommentIDto comment) {
        final Book existingBook = this.bookRepository.findById(idBook).orElseThrow();
        final User user = this.userRepository.findByNick(comment.getUser()).orElseThrow();
        final Comment save = this.commentRepository.save(this.commentMapper.toMO(comment, user));
        existingBook.addComment(save);
        return this.bookMapper.toDTO(this.bookRepository.save(existingBook));
    }

    @Override
    public CommentODto getComment(Long idBook, Long idComment) {
        return this.commentMapper.toODTO(this.commentRepository.findByIdAndIdBook(idComment, idBook).orElseThrow());
    }
}

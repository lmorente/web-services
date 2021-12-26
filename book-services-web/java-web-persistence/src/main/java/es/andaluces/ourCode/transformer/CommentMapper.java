package es.andaluces.ourCode.transformer;

import es.andaluces.ourCode.models.CommentIDto;
import es.andaluces.ourCode.models.CommentODto;
import es.andaluces.ourCode.persistences.entities.Comment;
import es.andaluces.ourCode.persistences.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;


@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "comment.book.id", target = "idBook")
    CommentODto toODTO(final Comment comment);

    Collection<CommentODto> toDTOS(final Collection<Comment> comments);

    @Mapping(source = "user", target = "user")
    Comment toMO(CommentIDto comment, User user);
}

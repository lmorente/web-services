package es.andaluces.ourCode.transformer;

import es.andaluces.ourCode.models.BookDto;
import es.andaluces.ourCode.persistences.entities.Book;
import org.mapstruct.Mapper;
import java.util.Collection;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface BookMapper {

    Collection<BookDto> toDTOS(final Collection<Book> books);

    Book toMO(BookDto book);

    BookDto toDTO(Book book);
}

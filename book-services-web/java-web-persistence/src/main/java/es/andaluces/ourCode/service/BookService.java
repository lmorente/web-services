package es.andaluces.ourCode.service;

import es.andaluces.ourCode.models.BookDto;
import java.util.Collection;

public interface BookService {

    Collection<BookDto> getAllBooks();

    BookDto save(BookDto book);

    BookDto getBookById(Long idBook);

    void deleteById(Long idBook);

    BookDto modify(Long idBook, BookDto book);
}



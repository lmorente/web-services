package es.andaluces.ourCode.service.impl;

import es.andaluces.ourCode.models.BookDto;
import es.andaluces.ourCode.persistences.entities.Book;
import es.andaluces.ourCode.persistences.repositories.BookRepository;
import es.andaluces.ourCode.service.BookService;
import es.andaluces.ourCode.transformer.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Collection<BookDto> getAllBooks() {
        return this.bookMapper.toDTOS(this.bookRepository.findAll());
    }

    @Override
    public BookDto save(BookDto book) {
        return this.bookMapper.toDTO(this.bookRepository.save(this.bookMapper.toMO(book)));
    }

    @Override
    public BookDto getBookById(Long id) {
        return this.bookMapper.toDTO(this.bookRepository.findById(id).orElseThrow());
    }

    @Override
    public void deleteById(Long id) {
        final Book book = this.bookRepository.findById(id).orElseThrow();
        book.removeAllComments();
        this.bookRepository.delete(this.bookRepository.findById(book.getId()).orElseThrow());
    }

    @Override
    public BookDto modify(Long idBook, BookDto book) {
        final Book existingBook = this.bookRepository.findById(idBook).orElseThrow();
        book.setId(existingBook.getId());
        this.bookRepository.save(this.bookMapper.toMO(book));
        return book;
    }
}

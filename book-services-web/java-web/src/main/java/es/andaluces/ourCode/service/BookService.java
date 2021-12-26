package es.andaluces.ourCode.service;

import es.andaluces.ourCode.models.Book;
import es.andaluces.ourCode.models.BookWithComments;
import es.andaluces.ourCode.models.Comment;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private final AtomicLong bookIdentificator = new AtomicLong(1L);
    private final ConcurrentHashMap<Long, BookWithComments> books = new ConcurrentHashMap();

    //TODO: construir datos en la clase
    public BookService() {
        this.books.put(0L, new BookWithComments(0L, "Manolito Gafotas", "La historia de un niño en carabanchel", "Perez Reverte", "Editorial Salamandra", LocalDate.parse("2018-05-05")));
        this.books.put(1L, new BookWithComments(1L, "Señor de los anillos", "Su historia se desarrolla en la Tercera Edad del " +
                "Sol de la Tierra Media, un lugar ficticio poblado por hombres y otras razas antropomorfas como los " +
                "hobbits, los elfos o los enanos, así como por muchas otras criaturas reales y fantásticas",
                "J. R. R. Tolkie", "Tirant Lo Blanch", LocalDate.parse("1954-07-29")));
    }

    public Collection<BookWithComments> getAllBooks(){
        return books.values();
    }

    public Book save(Book book) {
        final Long id = bookIdentificator.incrementAndGet();
        book.setId(id);
        this.books.put(id, buildCompleteBook(book));
        return book;
    }

    private BookWithComments buildCompleteBook(Book book) {
        final BookWithComments completedBook = new BookWithComments();
        completedBook.setId(book.getId());
        completedBook.setAuthor(book.getAuthor());
        completedBook.setPublished(book.getPublished());
        completedBook.setPublisher(book.getPublisher());
        completedBook.setSummary(book.getSummary());
        completedBook.setTitle(book.getTitle());
        return completedBook;
    }

    public BookWithComments getBookById(Long id){
        return books.containsKey(id) ? books.get(id) : null;
    }

    public void deleteById(Long id){
        this.books.remove(id);
    }

    public BookWithComments saveCommentByIdBook(Long idBook, Comment comment) {
        final BookWithComments book = this.getBookById(idBook);
        book.addComment(comment);
        return book;
    }

    public void deleteComment(Long idBook, Long idComment) {
        final BookWithComments book = this.getBookById(idBook);
        book.deleteComment(idComment);
    }

    public Comment getCommentById(Long idBook, Long idComment) {
        final BookWithComments book = this.getBookById(idBook);
        if(book != null){
            return book.getCommentById(idComment);
        }
        return null;
    }
}

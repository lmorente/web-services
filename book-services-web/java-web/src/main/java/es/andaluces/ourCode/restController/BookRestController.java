package es.andaluces.ourCode.restController;

import com.fasterxml.jackson.annotation.JsonView;
import es.andaluces.ourCode.models.ViewBook;
import es.andaluces.ourCode.models.Book;
import es.andaluces.ourCode.models.BookWithComments;
import es.andaluces.ourCode.models.Comment;
import es.andaluces.ourCode.service.BookService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/books")
@OpenAPIDefinition(info = @Info(title = "Book API REST", version = "1.0.0"))
public class BookRestController {

    private static final String BOOKS = "Books";
    private static final String COMMENTS = "Comments";

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get all the books")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all the books",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookWithComments.class),
                            examples = {@ExampleObject(
                                    name = "Book",
                                    summary = "Books info",
                                    value = "[{\"id\": 0,\n \"title\": \"Manolito Gafotas\"}, {\"id\": 1,\n \"title\": \"El señor de los anillos\"}]"
                            )}
                    )}
            )
    })
    @JsonView(ViewBook.Basic.class)
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookWithComments> getAllBooks(){
        return bookService.getAllBooks();
    }

    @Operation(summary = "Add a new book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A new book is created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The request input is incorrect",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "{}"
                            )}
                    )}
            )
    })
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        if(isBadBookInput(book)){
            return ResponseEntity.badRequest().build();
        }
        final Book savedBook = this.bookService.save(book);
        final URI location = fromCurrentRequest().path("/{id}").buildAndExpand(savedBook.getId()).toUri();
        return ResponseEntity.created(location).body(savedBook);
    }


    @Operation(summary = "Get a book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The book is retrieved by id"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The book is not found",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "{}"
                            )}
                    )}
            )
    })
    @GetMapping(value = "/{idBook}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookWithComments> getBook(@PathVariable Long idBook) {
        final BookWithComments book = this.bookService.getBookById(idBook);
        return Objects.nonNull(book) ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }


    @Operation(summary = "Delete a book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The book is deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "{}"
                            )}
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The book is not found",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "{}"
                            )}
                    )}
            )
    })
    @DeleteMapping(value = "/{idBook}")
    public ResponseEntity deleteBook(@PathVariable Long idBook) {
        if(Objects.nonNull(this.bookService.getBookById(idBook))){
            this.bookService.deleteById(idBook);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Add a new comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The comment is added"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The comment input request is wrong",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "{}"
                            )}
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The book is not found",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "{}"
                            )}
                    )}
            )
    })
    @PostMapping(value = "/{idBook}/comment", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookWithComments> addComment(@PathVariable Long idBook, @RequestBody Comment comment){
        if(isBadCommentInput(comment)){
            return ResponseEntity.badRequest().build();
        }
        if(Objects.nonNull(this.bookService.getBookById(idBook))){
            final BookWithComments book = this.bookService.saveCommentByIdBook(idBook, comment);
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The comment is deleted",
                    content = {@Content(
                    mediaType = "application/json",
                    examples = {@ExampleObject(
                            name = "Response",
                            value = "{}"
                        )}
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The comment is not found",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "{}"
                            )}
                    )}
            )
    })
    @DeleteMapping("{idBook}/comment/{idComment}")
    public ResponseEntity deleteComment(@PathVariable Long idBook, @PathVariable Long idComment){
        if(Objects.nonNull(this.bookService.getCommentById(idBook, idComment))){
            this.bookService.deleteComment(idBook, idComment);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean isBadBookInput(Book book){
        return Objects.isNull(book.getTitle());
    }

    private boolean isBadCommentInput(Comment comment){
        return Objects.isNull(comment.getComment()) || comment.getScore() > 5 || comment.getScore() < 0;
    }
}

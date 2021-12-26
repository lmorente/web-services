package es.andaluces.ourCode.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import es.andaluces.ourCode.controllers.views.ViewBook;
import es.andaluces.ourCode.models.BookDto;
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
import javax.annotation.PostConstruct;
import java.net.URI;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/books")
@OpenAPIDefinition(info = @Info(title = "Book API REST", version = "2.0.0"))
public class BookRestController {

    @Autowired
    private BookService bookService;

    @PostConstruct
    public void init() {
        this.bookService.save(BookDto.builder()
                .title("Manolito Gafotas")
                .summary("La historia de un niño en carabanchel")
                .author("Perez Reverte")
                .publisher("Editorial Salamandra")
                .published(LocalDate.parse("2018-05-05"))
                .build());
        this.bookService.save(BookDto.builder()
                .title("Señor de los anillos")
                .summary("Su historia se desarrolla en la Tercera Edad del Sol de la Tierra Media")
                .author("J. R. R. Tolkie")
                .publisher("Tirant Lo Blanch")
                .published(LocalDate.parse("1954-07-29"))
                .build());
    }

    @Operation(summary = "Get all the books")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all the books",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class),
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
    public Collection<BookDto> getAllBooks() {
        return this.bookService.getAllBooks();
    }

    @Operation(summary = "Modify book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book is modified"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The request input is incorrect",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "Element has not been found"
                            )}
                    )}
            )
    })
    @PutMapping(value = "{idBook}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> modifyBook(@PathVariable Long idBook, @RequestBody BookDto book) {
        if (isBadBookInput(book)) {
            return ResponseEntity.badRequest().build();
        }
        final BookDto savedBook = this.bookService.modify(idBook, book);
        return ResponseEntity.ok(savedBook);
    }

    @Operation(summary = "Add new book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
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
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto book) {
        if (isBadBookInput(book)) {
            return ResponseEntity.badRequest().build();
        }
        final BookDto savedBook = this.bookService.save(book);
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
                                    value = "Element has not been found"
                            )}
                    )}
            )
    })
    @GetMapping(value = "/{idBook}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> getBook(@PathVariable Long idBook) {
        return ResponseEntity.ok(this.bookService.getBookById(idBook));
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
                                    value = "Element has not been found"
                            )}
                    )}
            )
    })
    @DeleteMapping(value = "/{idBook}")
    public ResponseEntity<String> deleteBook(@PathVariable Long idBook) {
        this.bookService.deleteById(idBook);
        return ResponseEntity.ok("Book has been deleted");
    }

    private boolean isBadBookInput(BookDto book) {
        return Objects.isNull(book.getTitle());
    }
}

package es.andaluces.ourCode.controllers;

import es.andaluces.ourCode.models.BookDto;
import es.andaluces.ourCode.models.CommentIDto;
import es.andaluces.ourCode.models.CommentODto;
import es.andaluces.ourCode.service.CommentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("")
@OpenAPIDefinition(info = @Info(title = "Book API REST", version = "2.0.0"))
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "Get comments by user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The comment is added"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The comment is not found",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "Element has not been found"
                            )}
                    )}
            )
    })
    @GetMapping(value = "users/{nick}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Collection<CommentODto> getAllCommentsByUser(@PathVariable String nick) {
        return this.commentService.getAllCommentsByUser(nick);
    }

    @Operation(summary = "Add a new comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
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
                    description = "The comment is not found",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "Element has not been found"
                            )}
                    )}
            )
    })
    @PostMapping(value = "books/{idBook}/comment", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> addComment(@PathVariable Long idBook, @RequestBody CommentIDto comment) {
        if (isBadCommentInput(comment)) {
            return ResponseEntity.badRequest().build();
        }
        final BookDto save = this.commentService.saveComment(idBook, comment);
        final URI location = fromCurrentRequest()
                .path("/books/{idBook}/comment/{idComment}")
                .buildAndExpand(save.getId(), save.getComments().stream().reduce((first, second) -> second).orElse(null))
                .toUri();
        return ResponseEntity.created(location).body(save);
    }

    @Operation(summary = "Get comment")
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
                    description = "The comment is not found",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "Element has not been found"
                            )}
                    )}
            )
    })
    @GetMapping("books/{idBook}/comment/{idComment}")
    public ResponseEntity<CommentODto> getComment(@PathVariable Long idBook, @PathVariable Long idComment) {
        return ResponseEntity.ok(this.commentService.getComment(idBook, idComment));
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
                                    value = "Element has not been found"
                            )}
                    )}
            )
    })
    @DeleteMapping("books/{idBook}/comment/{idComment}")
    public ResponseEntity deleteComment(@PathVariable Long idBook, @PathVariable Long idComment) {
        if (Objects.nonNull(this.commentService.getComment(idBook, idComment))) {
            this.commentService.deleteComment(idBook, idComment);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean isBadCommentInput(CommentIDto comment) {
        return StringUtils.isAllBlank(comment.getContent()) || comment.getScore() > 5 || comment.getScore() < 0;
    }
}

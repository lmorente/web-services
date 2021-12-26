package es.andaluces.ourCode.controllers;

import es.andaluces.ourCode.exceptions.DeleteUserException;
import es.andaluces.ourCode.models.MailIDto;
import es.andaluces.ourCode.models.UserDto;
import es.andaluces.ourCode.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/users")
@OpenAPIDefinition(info = @Info(title = "Book API REST", version = "2.0.0"))
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init(){
        this.userService.save(UserDto.builder().nick("Elo").email("elo@fake.org").build());
        this.userService.save(UserDto.builder().nick("Fer").email("fer.mail@fake.org").build());
        this.userService.save(UserDto.builder().nick("Hg99").email("mail@fake.org").build());
    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User has been created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The request input is incorrect",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "Element already exists"
                            )}
                    )}
            )
    })
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        final UserDto savedUser = this.userService.save(user);
        final URI location = fromCurrentRequest().path("/{nick}").buildAndExpand(savedUser.getNick()).toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @Operation(summary = "Get user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found user"
            ),
            @ApiResponse(
                    responseCode = "404",
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
    @GetMapping(value = "/{nick}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserByNick(@PathVariable String nick) {
        return ResponseEntity.ok(this.userService.getUserByNick(nick));
    }

    @Operation(summary = "Modify mail user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Modified email"
            ),
            @ApiResponse(
                    responseCode = "404",
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
    @PatchMapping(value = "/{nick}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> modifyMailUser(@PathVariable String nick, @RequestBody MailIDto mail) {
        return ResponseEntity.ok(this.userService.modifyMail(nick, mail));
    }

    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Modified email"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The request input is incorrect",
                    content = {@Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response",
                                    value = "Element already exists"
                            )}
                    )}
            )
    })
    @DeleteMapping(value = "/{nick}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(@PathVariable String nick) throws DeleteUserException {
        this.userService.deleteUser(nick);
        return ResponseEntity.ok("User has been deleted");
    }
}

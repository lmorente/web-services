package es.andaluces.ourCode.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import es.andaluces.ourCode.controllers.views.ViewBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    @JsonView({ViewBook.Basic.class, ViewBook.Complete.class})
    private Long id;

    @JsonView({ViewBook.Basic.class, ViewBook.Complete.class})
    private String title;

    @JsonView(ViewBook.Complete.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String summary;

    @JsonView(ViewBook.Complete.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String author;

    @JsonView(ViewBook.Complete.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String publisher;

    @JsonView(ViewBook.Complete.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate published;

    @JsonView(ViewBook.Complete.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CommentODto> comments;
}

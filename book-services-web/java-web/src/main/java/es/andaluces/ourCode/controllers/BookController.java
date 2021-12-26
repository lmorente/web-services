package es.andaluces.ourCode.controllers;


import es.andaluces.ourCode.models.Book;
import es.andaluces.ourCode.models.Comment;
import es.andaluces.ourCode.service.BookService;
import es.andaluces.ourCode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    private static final String INDEX = "index";
    private static final String SHOW_BOOK = "show_book";
    private static final String SHOW_SAVED ="saved_book";
    private static final String SAVED_COMMENT ="saved_comment";
    private static final String DELETED_COMMENT ="deleted_element";

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showBooks(Model model) {
        model.addAttribute("books", this.bookService.getAllBooks());
        return INDEX;
    }

    @PostMapping("book/new")
    public String newBook(Book book) {
        this.bookService.save(book);
        return SHOW_SAVED;
    }

    @GetMapping("/book/{idBook}")
    public String showBook(@PathVariable Long idBook, Model model) {
        model.addAttribute("book", this.bookService.getBookById(idBook));
        model.addAttribute("user", this.userService.getInfo());
        return SHOW_BOOK;
    }

    @GetMapping("/book/{idBook}/delete")
    public String deleteBook(@PathVariable Long idBook) {
        this.bookService.deleteById(idBook);
        return DELETED_COMMENT;
    }

    @PostMapping("/book/{idBook}/comment")
    public String addComment(@PathVariable Long idBook, Comment comment){
        assert(comment.getScore() >= 0 && comment.getScore() <= 5);
        this.userService.setInfo(comment.getUser());
        this.bookService.saveCommentByIdBook(idBook,comment);
        return SAVED_COMMENT;
    }

    @GetMapping("/book/{idBook}/comment/{idComment}/delete")
    public String deleteComment(@PathVariable Long idBook, @PathVariable Long idComment){
        this.bookService.deleteComment(idBook, idComment);
        return DELETED_COMMENT;
    }
}
package com.task.SpringbootTask.controller;

import com.task.SpringbootTask.entity.Author;
import com.task.SpringbootTask.entity.Book;
import com.task.SpringbootTask.service.AuthorBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/author-book")
public class AuthorBookController {
    @Autowired
    AuthorBookService authorBookService;

    //To get Authors of a Book
    @GetMapping("/authors-of-book/{bookId}")
    public ResponseEntity<List<Author>> getAuthorsOfBook(@PathVariable Integer bookId){
        List<Author> authors= authorBookService.getAuthorsOfBook(bookId);
        return ResponseEntity.ok(authors);
    }

    //To get Books of an Author
    @GetMapping("/books-of-author/{authorId}")
    public ResponseEntity<List<Book>> getBooksOfAuthor(@PathVariable Integer authorId) {
        List<Book> books = authorBookService.getBooksOfAuthor(authorId);
        return ResponseEntity.ok(books);
    }
}

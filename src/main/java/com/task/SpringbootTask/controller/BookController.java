package com.task.SpringbootTask.controller;

import com.task.SpringbootTask.dto.requestDto.BookRequestDto;
import com.task.SpringbootTask.dto.responseDto.BookResponseDto;
import com.task.SpringbootTask.entity.Book;
import com.task.SpringbootTask.service.AuthorBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.task.SpringbootTask.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    AuthorBookService authorBookService;

    @PostMapping("/save")
    public ResponseEntity<BookResponseDto> saveBookInfo(@RequestBody BookRequestDto bookRequestDto){
        return new ResponseEntity<>(bookService.saveBookInfo(bookRequestDto), HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<BookResponseDto>> getAllBooks(){
        return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookInfoById(@PathVariable Integer id){
        return new ResponseEntity<>(bookService.getBookInfoById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookInfoById(@PathVariable Integer id){
        bookService.deleteBookInfoById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookInfo(@PathVariable Integer id, @RequestBody BookRequestDto dto){
        return new ResponseEntity<>(bookService.updateBookInfo(id,dto),HttpStatus.OK);
    }

    // Link an Author to a Book
    //for removing link check AuthorController
    @PostMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<String> linkAuthorToBook(@PathVariable Integer bookId, @PathVariable Integer authorId) {
        authorBookService.addAuthorToBook(authorId, bookId);
        return ResponseEntity.ok("Author linked to the book successfully.");
    }
}

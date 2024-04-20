package com.task.SpringbootTask.controller;

import com.task.SpringbootTask.dto.requestDto.AuthorRequestDto;
import com.task.SpringbootTask.dto.requestDto.BookRequestDto;
import com.task.SpringbootTask.dto.responseDto.AuthorResponseDto;
import com.task.SpringbootTask.dto.responseDto.BookResponseDto;
import com.task.SpringbootTask.service.AuthorBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.task.SpringbootTask.service.AuthorService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @Autowired
    AuthorBookService authorBookService;

    @PostMapping("/save")
    public ResponseEntity<AuthorResponseDto> saveAuthorInfo(@RequestBody AuthorRequestDto authorRequestDto){
        return new ResponseEntity<>(authorService.saveAuthorInfo(authorRequestDto), HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<AuthorResponseDto>> getAllAuthor(){
        return new ResponseEntity<>(authorService.getAllAuthor(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorInfoById(@PathVariable Integer id){
        return new ResponseEntity<>(authorService.getAuthorById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthorInfoById(@PathVariable Integer id){
        authorService.deleteAuthorById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthorInfo(@PathVariable Integer id, @RequestBody AuthorRequestDto dto){
        return new ResponseEntity<>(authorService.updateAuthorInfo(id,dto),HttpStatus.OK);
    }

    //Remove a Book from an author with end point /authors/{authorId}/books/{bookId}\
    //for linking book and author check book controller
    @DeleteMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<String> removeBookFromAuthor(@PathVariable Integer authorId, @PathVariable Integer bookId){
        try{
            authorBookService.removeAuthorFromBook(authorId, bookId);
            return ResponseEntity.ok("Book Removed from the author's list Successfully.");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

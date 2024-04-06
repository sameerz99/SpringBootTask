package com.task.SpringbootTask.controller;

import com.task.SpringbootTask.dto.requestDto.BookRequestDto;
import com.task.SpringbootTask.dto.responseDto.BookResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.task.SpringbootTask.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<BookResponseDto> saveBookInfo(@RequestBody BookRequestDto bookRequestDto){
        return new ResponseEntity<>(bookService.saveBookInfo(bookRequestDto), HttpStatus.CREATED);
    }
}

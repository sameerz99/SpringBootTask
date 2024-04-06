package com.task.SpringbootTask.controller;

import com.task.SpringbootTask.dto.requestDto.AuthorRequestDto;
import com.task.SpringbootTask.dto.responseDto.AuthorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.task.SpringbootTask.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @PostMapping("/save")
    public ResponseEntity<AuthorResponseDto> saveAuthorInfo(@RequestBody AuthorRequestDto authorRequestDto){
        return new ResponseEntity<>(authorService.saveAuthorInfo(authorRequestDto), HttpStatus.CREATED);
    }
}

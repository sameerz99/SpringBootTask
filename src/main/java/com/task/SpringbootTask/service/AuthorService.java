package com.task.SpringbootTask.service;

import com.task.SpringbootTask.dto.requestDto.AuthorRequestDto;
import com.task.SpringbootTask.dto.responseDto.AuthorResponseDto;

import java.util.List;

public interface AuthorService {
    AuthorResponseDto saveAuthorInfo (AuthorRequestDto authorRequestDto);
    AuthorResponseDto getAuthorById(Integer id);
    void deleteAuthorById(Integer id);
    AuthorResponseDto updateAuthorInfo(Integer id, AuthorRequestDto authorRequestDto);
    List<AuthorResponseDto> getAllAuthor();
}

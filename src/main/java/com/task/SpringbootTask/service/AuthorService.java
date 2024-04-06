package com.task.SpringbootTask.service;

import com.task.SpringbootTask.dto.requestDto.AuthorRequestDto;
import com.task.SpringbootTask.dto.responseDto.AuthorResponseDto;

public interface AuthorService {
    AuthorResponseDto saveAuthorInfo (AuthorRequestDto authorRequestDto);
}

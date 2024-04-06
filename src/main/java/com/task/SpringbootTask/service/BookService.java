package com.task.SpringbootTask.service;

import com.task.SpringbootTask.dto.requestDto.BookRequestDto;
import com.task.SpringbootTask.dto.responseDto.BookResponseDto;

public interface BookService {
    BookResponseDto saveBookInfo(BookRequestDto bookRequestDto);
}

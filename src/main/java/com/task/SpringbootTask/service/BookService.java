package com.task.SpringbootTask.service;

import com.task.SpringbootTask.dto.requestDto.BookRequestDto;
import com.task.SpringbootTask.dto.responseDto.BookResponseDto;

import java.util.List;

public interface BookService {
    BookResponseDto saveBookInfo(BookRequestDto bookRequestDto);
    BookResponseDto getBookInfoById(Integer id);
    void deleteBookInfoById(Integer id);
    BookResponseDto updateBookInfo(Integer id, BookRequestDto bookRequestDto);
    List<BookResponseDto> getAllBooks();
}

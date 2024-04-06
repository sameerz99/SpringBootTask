package com.task.SpringbootTask.service.impl;

import com.task.SpringbootTask.dto.requestDto.BookRequestDto;
import com.task.SpringbootTask.dto.responseDto.BookResponseDto;
import com.task.SpringbootTask.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.SpringbootTask.repo.BookRepo;
import com.task.SpringbootTask.service.BookService;
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepo bookRepo;

    @Override
    public BookResponseDto saveBookInfo(BookRequestDto bookRequestDto) {
        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        Book savedBook = bookRepo.save(book);
        BookResponseDto bookResponseDto = new BookResponseDto(savedBook);
        return bookResponseDto;
    }
}

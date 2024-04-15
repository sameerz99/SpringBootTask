package com.task.SpringbootTask.service.impl;

import com.task.SpringbootTask.dto.requestDto.BookRequestDto;
import com.task.SpringbootTask.dto.responseDto.BookResponseDto;
import com.task.SpringbootTask.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.SpringbootTask.repo.BookRepo;
import com.task.SpringbootTask.service.BookService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepo bookRepo;

    @Override
    @Transactional
    public BookResponseDto saveBookInfo(BookRequestDto bookRequestDto) {
        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        Book savedBook = bookRepo.save(book);
        BookResponseDto bookResponseDto = new BookResponseDto(savedBook);
        return bookResponseDto;
    }

    @Override
    public BookResponseDto getBookInfoById(Integer id) {
        Book book = bookRepo.findById(id).get();
        return new BookResponseDto(book);
    }

    @Override
    @Transactional
    public void deleteBookInfoById(Integer id) {
        bookRepo.deleteById(id);
    }

    @Override
    @Transactional
    public BookResponseDto updateBookInfo(Integer id, BookRequestDto bookRequestDto) {
        Book book = bookRepo.findById(id).get();
        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        Book savedBook = bookRepo.save(book);
        return new BookResponseDto(savedBook);
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        List<BookResponseDto> returnList = new ArrayList<>();
        List<Book> bookList = bookRepo.findAll();
        for(Book book : bookList){
            returnList.add(new BookResponseDto(book));
        }
        return returnList;
    }

    @Override
    public void deleteAllBooks() {
        bookRepo.deleteAll();
    }
}

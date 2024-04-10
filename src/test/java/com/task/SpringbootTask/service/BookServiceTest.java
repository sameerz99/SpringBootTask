package com.task.SpringbootTask.service;

import com.task.SpringbootTask.dto.requestDto.BookRequestDto;
import com.task.SpringbootTask.dto.responseDto.BookResponseDto;
import com.task.SpringbootTask.entity.Book;
import com.task.SpringbootTask.repo.AuthorRepo;
import com.task.SpringbootTask.repo.BookRepo;
import com.task.SpringbootTask.service.impl.BookServiceImpl;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookServiceImpl bookService;

    @DisplayName("saveBookInfo method")
    @Test
    public void bookservice_saveBookInfo_returnBookResponseDto(){

        //create a mock BookRequestDto
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("Ramayan");
        bookRequestDto.setIsbn("11-2255");

        //create a mock Book entity
        Book savedBook = Book.builder()
                .id(1)
                .title("Ramayan")
                .isbn("11-2255").build();
        //Mock the behavior of bookRepo.save() method
        when(bookRepo.save(Mockito.any(Book.class))).thenReturn(savedBook);

        BookResponseDto responseDto = bookService.saveBookInfo(bookRequestDto);

        //verify responseDto is not null
        Assertions.assertNotNull(responseDto);

        //verify responseDto is equal to savedBook
        Assertions.assertEquals(responseDto.getTitle(), savedBook.getTitle());
        Assertions.assertEquals(responseDto.getIsbn(), savedBook.getIsbn());
    }

    @DisplayName("getBookInfoById method")
    @Test
    public void bookservice_getBookInfoById_returnBookResponseDto(){
        Book book = Book.builder()
                .id(1)
                .title("Ramayan")
                .isbn("11-2255").build();
        when(bookRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(book));

        //call the getBookInfoById method with dummy Book Id
        Integer bookId = 1;
        BookResponseDto responseDto = bookService.getBookInfoById(bookId);

        // Verify that the method returns a BookResponseDto object with the correct data
        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals(responseDto.getId(), book.getId());
        Assertions.assertEquals(responseDto.getTitle(), book.getTitle());
        Assertions.assertEquals(responseDto.getIsbn(), book.getIsbn());
    }

    @DisplayName("DeleteBookInfoById method")
    @Test
    public void bookservice_deleteBookInfoById(){
        Book book = Book.builder()
                .id(1)
                .title("Ramayan")
                .isbn("11-2255").build();
        Integer bookId = 1;
        doNothing().when(bookRepo).deleteById(Mockito.any(Integer.class));
        bookService.deleteBookInfoById(bookId);

        // Verify that the bookRepo.deleteById() method is called with the correct argument
        verify(bookRepo, times(1)).deleteById(bookId);

    }

    @DisplayName("updateBookInfo method")
    @Test
    public void bookservice_updateBookInfo_returnBookResponseDto(){
        Integer bookId = 1;
        Book existingBook = Book.builder()
                .id(bookId)
                .title("old title")
                .isbn("old isbn").build();

        when(bookRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(existingBook));
        when(bookRepo.save(Mockito.any(Book.class))).thenReturn(existingBook);

        String title = "new title";
        String isbn = "new isbn";

        BookRequestDto requestDto = new BookRequestDto();
        requestDto.setTitle(title);
        requestDto.setIsbn(isbn);

        BookResponseDto responseDto = bookService.updateBookInfo(bookId,requestDto);

        // Verify that the updated book information is returned as a BookResponseDto
        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals(title, responseDto.getTitle());
        Assertions.assertEquals(isbn, responseDto.getIsbn());
    }

    @DisplayName("getAllBooks method")
    @Test
    public void bookService_getAllBooks_returnBookResponseDto(){
        List<Book> books = Arrays.asList(
                Book.builder().id(1).title("Book1").isbn("12345").build(),
                Book.builder().id(2).title("Book2").isbn("55645").build()
                );
        when(bookRepo.findAll()).thenReturn(books);

        List<BookResponseDto> responseDtoList = bookService.getAllBooks();


        // Verify that the returned list of BookResponseDto is not null and contains the expected number of elements
        Assertions.assertNotNull(responseDtoList);
        Assertions.assertEquals(2, responseDtoList.size());

        // Verify the content of each BookResponseDto in the list
        Assertions.assertEquals(1, responseDtoList.get(0).getId());
        Assertions.assertEquals("Book1", responseDtoList.get(0).getTitle());
        Assertions.assertEquals("12345", responseDtoList.get(0).getIsbn());

        Assertions.assertEquals(2, responseDtoList.get(1).getId());
        Assertions.assertEquals("Book2", responseDtoList.get(1).getTitle());
        Assertions.assertEquals("55645", responseDtoList.get(1).getIsbn());
    }

}

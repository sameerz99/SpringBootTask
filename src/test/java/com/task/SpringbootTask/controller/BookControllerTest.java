package com.task.SpringbootTask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.SpringbootTask.dto.requestDto.BookRequestDto;
import com.task.SpringbootTask.dto.responseDto.BookResponseDto;
import com.task.SpringbootTask.service.AuthorBookService;
import com.task.SpringbootTask.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest {
  @Mock
  private BookService bookService;
  @Mock
  private AuthorBookService authorBookService;

  @InjectMocks
  private BookController bookController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void test_saveBookInfo() {
    // Prepare mock data
    BookRequestDto bookRequestDto = new BookRequestDto("Test Book", "12345");
    BookResponseDto expectedResponse = new BookResponseDto(1, "Test Book", "12345");

    // Mock bookService behavior
    when(bookService.saveBookInfo(bookRequestDto)).thenReturn(expectedResponse);

    // Call the controller method
    ResponseEntity<BookResponseDto> response = bookController.saveBookInfo(bookRequestDto);

    // Assert response status and content
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  void test_getAllBooks() {
    List<BookResponseDto> responseDtoList = Arrays.asList(
            new BookResponseDto(1, "Book1", "123"),
            new BookResponseDto(2,"Book2", "456")
    );
    when(bookService.getAllBooks()).thenReturn(responseDtoList);

    ResponseEntity<List<BookResponseDto>> response = bookController.getAllBooks();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(responseDtoList, response.getBody());

  }

  @Test
  void test_getBookInfoById_success(){
    Integer bookId = 1;
    BookResponseDto expectedResponse = new BookResponseDto(bookId, "Test Book", "123");

    // Mock bookService behavior
    when(bookService.getBookInfoById(bookId)).thenReturn(expectedResponse);

    // Call the controller method
    ResponseEntity<?> response = bookController.getBookInfoById(bookId);

    // Assert response status and content
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  void test_DeleteBookInfoById(){
    Integer bookId = 1;

    doNothing().when(bookService).deleteBookInfoById(bookId);

    ResponseEntity<?> response = bookController.deleteBookInfoById(bookId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testUpdateBookInfo_success() {
    Integer bookId = 1;
    BookRequestDto updateDto = new BookRequestDto("Updated Title", "123");
    BookResponseDto expectedResponse = new BookResponseDto(bookId, updateDto.getTitle(), updateDto.getIsbn());

    // Mock bookService behavior
    when(bookService.updateBookInfo(bookId, updateDto)).thenReturn(expectedResponse);

    // Call the controller method
    ResponseEntity<?> response = bookController.updateBookInfo(bookId, updateDto);

    // Assert response status and content
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  public void testLinkAuthorToBook() {
    Integer bookId = 1;
    Integer authorId = 2;

    // Mock authorBookService behavior (no need to return anything for void method)
    Mockito.doNothing().when(authorBookService).addAuthorToBook(authorId, bookId);

    // Call the controller method
    ResponseEntity<String> response = bookController.linkAuthorToBook(bookId, authorId);

    // Assert response status and message (assuming successful linking)
    assertEquals(HttpStatus.OK, response.getStatusCode());
    // Assert the response body contains a success message (modify if needed)
    assertTrue(response.getBody().contains("Author linked to the book successfully."));
  }
}

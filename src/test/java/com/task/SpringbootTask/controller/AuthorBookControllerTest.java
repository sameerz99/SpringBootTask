package com.task.SpringbootTask.controller;

import com.task.SpringbootTask.entity.Author;
import com.task.SpringbootTask.entity.Book;
import com.task.SpringbootTask.service.AuthorBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthorBookControllerTest {
    @Mock
    private AuthorBookService authorBookService;
    @InjectMocks
    private AuthorBookController authorBookController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_getAuthorsOfBook(){
        Integer bookId = 1;

        // Mock authorBookService behavior (assuming it returns a list of authors)
        List<Author> expectedAuthors = Arrays.asList(
                new Author(1, "John Doe"),
                new Author(2, "Jane Doe")
        );

        when(authorBookService.getAuthorsOfBook(bookId)).thenReturn(expectedAuthors);

        // Call the controller method
        ResponseEntity<List<Author>> response = authorBookController.getAuthorsOfBook(bookId);

        // Assert response status and content
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAuthors, response.getBody());

        verify(authorBookService, times(1)).getAuthorsOfBook(bookId);
    }

    @Test
    void test_getBooksOfAuthor(){
        Integer authorId = 1;
        List<Book> expectedBooks = Arrays.asList(
                new Book(1, "book1","123"),
                new Book(2, "book2","456")
        );
        when(authorBookService.getBooksOfAuthor(authorId)).thenReturn(expectedBooks);

        ResponseEntity<List<Book>> response = authorBookController.getBooksOfAuthor(authorId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBooks, response.getBody());

    }
}

package com.task.SpringbootTask.service;

import com.task.SpringbootTask.entity.Author;
import com.task.SpringbootTask.entity.AuthorBook;
import com.task.SpringbootTask.entity.Book;
import com.task.SpringbootTask.repo.AuthorBookRepo;
import com.task.SpringbootTask.repo.AuthorRepo;
import com.task.SpringbootTask.repo.BookRepo;
import com.task.SpringbootTask.service.impl.AuthorBookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthorBookServiceTest {
    @Mock
    private BookRepo bookRepo;
    @Mock
    private AuthorRepo authorRepo;
    @Mock
    private AuthorBookRepo authorBookRepo;
    @InjectMocks
    private AuthorBookServiceImpl authorBookService;

    @DisplayName("Add Author to Book")
    @Test
    public void testAddAuthorToBook() {
        // Given
        Author author = Author.builder()
                .id(1)
                .name("John Doe")
                .build();
        Book book = Book.builder()
                .id(1)
                .title("Some Book Title")
                .isbn("978-3-16-148410-0")
                .build();
        when(authorRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(author));
        when(bookRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(book));

        // Call the method
        authorBookService.addAuthorToBook(1, 1);

        // Verify that save method was called
        verify(authorBookRepo, times(1)).save(any(AuthorBook.class));
    }

    @Test
    public void testAddAuthorToBook_AuthorNotFoundException() {
        //Mock behavior
        when(authorRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());
        when(bookRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(new Book()));

        //call the method and assert for exception
        Assertions.assertThrows(EntityNotFoundException.class, () -> authorBookService.addAuthorToBook(1, 1));
    }
    @Test
    public void testAddAuthorToBook_BookNotFound() {
        // Mock behavior
        when(authorRepo.findById(1)).thenReturn(Optional.of(new Author()));
        when(bookRepo.findById(1)).thenReturn(Optional.empty());

        // Call the method and assert for exception
        Assertions.assertThrows(EntityNotFoundException.class, () -> authorBookService.addAuthorToBook(1, 1));
    }

    @Test
    public void testRemoveAuthorFromBook_Success() {
        // Mock data
        Author author = Author.builder()
                .id(1)
                .name("John Doe")
                .build();
        Book book = Book.builder()
                .id(1)
                .title("Some Book Title")
                .isbn("978-3-16-148410-0")
                .build();

        AuthorBook authorBook = new AuthorBook();
        authorBook.setAuthor(author);
        authorBook.setBook(book);

        // Mock behavior
        when(authorBookRepo.findByAuthorIdAndBookId(1, 1)).thenReturn(Optional.of(authorBook));

        // Call the method
        authorBookService.removeAuthorFromBook(1, 1);

        // Verify that delete method was called
        verify(authorBookRepo, times(1)).delete(authorBook);
    }

    @Test
    public void testRemoveAuthorFromBook_AssociationNotFound() {
        // Mock behavior
        when(authorBookRepo.findByAuthorIdAndBookId(anyInt(), anyInt())).thenReturn(Optional.empty());

        // Call the method and assert for exception
        Assertions.assertThrows(EntityNotFoundException.class, () -> authorBookService.removeAuthorFromBook(1, 1));
    }

    @DisplayName("getAuthorsOfBook method")
    @Test
    public void testGetAuthorsOfBook() {
        // Given
        Book book = Book.builder()
                .id(1)
                .title("HarryPotter")
                .isbn("12345")
                .build();
        Author author1 = Author.builder()
                .id(1)
                .name("John Doe")
                .build();
        Author author2 = Author.builder()
                .id(2)
                .name("ram")
                .build();

// Mock the author book repository to return a list of AuthorBook objects
        List<AuthorBook> authorBooks = new ArrayList<>();
        authorBooks.add(new AuthorBook(1,author1, book));
        authorBooks.add(new AuthorBook(2,author2, book));
        when(authorBookRepo.findByBookId(Mockito.any(Integer.class))).thenReturn(authorBooks);

// Mock the author repository to return authors when findById is called
        when(authorRepo.findById(1)).thenReturn(Optional.of(author1));
        when(authorRepo.findById(2)).thenReturn(Optional.of(author2));

// Verification
        List<Author> authors = authorBookService.getAuthorsOfBook(1);

// Assertion
        Assertions.assertNotNull(authors);
        Assertions.assertEquals(2, authors.size()); // Expecting 2 authors
        Assertions.assertTrue(authors.contains(author1));
        Assertions.assertTrue(authors.contains(author2));
    }

    @DisplayName("getBooksOfAuthor method")
    @Test
    public void testGetBooksOfAuthor() {
        // Given
        Book book1 = Book.builder()
                .id(1)
                .title("HarryPotter")
                .isbn("12345")
                .build();
        Book book2 = Book.builder()
                .id(2)
                .title("Ramayan")
                .isbn("23564")
                .build();
        Author author = Author.builder()
                .id(1)
                .name("Hari").build();

        // Mock the author book repository to return a list of AuthorBook objects
        List<AuthorBook> authorBooks = new ArrayList<>();
        authorBooks.add(new AuthorBook(1,author, book1));
        authorBooks.add(new AuthorBook(2,author, book2));
        when(authorBookRepo.findByAuthorId(Mockito.any(Integer.class))).thenReturn(authorBooks);

        //Mock the bookRepo
        when(bookRepo.findById(1)).thenReturn(Optional.of(book1));
        when(bookRepo.findById(2)).thenReturn(Optional.of(book2));

        List<Book> books = authorBookService.getBooksOfAuthor(1);
        Assertions.assertNotNull(books);
        Assertions.assertEquals(2, books.size());
        Assertions.assertTrue(books.contains(book1));
        Assertions.assertTrue(books.contains(book2));
    }

}

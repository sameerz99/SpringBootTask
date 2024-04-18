package com.task.SpringbootTask.integration;

import com.task.SpringbootTask.entity.Author;
import com.task.SpringbootTask.entity.Book;
import com.task.SpringbootTask.repo.AuthorBookRepo;
import com.task.SpringbootTask.repo.AuthorRepo;
import com.task.SpringbootTask.repo.BookRepo;
import com.task.SpringbootTask.service.AuthorBookService;
import com.task.SpringbootTask.service.impl.AuthorBookServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(AuthorBookServiceImpl.class)
public class AuthorBookIntegrationTest {
    @Autowired
    private AuthorBookRepo authorBookRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private AuthorBookServiceImpl authorBookService;


    @AfterEach
    void tearDown() {
        authorRepo.deleteAll();
        bookRepo.deleteAll();
        authorBookRepo.deleteAll();
    }

    @Test
    void test_linkAuthorAndBook(){

        //Arrange
        Author author = new Author(1,"Hari");
        authorRepo.save(author);

        Book book = new Book(1,"Science","1234");
        bookRepo.save(book);

        //Act - Link author and book
        authorBookService.addAuthorToBook(author.getId(), book.getId());

        //Assert - verify the link exists
        assertThat(authorBookRepo.findByAuthorIdAndBookId(author.getId(), book.getId()).isPresent()).isTrue();
    }

    @Test
    void test_removeLinkBetweenAuthorAndBook(){
        //Arrange
        Author author = new Author(1,"Hari");
        authorRepo.save(author);

        Book book = new Book(1,"Science","1234");
        bookRepo.save(book);

        authorBookService.addAuthorToBook(author.getId(), book.getId());

        //Act - Remove the link
        authorBookService.removeAuthorFromBook(author.getId(), book.getId());

        // Assert - verify the link is removed
        assertThat(authorBookRepo.findByAuthorIdAndBookId(author.getId(), book.getId()).isPresent()).isFalse();
    }

    @Test
    void test_getAuthorsOfBook(){
        Author author1 = new Author(1,"Hari");
        authorRepo.save(author1);
        Author author2 = new Author(2,"Ram");
        authorRepo.save(author2);

        Book book1 = new Book(1,"Science","1234");
        bookRepo.save(book1);

        authorBookService.addAuthorToBook(author1.getId(), book1.getId());
        authorBookService.addAuthorToBook(author2.getId(), book1.getId());


        List<Author> authorList = authorBookService.getAuthorsOfBook(book1.getId());

        assertNotNull(authorList);
        assertThat(authorList.size()).isEqualTo(2);

    }

    @Test
    void test_getBooksOfAuthor(){
        Author author1 = new Author(1,"Hari");
        authorRepo.save(author1);

        Book book1 = new Book(1,"Science","1234");
        bookRepo.save(book1);

        Book book2 = new Book(2,"Math","2354");
        bookRepo.save(book2);

        authorBookService.addAuthorToBook(author1.getId(), book1.getId());
        authorBookService.addAuthorToBook(author1.getId(), book2.getId());

        List<Book> bookList = authorBookService.getBooksOfAuthor(author1.getId());

        assertNotNull(bookList);
        assertThat(bookList.size()).isEqualTo(2);
    }
}

package com.task.SpringbootTask.service.impl;

import com.task.SpringbootTask.entity.Author;
import com.task.SpringbootTask.entity.AuthorBook;
import com.task.SpringbootTask.entity.Book;
import com.task.SpringbootTask.repo.AuthorBookRepo;
import com.task.SpringbootTask.repo.AuthorRepo;
import com.task.SpringbootTask.repo.BookRepo;
import com.task.SpringbootTask.service.AuthorBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorBookServiceImpl implements AuthorBookService {
    @Autowired
    AuthorBookRepo authorBookRepo;
    @Autowired
    AuthorRepo authorRepo;
    @Autowired
    BookRepo bookRepo;
    @Override
    @Transactional
    public void addAuthorToBook(Integer authorId, Integer bookId) {
        AuthorBook authorBook = new AuthorBook();
        Author author = authorRepo.findById(authorId).orElseThrow(
                () -> new EntityNotFoundException("Author not found with id " + authorId ));
        Book book = bookRepo.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book not found with id " + bookId )
        );
        authorBook.setAuthor(author);
        authorBook.setBook(book);
        authorBookRepo.save(authorBook);
    }

    @Override
    @Transactional
    public void removeAuthorFromBook(Integer authorId, Integer bookId) {
        AuthorBook authorBook = authorBookRepo.findByAuthorIdAndBookId(authorId, bookId)
                .orElseThrow(()-> new EntityNotFoundException("Association not found between author and book"));
        authorBookRepo.delete(authorBook);
    }

    @Override
    public List<Author> getAuthorsOfBook(Integer bookId) {
        List<AuthorBook> authorBooks = authorBookRepo.findByBookId(bookId);
        return authorBooks.stream().map(AuthorBook::getAuthor).collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksOfAuthor(Integer authorId) {
        List<AuthorBook> authorBooks = authorBookRepo.findByAuthorId(authorId);
        return authorBooks.stream().map(AuthorBook::getBook).collect(Collectors.toList());
    }

    @Override
    public void deleteAllAuthorBookLinks() {
        authorBookRepo.deleteAll();
    }
}

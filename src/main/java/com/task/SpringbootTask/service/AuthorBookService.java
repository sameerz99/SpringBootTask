package com.task.SpringbootTask.service;

import com.task.SpringbootTask.entity.Author;
import com.task.SpringbootTask.entity.Book;

import java.util.List;

public interface AuthorBookService {
    void addAuthorToBook(Integer authorId, Integer bookId);
    void removeAuthorFromBook(Integer authorId, Integer bookId);
    List<Author> getAuthorsOfBook(Integer bookId);
    List<Book> getBooksOfAuthor(Integer authorId);
    void deleteAllAuthorBookLinks();
}

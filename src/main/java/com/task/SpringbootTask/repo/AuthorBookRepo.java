package com.task.SpringbootTask.repo;

import com.task.SpringbootTask.entity.AuthorBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorBookRepo extends JpaRepository<AuthorBook, Integer>{
    Optional<AuthorBook> findByAuthorIdAndBookId(Integer authorId, Integer bookId);
    List<AuthorBook> findByBookId(Integer bookId);
    List<AuthorBook> findByAuthorId(Integer authorId);
}

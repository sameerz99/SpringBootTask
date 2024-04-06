package com.task.SpringbootTask.service.impl;

import com.task.SpringbootTask.dto.requestDto.AuthorRequestDto;
import com.task.SpringbootTask.dto.responseDto.AuthorResponseDto;
import com.task.SpringbootTask.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.SpringbootTask.repo.AuthorRepo;
import com.task.SpringbootTask.service.AuthorService;
@Service
public class AuthorServiceDto implements AuthorService {
    @Autowired
    AuthorRepo authorRepo;

    @Override
    public AuthorResponseDto saveAuthorInfo(AuthorRequestDto authorRequestDto) {
        Author author = new Author();
        author.setName(authorRequestDto.getName());
        Author savedAuthor = authorRepo.save(author);
        return new AuthorResponseDto(savedAuthor);
    }
}

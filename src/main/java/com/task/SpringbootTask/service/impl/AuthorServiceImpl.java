package com.task.SpringbootTask.service.impl;

import com.task.SpringbootTask.dto.requestDto.AuthorRequestDto;
import com.task.SpringbootTask.dto.responseDto.AuthorResponseDto;
import com.task.SpringbootTask.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.SpringbootTask.repo.AuthorRepo;
import com.task.SpringbootTask.service.AuthorService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    AuthorRepo authorRepo;

    @Override
    @Transactional
    public AuthorResponseDto saveAuthorInfo(AuthorRequestDto authorRequestDto) {
        Author author = new Author();
        author.setName(authorRequestDto.getName());
        Author savedAuthor = authorRepo.save(author);
        return new AuthorResponseDto(savedAuthor);
    }

    @Override
    public AuthorResponseDto getAuthorById(Integer id) {
        Author author = authorRepo.findById(id).get();
        return new AuthorResponseDto(author);
    }

    @Override
    @Transactional
    public void deleteAuthorById(Integer id) {
        authorRepo.deleteById(id);
    }

    @Override
    @Transactional
    public AuthorResponseDto updateAuthorInfo(Integer id, AuthorRequestDto authorRequestDto) {
        Author author = authorRepo.findById(id).get();
        author.setName(authorRequestDto.getName());
        Author savedAuthor = authorRepo.save(author);
        return new AuthorResponseDto(savedAuthor);
    }

    @Override
    public List<AuthorResponseDto> getAllAuthor() {
        List<AuthorResponseDto> returnList = new ArrayList<>();
        List<Author> authorList = authorRepo.findAll();
        for(Author author : authorList){
            returnList.add(new AuthorResponseDto(author));
        }
        return returnList;
    }
}

package com.task.SpringbootTask.service;

import com.task.SpringbootTask.dto.requestDto.AuthorRequestDto;
import com.task.SpringbootTask.dto.responseDto.AuthorResponseDto;
import com.task.SpringbootTask.entity.Author;
import com.task.SpringbootTask.repo.AuthorRepo;
import com.task.SpringbootTask.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @DisplayName("saveAuthorInfo method")
    @Test
    public void authorService_saveAuthorInfo_returnAuthorResponseDto(){
        AuthorRequestDto requestDto = new AuthorRequestDto();
        requestDto.setName("ram");

        Author savedAuthor = Author.builder()
                .id(1)
                .name("ram").build();
        when(authorRepo.save(Mockito.any(Author.class))).thenReturn(savedAuthor);
        AuthorResponseDto responseDto = authorService.saveAuthorInfo(requestDto);

        // Verify that the method returns a BookResponseDto object with the correct data
        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals(savedAuthor.getId(), responseDto.getId());
        Assertions.assertEquals(savedAuthor.getName(), responseDto.getName());

    }

    @DisplayName("getAuthorById method")
    @Test
    public void authorService_getAuthorById_returnAuthorResponseDto(){
        Author author = Author.builder().id(1).name("ram").build();
        when(authorRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(author));

        Integer authorId = 1;
        AuthorResponseDto responseDto = authorService.getAuthorById(authorId);

        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals(authorId, responseDto.getId());
        Assertions.assertEquals(author.getName(), responseDto.getName());
    }

    @DisplayName("deleteAuthorById method")
    @Test
    public void authorService_deleteAuthorById(){
        Author author = Author.builder().id(1).name("ram").build();
        Integer authorId = 1;
        doNothing().when(authorRepo).deleteById(Mockito.any(Integer.class));
        authorService.deleteAuthorById(authorId);
        //verify  that authorRepo.deleteById() method is called with correct argument
        verify(authorRepo, times(1)).deleteById(authorId);
    }

    @DisplayName("updateAuthorInfo method")
    @Test
    public void authorService_updateAuthorInfo_returnAuthorResponseDto(){
        Integer authorId = 1;
        Author existingAuthor = Author.builder().id(authorId).name("old name").build();

        when(authorRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(existingAuthor));
        when(authorRepo.save(Mockito.any(Author.class))).thenReturn(existingAuthor);

        String name = "new name";

        AuthorRequestDto requestDto = new AuthorRequestDto();
        requestDto.setName(name);

        AuthorResponseDto responseDto = authorService.updateAuthorInfo(authorId,requestDto);

        //verify that the updated Author is returned as AuthorResponseDto
        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals(name,responseDto.getName());
    }

    @DisplayName("getAllAuthor method")
    @Test
    public void authorService_getAllAuthor_returnAuthorResponseDto(){
        List<Author> authors = Arrays.asList(
                Author.builder().id(1).name("name1").build(),
                Author.builder().id(2).name("name2").build()
        );

        when(authorRepo.findAll()).thenReturn(authors);

        List<AuthorResponseDto> responseDtoList = authorService.getAllAuthor();

        // Verify that the returned list of AuthorResponseDto is not null and contains the expected number of elements
        Assertions.assertNotNull(responseDtoList);
        Assertions.assertEquals(2, responseDtoList.size());

        // Verify the content of each BookResponseDto in the list
        Assertions.assertEquals(1,responseDtoList.get(0).getId());
        Assertions.assertEquals("name1",responseDtoList.get(0).getName());

        Assertions.assertEquals(2,responseDtoList.get(1).getId());
        Assertions.assertEquals("name2",responseDtoList.get(1).getName());
    }
}

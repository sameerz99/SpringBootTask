package com.task.SpringbootTask.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.SpringbootTask.dto.requestDto.AuthorRequestDto;
import com.task.SpringbootTask.dto.responseDto.AuthorResponseDto;
import com.task.SpringbootTask.entity.Author;
import com.task.SpringbootTask.repo.AuthorRepo;
import com.task.SpringbootTask.service.AuthorBookService;
import com.task.SpringbootTask.service.AuthorService;
import com.task.SpringbootTask.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;
    @MockBean
    private AuthorBookService authorBookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_saveAuthorInfo() throws Exception {
        AuthorRequestDto requestDto = new AuthorRequestDto("Hari");
        AuthorResponseDto responseDto = new AuthorResponseDto(1,"Hari");
        when(authorService.saveAuthorInfo(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/authors/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void test_getAuthorInfoById() throws Exception {
        AuthorResponseDto responseDto = new AuthorResponseDto(1,"Hari");
        when(authorService.getAuthorById(Mockito.any(Integer.class))).thenReturn(responseDto);

        mockMvc.perform(get("/authors/{id}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hari"));

    }

    @Test
    public void test_getAllAuthor() throws Exception {
        List<AuthorResponseDto> responseDtoList = Arrays.asList(
                new AuthorResponseDto(1,"Hari"),
                new AuthorResponseDto(2,"Ram")
        );
        when(authorService.getAllAuthor()).thenReturn(responseDtoList);

        mockMvc.perform(get("/authors/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hari"))
                .andExpect(jsonPath("$[1].name").value("Ram"));

    }

    @Test
    public void test_deleteAuthorInfoById() throws Exception {
        // Arrange
        Integer authorId = 1;

        // Mock the behavior of the AuthorService
        doNothing().when(authorService).deleteAuthorById(authorId);

        // Act
        mockMvc.perform(delete("/authors/{id}", authorId))
                .andExpect(status().isOk());

        // Assert
        verify(authorService, times(1)).deleteAuthorById(authorId);
    }

    @Test
    public void test_updateAuthorInfo() throws Exception {
        // Arrange
        Integer authorId = 1;
        AuthorRequestDto requestDto = new AuthorRequestDto("Hari");
        AuthorResponseDto responseDto = new AuthorResponseDto(1, "Hari");

        // Mock the behavior of the AuthorService
        when(authorService.updateAuthorInfo(authorId, requestDto)).thenReturn(responseDto);

        // Act and Assert
        mockMvc.perform(put("/authors/{id}", authorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_removeBookFromAuthor() throws Exception {
        // Arrange
        Integer authorId = 1;
        Integer bookId = 1;

        // Mock the behavior of the AuthorBookService
        doNothing().when(authorBookService).removeAuthorFromBook(authorId, bookId);

        // Act and Assert
        mockMvc.perform(delete("/authors/{authorId}/books/{bookId}", authorId, bookId))
                .andExpect(status().isOk())
                .andExpect(content().string("Book Removed from the author's list Successfully."));

        // Verify that the service method was called with the correct parameters
        verify(authorBookService, times(1)).removeAuthorFromBook(authorId, bookId);
    }

}

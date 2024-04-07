package com.task.SpringbootTask.dto.responseDto;

import com.task.SpringbootTask.entity.Author;
import com.task.SpringbootTask.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDto {
    private Integer id;
    private String name;

    public AuthorResponseDto(Author a){
        this.id = a.getId();
        this.name = a.getName();
    }
}

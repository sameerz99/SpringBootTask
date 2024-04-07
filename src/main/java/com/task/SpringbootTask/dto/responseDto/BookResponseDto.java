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
public class BookResponseDto {
    private Integer id;
    private String title;
    private String isbn;

    public BookResponseDto(Book b){
        this.id = b.getId();
        this.title = b.getTitle();
        this.isbn = b.getIsbn();
    }
}

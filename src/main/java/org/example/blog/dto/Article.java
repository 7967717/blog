package org.example.blog.dto;

import org.example.blog.entity.Genre;

import java.time.LocalDate;

public record Article(
        long id,
        String name,
        String content,
        LocalDate date,
        Genre genre,
        Author author
) {
}

package org.example.blog.dto;

public record Comment(
        long id,
        String content,
        Article article
) {
}

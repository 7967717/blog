package org.example.blog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "article")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String content;

    @NotNull
    private LocalDate date;

    @NotNull
    private Genre genre;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    public ArticleEntity(String name, String content, LocalDate date, Genre genre, AuthorEntity authorEntity) {
    }
}

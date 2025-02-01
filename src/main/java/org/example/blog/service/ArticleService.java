package org.example.blog.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.dto.Article;
import org.example.blog.dto.Author;
import org.example.blog.entity.ArticleEntity;
import org.example.blog.entity.AuthorEntity;
import org.example.blog.error.NotSupportedException;
import org.example.blog.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> findArticlesBefore(LocalDate date) {
        return articleRepository.findByDateLessThan(date).stream()
                .map(ae -> {
                    final var author = new Author(ae.getAuthor().getId(), ae.getAuthor().getName());
                    return new Article(ae.getId(), ae.getName(), ae.getContent(), ae.getDate(), ae.getGenre(), author);
                })
                .toList();
    }

    public long createArticle(Article article) {
        final var authorEntity = new AuthorEntity(article.author().id(), article.author().name());
        final var articleEntity = new ArticleEntity(article.name(), article.content(), article.date(), article.genre(), authorEntity);
        return articleRepository.save(articleEntity).getId();
    }

    public List<Article> fetchAllArticles() {
        return articleRepository.findAll().stream()
                .map(ae -> {
                    final var author = new Author(ae.getAuthor().getId(), ae.getAuthor().getName());
                    return new Article(ae.getId(), ae.getName(), ae.getContent(), ae.getDate(), ae.getGenre(), author);
                })
                .toList();
    }

    public Article updateArticle(Article article) {
        //TODO check and update new fields
        throw new NotSupportedException("The operation updateArticle is not supported yet.");
    }

    public void deleteArticle(long id) {
        articleRepository.deleteById(id);
    }

}

package org.example.blog.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.dto.Article;
import org.example.blog.service.ArticleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping(path = "/article/{date}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article>> findArticlesBefore(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.debug("Received request to find articles before: {}", date);
        final var response = articleService.findArticlesBefore(date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/article",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createArticle(@RequestBody Article article) {
        log.debug("Received request to create article: {}", article);
        return new ResponseEntity<>(articleService.createArticle(article), HttpStatus.OK);
    }

    @GetMapping(path = "/articles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article>> getAllArticles() {
        log.debug("Received request to find all articles");
        return new ResponseEntity<>(articleService.fetchAllArticles(), HttpStatus.OK);
    }

    @PatchMapping(path = "/article",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> updateArticle(@RequestBody Article article) {
        log.debug("Received request to update article: {}", article);
        return new ResponseEntity<>(articleService.updateArticle(article).id(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/article{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        log.debug("Received request to delete article with id: {}", id);
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

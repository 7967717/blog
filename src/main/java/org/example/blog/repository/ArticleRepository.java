package org.example.blog.repository;

import org.example.blog.entity.Article;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

    List<Article> findByDateLessThan(LocalDateTime date);

}

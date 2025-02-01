package org.example.blog.repository;

import org.example.blog.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    List<ArticleEntity> findByDateLessThan(LocalDate date);

}

package org.example.blog.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

}

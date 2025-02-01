package org.example.blog.controller;

import org.example.blog.error.NotSupportedException;
import org.example.blog.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.example.blog.CreateTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ArticleControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    private ArticleService articleService;

    private static String jsonString;

    @BeforeEach
    void setUp() {
        jsonString = convertObjectToJsonString(getArticle());
    }

    @Test
    void createArticleTestWithRestTemplate_OK() {
        // Arrange
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        when(articleService.createArticle(any())).thenReturn(1L);
        var entity = new HttpEntity<>(jsonString, headers);

        // Act
        var response = testRestTemplate.postForEntity("/article", entity, Long.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).longValue());

    }

    @Test
    void updateArticleTestWithRestTemplate_NotSupportedException() {
        // Arrange
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final var entity = new HttpEntity<>(jsonString, headers);
        final var message = "The operation updateArticle is not supported yet.";
        when(articleService.updateArticle(any())).thenThrow(
                new NotSupportedException(message));

        // Act
        var response = testRestTemplate.patchForObject("/article", entity, String.class);

        // Assert
        assertEquals(message, response);
    }

    @Test
    void createArticleTest_OK() throws Exception {
        // Arrange
        when(articleService.createArticle(any())).thenReturn(2L);

        // Act
        var response = mvc.perform(post("/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // Assert
        assertEquals("2", response.getContentAsString());

    }

    @Test
    void findArticlesBeforeTest_OK() throws Exception {
        // Arrange
        when(articleService.findArticlesBefore(any())).thenReturn(List.of(getArticle()));

        // Act
        final var date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        var response = mvc.perform(get("/article/%s".formatted(date)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // Assert
        assertEquals(List.of(getResourceAsString("/article.json")).toString().replaceAll("\\s+", "")
                , response.getContentAsString());

    }

    @Test
    void updateArticleTest_NotSupportedException() throws Exception {
        // Arrange
        when(articleService.updateArticle(any())).thenThrow(NotSupportedException.class);

        // Act and Assert
        mvc.perform(patch("/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().is5xxServerError())
                .andReturn()
                .getResponse();

    }

}

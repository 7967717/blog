package org.example.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.dto.Article;
import org.example.blog.dto.Author;
import org.example.blog.entity.Genre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Slf4j
public class CreateTestData {

    public static final String EMPTY = "";

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    public static Article getArticle() {
        return new Article(1L, "ArticleName", "Content", LocalDate.now(), Genre.PULP_FICTION,
                new Author(1L, "AuthorName"));
    }

    @NotNull
    public static String getResourceAsString(String resource) throws IOException {
        String string;
        try (var reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(CreateTestData.class.getResourceAsStream(resource))))) {
            string = reader.lines().collect(Collectors.joining());
        }
        return string;
    }

    public static String convertObjectToJsonString(Object objectToConvert) {
        if (Objects.isNull(objectToConvert)) {
            return EMPTY;
        }
        ObjectWriter ow = MAPPER.writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(objectToConvert);
        } catch (JsonProcessingException e) {
            log.error("An error occurred processing the object to json string: {}", e.getMessage());
            log.error("Object that was being parsed: {}", objectToConvert);
        }
        return EMPTY;
    }

}

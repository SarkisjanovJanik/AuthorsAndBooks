package com.authorsandbooksapp.service;

import com.authorsandbooksapp.entity.Author;
import com.authorsandbooksapp.exception.NotFoundException;
import com.authorsandbooksapp.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    public static final String NOT_FOUND = "resourceNotFound";

    @Test
    public void testGetAuthorById() {
        // Given
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        author.setFirstName("Ernest");
        author.setLastName("Hemingway");
        author.setBirthDate(LocalDateTime.of(
                        2000, 10, 10, 10, 10, 10
                )
        );
        author.setComment("Author of 'The Old Man and The Sea'");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        // When
        Author result = authorService.getAuthorById(authorId);

        // Then
        assertEquals(authorId, result.getId());
        assertEquals(author.getFirstName(), result.getFirstName());
    }

    @Test
    public void testGetAuthorById_ThrowsNotFoundException() {
        // Given
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(NotFoundException.class, () ->
                authorService.getAuthorById(authorId));
        String actualMessage = exception.getMessage();

        // Then
        assertEquals(NOT_FOUND, actualMessage);
    }

    @Test
    public void testGetAllAuthors() {
        // Given
        List<Author> authors = Arrays.asList(new Author(), new Author());
        when(authorRepository.findAll()).thenReturn(authors);

        // When
        List<Author> result = authorService.getAllAuthors();

        // Then
        assertEquals(authors.size(), result.size());
    }

    @Test
    public void testAddAuthor() {
        // Given
        Author author = new Author();
        when(authorRepository.save(author)).thenReturn(author);

        // When
        Author result = authorService.addAuthor(author);

        // Then
        assertNotNull(result);
    }

    @Test
    public void testDeleteAuthor() {
        // Given
        Long authorId = 1L;
        when(authorRepository.existsById(authorId)).thenReturn(true);

        // When
        authorService.deleteAuthor(authorId);

        // Then
        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    public void testDeleteAuthor_ThrowsNotFoundException() {
        // Given
        Long authorId = 1L;
        when(authorRepository.existsById(authorId)).thenReturn(false);

        // When
        Exception exception = assertThrows(NotFoundException.class, () ->
                authorService.deleteAuthor(authorId));
        String actualMessage = exception.getMessage();

        // Then
        assertEquals(NOT_FOUND, actualMessage);
    }

    @Test
    public void testUpdateAuthor() {
        // Given
        Long authorId = 1L;
        Author existingAuthor = new Author();
        existingAuthor.setFirstName("Paulo");
        Author requestedAuthor = new Author();
        requestedAuthor.setFirstName("Paul");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(existingAuthor)).thenReturn(existingAuthor);

        // When
        Author result = authorService.updateAuthor(authorId, requestedAuthor);

        // Then
        assertNotNull(result);
        assertEquals("Paul",existingAuthor.getFirstName());
    }

    @Test
    public void testUpdateAuthor_ThrowsNotFoundException() {
        // Given
        Long authorId = 1L;
        Author author = new Author();

        // When
        Exception exception = assertThrows(NotFoundException.class, () ->
                authorService.updateAuthor(authorId, author));
        String actualMessage = exception.getMessage();

        // Then
        assertEquals(NOT_FOUND, actualMessage);
    }
}

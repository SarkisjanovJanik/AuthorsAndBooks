package com.authorsandbooksapp.service;

import com.authorsandbooksapp.entity.Author;
import com.authorsandbooksapp.entity.Book;
import com.authorsandbooksapp.exception.NotFoundException;
import com.authorsandbooksapp.repository.BookRepository;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    public static final String NOT_FOUND = "resourceNotFound";

    @Test
    public void testGetBookById() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        Author author = new Author();
        book.setId(bookId);
        book.setAuthor(author);
        book.setTitle("The Old Man and The Sea");
        book.setPublishedAt(LocalDateTime.of(
                2000, 10, 10, 10, 10, 10
        ));
        book.setGenre("Novel");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // When
        Book result = bookService.getBookById(bookId);

        // Then
        assertEquals(bookId, result.getId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
    }

    @Test
    public void testGetBookById_ThrowsNotFoundException() {
        // Given
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(NotFoundException.class, () ->
                bookService.getBookById(bookId));

        String actualMessage = exception.getMessage();

        // Then
        assertEquals(NOT_FOUND, actualMessage);
    }

    @Test
    public void testGetAllBooks() {
        // Given
        List<Book> books = Arrays.asList(new Book(), new Book());

        when(bookRepository.findAll()).thenReturn(books);

        // When
        List<Book> result = bookService.getAllBooks();

        // Then
        assertEquals(books.size(), result.size());
    }

    @Test
    public void testAddBook() {
        // Given
        Book book = new Book();

        when(bookRepository.save(book)).thenReturn(book);

        // When
        Book result = bookService.addBook(book);

        // Then
        assertNotNull(result);
    }

    @Test
    public void testDeleteBook() {
        // Given
        Long bookId = 1L;

        when(bookRepository.existsById(bookId)).thenReturn(true);

        // When
        bookService.deleteBook(bookId);

        // Then
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    public void testDeleteBook_ThrowsNotFoundException() {
        // Given
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(false);

        // When
        Exception exception = assertThrows(NotFoundException.class, () ->
                bookService.deleteBook(bookId));
        String actualMessage = exception.getMessage();

        // Then
        assertEquals(NOT_FOUND, actualMessage);
    }

    @Test
    public void testUpdateBook() {
        // Given
        Long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setTitle("The Alchemist");
        Book requestedBook = new Book();
        requestedBook.setTitle("The Devil And Miss Prym");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        // When
        Book result = bookService.updateBook(bookId, requestedBook);

        // Then
        assertNotNull(result);

        assertEquals("The Devil And Miss Prym",existingBook.getTitle());
    }

    @Test
    public void testUpdateBook_ThrowsNotFoundException() {
        // Given
        Long bookId = 1L;
        Book book = new Book();

        // When
        Exception exception = assertThrows(NotFoundException.class, () ->
                bookService.updateBook(bookId, book));
        String actualMessage = exception.getMessage();

        // Then
        assertEquals(NOT_FOUND, actualMessage);
    }
}

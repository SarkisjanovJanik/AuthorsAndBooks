package com.authorsandbooksapp.service;

import com.authorsandbooksapp.entity.Author;
import com.authorsandbooksapp.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import com.authorsandbooksapp.entity.Book;
import com.authorsandbooksapp.exception.NotFoundException;
import com.authorsandbooksapp.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Book getBookById(Long id) {
        log.info("Getting book by id: {}", id);

        return bookRepository.findById(id).orElseThrow(
                NotFoundException::new
        );
    }

    public List<Book> getAllBooks() {
        log.info("Getting all books");

        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        log.info("Adding book: {}", book);

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        log.info("Updating book by id: {} for {}", id, book);

        Book existingBook = bookRepository.findById(id).orElseThrow(
                NotFoundException::new
        );

        if (book.getAuthor() != null) {

            Author author = authorRepository.findById(book.getAuthor().getId())
                    .orElseThrow(NotFoundException::new);

            existingBook.setAuthor(author);
        }

        if (book.getGenre() != null) {
            existingBook.setTitle(book.getTitle());
        }

        if (book.getTitle() != null) {
            existingBook.setTitle(book.getTitle());
        }

        if (book.getPublishedAt() != null) {
            existingBook.setPublishedAt(book.getPublishedAt());
        }

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        log.info("Deleting book by id: {}", id);

        if (!bookRepository.existsById(id)) {
            throw new NotFoundException();
        }

        bookRepository.deleteById(id);
    }
}

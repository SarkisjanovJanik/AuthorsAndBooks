package com.authorsandbooksapp.service;

import com.authorsandbooksapp.entity.Author;
import com.authorsandbooksapp.exception.NotFoundException;
import com.authorsandbooksapp.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author getAuthorById(Long id) {
        log.info("Getting author by id: {}", id);

        return authorRepository.findById(id).orElseThrow(
                NotFoundException::new
        );
    }

    public List<Author> getAllAuthors() {
        log.info("Getting all authors");

        return authorRepository.findAll();
    }

    public Author addAuthor(Author author) {
        log.info("Adding author: {}", author);

        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author author) {
        log.info("Updating author for id: {} by {}", id, author);

        Author existingAuthor = authorRepository.findById(id).orElseThrow(
                NotFoundException::new
        );

        if (author.getFirstName() != null) {
            existingAuthor.setFirstName(author.getFirstName());
        }

        if (author.getLastName() != null) {
            existingAuthor.setLastName(author.getLastName());
        }

        if (author.getBirthDate() != null) {
            existingAuthor.setBirthDate(author.getBirthDate());
        }

        if (author.getComment() != null) {
            existingAuthor.setComment(author.getComment());
        }

        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(Long id) {
        log.info("Deleting author by id: {}", id);

        if (!authorRepository.existsById(id)) {
            throw new NotFoundException();
        }
        authorRepository.deleteById(id);
    }
}

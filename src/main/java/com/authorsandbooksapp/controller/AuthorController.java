package com.authorsandbooksapp.controller;

import com.authorsandbooksapp.entity.Author;
import com.authorsandbooksapp.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/authors")
@RequiredArgsConstructor
@Validated
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {

        Author author = authorService.getAuthorById(id);

        return ResponseEntity.ok(author);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping("/add")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {

        Author addedAuthor = authorService.addAuthor(author);

        return ResponseEntity.created(URI.create("/api/authors/add" + addedAuthor.getId()))
                .body(addedAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        Author updatedAuthor = authorService.updateAuthor(id, author);

        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}

package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.dto.AuthorDTO;
import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.service.IAuthorsService;

import java.util.Collection;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private IAuthorsService authorsService;

    public AuthorsController(IAuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping
        public Page<AuthorDTO> getAuthors(
            @PageableDefault(size = 5, sort = "lastName") Pageable pageable) {
        return authorsService.getAuthors(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id) {
        return new ResponseEntity<>(authorsService.getAuthor(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getAuthorCount() {
        return new ResponseEntity<>(authorsService.getAuthorCount(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody AuthorDTO author) {
        AuthorDTO added = authorsService.addAuthor(author);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO author) {
        authorsService.updateAuthor(id, author);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorsService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
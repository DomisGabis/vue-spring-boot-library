package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.dto.AuthorDTO;
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
    public ResponseEntity<Collection<AuthorDTO>> getAuthors() {
        return new ResponseEntity<>(authorsService.getAuthors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id) {
        return new ResponseEntity<>(authorsService.getAuthor(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody AuthorDTO author) {
        authorsService.addAuthor(author);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
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
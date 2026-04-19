package pl.edu.pwr.ztw.books.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.service.IBooksService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final IBooksService booksService;

    public BooksController(IBooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public Page<BookDTO> getAllBooks(
            @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return booksService.getBooks(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        BookDTO book = booksService.getBook(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getBookCount() {
        return new ResponseEntity<>(booksService.getBookCount(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDto) {
        booksService.addBook(bookDto);
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDto) {
        booksService.updateBook(id, bookDto);
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        booksService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleConflict(ResponseStatusException e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getReason());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
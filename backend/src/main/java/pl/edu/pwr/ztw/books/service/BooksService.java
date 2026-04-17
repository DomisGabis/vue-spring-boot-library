package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.repository.AuthorRepository;
import pl.edu.pwr.ztw.books.repository.BookRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksService implements IBooksService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BooksService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPages(book.getPages());

        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
            dto.setAuthorFirstName(book.getAuthor().getFirstName());
            dto.setAuthorLastName(book.getAuthor().getLastName());
        }
        else {
            dto.setAuthorLastName("Unknown");
        }
        return dto;
    }

    @Override
    public Collection<BookDTO> getBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBook(Long id) {
        return bookRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    @Override
    public void addBook(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setPages(dto.getPages());
        authorRepository.findById(dto.getAuthorId()).ifPresent(book::setAuthor);
        bookRepository.save(book);
    }

    @Override
    public void updateBook(Long id, BookDTO dto) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setTitle(dto.getTitle());
            book.setPages(dto.getPages());
            authorRepository.findById(dto.getAuthorId()).ifPresent(book::setAuthor);
            bookRepository.save(book);
        });
    }

    @Override
    public void deleteBook(Long id) { bookRepository.deleteById(id); }
}
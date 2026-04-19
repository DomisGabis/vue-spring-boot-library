package pl.edu.pwr.ztw.books.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.repository.AuthorRepository;
import pl.edu.pwr.ztw.books.repository.BookRepository;
import pl.edu.pwr.ztw.books.repository.RentalRepository;

@Service
public class BooksService implements IBooksService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final RentalRepository rentalRepository;

    public BooksService(BookRepository bookRepository, AuthorRepository authorRepository, RentalRepository rentalRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.rentalRepository = rentalRepository;
    }

    private BookDTO mapToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPages(book.getPages());
        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
            dto.setAuthorFirstName(book.getAuthor().getFirstName());
            dto.setAuthorLastName(book.getAuthor().getLastName());
        }

        boolean isCurrentlyRented = rentalRepository.existsByBookIdAndReturnDateIsNull(book.getId());
        dto.setRented(isCurrentlyRented);

        return dto;
    }

    @Override
    public Page<BookDTO> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public BookDTO getBook(Long id) {
        return bookRepository.findById(id).map(this::mapToDTO).orElse(null);
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
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        boolean isRented = rentalRepository.existsByBookIdAndReturnDateIsNull(id);

        if (isRented) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete a book that is currently rented"
            );
        }
        bookRepository.deleteById(id);
    }

    @Override
    public long getBookCount() {
        return bookRepository.count();
    }

    @Override
    public Page<BookDTO> getBooksByAuthorId(Long authorId, Pageable pageable) {
        Page<Book> booksPage = bookRepository.findByAuthorId(authorId, pageable);

        return booksPage.map(this::mapToDTO);
    }
}
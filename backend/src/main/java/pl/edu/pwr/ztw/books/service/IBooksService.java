package pl.edu.pwr.ztw.books.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.model.Book;
import java.util.Collection;

public interface IBooksService {
    Page<BookDTO> getBooks(Pageable pageable);
    BookDTO getBook(Long id);
    void addBook(BookDTO bookDto);
    void updateBook(Long id, BookDTO bookDto);
    void deleteBook(Long id);
    long getBookCount();

    Page<BookDTO> getBooksByAuthorId(Long authorId, Pageable pageable);
}
package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.model.Book;
import java.util.Collection;

public interface IBooksService {
    Collection<BookDTO> getBooks();
    BookDTO getBook(Long id);
    void addBook(BookDTO bookDto);
    void updateBook(Long id, BookDTO bookDto);
    void deleteBook(Long id);
}
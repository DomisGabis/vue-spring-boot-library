package pl.edu.pwr.ztw.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.ztw.books.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}

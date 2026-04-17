package pl.edu.pwr.ztw.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.ztw.books.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

package pl.edu.pwr.ztw.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.ztw.books.model.Reader;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    long count();
}

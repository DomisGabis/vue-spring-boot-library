package pl.edu.pwr.ztw.books.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long>  {
    long count();
    long countByReturnDateIsNull();
    boolean existsByBookId(Long bookId);
    Rental findByBookId(Long bookId);
    Page<Rental> findByReturnDateIsNull(Pageable pageable);
    Page<Rental> findByReaderId(Long readerId, Pageable pageable);
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
}

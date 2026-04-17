package pl.edu.pwr.ztw.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.ztw.books.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long>  {
    boolean existsByBookIdAndIsReturnedFalse(Long bookId);
    Rental findByBookIdAndIsReturnedFalse(Long bookId);
}

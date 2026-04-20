package pl.edu.pwr.ztw.books.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.ztw.books.dto.AuthorDTO;
import pl.edu.pwr.ztw.books.dto.RentalDTO;
import pl.edu.pwr.ztw.books.model.Rental;
import java.util.Collection;

public interface IRentalsService {
    Page<RentalDTO> getRentals(boolean activeOnly, Pageable pageable);
    RentalDTO getRental(Long id);
    void addRental(RentalDTO rentalDto);
    void updateRental(Long id, RentalDTO rentalDto);
    void deleteRental(Long id);
    long getTotalCount();
    long getActiveCount();
    Page<RentalDTO> getRentalsByReaderId(long readerId, Pageable pageable);
}
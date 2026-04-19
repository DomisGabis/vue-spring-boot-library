package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.dto.AuthorDTO;
import pl.edu.pwr.ztw.books.dto.RentalDTO;
import pl.edu.pwr.ztw.books.model.Rental;
import java.util.Collection;

public interface IRentalsService {
    Collection<RentalDTO> getRentals();
    RentalDTO getRental(Long id);
    void addRental(RentalDTO rentalDto);
    void updateRental(Long id, RentalDTO rentalDto);
    void deleteRental(Long id);
    long getTotalCount();
    long getActiveCount();
}
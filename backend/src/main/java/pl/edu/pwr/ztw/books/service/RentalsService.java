package pl.edu.pwr.ztw.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.dto.RentalDTO;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.Reader;
import pl.edu.pwr.ztw.books.model.Rental;
import pl.edu.pwr.ztw.books.repository.BookRepository;
import pl.edu.pwr.ztw.books.repository.ReaderRepository;
import pl.edu.pwr.ztw.books.repository.RentalRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalsService implements IRentalsService {
    @Autowired
    private IBooksService booksService;
    @Autowired
    private IReadersService readersService;

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public RentalsService(RentalRepository renRepo, BookRepository bRepo, ReaderRepository readRepo) {
        this.rentalRepository = renRepo;
        this.bookRepository = bRepo;
        this.readerRepository = readRepo;
    }
    private RentalDTO mapToDTO(Rental r) {
        RentalDTO dto = new RentalDTO();
        dto.setId(r.getId());
        if (r.getBook() != null) {
            dto.setBookId(r.getBook().getId());
            dto.setBookTitle(r.getBook().getTitle());
        }
        if (r.getReader() != null) {
            dto.setReaderId(r.getReader().getId());
            dto.setReaderFirstName(r.getReader().getFirstName());
            dto.setReaderLastName(r.getReader().getLastName());
            dto.setReaderEmail(r.getReader().getEmail());
        }
        dto.setRentalDate(r.getRentalDate());
        dto.setReturnDate(r.getReturnDate());
        dto.setReturned(r.getReturnDate() != null);
        return dto;
    }

    @Override
    public Page<RentalDTO> getRentals(boolean activeOnly, Pageable pageable) {
        return activeOnly ? rentalRepository.findByReturnDateIsNull(pageable).map(this::mapToDTO)
                : rentalRepository.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public RentalDTO getRental(Long id) {
        return rentalRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public void addRental(RentalDTO dto) {
        Rental rental = new Rental();

        Book book = bookRepository.findById(dto.getBookId()).orElse(null);
        Reader reader = readerRepository.findById(dto.getReaderId()).orElse(null);

        if (book != null && reader != null) {
            rental.setBook(book);
            rental.setReader(reader);
            rental.setRentalDate(dto.getRentalDate());
            rentalRepository.save(rental);
        }
    }

    @Override
    public void updateRental(Long id, RentalDTO dto) {
        rentalRepository.findById(id).ifPresent(rental -> {
            Book book = bookRepository.findById(dto.getBookId()).orElse(null);
            Reader reader = readerRepository.findById(dto.getReaderId()).orElse(null);

            if (book != null && reader != null) {
                rental.setBook(book);
                rental.setReader(reader);
                rental.setRentalDate(dto.getRentalDate());
                rental.setReturnDate(dto.getReturnDate());
                rentalRepository.save(rental);
            }
        });
    }

    @Override
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    public long getTotalCount() {
        return rentalRepository.count();
    }

    public long getActiveCount() {
        return rentalRepository.countByReturnDateIsNull();
    }
}
package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.dto.BookDTO;
import pl.edu.pwr.ztw.books.dto.RentalDTO;
import pl.edu.pwr.ztw.books.model.Rental;
import pl.edu.pwr.ztw.books.service.IRentalsService;

import java.util.Collection;

@RestController
@RequestMapping("/rentals")
public class RentalsController {

    @Autowired
    private IRentalsService rentalsService;

    public RentalsController(IRentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    @GetMapping
    public ResponseEntity<Page<RentalDTO>> getRentals(
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 5, sort = "rentalDate") Pageable pageable) {
        return new ResponseEntity<>(rentalsService.getRentals(active, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable Long id) {
        RentalDTO rental = rentalsService.getRental(id);
        if (rental != null) {
            return ResponseEntity.ok(rental);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getRentalCount() {
        return new ResponseEntity<>(rentalsService.getTotalCount(), HttpStatus.OK);
    }

    @GetMapping("/active-count")
    public ResponseEntity<Long> getActiveRentalCount() {
        return new ResponseEntity<>(rentalsService.getActiveCount(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addRental(@RequestBody RentalDTO rentalDto) {
        rentalsService.addRental(rentalDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRental(@PathVariable Long id, @RequestBody RentalDTO rentalDto) {
        if (rentalsService.getRental(id) == null) {
            return ResponseEntity.notFound().build();
        }
        rentalsService.updateRental(id, rentalDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        if (rentalsService.getRental(id) == null) {
            return ResponseEntity.notFound().build();
        }
        rentalsService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reader/{readerId}")
    public ResponseEntity<Page<RentalDTO>> getRentalsByReader(@PathVariable Long readerId, @PageableDefault(size = 10, sort = "rentalDate") Pageable pageable) {
        Page<RentalDTO> books = rentalsService.getRentalsByReaderId(readerId, pageable);
        return ResponseEntity.ok(books);
    }
}
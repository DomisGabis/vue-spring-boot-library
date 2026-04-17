package pl.edu.pwr.ztw.books.model;

import jakarta.persistence.*;
import tools.jackson.databind.util.Converter;

import java.time.LocalDate;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean isReturned;

    public Rental() {}

    public Rental(Long id, Book book, Reader reader, LocalDate rentalDate, boolean isReturned) {
        this.id = id;
        this.book = book;
        this.reader = reader;
        this.rentalDate = rentalDate;
        this.isReturned = isReturned;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isReturned() { return isReturned; }
    public void setReturned(boolean returned) { isReturned = returned; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public Reader getReader() { return reader; }
    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public LocalDate getRentalDate() { return rentalDate; }
    public void setRentalDate(LocalDate rentalDate) { this.rentalDate = rentalDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
}
package pl.edu.pwr.ztw.books.dto;

import java.time.LocalDate;

public class RentalDTO {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long readerId;
    private String readerFirstName;
    private String readerLastName;
    private String readerEmail;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean isReturned;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getBookId() {
        return bookId;
    }
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    public Long getReaderId() {
        return readerId;
    }
    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }
    public String getReaderFirstName() {
        return readerFirstName;
    }
    public void setReaderFirstName(String readerFirstName) {
        this.readerFirstName = readerFirstName;
    }
    public String getReaderLastName() {
        return readerLastName;
    }
    public void setReaderLastName(String readerLastName) {
        this.readerLastName = readerLastName;
    }
    public String getReaderEmail() {
        return readerEmail;
    }
    public void setReaderEmail(String readerEmail) {
        this.readerEmail = readerEmail;
    }
    public LocalDate getRentalDate() {
        return rentalDate;
    }
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public boolean isReturned() {
        return isReturned;
    }
    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}

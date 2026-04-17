package pl.edu.pwr.ztw.books.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.Reader;
import pl.edu.pwr.ztw.books.repository.AuthorRepository;
import pl.edu.pwr.ztw.books.repository.BookRepository;
import pl.edu.pwr.ztw.books.repository.ReaderRepository;

@Configuration
public class DbInitializer {
    @Bean
    public CommandLineRunner initData(BookRepository bookRepo, AuthorRepository authorRepo, ReaderRepository readerRepo) {
        return args -> {
            //AUTHORS
            Author a1 = new Author();
            a1.setFirstName("Henryk");
            a1.setLastName("Sienkiewicz");
            authorRepo.save(a1);

            Author a2 = new Author();
            a2.setFirstName("Adam");
            a2.setLastName("Mickiewicz");
            authorRepo.save(a2);

            Author a3 = new Author();
            a3.setFirstName("Gabriela");
            a3.setLastName("Zapolska");
            authorRepo.save(a3);

            //BOOKS
            Book b1 = new Book();
            b1.setTitle("Potop");
            b1.setPages(936);
            b1.setAuthor(a1);
            bookRepo.save(b1);

            Book b2 = new Book();
            b2.setTitle("Moralność Pani Dulskiej");
            b2.setPages(100);
            b2.setAuthor(a3);
            bookRepo.save(b2);

            Book b3 = new Book();
            b3.setTitle("Heated Rivalry");
            b3.setPages(2000);
            b3.setAuthor(a3);
            bookRepo.save(b3);

            //READERS
            Reader r1 = new Reader();
            r1.setFirstName("Jan");
            r1.setLastName("Kowalski");
            r1.setEmail("jan@wp.pl");
            readerRepo.save(r1);

            Reader r2 = new Reader();
            r2.setFirstName("Inga");
            r2.setLastName("Grabska");
            r2.setEmail("inga_g@gmail.com");
            readerRepo.save(r2);

            //RENTALS
            Rental rent1 = new Rental();
            rent1.setBook(b1);
            rent1.setReader(r1);
            rent1.setRentalDate(LocalDate.now().minusDays(10));
            rent1.setReturnDate(null);
            rentalRepo.save(rent1);

            Rental rent2 = new Rental();
            rent2.setBook(b2);
            rent2.setReader(r2);
            rent2.setRentalDate(LocalDate.now().minusDays(20));
            rent2.setReturnDate(LocalDate.now().minusDays(5));
            rentalRepo.save(rent2);

            Rental rent3 = new Rental();
            rent3.setBook(b3);
            rent3.setReader(r1);
            rent3.setRentalDate(LocalDate.now().minusDays(2));
            rent3.setReturnDate(null);
            rentalRepo.save(rent3);
            
            //System.out.println("Baza danych została zainicjalizowana!");
        };
    }
}

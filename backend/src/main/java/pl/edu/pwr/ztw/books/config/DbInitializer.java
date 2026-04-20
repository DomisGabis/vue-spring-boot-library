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
import pl.edu.pwr.ztw.books.model.Rental;
import pl.edu.pwr.ztw.books.repository.RentalRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DbInitializer {
    @Bean
    public CommandLineRunner initData(BookRepository bookRepo, AuthorRepository authorRepo, ReaderRepository readerRepo, RentalRepository rentalRepo) {
        return args -> {
            Random random = new Random();
            List<Author> authors = new ArrayList<>();
            String[][] authorData = {
                    {"Henryk", "Sienkiewicz"}, {"Adam", "Mickiewicz"}, {"Gabriela", "Zapolska"},
                    {"Stephen", "King"}, {"J.K.", "Rowling"}, {"George R.R.", "Martin"},
                    {"Agatha", "Christie"}, {"Haruki", "Murakami"}
            };

            for (String[] data : authorData) {
                Author a = new Author();
                a.setFirstName(data[0]);
                a.setLastName(data[1]);
                authors.add(authorRepo.save(a));
            }

            List<Book> books = new ArrayList<>();
            String[][] bookData = {
                    {"Potop", "936", "0"}, {"Moralność Pani Dulskiej", "100", "2"},
                    {"Heated Rivalry", "2000", "2"}, {"The Shining", "447", "3"},
                    {"It", "1138", "3"}, {"Harry Potter and the Sorcerer's Stone", "309", "4"},
                    {"Harry Potter and the Chamber of Secrets", "341", "4"}, {"A Game of Thrones", "694", "5"},
                    {"A Clash of Kings", "768", "5"}, {"Murder on the Orient Express", "256", "6"},
                    {"Death on the Nile", "288", "6"}, {"Norwegian Wood", "296", "7"},
                    {"Kafka on the Shore", "505", "7"}, {"The Great Gatsby", "180", "1"}
            };

            for (String[] data : bookData) {
                Book b = new Book();
                b.setTitle(data[0]);
                b.setPages(Integer.parseInt(data[1]));
                b.setAuthor(authors.get(Integer.parseInt(data[2])));
                books.add(bookRepo.save(b));
            }

            List<Reader> readers = new ArrayList<>();
            String[] firstNames = {"Jan", "Anna", "Piotr", "Maria", "Krzysztof", "Katarzyna", "Marek", "Magdalena"};
            String[] lastNames = {"Kowal", "Nowak", "Bik", "Wójcik", "Zielewicz", "Szymczyk", "Lemańczyk"};

            for (int i = 1; i <= 20; i++) {
                Reader r = new Reader();
                r.setFirstName(firstNames[random.nextInt(firstNames.length)]);
                r.setLastName(lastNames[random.nextInt(lastNames.length)]);
                r.setEmail("user" + i + "@example.com");
                readers.add(readerRepo.save(r));
            }

            // 4. WYPOŻYCZENIA (Zhardkodowane i bezpieczne)
            List<Rental> rentals = new ArrayList<>();

            Rental rent1 = new Rental();
            rent1.setBook(books.get(0));
            rent1.setReader(readers.get(0));
            rent1.setRentalDate(LocalDate.now().minusDays(10));
            rent1.setReturnDate(null);
            rentalRepo.save(rent1);

            Rental rent2 = new Rental();
            rent2.setBook(books.get(1));
            rent2.setReader(readers.get(1));
            rent2.setRentalDate(LocalDate.now().minusDays(20));
            rent2.setReturnDate(LocalDate.now().minusDays(5));
            rentalRepo.save(rent2);

            Rental rent3 = new Rental();
            rent3.setBook(books.get(2));
            rent3.setReader(readers.get(2));
            rent3.setRentalDate(LocalDate.now().minusDays(2));
            rent3.setReturnDate(null);
            rentalRepo.save(rent3);

            for (int i = 3; i < 13; i++) {
                Rental historyRent = new Rental();
                historyRent.setBook(books.get(i));
                historyRent.setReader(readers.get(i % readers.size()));
                historyRent.setRentalDate(LocalDate.now().minusMonths(2).plusDays(i));
                if(random.nextBoolean())historyRent.setReturnDate(LocalDate.now().minusMonths(1).plusDays(i));
                rentalRepo.save(historyRent);
            }

            
            //System.out.println("Baza danych została zainicjalizowana!");
        };
    }
}

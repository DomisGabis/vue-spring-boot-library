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
            Author a1 = new Author();
            a1.setFirstName("Henryk");
            a1.setLastName("Sienkiewicz");
            authorRepo.save(a1);

            Book b1 = new Book();
            b1.setTitle("Potop");
            b1.setPages(936);
            b1.setAuthor(a1);
            bookRepo.save(b1);

            Reader r1 = new Reader();
            r1.setFirstName("Jan");
            r1.setLastName("Kowalski");
            r1.setEmail("jan@wp.pl");
            readerRepo.save(r1);

            //System.out.println("Baza danych została zainicjalizowana!");
        };
    }
}

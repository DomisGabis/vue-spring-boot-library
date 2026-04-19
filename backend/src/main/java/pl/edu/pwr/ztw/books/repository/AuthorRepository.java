package pl.edu.pwr.ztw.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pwr.ztw.books.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT COUNT(b) FROM Book b WHERE b.author.id = :authorId AND b.isDeleted = false")
    int countBooksByAuthorId(@Param("authorId") Long authorId);
}

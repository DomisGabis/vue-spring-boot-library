package pl.edu.pwr.ztw.books.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.ztw.books.dto.AuthorDTO;
import pl.edu.pwr.ztw.books.model.Author;
import java.util.Collection;

public interface IAuthorsService {
    Page<AuthorDTO> getAuthors(Pageable pageable);
    AuthorDTO getAuthor(Long id);
    AuthorDTO addAuthor(AuthorDTO authorDto);
    void updateAuthor(Long id, AuthorDTO authorDto);
    void deleteAuthor(Long id);
    long getAuthorCount();
}
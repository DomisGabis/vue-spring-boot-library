package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.dto.AuthorDTO;
import pl.edu.pwr.ztw.books.model.Author;
import java.util.Collection;

public interface IAuthorsService {
    Collection<AuthorDTO> getAuthors();
    AuthorDTO getAuthor(Long id);
    void addAuthor(AuthorDTO authorDto);
    void updateAuthor(Long id, AuthorDTO authorDto);
    void deleteAuthor(Long id);
}
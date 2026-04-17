package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.dto.AuthorDTO;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.repository.AuthorRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AuthorsService implements IAuthorsService {
    private final AuthorRepository authorRepository;

    public AuthorsService(AuthorRepository authorRepository) { this.authorRepository = authorRepository; }

    private AuthorDTO mapToDTO(Author a) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(a.getId());
        dto.setFirstName(a.getFirstName());
        dto.setLastName(a.getLastName());
        return dto;
    }
    @Override
    public Collection<AuthorDTO> getAuthors() {
        return authorRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        return authorRepository.findById(id).map(this::mapToDTO).orElse(null);
    }
    @Override
    public void addAuthor(AuthorDTO dto) {
        Author author = new Author();
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        authorRepository.save(author);
    }

    @Override
    public void updateAuthor(Long id, AuthorDTO dto) {
        authorRepository.findById(id).ifPresent(author -> {
            author.setFirstName(dto.getFirstName());
            author.setLastName(dto.getLastName());
            authorRepository.save(author);
        });
    }

    @Override
    public void deleteAuthor(Long id) { authorRepository.deleteById(id); }
}
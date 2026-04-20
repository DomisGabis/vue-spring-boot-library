package pl.edu.pwr.ztw.books.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.ztw.books.dto.AuthorDTO;
import pl.edu.pwr.ztw.books.dto.ReaderDTO;
import pl.edu.pwr.ztw.books.model.Reader;
import java.util.Collection;

public interface IReadersService {
    Page<ReaderDTO> getReaders(Pageable pageable);
    ReaderDTO getReader(Long id);
    void addReader(ReaderDTO readerDto);
    void updateReader(Long id, ReaderDTO readerDto);
    void deleteReader(Long id);
    long getCount();
}
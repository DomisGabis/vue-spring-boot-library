package pl.edu.pwr.ztw.books.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.dto.ReaderDTO;
import pl.edu.pwr.ztw.books.model.Reader;
import pl.edu.pwr.ztw.books.repository.ReaderRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReadersService implements IReadersService {
    private final ReaderRepository readerRepository;

    public ReadersService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    private ReaderDTO mapToDTO(Reader reader) {
        ReaderDTO dto = new ReaderDTO();
        dto.setId(reader.getId());
        dto.setFirstName(reader.getFirstName());
        dto.setLastName(reader.getLastName());
        dto.setEmail(reader.getEmail());
        return dto;
    }

    @Override
    public Page<ReaderDTO> getReaders(Pageable pageable) {
        return readerRepository.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public ReaderDTO getReader(Long id) {
        return readerRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public void addReader(ReaderDTO readerDto) {
        Reader reader = new Reader();
        reader.setFirstName(readerDto.getFirstName());
        reader.setLastName(readerDto.getLastName());
        reader.setEmail(readerDto.getEmail());

        readerRepository.save(reader);
    }

    @Override
    public void updateReader(Long id, ReaderDTO readerDto) {
        readerRepository.findById(id).ifPresent(reader -> {
            reader.setFirstName(readerDto.getFirstName());
            reader.setLastName(readerDto.getLastName());
            reader.setEmail(readerDto.getEmail());
            readerRepository.save(reader);
        });
    }

    @Override
    public void deleteReader(Long id) {
        readerRepository.deleteById(id);
    }

    @Override
    public long getCount() {
        return readerRepository.count();
    }
}
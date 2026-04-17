package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.dto.ReaderDTO;
import pl.edu.pwr.ztw.books.service.IReadersService;

import java.util.Collection;

@RestController
@RequestMapping("/readers")
public class ReadersController {

    @Autowired
    private IReadersService readersService;

    public ReadersController(IReadersService readersService) {
        this.readersService = readersService;
    }

    @GetMapping
    public ResponseEntity<Collection<ReaderDTO>> getReaders() {
        return new ResponseEntity<>(readersService.getReaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderDTO> getReader(@PathVariable Long id) {
        return new ResponseEntity<>(readersService.getReader(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReaderDTO> addReader(@RequestBody ReaderDTO reader) {
        readersService.addReader(reader);
        return new ResponseEntity<>(reader, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReaderDTO> updateReader(@PathVariable Long id, @RequestBody ReaderDTO reader) {
        readersService.updateReader(id, reader);
        return new ResponseEntity<>(reader, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        readersService.deleteReader(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
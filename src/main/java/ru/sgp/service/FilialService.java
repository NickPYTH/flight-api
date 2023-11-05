package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.FilialDTO;
import ru.sgp.model.Filial;
import ru.sgp.repository.FilialRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilialService {
    @Autowired
    FilialRepository filialRepository;

    public ResponseEntity<FilialDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        Optional<Filial> filialOptional = filialRepository.findById(id);
        if (filialOptional.isPresent()) {
            return new ResponseEntity<>(mapper.map(filialOptional.get(), FilialDTO.class), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<FilialDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        List<FilialDTO> response = new ArrayList<>();
        filialRepository.findAll().forEach(filial -> response.add(mapper.map(filial, FilialDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
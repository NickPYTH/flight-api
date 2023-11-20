package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.WorkTypeDTO;
import ru.sgp.model.WorkType;
import ru.sgp.repository.WorkTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkTypeService {
    @Autowired
    WorkTypeRepository workTypeRepository;

    public ResponseEntity<WorkTypeDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        Optional<WorkType> workTypeOptional = workTypeRepository.findById(id);
        if (workTypeOptional.isPresent()) {
            return new ResponseEntity<>(mapper.map(workTypeOptional.get(), WorkTypeDTO.class), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<WorkTypeDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        List<WorkTypeDTO> response = new ArrayList<>();
        workTypeRepository.findAll().forEach(workType -> response.add(mapper.map(workType, WorkTypeDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
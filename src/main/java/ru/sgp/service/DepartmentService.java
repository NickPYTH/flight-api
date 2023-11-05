package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.DepartmentDTO;
import ru.sgp.model.Department;
import ru.sgp.repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public ResponseEntity<DepartmentDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isPresent()) {
            return new ResponseEntity<>(mapper.map(departmentOptional.get(), DepartmentDTO.class), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<DepartmentDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        List<DepartmentDTO> response = new ArrayList<>();
        departmentRepository.findAll().forEach(department -> response.add(mapper.map(department, DepartmentDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
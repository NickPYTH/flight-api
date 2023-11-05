package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.EmployeeDTO;
import ru.sgp.model.Employee;
import ru.sgp.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public ResponseEntity<Employee> createEmployee(EmployeeDTO employeeDTO) {
        ModelMapper mapper = new ModelMapper();
        Employee employee = mapper.map(employeeDTO, Employee.class);
        employeeRepository.save(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<EmployeeDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            return new ResponseEntity<>(mapper.map(employeeOptional.get(), EmployeeDTO.class), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<EmployeeDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        List<EmployeeDTO> response = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> response.add(mapper.map(employee, EmployeeDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
// String username = SecurityManager.getCurrentUser();

}
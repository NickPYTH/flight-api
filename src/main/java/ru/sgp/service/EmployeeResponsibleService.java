package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.EmployeeResponsibleDTO;
import ru.sgp.model.Employee;
import ru.sgp.model.EmployeeResponsible;
import ru.sgp.repository.EmployeeResponsibleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeResponsibleService {
    @Autowired
    EmployeeResponsibleRepository employeeResponsibleRepository;

    public ResponseEntity<EmployeeResponsibleDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        Optional<EmployeeResponsible> employeeResponsibleOptional = employeeResponsibleRepository.findById(id);
        if (employeeResponsibleOptional.isPresent()) {
            EmployeeResponsibleDTO tmp = new EmployeeResponsibleDTO();
            Employee employee = employeeResponsibleOptional.get().getIdEmployee();
            String FIO = employee.getSecondname() + ' ' + employee.getFirstName() + ' ' + employee.getLastname();
            tmp = mapper.map(employeeResponsibleOptional.get(), EmployeeResponsibleDTO.class);
            tmp.setFIO(FIO);
            return new ResponseEntity<>(tmp, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<EmployeeResponsibleDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        List<EmployeeResponsibleDTO> response = new ArrayList<>();
        for (EmployeeResponsible empResp : employeeResponsibleRepository.findAll()) {
            EmployeeResponsibleDTO tmp = new EmployeeResponsibleDTO();
            Employee employee = empResp.getIdEmployee();
            String FIO = employee.getLastname() + ' ' + employee.getFirstName() + ' ' + employee.getSecondname();
            tmp = mapper.map(empResp, EmployeeResponsibleDTO.class);
            tmp.setFIO(FIO);
            response.add(tmp);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
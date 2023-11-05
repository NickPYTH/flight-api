package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.EmployeeDTO;
import ru.sgp.model.Employee;
import ru.sgp.service.EmployeeService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping(path = "/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<EmployeeDTO> get(@RequestParam Long id) {
        return employeeService.get(id);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        return employeeService.getAll();
    }
}
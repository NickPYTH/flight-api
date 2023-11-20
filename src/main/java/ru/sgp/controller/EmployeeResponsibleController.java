package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.EmployeeResponsibleDTO;
import ru.sgp.dto.WorkTypeDTO;
import ru.sgp.service.EmployeeResponsibleService;
import ru.sgp.service.WorkTypeService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/employeeResponsible")
public class EmployeeResponsibleController {
    @Autowired
    EmployeeResponsibleService employeeResponsibleService;

    @GetMapping(path = "/get")
    public ResponseEntity<EmployeeResponsibleDTO> get(@RequestParam Long id) {
        return employeeResponsibleService.get(id);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<EmployeeResponsibleDTO>> getAll() {
        return employeeResponsibleService.getAll();
    }
}

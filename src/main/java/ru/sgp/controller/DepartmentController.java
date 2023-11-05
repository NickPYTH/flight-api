package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.DepartmentDTO;
import ru.sgp.service.DepartmentService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping(path = "/get")
    public ResponseEntity<DepartmentDTO> get(@RequestParam Long id) {
        return departmentService.get(id);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<DepartmentDTO>> getAll() {
        return departmentService.getAll();
    }
}

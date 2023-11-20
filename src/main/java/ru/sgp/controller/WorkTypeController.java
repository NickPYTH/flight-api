package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.WorkTypeDTO;
import ru.sgp.service.WorkTypeService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/worktype")
public class WorkTypeController {
    @Autowired
    WorkTypeService workTypeService;

    @GetMapping(path = "/get")
    public ResponseEntity<WorkTypeDTO> get(@RequestParam Long id) {
        return workTypeService.get(id);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<WorkTypeDTO>> getAll() {
        return workTypeService.getAll();
    }
}

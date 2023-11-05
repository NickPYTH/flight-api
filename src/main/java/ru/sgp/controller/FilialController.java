package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.FilialDTO;
import ru.sgp.service.FilialService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/filial")
public class FilialController {
    @Autowired
    FilialService filialService;

    @GetMapping(path = "/get")
    public ResponseEntity<FilialDTO> get(@RequestParam Long id) {
        return filialService.get(id);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<FilialDTO>> getAll() {
        return filialService.getAll();
    }
}

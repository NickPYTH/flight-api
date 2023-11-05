package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.AirportDTO;
import ru.sgp.service.AirportService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/airport")
public class AirportController {
    @Autowired
    AirportService airportService;

    @GetMapping(path = "/get")
    public ResponseEntity<AirportDTO> get(@RequestParam Long id) {
        return airportService.get(id);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<AirportDTO>> getAll() {
        return airportService.getAll();
    }
}

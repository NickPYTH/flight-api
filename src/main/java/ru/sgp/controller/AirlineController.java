package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.AirlineDTO;
import ru.sgp.dto.AirportDTO;
import ru.sgp.service.AirlineService;
import ru.sgp.service.AirportService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/airline")
public class AirlineController {
    @Autowired
    AirlineService airlineService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<AirlineDTO>> getAll() {
        return airlineService.getAll();
    }
}

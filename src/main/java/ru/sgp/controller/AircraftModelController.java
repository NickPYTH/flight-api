package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sgp.dto.AircraftModelDTO;
import ru.sgp.service.AircraftModelService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/aircraftModel")
public class AircraftModelController {
    @Autowired
    AircraftModelService aircraftModelService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<AircraftModelDTO>> getAll() {
        return aircraftModelService.getAll();
    }
}

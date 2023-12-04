package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sgp.dto.FlightTargetDTO;
import ru.sgp.service.FlightTargetService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/flightTarget")
public class FlightTargetController {
    @Autowired
    FlightTargetService flightTargetService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<FlightTargetDTO>> getAll() {
        return flightTargetService.getAll();
    }
}

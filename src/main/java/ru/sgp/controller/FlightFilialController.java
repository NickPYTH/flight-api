package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.EmployeeDTO;
import ru.sgp.dto.FlightFilialDTO;
import ru.sgp.model.Employee;
import ru.sgp.service.FlightFilialService;

import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/flightFilial")
public class FlightFilialController {
    @Autowired
    FlightFilialService flightFilialService;

    @GetMapping(path = "/get")
    public ResponseEntity<FlightFilialDTO> get(@RequestParam Long id) {
        return flightFilialService.get(id);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<FlightFilialDTO>> getAll() {
        return flightFilialService.getAll();
    }

    @PatchMapping(path = "/update")
    public ResponseEntity<FlightFilialDTO> update(@RequestBody FlightFilialDTO flightFilialDTO) throws ParseException {
        return flightFilialService.update(flightFilialDTO);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<FlightFilialDTO> create(@RequestBody FlightFilialDTO flightFilialDTO) throws ParseException {
        return flightFilialService.create(flightFilialDTO);
    }
}

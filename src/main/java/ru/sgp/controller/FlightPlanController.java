package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.FlightPlanDTO;
import ru.sgp.service.FlightPlanService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/flightPlan")
public class FlightPlanController {
    @Autowired
    FlightPlanService flightPlanService;

    @GetMapping(path = "/get")
    public ResponseEntity<FlightPlanDTO> get(@RequestParam Long id) {
        return flightPlanService.get(id);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<FlightPlanDTO>> getAll() {
        return flightPlanService.getAll();
    }

    @PatchMapping(path = "/update")
    public ResponseEntity<FlightPlanDTO> update(@RequestBody FlightPlanDTO flightFilialDTO) {
        return flightPlanService.update(flightFilialDTO);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<FlightPlanDTO> create(@RequestBody FlightPlanDTO flightFilialDTO) {
        return flightPlanService.create(flightFilialDTO);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Long> create(@RequestParam Long id) {
        return flightPlanService.delete(id);
    }
}

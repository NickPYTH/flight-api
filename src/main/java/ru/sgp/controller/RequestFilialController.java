package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.RequestFilialDTO;
import ru.sgp.service.RequestFilialService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/requestFilial")
public class RequestFilialController {
    @Autowired
    RequestFilialService requestFilialService;

    @GetMapping(path = "/get")
    public ResponseEntity<RequestFilialDTO> get(@RequestParam Long id) {
        return requestFilialService.get(id);
    }

    @GetMapping(path = "/getAllByYear")
    public ResponseEntity<List<RequestFilialDTO>> getAllByYear(@RequestParam Integer year) {
        return requestFilialService.getAllByYear(year);
    }

}

package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.RequestDTO;
import ru.sgp.service.RequestService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    RequestService requestService;

    @GetMapping(path = "/getAllByYear")
    public ResponseEntity<List<RequestDTO>> getAllByYear(@RequestParam Integer year) {
        return requestService.getAllByYear(year);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<RequestDTO> get(@RequestParam Long id) {
        return requestService.get(id);
    }

}

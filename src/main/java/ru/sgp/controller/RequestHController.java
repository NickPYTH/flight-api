package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.RequestHDTO;
import ru.sgp.service.RequestHService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/requestHelicopter")
public class RequestHController {
    @Autowired
    RequestHService requestHService;

    @GetMapping(path = "/getAllByYear")
    public ResponseEntity<List<RequestHDTO>> getAllByYear(@RequestParam Integer year) {
        return requestHService.getAllByYear(year);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<RequestHDTO> getAllByYear(@RequestParam Long id) {
        return requestHService.get(id);
    }

}

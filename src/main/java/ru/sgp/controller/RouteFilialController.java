package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.RequestFilialDTO;
import ru.sgp.dto.RouteFilialDTO;
import ru.sgp.service.RequestFilialService;
import ru.sgp.service.RouteFilialService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/routeFilial")
public class RouteFilialController {
    @Autowired
    RouteFilialService routeFilialService;

    @GetMapping(path = "/get")
    public ResponseEntity<RouteFilialDTO> get(@RequestParam Long id) {
        return routeFilialService.get(id);
    }

}

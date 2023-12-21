package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.RequestFilialDTO;
import ru.sgp.dto.RequestStateDTO;
import ru.sgp.service.RequestFilialService;

import java.text.ParseException;
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

    @PostMapping(path = "/sendOnConfirm")
    public ResponseEntity<RequestStateDTO> sendOnConfirm(@RequestParam Long flightRequestId) {
        return requestFilialService.sendOnConfirm(flightRequestId);
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<RequestStateDTO> confirm(@RequestParam Long flightRequestId) {
        return requestFilialService.confirm(flightRequestId);
    }

    @PostMapping(path = "/decline")
    public ResponseEntity<RequestStateDTO> decline(@RequestParam Long flightRequestId) {
        return requestFilialService.decline(flightRequestId);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<RequestFilialDTO> create(@RequestBody RequestFilialDTO body) throws ParseException {
        return requestFilialService.create(body);
    }

}

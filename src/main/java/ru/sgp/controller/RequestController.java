package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.RequestDTO;
import ru.sgp.dto.RequestHistoryDTO;
import ru.sgp.dto.UpdateRequestDTO;
import ru.sgp.service.RequestHistoryService;
import ru.sgp.service.RequestService;

import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    RequestService requestService;
    @Autowired
    RequestHistoryService requestHistoryService;

    @GetMapping(path = "/getAllByYear")
    public ResponseEntity<List<RequestDTO>> getAllByYear(@RequestParam Integer year) {
        return requestService.getAllByYear(year);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<RequestDTO> get(@RequestParam Long id) {
        return requestService.get(id);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<UpdateRequestDTO> update(@RequestBody UpdateRequestDTO updateRequestDTO) throws ParseException {
        return requestService.update(updateRequestDTO);
    }

    @GetMapping(path = "/getHistory")
    public ResponseEntity<List<RequestHistoryDTO>> getHistory(@RequestParam Long id) {
        return requestHistoryService.getHistory(id);
    }

}

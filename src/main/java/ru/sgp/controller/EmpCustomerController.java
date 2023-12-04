package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sgp.dto.EmpCustomerDTO;
import ru.sgp.service.EmpCustomerService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/empCustomer")
public class EmpCustomerController {
    @Autowired
    EmpCustomerService empCustomerService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<EmpCustomerDTO>> getAll() {
        return empCustomerService.getAll();
    }
}

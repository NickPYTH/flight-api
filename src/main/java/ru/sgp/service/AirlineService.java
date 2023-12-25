package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.AirlineDTO;
import ru.sgp.repository.AirlineRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirlineService {
    @Autowired
    AirlineRepository airportRepository;

    public ResponseEntity<List<AirlineDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        List<AirlineDTO> response = new ArrayList<>();
        airportRepository.findAll().forEach(airline -> response.add(mapper.map(airline, AirlineDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.FlightTargetDTO;
import ru.sgp.repository.FlightTargetRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightTargetService {
    @Autowired
    FlightTargetRepository flightTargetRepository;

    public ResponseEntity<List<FlightTargetDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        List<FlightTargetDTO> response = new ArrayList<>();
        flightTargetRepository.findAll().forEach(flightTarget -> response.add(mapper.map(flightTarget, FlightTargetDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
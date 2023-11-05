package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.AirportDTO;
import ru.sgp.model.Airport;
import ru.sgp.repository.AirportRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirportService {
    @Autowired
    AirportRepository airportRepository;

    public ResponseEntity<AirportDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        Optional<Airport> airportOptional = airportRepository.findById(id);
        if (airportOptional.isPresent()) {
            return new ResponseEntity<>(mapper.map(airportOptional.get(), AirportDTO.class), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<AirportDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        List<AirportDTO> response = new ArrayList<>();
        airportRepository.findAll().forEach(airport -> response.add(mapper.map(airport, AirportDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
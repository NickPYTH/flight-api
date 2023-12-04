package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.AircraftModelDTO;
import ru.sgp.repository.AircraftModelRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AircraftModelService {
    @Autowired
    AircraftModelRepository aircraftModelRepository;

    public ResponseEntity<List<AircraftModelDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        List<AircraftModelDTO> response = new ArrayList<>();
        aircraftModelRepository.findAll().forEach(aircraftModel -> response.add(mapper.map(aircraftModel, AircraftModelDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
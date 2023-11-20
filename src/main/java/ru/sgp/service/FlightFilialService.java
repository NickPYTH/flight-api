package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.FlightFilialDTO;
import ru.sgp.model.FlightFilial;
import ru.sgp.repository.FlightFilialRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightFilialService {
    @Autowired
    FlightFilialRepository flightFilialRepository;

    public ResponseEntity<FlightFilialDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        Optional<FlightFilial> flightFilialOptional = flightFilialRepository.findById(id);
        if (flightFilialOptional.isPresent()) {
            return new ResponseEntity<>(mapper.map(flightFilialOptional.get(), FlightFilialDTO.class), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<FlightFilialDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        List<FlightFilialDTO> response = new ArrayList<>();
        flightFilialRepository.findAll().forEach(filial -> response.add(mapper.map(filial, FlightFilialDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<FlightFilialDTO> update(FlightFilialDTO flightFilialDTO) {
        ModelMapper mapper = new ModelMapper();
        Optional<FlightFilial> flightFilialOptional = flightFilialRepository.findById(flightFilialDTO.getId());
        FlightFilialDTO response = new FlightFilialDTO();
        if (flightFilialOptional.isPresent()) {
            FlightFilial flightFilial = mapper.map(flightFilialDTO, FlightFilial.class);
            flightFilialRepository.save(flightFilial);
            return new ResponseEntity<>(flightFilialDTO, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.RouteFilialDTO;
import ru.sgp.model.RouteFilial;
import ru.sgp.repository.RouteFilialRepository;

import java.util.Optional;

@Service
public class RouteFilialService {
    @Autowired
    RouteFilialRepository routeFilialRepository;

    public ResponseEntity<RouteFilialDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        Optional<RouteFilial> routeFilialOptional = routeFilialRepository.findById(id);
        if (routeFilialOptional.isPresent()) {
            return new ResponseEntity<>(mapper.map(routeFilialOptional.get(), RouteFilialDTO.class), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
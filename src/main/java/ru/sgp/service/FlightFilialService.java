package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.FlightFilialDTO;
import ru.sgp.model.*;
import ru.sgp.repository.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightFilialService {
    @Autowired
    FlightFilialRepository flightFilialRepository;
    @Autowired
    WorkTypeRepository workTypeRepository;
    @Autowired
    RouteFilialRepository routeFilialRepository;
    @Autowired
    EmployeeResponsibleRepository employeeResponsibleRepository;
    @Autowired
    RequestFilialRepository requestFilialRepository;

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

    @Transactional
    public ResponseEntity<FlightFilialDTO> update(FlightFilialDTO flightFilialDTO) {  // to do Responsible Employee
        ModelMapper mapper = new ModelMapper();
        Optional<FlightFilial> flightFilialOptional = flightFilialRepository.findById(flightFilialDTO.getId());
        FlightFilialDTO response = new FlightFilialDTO();
        if (flightFilialOptional.isPresent()) {
            FlightFilial flightFilial = mapper.map(flightFilialDTO, FlightFilial.class);
            flightFilialRepository.save(flightFilial);
            WorkType workType = workTypeRepository.getById(flightFilialDTO.getIdWorkType());
            RouteFilial routeFilial = routeFilialRepository.getById(flightFilialDTO.getIdRoute());
            routeFilial.setIdWorkType(workType);
            routeFilialRepository.save(routeFilial);
            return new ResponseEntity<>(flightFilialDTO, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<FlightFilialDTO> create(FlightFilialDTO flightFilialDTO) {
        RequestFilial request = requestFilialRepository.getById(flightFilialDTO.getIdRequestFilial());
        List<RouteFilial> routes = routeFilialRepository.findAllByIdRequest(request);
        ModelMapper mapper = new ModelMapper();
        FlightFilial flightFilial = new FlightFilial();
        FlightFilialDTO response = new FlightFilialDTO();
        flightFilial = mapper.map(flightFilialDTO, FlightFilial.class);
        flightFilialRepository.save(flightFilial);
        WorkType workType = workTypeRepository.getById(flightFilialDTO.getIdWorkType());
        EmployeeResponsible employeeResponsible = employeeResponsibleRepository.getById(flightFilialDTO.getIdEmpResp());
        RouteFilial route = routes.stream().filter(routeFilial -> routeFilial.getIdWorkType().equals(workType) && routeFilial.getIdEmpResp().equals(employeeResponsible)).findAny().orElse(null);
        if (route != null){ // if route exist
            flightFilial.setIdRoute(route);
            flightFilialRepository.save(flightFilial);
        }  else {
            RouteFilial routeFilial = new RouteFilial();
            routeFilial.setIdRequest(request);
            routeFilial.setIdWorkType(workType);
            routeFilial.setIdEmpResp(employeeResponsible);
            routeFilialRepository.save(routeFilial);
            flightFilial.setIdRoute(routeFilial);
            flightFilialRepository.save(flightFilial);
        }
        return new ResponseEntity<>(flightFilialDTO, HttpStatus.CREATED);

    }
}
package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.RequestFilialDTO;
import ru.sgp.model.*;
import ru.sgp.repository.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RequestFilialService {
    @Autowired
    RequestFilialRepository requestFilialRepository;
    @Autowired
    RouteFilialRepository routeFilialRepository;
    @Autowired
    FlightFilialRepository flightFilialRepository;
    @Autowired
    AirWorkTypeRepository workTypeRepository;
    @Autowired
    EmployeeResponsibleRepository employeeResponsibleRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Transactional
    public ResponseEntity<RequestFilialDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        TypeMap<RequestFilial, RequestFilialDTO> propertyMapper = mapper.createTypeMap(RequestFilial.class, RequestFilialDTO.class);
        propertyMapper.addMappings(m -> m.map(src -> src.getIdFilial().getId(), RequestFilialDTO::setIdFilial));
        propertyMapper.addMappings(m -> m.map(src -> src.getIdRequestFile().getId(), RequestFilialDTO::setIdRequestFile));
        propertyMapper.addMappings(m -> m.map(src -> src.getIdRequestFile().getName(), RequestFilialDTO::setNameRequestFile));
        propertyMapper.addMappings(m -> m.map(src -> src.getIdState().getId(), RequestFilialDTO::setIdState));
        propertyMapper.addMappings(m -> m.map(RequestFilial::getCreateDate, RequestFilialDTO::setCreateDate));
        propertyMapper.addMappings(m -> m.map(RequestFilial::getRejectNote, RequestFilialDTO::setRejectNote));
        Optional<RequestFilial> requestFilialOptional = requestFilialRepository.findById(id);
        if (requestFilialOptional.isPresent()) {
            RequestFilial request = requestFilialOptional.get();
            RequestFilialDTO response = new RequestFilialDTO();
            List<RouteFilial> routes = routeFilialRepository.findAllByIdRequest(request);
            List<HashMap<String, String>> routesDTO = new ArrayList<>();
            for (RouteFilial route: routes){
                List<FlightFilial> flights = flightFilialRepository.findAllByIdRoute(route);
                for(FlightFilial flight: flights){
                    HashMap<String, String> pair = new HashMap<>();
                    pair.put("workType", workTypeRepository.getById(route.getIdWorkType().getId()).getName());
                    Employee employee = employeeResponsibleRepository.getById(route.getIdEmpResp().getId()).getIdEmployee();
                    pair.put("employee", employee.getSecondname() + ' ' + employee.getFirstName() + ' ' + employee.getLastname());
                    pair.put("dateTime", flight.getFlyDate().toString());
                    pair.put("airportDeparture", flight.getIdAirportDeparture().getName());
                    pair.put("airportArrival", flight.getIdAirportArrival().getName());
                    pair.put("passengerCount", flight.getPassengerCount().toString());
                    pair.put("cargoWeightMount", flight.getCargoWeightMount().toString());
                    pair.put("cargoWeightIn", flight.getCargoWeightIn().toString());
                    pair.put("cargoWeightOut", flight.getCargoWeightOut().toString());
                    pair.put("id", flight.getId().toString());
                    routesDTO.add(pair);
                }
            }
            response = mapper.map(request, RequestFilialDTO.class);
            response.setRoutes(routesDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<List<RequestFilialDTO>> getAllByYear(Integer year) {
        ModelMapper mapper = new ModelMapper();
        TypeMap<RequestFilial, RequestFilialDTO> propertyMapper = mapper.createTypeMap(RequestFilial.class, RequestFilialDTO.class);
        propertyMapper.addMappings(m -> m.map(src -> src.getIdFilial().getId(), RequestFilialDTO::setIdFilial));
        propertyMapper.addMappings(m -> m.map(src -> src.getIdRequestFile().getId(), RequestFilialDTO::setIdRequestFile));
        propertyMapper.addMappings(m -> m.map(src -> src.getIdRequestFile().getName(), RequestFilialDTO::setNameRequestFile));
        propertyMapper.addMappings(m -> m.map(src -> src.getIdState().getId(), RequestFilialDTO::setIdState));
        propertyMapper.addMappings(m -> m.map(RequestFilial::getCreateDate, RequestFilialDTO::setCreateDate));
        propertyMapper.addMappings(m -> m.map(RequestFilial::getRejectNote, RequestFilialDTO::setRejectNote));
        List<RequestFilialDTO> response = new ArrayList<>();
        requestFilialRepository.findAllByYear(year).forEach(filial -> response.add(mapper.map(filial, RequestFilialDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.RequestDTO;
import ru.sgp.dto.RequestFilialDTO;
import ru.sgp.model.*;
import ru.sgp.repository.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RoutePlanRepository routePlanRepository;
    @Autowired
    FlightPlanRepository flightPlanRepository;
    @Autowired
    WorkTypeRepository workTypeRepository;
    @Autowired
    EmployeeResponsibleRepository employeeResponsibleRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RequestStateRepository requestStateRepository;

    @Transactional
    public ResponseEntity<List<RequestDTO>> getAllByYear(Integer year) {
        ModelMapper mapper = new ModelMapper();
        TypeMap<Request, RequestDTO> propertyMapper = mapper.createTypeMap(Request.class, RequestDTO.class);
        propertyMapper.addMappings(m -> m.map(src -> src.getAircraftType().getName(), RequestDTO::setAircraftTypeName));
        propertyMapper.addMappings(m -> m.map(src -> src.getFlightTarget().getName(), RequestDTO::setFlightTargetName));
        propertyMapper.addMappings(m -> m.map(src -> src.getState().getName(), RequestDTO::setStateName));
        propertyMapper.addMappings(m -> m.map(src -> src.getEmpCustomer().getEmployee().getFirstName(), RequestDTO::setEmpCustomerName));
        propertyMapper.addMappings(m -> m.map(src -> src.getContractData().getAircraftModel().getName(), RequestDTO::setAircraftModelName));
        propertyMapper.addMappings(m -> m.map(src -> src.getContractData().getContract().getAirline().getName(), RequestDTO::setAirlineName));
        propertyMapper.addMappings(m -> m.map(src -> src.getContractData().getContract().getDocNum(), RequestDTO::setDocName));
        List<RequestDTO> response = new ArrayList<>();
        requestRepository.findAllByYear(year).forEach(request -> response.add(mapper.map(request, RequestDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<RequestDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        TypeMap<Request, RequestDTO> propertyMapper = mapper.createTypeMap(Request.class, RequestDTO.class);
        propertyMapper.addMappings(m -> m.map(src -> src.getAircraftType().getName(), RequestDTO::setAircraftTypeName));
        propertyMapper.addMappings(m -> m.map(src -> src.getFlightTarget().getName(), RequestDTO::setFlightTargetName));
        propertyMapper.addMappings(m -> m.map(src -> src.getState().getName(), RequestDTO::setStateName));
        propertyMapper.addMappings(m -> m.map(src -> src.getEmpCustomer().getEmployee().getFirstName(), RequestDTO::setEmpCustomerName));
        propertyMapper.addMappings(m -> m.map(src -> src.getEmpCustomer().getEmployee().getSecondname(), RequestDTO::setEmpCustomerSecondName));
        propertyMapper.addMappings(m -> m.map(src -> src.getEmpCustomer().getEmployee().getLastname(), RequestDTO::setEmpCustomerLastName));
        propertyMapper.addMappings(m -> m.map(src -> src.getContractData().getAircraftModel().getName(), RequestDTO::setAircraftModelName));
        propertyMapper.addMappings(m -> m.map(src -> src.getContractData().getContract().getAirline().getName(), RequestDTO::setAirlineName));
        propertyMapper.addMappings(m -> m.map(src -> src.getContractData().getContract().getDocNum(), RequestDTO::setDocName));
        Optional<Request> requestOptional = requestRepository.findById(id);
        if (requestOptional.isPresent()) {
            Request request = requestOptional.get();
            RequestDTO response = new RequestDTO();
            List<RoutePlan> routes = routePlanRepository.findAllByIdRequest(request);
            List<HashMap<String, String>> routesDTO = new ArrayList<>();
            for (RoutePlan route : routes) {
                List<FlightPlan> flights = flightPlanRepository.findAllByIdRouteOrderById(route);
                for (FlightPlan flight : flights) {
                    HashMap<String, String> pair = new HashMap<>();
                    pair.put("workType", workTypeRepository.getById(route.getIdWorkType().getId()).getName());
                    Employee employee = employeeResponsibleRepository.getById(route.getIdEmpResp().getId()).getIdEmployee();
                    pair.put("employee", employee.getLastname() + ' ' + employee.getFirstName() + ' ' + employee.getSecondname());
                    pair.put("dateTime", flight.getFlyDate().toString());
                    pair.put("airportDeparture", flight.getIdAirportDeparture().getName());
                    pair.put("airportArrival", flight.getIdAirportArrival().getName());
                    pair.put("passengerCount", flight.getPassengerCount() == null ? "" : flight.getPassengerCount().toString());
                    pair.put("cargoWeightMount", flight.getCargoWeightMount() == null ? "" : flight.getCargoWeightMount().toString());
                    pair.put("cargoWeightIn", flight.getCargoWeightIn() == null ? "" : flight.getCargoWeightIn().toString());
                    pair.put("cargoWeightOut", flight.getCargoWeightOut() == null ? "" : flight.getCargoWeightOut().toString());
                    pair.put("routeId", flight.getIdRoute().getId().toString());
                    pair.put("id", flight.getId().toString());
                    routesDTO.add(pair);
                }
            }
            response = mapper.map(request, RequestDTO.class);
            response.setRoutes(routesDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
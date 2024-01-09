package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.RequestHDTO;
import ru.sgp.model.Employee;
import ru.sgp.model.FlightPlanHelicopter;
import ru.sgp.model.RequestH;
import ru.sgp.model.RoutePlanHelicopter;
import ru.sgp.repository.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RequestHService {
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
    @Autowired
    AircraftModelRepository aircraftModelRepository;
    @Autowired
    ContractDataRepository contractDataRepository;
    @Autowired
    FlightTargetRepository flightTargetRepository;
    @Autowired
    EmpCustomerRepository empCustomerRepository;
    @Autowired
    RequestHistoryRepository requestHistoryRepository;
    @Autowired
    RequestCostRepository requestCostRepository;
    @Autowired
    FilialRepository filialRepository;
    @Autowired
    RouteFactRepository routeFactRepository;
    @Autowired
    FlightFactRepository flightFactRepository;
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    AircraftTypeRepository aircraftTypeRepository;
    @Autowired
    EmployeeCustomerRepository employeeCustomerRepository;
    @Autowired
    RequestHRepository requestHRepository;
    @Autowired
    RoutePlanHelicopterRepository routePlanHelicopterRepository;
    @Autowired
    FlightPlanHelicopterRepository flightPlanHelicopterRepository;

    @Transactional
    public ResponseEntity<List<RequestHDTO>> getAllByYear(Integer year) {
        ModelMapper mapper = new ModelMapper();
        TypeMap<RequestH, RequestHDTO> propertyMapper = mapper.createTypeMap(RequestH.class, RequestHDTO.class);
        propertyMapper.addMappings(m -> m.map(src -> src.getAirline().getId(), RequestHDTO::setAirlineId));
        propertyMapper.addMappings(m -> m.map(src -> src.getAirline().getName(), RequestHDTO::setAirlineName));
        propertyMapper.addMappings(m -> m.map(src -> src.getWorkType().getName(), RequestHDTO::setWorkTypeName));
        propertyMapper.addMappings(m -> m.map(src -> src.getWorkType().getId(), RequestHDTO::setWorkTypeId));
        propertyMapper.addMappings(m -> m.map(RequestH::getDate, RequestHDTO::setDate));
        propertyMapper.addMappings(m -> m.map(RequestH::getFlyDateStart, RequestHDTO::setFlyDateStart));
        propertyMapper.addMappings(m -> m.map(RequestH::getFlyDateFinish, RequestHDTO::setFlyDateFinish));
        List<RequestHDTO> response = new ArrayList<>();
        requestHRepository.findAllByYear(year).forEach(request -> response.add(mapper.map(request, RequestHDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<RequestHDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        TypeMap<RequestH, RequestHDTO> propertyMapper = mapper.createTypeMap(RequestH.class, RequestHDTO.class);
        propertyMapper.addMappings(m -> m.map(src -> src.getAirline().getId(), RequestHDTO::setAirlineId));
        propertyMapper.addMappings(m -> m.map(src -> src.getAirline().getName(), RequestHDTO::setAirlineName));
        propertyMapper.addMappings(m -> m.map(src -> src.getWorkType().getName(), RequestHDTO::setWorkTypeName));
        propertyMapper.addMappings(m -> m.map(src -> src.getWorkType().getId(), RequestHDTO::setWorkTypeId));
        propertyMapper.addMappings(m -> m.map(RequestH::getDate, RequestHDTO::setDate));
        propertyMapper.addMappings(m -> m.map(RequestH::getFlyDateStart, RequestHDTO::setFlyDateStart));
        propertyMapper.addMappings(m -> m.map(RequestH::getFlyDateFinish, RequestHDTO::setFlyDateFinish));
        RequestH requestH = requestHRepository.getById(id);
        RequestHDTO response = mapper.map(requestH, RequestHDTO.class);
        response.setNameState("Создано");
        List<HashMap<String, String>> routesDTO = new ArrayList<>();
        List<RoutePlanHelicopter> planRoutes = routePlanHelicopterRepository.findAllByIdRequestH(requestH);
        for (RoutePlanHelicopter route : planRoutes) {
            List<FlightPlanHelicopter> flights = flightPlanHelicopterRepository.findAllByIdRouteHelicopterOrderById(route);
            for (FlightPlanHelicopter flight : flights) {
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
                pair.put("routeId", flight.getIdRouteHelicopter().getId().toString());
                pair.put("id", flight.getId().toString());
                routesDTO.add(pair);
            }
        }
        response.setRoutes(routesDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
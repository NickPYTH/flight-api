package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.RequestFilialDTO;
import ru.sgp.dto.RequestStateDTO;
import ru.sgp.model.*;
import ru.sgp.repository.*;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

@Service
public class RequestFilialService {
    @Autowired
    RequestFilialRepository requestFilialRepository;
    @Autowired
    RouteFilialRepository routeFilialRepository;
    @Autowired
    FlightFilialRepository flightFilialRepository;
    @Autowired
    WorkTypeRepository workTypeRepository;
    @Autowired
    EmployeeResponsibleRepository employeeResponsibleRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RequestStateRepository requestStateRepository;
    @Autowired
    FilialRepository filialRepository;
    @Autowired
    AirportRepository airportRepository;

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
            for (RouteFilial route : routes) {
                List<FlightFilial> flights = flightFilialRepository.findAllByIdRouteOrderById(route);
                for (FlightFilial flight : flights) {
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

    @Transactional
    public ResponseEntity<RequestStateDTO> sendOnConfirm(Long flightRequestId) {
        ModelMapper mapper = new ModelMapper();
        Optional<RequestFilial> requestFilialOptional = requestFilialRepository.findById(flightRequestId);
        if (requestFilialOptional.isPresent()) {
            RequestFilial requestFilial = requestFilialOptional.get();
            RequestState state = requestStateRepository.getById(2L); // On confirm id state
            requestFilial.setIdState(state);
            requestFilialRepository.save(requestFilial);
            RequestStateDTO response = mapper.map(state, RequestStateDTO.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<RequestStateDTO> confirm(Long flightRequestId) {
        ModelMapper mapper = new ModelMapper();
        Optional<RequestFilial> requestFilialOptional = requestFilialRepository.findById(flightRequestId);
        if (requestFilialOptional.isPresent()) {
            RequestFilial requestFilial = requestFilialOptional.get();
            RequestState state = requestStateRepository.getById(3L); // Confirm id state
            requestFilial.setIdState(state);
            requestFilialRepository.save(requestFilial);
            RequestStateDTO response = mapper.map(state, RequestStateDTO.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<RequestStateDTO> decline(Long flightRequestId) {
        ModelMapper mapper = new ModelMapper();
        Optional<RequestFilial> requestFilialOptional = requestFilialRepository.findById(flightRequestId);
        if (requestFilialOptional.isPresent()) {
            RequestFilial requestFilial = requestFilialOptional.get();
            RequestState state = requestStateRepository.getById(4L); // Decline id state
            requestFilial.setIdState(state);
            requestFilialRepository.save(requestFilial);
            RequestStateDTO response = mapper.map(state, RequestStateDTO.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<RequestFilialDTO> create(RequestFilialDTO body) throws ParseException {
        Optional<Filial> filialOpt = filialRepository.findById(body.getIdFilial());
        RequestState state = requestStateRepository.getById(1L);
        if (filialOpt.isPresent()) {
            RequestFilial requestFilial = new RequestFilial();
            requestFilial.setIdFilial(filialOpt.get());
            requestFilial.setIdState(state);
            requestFilial.setCreateDate(new Date());
            requestFilial.setYear(Year.now().getValue());
            requestFilialRepository.save(requestFilial);
            for (HashMap<String, String> pair : body.getRoutes()) {
                Airport airportArrival = airportRepository.getById(Long.valueOf(pair.get("airportArrivalId")));
                Airport airportDeparture = airportRepository.getById(Long.valueOf(pair.get("airportDepartureId")));
                EmployeeResponsible employeeResponsible = employeeResponsibleRepository.getById(Long.valueOf(pair.get("employeeId")));
                WorkType workType = workTypeRepository.getById(Long.valueOf(pair.get("workTypeId")));
                Optional<RouteFilial> routeFilialOpt = routeFilialRepository.findByIdRequestAndIdEmpRespAndIdWorkType(requestFilial, employeeResponsible, workType);
                RouteFilial routeFilial = new RouteFilial();
                if (routeFilialOpt.isEmpty()) {
                    routeFilial.setIdRequest(requestFilial);
                    routeFilial.setIdEmpResp(employeeResponsible);
                    routeFilial.setIdWorkType(workType);
                    routeFilialRepository.save(routeFilial);
                } else routeFilial = routeFilialOpt.get();
                FlightFilial flightFilial = new FlightFilial();
                flightFilial.setIdRoute(routeFilial);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //java.sql.Date sqlDate = new java.sql.Date(formatter.parse().getTime());
                flightFilial.setFlyDate(formatter.parse(pair.get("dateTime")));
                flightFilial.setIdAirportArrival(airportArrival);
                flightFilial.setIdAirportDeparture(airportDeparture);
                flightFilial.setPassengerCount(Integer.valueOf(pair.get("passengerCount")));
                flightFilial.setCargoWeightIn(Float.valueOf(pair.get("cargoWeightIn")));
                flightFilial.setCargoWeightOut(Float.valueOf(pair.get("cargoWeightOut")));
                flightFilial.setCargoWeightMount(Float.valueOf(pair.get("cargoWeightMount")));
                flightFilialRepository.save(flightFilial);
            }
            body.setId(requestFilial.getId());
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
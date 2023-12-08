package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.RequestDTO;
import ru.sgp.dto.UpdateRequestDTO;
import ru.sgp.model.*;
import ru.sgp.repository.*;
import ru.sgp.utils.SecurityManager;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Transactional
    public ResponseEntity<List<RequestDTO>> getAllByYear(Integer year) {
        ModelMapper mapper = new ModelMapper();
        TypeMap<Request, RequestDTO> propertyMapper = mapper.createTypeMap(Request.class, RequestDTO.class);
        propertyMapper.addMappings(m -> m.map(src -> src.getContractData().getAircraftModel().getIdType().getName(), RequestDTO::setAircraftTypeName));
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

    @Transactional
    public ResponseEntity<UpdateRequestDTO> update(UpdateRequestDTO updateRequestDTO) throws ParseException {
        Optional<Request> requestOptional = requestRepository.findById(updateRequestDTO.getId());
        if (requestOptional.isPresent()) {
            RequestHistory requestHistory = new RequestHistory();
            Employee employee = employeeRepository.findByLogin(SecurityManager.getCurrentUser());
            requestHistory.setRequest(requestOptional.get());
            Date date = new Date();
            requestHistory.setDate(date);
            requestHistory.setAction("update");
            requestHistory.setEmployee(employee);
            Request request = requestOptional.get();
            if (updateRequestDTO.getField().equals("date")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateStart = formatter.parse(updateRequestDTO.getDateStart());
                Date dateFinish = formatter.parse(updateRequestDTO.getDateFinish());
                if (!request.getFlyDateStart().equals(dateStart)) { // old value and new value is similar start date
                    requestHistory.setField("startDate");
                    requestHistory.setOldValue(request.getFlyDateStart().toString());
                    requestHistory.setNewValue(updateRequestDTO.getDateStart());
                    requestHistoryRepository.save(requestHistory);
                }
                if (!request.getFlyDateStart().equals(dateStart)) { // old value and new value is similar finish date
                    requestHistory.setField("finishDate");
                    requestHistory.setOldValue(request.getFlyDateFinish().toString());
                    requestHistory.setNewValue(updateRequestDTO.getDateFinish());
                    requestHistoryRepository.save(requestHistory);
                }
                request.setFlyDateStart(dateStart);
                request.setFlyDateFinish(dateFinish);
                requestRepository.save(request);
            } else if (updateRequestDTO.getField().equals("aircraft")) {
                ContractData contractData = contractDataRepository.getById(updateRequestDTO.getIdContractData());
                requestHistory.setOldValue(request.getContractData().getAircraftModel().getName());
                request.setContractData(contractData);
                requestRepository.save(request);
                requestHistory.setField("aircraft");
                requestHistory.setNewValue(contractData.getAircraftModel().getName());
                requestHistoryRepository.save(requestHistory);
            } else if (updateRequestDTO.getField().equals("target")) {
                FlightTarget target = flightTargetRepository.getById(updateRequestDTO.getIdTarget());
                requestHistory.setOldValue(request.getFlightTarget().getName());
                request.setFlightTarget(target);
                requestRepository.save(request);
                requestHistory.setField("target");
                requestHistory.setNewValue(target.getName());
                requestHistoryRepository.save(requestHistory);
            } else if (updateRequestDTO.getField().equals("empCustomer")) {
                EmpCustomer empCustomer = empCustomerRepository.getById(updateRequestDTO.getIdEmpCustomer());
                requestHistory.setOldValue(request.getEmpCustomer().getEmployee().getLastname() + " " + request.getEmpCustomer().getEmployee().getFirstName() + " " + request.getEmpCustomer().getEmployee().getSecondname());
                request.setEmpCustomer(empCustomer);
                requestRepository.save(request);
                requestHistory.setField("target");
                requestHistory.setNewValue(empCustomer.getEmployee().getLastname() + " " + empCustomer.getEmployee().getFirstName() + " " + empCustomer.getEmployee().getSecondname());
                requestHistoryRepository.save(requestHistory);
            }
            return new ResponseEntity<>(updateRequestDTO, HttpStatus.OK);
        } else
            return new ResponseEntity<>(updateRequestDTO, HttpStatus.NOT_FOUND);
    }
}
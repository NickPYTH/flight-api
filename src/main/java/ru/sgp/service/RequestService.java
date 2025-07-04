package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.CostDTO;
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
        propertyMapper.addMappings(m -> m.map(Request::getDuration, RequestDTO::setDuration));
        propertyMapper.addMappings(m -> m.map(Request::getDurationOut, RequestDTO::setDurationOut));
        propertyMapper.addMappings(m -> m.map(Request::getCost, RequestDTO::setCost));
        propertyMapper.addMappings(m -> m.map(Request::getCostOut, RequestDTO::setCostOut));
        Optional<Request> requestOptional = requestRepository.findById(id);
        if (requestOptional.isPresent()) {
            Request request = requestOptional.get();
            RequestDTO response = new RequestDTO();
            List<RoutePlan> planRoutes = routePlanRepository.findAllByIdRequest(request);
            List<RouteFact> factRoutes = routeFactRepository.findAllByIdRequest(request);
            List<HashMap<String, String>> costsDTO = new ArrayList<>();
            List<HashMap<String, String>> routesDTO = new ArrayList<>();
            List<HashMap<String, String>> planRoutesDTO = new ArrayList<>();
            for (RoutePlan route : planRoutes) {
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
                List<RequestCost> costs = requestCostRepository.findAllByRequest(request);
                for (RequestCost cost : costs) {
                    HashMap<String, String> pair = new HashMap<>();
                    pair.put("id", cost.getId() != null ? cost.getId().toString() : "");
                    pair.put("filial", cost.getFilial() != null ? cost.getFilial().getShortName() : "");
                    pair.put("workType", cost.getWorkType() != null ? cost.getWorkType().getName() : "");
                    pair.put("duration", cost.getDuration() != null ? cost.getDuration() : "");
                    costsDTO.add(pair);
                }
            }
            for (RouteFact route : factRoutes) {
                List<FlightFact> flights = flightFactRepository.findAllByIdRouteOrderById(route);
                for (FlightFact flight : flights) {
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
                    planRoutesDTO.add(pair);
                }
                List<RequestCost> costs = requestCostRepository.findAllByRequest(request);
                for (RequestCost cost : costs) {
                    HashMap<String, String> pair = new HashMap<>();
                    pair.put("id", cost.getId() != null ? cost.getId().toString() : "");
                    pair.put("filial", cost.getFilial() != null ? cost.getFilial().getShortName() : "");
                    pair.put("workType", cost.getWorkType() != null ? cost.getWorkType().getName() : "");
                    pair.put("duration", cost.getDuration() != null ? cost.getDuration() : "");
                    costsDTO.add(pair);
                }
            }
            response = mapper.map(request, RequestDTO.class);
            response.setRoutes(routesDTO);
            response.setFactRoutes(planRoutesDTO);
            response.setCosts(costsDTO);
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
            } else if (updateRequestDTO.getField().equals("flightDuration")) {
                requestHistory.setOldValue(request.getDuration() == null ? "0" : String.valueOf(request.getDuration()));
                request.setDuration(Double.valueOf(updateRequestDTO.getValue()));
                requestRepository.save(request);
                requestHistory.setField("flightDuration");
                requestHistory.setNewValue(updateRequestDTO.getValue());
                requestHistoryRepository.save(requestHistory);
            } else if (updateRequestDTO.getField().equals("flightDurationOut")) {
                requestHistory.setOldValue(request.getDurationOut() == null ? "0" : String.valueOf(request.getDurationOut()));
                request.setDurationOut(Double.valueOf(updateRequestDTO.getValue()));
                requestRepository.save(request);
                requestHistory.setField("flightDuration");
                requestHistory.setNewValue(updateRequestDTO.getValue());
                requestHistoryRepository.save(requestHistory);
            } else if (updateRequestDTO.getField().equals("cost")) {
                requestHistory.setOldValue(request.getCost() == null ? "0" : String.valueOf(request.getCost()));
                request.setCost(Double.valueOf(updateRequestDTO.getValue()));
                requestRepository.save(request);
                requestHistory.setField("cost");
                requestHistory.setNewValue(updateRequestDTO.getValue());
                requestHistoryRepository.save(requestHistory);
            } else if (updateRequestDTO.getField().equals("costOut")) {
                requestHistory.setOldValue(request.getCostOut() == null ? "0" : String.valueOf(request.getCostOut()));
                request.setCostOut(Double.valueOf(updateRequestDTO.getValue()));
                requestRepository.save(request);
                requestHistory.setField("costOut");
                requestHistory.setNewValue(updateRequestDTO.getValue());
                requestHistoryRepository.save(requestHistory);
            } else if (updateRequestDTO.getField().equals("roundDigit")) {
                requestHistory.setOldValue(request.getRoundDigit() == null ? "0" : String.valueOf(request.getRoundDigit()));
                request.setRoundDigit(Integer.valueOf(updateRequestDTO.getValue()));
                requestRepository.save(request);
                requestHistory.setField("roundDigit");
                requestHistory.setNewValue(updateRequestDTO.getValue());
                requestHistoryRepository.save(requestHistory);
            }
            return new ResponseEntity<>(updateRequestDTO, HttpStatus.OK);
        } else
            return new ResponseEntity<>(updateRequestDTO, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<CostDTO> createCost(CostDTO costDTO) {
        Optional<Request> requestOpt = requestRepository.findById(costDTO.getRequestId());
        Optional<Filial> filialOpt = filialRepository.findById(costDTO.getFilialId());
        Optional<WorkType> workTypeOpt = workTypeRepository.findById(costDTO.getWorkTypeId());
        if (requestOpt.isPresent() && filialOpt.isPresent() && workTypeOpt.isPresent()) {
            RequestCost requestCost = new RequestCost();
            requestCost.setRequest(requestOpt.get());
            requestCost.setFilial(filialOpt.get());
            requestCost.setWorkType(workTypeOpt.get());
            requestCost.setDuration(costDTO.getDuration().toString());
            requestCost.setDurationOut(costDTO.getCost().toString());
            requestCostRepository.save(requestCost);
            return new ResponseEntity<>(costDTO, HttpStatus.CREATED);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<CostDTO> updateCost(CostDTO costDTO) {
        Optional<Request> requestOpt = requestRepository.findById(costDTO.getRequestId());
        Optional<Filial> filialOpt = filialRepository.findById(costDTO.getFilialId());
        Optional<WorkType> workTypeOpt = workTypeRepository.findById(costDTO.getWorkTypeId());
        Optional<RequestCost> requestCostOpt = requestCostRepository.findById(costDTO.getCostId());
        if (requestOpt.isPresent() && filialOpt.isPresent() && workTypeOpt.isPresent() && requestCostOpt.isPresent()) {
            RequestCost requestCost = requestCostOpt.get();
            requestCost.setRequest(requestOpt.get());
            requestCost.setFilial(filialOpt.get());
            requestCost.setWorkType(workTypeOpt.get());
            requestCost.setDuration(costDTO.getDuration().toString());
            requestCost.setDurationOut(costDTO.getCost().toString());
            requestCostRepository.save(requestCost);
            return new ResponseEntity<>(costDTO, HttpStatus.CREATED);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<List<CostDTO>> getCostsByRequestId(Long id) {
        List<CostDTO> response = new ArrayList<>();
        Optional<Request> requestOpt = requestRepository.findById(id);
        if (requestOpt.isPresent()) {
            for (RequestCost requestCost : requestCostRepository.findAllByRequest(requestOpt.get())) {
                CostDTO costDTO = new CostDTO();
                costDTO.setCostId(requestCost.getId());
                costDTO.setRequestId(requestCost.getRequest().getId());
                costDTO.setFilialName(requestCost.getFilial().getName());
                costDTO.setFilialId(requestCost.getFilial().getId());
                costDTO.setWorkTypeName(requestCost.getWorkType().getName());
                costDTO.setWorkTypeId(requestCost.getWorkType().getId());
                costDTO.setDuration(Double.valueOf(requestCost.getDuration()));
                costDTO.setCost(Double.valueOf(requestCost.getDurationOut()));
                response.add(costDTO);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<RequestDTO> create(RequestDTO body) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        RequestState state = requestStateRepository.getById(1L);
        Request request = new Request();
        java.sql.Date startDate = new java.sql.Date(formatter.parse(body.getFlyDateStart()).getTime());
        java.sql.Date finishDate = new java.sql.Date(formatter.parse(body.getFlyDateStart()).getTime());
        AircraftModel aircraftModel = aircraftModelRepository.getById(body.getAircraftModelId());
        ContractData contractData = contractDataRepository.getById(body.getContractDataId());
        FlightTarget flightTarget = flightTargetRepository.getById(body.getFlightTargetId());
        EmpCustomer employeeCustomer = employeeCustomerRepository.getById(body.getEmpCustomerId());
        request.setYear(2023);
        request.setFlyDateStart(startDate);
        request.setFlyDateFinish(finishDate);
        request.setAircraftType(aircraftModel.getIdType());
        request.setContractData(contractData);
        request.setFlightTarget(flightTarget);
        request.setEmpCustomer(employeeCustomer);
        request.setState(state);
        requestRepository.save(request);
        for (HashMap<String, String> pair : body.getRoutes()) {
            Airport airportArrival = airportRepository.getById(Long.valueOf(pair.get("airportArrivalId")));
            Airport airportDeparture = airportRepository.getById(Long.valueOf(pair.get("airportDepartureId")));
            EmployeeResponsible employeeResponsible = employeeResponsibleRepository.getById(Long.valueOf(pair.get("employeeId")));
            WorkType workType = workTypeRepository.getById(Long.valueOf(pair.get("workTypeId")));
            Optional<RoutePlan> routePlanOpt = routePlanRepository.findByIdRequestAndIdEmpRespAndIdWorkType(request, employeeResponsible, workType);
            RoutePlan routePlan = new RoutePlan();
            if (routePlanOpt.isEmpty()) {
                routePlan.setIdRequest(request);
                routePlan.setIdEmpResp(employeeResponsible);
                routePlan.setIdWorkType(workType);
                routePlanRepository.save(routePlan);
            } else routePlan = routePlanOpt.get();
            FlightPlan flightPlan = new FlightPlan();
            flightPlan.setIdRoute(routePlan);
            java.sql.Date sqlDate = new java.sql.Date(formatter.parse(pair.get("dateTime")).getTime());
            flightPlan.setFlyDate(sqlDate);
            flightPlan.setIdAirportArrival(airportArrival);
            flightPlan.setIdAirportDeparture(airportDeparture);
            flightPlan.setPassengerCount(Integer.valueOf(pair.get("passengerCount")));
            flightPlan.setCargoWeightIn(Float.valueOf(pair.get("cargoWeightIn")));
            flightPlan.setCargoWeightOut(Float.valueOf(pair.get("cargoWeightOut")));
            flightPlan.setCargoWeightMount(Float.valueOf(pair.get("cargoWeightMount")));
            flightPlanRepository.save(flightPlan);
        }
        body.setId(request.getId());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.FlightPlanDTO;
import ru.sgp.model.*;
import ru.sgp.repository.*;
import ru.sgp.utils.SecurityManager;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FlightPlanService {
    @Autowired
    FlightPlanRepository flightPlanRepository;
    @Autowired
    WorkTypeRepository workTypeRepository;
    @Autowired
    RoutePlanRepository routePlanRepository;
    @Autowired
    EmployeeResponsibleRepository employeeResponsibleRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RequestHistoryRepository requestHistoryRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public ResponseEntity<FlightPlanDTO> get(Long id) {
        ModelMapper mapper = new ModelMapper();
        Optional<FlightPlan> flightPlanOptional = flightPlanRepository.findById(id);
        if (flightPlanOptional.isPresent()) {
            return new ResponseEntity<>(mapper.map(flightPlanOptional.get(), FlightPlanDTO.class), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<FlightPlanDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        List<FlightPlanDTO> response = new ArrayList<>();
        flightPlanRepository.findAll().forEach(f -> response.add(mapper.map(f, FlightPlanDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<FlightPlanDTO> update(FlightPlanDTO flightPlanDTO) {  // to do Responsible Employee
        ModelMapper mapper = new ModelMapper();
        Optional<FlightPlan> flightPlanOptional = flightPlanRepository.findById(flightPlanDTO.getId());
        FlightPlanDTO response = new FlightPlanDTO();
        if (flightPlanOptional.isPresent()) {
            FlightPlan flightPlan = mapper.map(flightPlanDTO, FlightPlan.class);
            flightPlanRepository.save(flightPlan);
            WorkType workType = workTypeRepository.getById(flightPlanDTO.getIdWorkType());
            RoutePlan routePlan = routePlanRepository.getById(flightPlanDTO.getIdRoute());
            routePlan.setIdWorkType(workType);
            routePlanRepository.save(routePlan);
            //Logging
            Employee employee = employeeRepository.findByLogin(SecurityManager.getCurrentUser());
            RequestHistory requestHistory = new RequestHistory();
            requestHistory.setRequest(flightPlan.getIdRoute().getIdRequest());
            Date date = new Date();
            requestHistory.setDate(date);
            requestHistory.setAction("update");
            requestHistory.setEmployee(employee);
            requestHistory.setField("flightPlan");
            requestHistory.setOldValue("some changes");
            requestHistory.setNewValue("some new values");
            requestHistoryRepository.save(requestHistory);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<FlightPlanDTO> create(FlightPlanDTO flightPlanDTO) throws ParseException {
        Request request = requestRepository.getById(flightPlanDTO.getIdRequest());
        List<RoutePlan> routes = routePlanRepository.findAllByIdRequest(request);
        ModelMapper mapper = new ModelMapper();
        FlightPlan flightPlan = new FlightPlan();
        FlightPlanDTO response = new FlightPlanDTO();
        flightPlan = mapper.map(flightPlanDTO, FlightPlan.class);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        flightPlan.setFlyDate(formatter.parse(flightPlanDTO.getFlyDate()));
        flightPlanRepository.save(flightPlan);
        WorkType workType = workTypeRepository.getById(flightPlanDTO.getIdWorkType());
        EmployeeResponsible employeeResponsible = employeeResponsibleRepository.getById(flightPlanDTO.getIdEmpResp());
        RoutePlan route = routes.stream().filter(routePlan -> routePlan.getIdWorkType().equals(workType) && routePlan.getIdEmpResp().equals(employeeResponsible)).findAny().orElse(null);
        if (route != null) { // if route exist
            flightPlan.setIdRoute(route);
            flightPlanRepository.save(flightPlan);
        } else {
            RoutePlan routePlan = new RoutePlan();
            routePlan.setIdRequest(request);
            routePlan.setIdWorkType(workType);
            routePlan.setIdEmpResp(employeeResponsible);
            routePlanRepository.save(routePlan);
            flightPlan.setIdRoute(routePlan);
            flightPlanRepository.save(flightPlan);
        }
        return new ResponseEntity<>(flightPlanDTO, HttpStatus.CREATED);

    }

    public ResponseEntity<Long> delete(Long id) {
        Optional<FlightPlan> flightPlanOptional = flightPlanRepository.findById(id);
        if (flightPlanOptional.isPresent()) {
            flightPlanRepository.delete(flightPlanOptional.get());
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
    }
}
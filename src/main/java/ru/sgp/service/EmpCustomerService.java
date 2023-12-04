package ru.sgp.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.EmpCustomerDTO;
import ru.sgp.model.EmpCustomer;
import ru.sgp.repository.EmpCustomerRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmpCustomerService {
    @Autowired
    EmpCustomerRepository empCustomerRepository;

    @Transactional
    public ResponseEntity<List<EmpCustomerDTO>> getAll() {
        ModelMapper mapper = new ModelMapper();
        TypeMap<EmpCustomer, EmpCustomerDTO> propertyMapper = mapper.createTypeMap(EmpCustomer.class, EmpCustomerDTO.class);
        propertyMapper.addMappings(m -> m.map(src -> src.getEmployee().getFirstName(), EmpCustomerDTO::setEmpName));
        propertyMapper.addMappings(m -> m.map(src -> src.getEmployee().getLastname(), EmpCustomerDTO::setEmpLastName));
        propertyMapper.addMappings(m -> m.map(src -> src.getEmployee().getSecondname(), EmpCustomerDTO::setEmpSecondName));
        propertyMapper.addMappings(m -> m.map(src -> src.getEmployee().getId(), EmpCustomerDTO::setEmpId));
        List<EmpCustomerDTO> response = new ArrayList<>();
        empCustomerRepository.findAll().forEach(empCustomer -> response.add(mapper.map(empCustomer, EmpCustomerDTO.class)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
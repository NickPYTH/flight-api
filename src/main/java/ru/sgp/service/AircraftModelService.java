package ru.sgp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.AircraftModelDTO;
import ru.sgp.model.ContractData;
import ru.sgp.repository.AircraftModelRepository;
import ru.sgp.repository.ContractDataRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AircraftModelService {
    @Autowired
    AircraftModelRepository aircraftModelRepository;
    @Autowired
    ContractDataRepository contractDataRepository;

    @Transactional
    public ResponseEntity<List<AircraftModelDTO>> getAll() {
        List<AircraftModelDTO> response = new ArrayList<>();
        for (ContractData data : contractDataRepository.findAll()) {
            AircraftModelDTO tmp = new AircraftModelDTO();
            tmp.setAircraftModelId(data.getAircraftModel().getId());
            tmp.setAircraftModelName(data.getAircraftModel().getName());
            tmp.setContractName(data.getContract().getDocNum());
            tmp.setAirlineName(data.getContract().getAirline().getName());
            tmp.setIdContractData(data.getId());
            response.add(tmp);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
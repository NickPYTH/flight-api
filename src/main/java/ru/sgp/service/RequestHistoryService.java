package ru.sgp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.RequestHistoryDTO;
import ru.sgp.model.Request;
import ru.sgp.model.RequestHistory;
import ru.sgp.repository.RequestHistoryRepository;
import ru.sgp.repository.RequestRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestHistoryService {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RequestHistoryRepository requestHistoryRepository;

    @Transactional
    public ResponseEntity<List<RequestHistoryDTO>> getHistory(Long id) {
        Optional<Request> requestOptional = requestRepository.findById(id);
        if (requestOptional.isPresent()) {
            List<RequestHistoryDTO> response = new ArrayList<>();
            for (RequestHistory requestHistory : requestHistoryRepository.findAllByRequestOrderByDateDesc(requestOptional.get())) {
                RequestHistoryDTO record = new RequestHistoryDTO();
                record.setId(requestHistory.getId());
                String FIO = requestHistory.getEmployee().getLastname() + " " + requestHistory.getEmployee().getFirstName() + " " + requestHistory.getEmployee().getSecondname();
                record.setEmployee(FIO);
                record.setDate(requestHistory.getDate().toString());
                record.setField(requestHistory.getField());
                record.setNewValue(requestHistory.getNewValue());
                record.setOldValue(requestHistory.getOldValue());
                record.setAction(requestHistory.getAction());
                response.add(record);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
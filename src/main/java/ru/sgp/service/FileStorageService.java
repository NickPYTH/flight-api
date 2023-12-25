package ru.sgp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.FileStorageDTO;
import ru.sgp.model.*;
import ru.sgp.repository.*;
import ru.sgp.utils.SecurityManager;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class FileStorageService {
    @Autowired
    FileStorageRepository fileStorageRepository;

    @Autowired
    RequestFilialRepository requestFilialRepository;

    @Autowired
    RequestStateRepository requestStateRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RequestHistoryRepository requestHistoryRepository;

    public ResponseEntity<InputStreamResource> get(Long id) {
        Optional<FileStorage> fileStorageOptional = fileStorageRepository.findById(id);
        if (fileStorageOptional.isPresent()) {
            InputStream targetStream = new ByteArrayInputStream(fileStorageOptional.get().getFileBody());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Description", "File Transfer");
            headers.add("Content-Disposition", "attachment; filename=File.xlsx");
            headers.add("Content-Transfer-Encoding", "binary");
            headers.add("Connection", "Keep-Alive");
            headers.setContentType(
                    MediaType.parseMediaType("application/excel"));
            return ResponseEntity.ok().headers(headers).body(new InputStreamResource(targetStream));
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<FileStorage> create(FileStorageDTO fileStorageDTO) throws IOException {
        RequestFilial requestFilial = requestFilialRepository.getById(fileStorageDTO.getIdRequestFilial());
        if (requestFilial.getIdRequestFile() != null) {
            FileStorage oldFile = fileStorageRepository.getById(requestFilial.getIdRequestFile().getId());
            fileStorageRepository.delete(oldFile);
        }
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(fileStorageDTO.getFileName());
        fileStorage.setFileBody(fileStorageDTO.getFileBody().getBytes());
        requestFilialRepository.save(requestFilial);
        fileStorage.setIdRequestFilial(requestFilial.getIdRequestFile().getIdRequestFilial());
        fileStorageRepository.save(fileStorage);
        requestFilial.setIdRequestFile(fileStorage);
        requestFilialRepository.save(requestFilial);
        return new ResponseEntity<FileStorage>(fileStorage, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<Long> addToRequest(FileStorageDTO fileStorageDTO) throws IOException {
        Request request = requestRepository.getById(fileStorageDTO.getIdRequest());
        Employee employee = employeeRepository.findByLogin(SecurityManager.getCurrentUser());
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(fileStorageDTO.getFileName());
        fileStorage.setFileBody(fileStorageDTO.getFileBody().getBytes());
        fileStorage.setIdRequest(request);
        fileStorageRepository.save(fileStorage);
        RequestHistory requestHistory = new RequestHistory();
        requestHistory.setRequest(request);
        Date date = new Date();
        requestHistory.setDate(date);
        requestHistory.setAction("createFile");
        requestHistory.setEmployee(employee);
        requestHistory.setField("file");
        requestHistory.setNewValue(fileStorageDTO.getFileName());
        requestHistoryRepository.save(requestHistory);
        return new ResponseEntity<>(fileStorage.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity<FileStorage> delete(Long id) {
        Optional<FileStorage> fileStorageOptional = fileStorageRepository.findById(id);
        if (fileStorageOptional.isPresent()) {
            fileStorageRepository.delete(fileStorageOptional.get());
            if (fileStorageOptional.get().getIdRequest() != null) {
                Optional<Request> requestOpt = requestRepository.findById(fileStorageOptional.get().getIdRequest().getId());
                if (requestOpt.isPresent()) {
                    Employee employee = employeeRepository.findByLogin(SecurityManager.getCurrentUser());
                    Request request = requestOpt.get();
                    RequestHistory requestHistory = new RequestHistory();
                    requestHistory.setRequest(request);
                    Date date = new Date();
                    requestHistory.setDate(date);
                    requestHistory.setAction("deleteFile");
                    requestHistory.setEmployee(employee);
                    requestHistory.setField("file");
                    requestHistory.setNewValue(fileStorageOptional.get().getName());
                    requestHistoryRepository.save(requestHistory);
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<HashMap<String, String>>> getFilesByRequest(Long requestId) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);
        if (requestOptional.isPresent()) {
            List<HashMap<String, String>> response = new ArrayList<>();
            for (FileStorage file : fileStorageRepository.findAllByIdRequest(requestOptional.get())) {
                HashMap<String, String> pair = new HashMap<>();
                pair.put("name", file.getName());
                pair.put("id", file.getId().toString());
                pair.put("url", "http://localhost:8080/flight/api/file/get?id=" + file.getId().toString());
                response.add(pair);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
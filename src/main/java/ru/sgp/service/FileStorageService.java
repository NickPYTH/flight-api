package ru.sgp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sgp.dto.FileStorageDTO;
import ru.sgp.model.FileStorage;
import ru.sgp.model.RequestFilial;
import ru.sgp.repository.FileStorageRepository;
import ru.sgp.repository.RequestFilialRepository;
import ru.sgp.repository.RequestStateRepository;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class FileStorageService {
    @Autowired
    FileStorageRepository fileStorageRepository;

    @Autowired
    RequestFilialRepository requestFilialRepository;

    @Autowired
    RequestStateRepository requestStateRepository;

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
        fileStorageRepository.save(fileStorage);
        requestFilial.setIdRequestFile(fileStorage);
        requestFilialRepository.save(requestFilial);
        return new ResponseEntity<FileStorage>(fileStorage, HttpStatus.CREATED);
    }

    public ResponseEntity<FileStorage> delete(Long id) {
        Optional<FileStorage> fileStorageOptional = fileStorageRepository.findById(id);
        if (fileStorageOptional.isPresent()) {
            fileStorageRepository.delete(fileStorageOptional.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
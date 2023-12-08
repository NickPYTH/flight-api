package ru.sgp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sgp.dto.FileStorageDTO;
import ru.sgp.model.FileStorage;
import ru.sgp.service.FileStorageService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/file")
public class FileStorageController {
    @Autowired
    FileStorageService fileStorageService;

    @GetMapping(path = "/get")
    public ResponseEntity<InputStreamResource> get(@RequestParam Long id) {
        return fileStorageService.get(id);
    }

    @GetMapping(path = "/getFilesByRequest")
    public ResponseEntity<List<HashMap<String, String>>> getFilesByRequest(@RequestParam Long id) {
        return fileStorageService.getFilesByRequest(id);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<FileStorage> create(@ModelAttribute FileStorageDTO fileStorageDTO) throws IOException {
        return fileStorageService.create(fileStorageDTO);
    }

    @PostMapping(path = "/addToRequest")
    public ResponseEntity<Long> addToRequest(@ModelAttribute FileStorageDTO fileStorageDTO) throws IOException {
        return fileStorageService.addToRequest(fileStorageDTO);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<FileStorage> delete(@RequestParam Long id) {
        return fileStorageService.delete(id);
    }

}

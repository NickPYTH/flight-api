package ru.sgp.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileStorageDTO {
    private Long id;
    private String fileName;
    private MultipartFile fileBody;

    private Long idRequestFilial;
}

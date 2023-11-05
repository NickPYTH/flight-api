package ru.sgp.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class RequestFilialDTO {

    private Long id;
    private Long idFilial;
    private Long idRequestFile;
    private String nameRequestFile;
    private Long idState;
    private String createDate;
    private String rejectNote;
    private String nameState;
    private String nameFilial;
    private List<HashMap<String, String>> routes;

}

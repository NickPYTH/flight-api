package ru.sgp.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class RequestHDTO {
    private Long id;
    private String airlineName;
    private Long airlineId;
    private String workTypeName;
    private Long workTypeId;
    private Integer year;
    private String date;
    private String flyDateStart;
    private String flyDateFinish;
    private String nameState;
    private Long idState;

    private List<HashMap<String, String>> routes;
}

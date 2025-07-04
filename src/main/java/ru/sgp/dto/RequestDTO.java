package ru.sgp.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class RequestDTO {
    private Long id;
    private Integer year;
    private Integer docNum;
    private String docDate;
    private String flyDateStart;
    private String flyDateFinish;
    private String aircraftTypeName;
    private Long aircraftTypeId;
    private String flightTargetName;
    private Long flightTargetId;
    private String stateName;
    private Double duration;
    private Double durationOut;
    private Integer roundDigit;
    private Double cost;
    private Double costOut;
    private String empCustomerName;
    private String empCustomerSecondName;
    private String empCustomerLastName;
    private Long empCustomerId;
    private Integer docCode;
    private String rejectNote;
    private String note;
    private String aircraftModelName;
    private Long aircraftModelId;
    private Long contractDataId;
    private String airlineName;
    private String docName;
    private List<HashMap<String, String>> routes;
    private List<HashMap<String, String>> factRoutes;
    private List<HashMap<String, String>> costs;
}

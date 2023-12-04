package ru.sgp.dto;

import lombok.Data;
import ru.sgp.model.EmpCustomer;

import java.util.Date;
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
    private String flightTargetName;
    private String stateName;
    private Double duration;
    private Double durationOut;
    private Integer roundDigit;
    private Double cost;
    private Double costOut;
    private String empCustomerName;
    private String empCustomerSecondName;
    private String empCustomerLastName;
    private Integer docCode;
    private String rejectNote;
    private String note;
    private String aircraftModelName;
    private String airlineName;
    private String docName;
    private List<HashMap<String, String>> routes;
}

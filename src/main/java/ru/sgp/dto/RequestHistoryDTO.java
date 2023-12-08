package ru.sgp.dto;

import lombok.Data;

@Data
public class RequestHistoryDTO {

    private Long id;
    private String employee;
    private String date;
    private String field;
    private String newValue;
    private String oldValue;
    private String action;

}

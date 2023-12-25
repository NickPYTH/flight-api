package ru.sgp.dto;

import lombok.Data;

@Data
public class AirlineDTO {
    private Long id;
    private String name;
    private String address;
    private String certificate;
    private String contractorCode;
    private String shortName;
}

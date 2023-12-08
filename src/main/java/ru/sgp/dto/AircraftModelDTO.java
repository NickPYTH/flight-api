package ru.sgp.dto;

import lombok.Data;

@Data
public class AircraftModelDTO {
    private Long aircraftModelId;
    private String aircraftModelName;
    private String contractName;
    private String airlineName;
    private String idType;
    private Long idContractData;
}

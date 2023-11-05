package ru.sgp.dto;

import lombok.Data;

@Data
public class AirportDTO {
    private Long id;
    private String name;
    private Long idAircraftType;
}

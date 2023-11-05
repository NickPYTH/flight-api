package ru.sgp.dto;

import lombok.Data;

@Data
public class FlightFilialDTO {
    private Long id;
    private Long idRoute;
    private String flyDate;
    private Long idAirportArrival;
    private Long idAirportDeparture;
    private Integer passengerCount;
    private Float cargoWeightIn;
    private Float cargoWeightOut;
    private Float cargoWeightMount;
}

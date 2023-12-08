package ru.sgp.dto;

import lombok.Data;

@Data
public class FlightPlanDTO {
    private Long idRequest;
    private Long idFlightFilial;
    private Long idFuelPoint;
    private Long id;
    private Long idRoute;
    private Long idWorkType;
    private String FIO;
    private Long idEmpResp;
    private String flyDate;
    private Long idAirportArrival;
    private Long idAirportDeparture;
    private Integer passengerCount;
    private Float cargoWeightIn;
    private Float cargoWeightOut;
    private Float cargoWeightMount;
}

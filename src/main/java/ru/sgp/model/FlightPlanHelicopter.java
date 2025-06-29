package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "fly_flight_plan_helicopter", schema = "public")
public class FlightPlanHelicopter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_route")
    private RoutePlanHelicopter idRouteHelicopter;

    @Column(name = "fly_date")
    private Date flyDate;

    @OneToOne
    @JoinColumn(name = "id_airport_arrival")
    private Airport idAirportArrival;

    @OneToOne
    @JoinColumn(name = "id_airport_departure")
    private Airport idAirportDeparture;

    @Column(name = "passenger_count")
    private Integer passengerCount;

    @Column(name = "cargo_weight_in")
    private Float cargoWeightIn;

    @Column(name = "cargo_weight_out")
    private Float cargoWeightOut;

    @Column(name = "cargo_weight_mount")
    private Float cargoWeightMount;
}

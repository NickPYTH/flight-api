package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "fly_flight_fact", schema = "public")
public class FlightFact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_route")
    private RouteFilial idRoute;

    @Column(name = "fly_date")
    private Date flyDate;

    @OneToOne
    @JoinColumn(name = "id_airport_arrival")
    private Airport idAirportArrival;

    @OneToOne
    @JoinColumn(name = "id_airport_departure")
    private Airport idAirportDeparture;

    @OneToOne
    @JoinColumn(name = "id_flight_plan")
    private FlightPlan idFlightPlan;

    @OneToOne
    @JoinColumn(name = "id_fuel_point")
    private FuelPoint idFuelPoint;

    @Column(name = "passenger_count")
    private Integer passengerCount;

    @Column(name = "cargo_weight_in")
    private Float cargoWeightIn;

    @Column(name = "cargo_weight_out")
    private Float cargoWeightOut;

    @Column(name = "cargo_weight_mount")
    private Float cargoWeightMount;

    @Column(name = "fly_duration")
    private Float flyDuration;

    @Column(name = "is_cancel")
    private Boolean isCancel;

    @Column(name = "reason_cancel")
    private Float reasonCancel;

}

package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "fly_flight_plan", schema = "public")
public class FlightPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_route")
    private RoutePlan idRoute;

    @Column(name = "fly_date")
    private Date flyDate;

    @OneToOne
    @JoinColumn(name = "id_airport_arrival")
    private Airport idAirportArrival;

    @OneToOne
    @JoinColumn(name = "id_airport_departure")
    private Airport idAirportDeparture;

    @OneToOne
    @JoinColumn(name = "id_flight_filial")
    private FlightFilial idFlightFilial;

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
}

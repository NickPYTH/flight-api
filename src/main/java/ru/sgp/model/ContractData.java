package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "doc_contract_data", schema = "public")
public class ContractData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contract")
    private Contract contract;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aircraft_model")
    private AircraftModel aircraftModel;

    @Column(name = "price_hour")
    private Integer priceHour;

    @Column(name = "price")
    private Integer price;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "vat")
    private Integer vat;

    @Column(name = "count_hour")
    private Integer countHour;

    @Column(name = "note")
    private String note;

}

package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "doc_contract", schema = "public")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doc_num")
    private String docNum;

    @Column(name = "doc_date")
    private Date docDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_airline")
    private Airline airline;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "finish_date")
    private Date finishDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_aircraft_type")
    private AircraftType aircraftType;


}

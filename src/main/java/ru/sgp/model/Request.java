package ru.sgp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "fly_request", schema = "public")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "doc_num")
    private Integer docNum;

    @Column(name = "doc_date")
    private Date docDate;

    @Column(name = "fly_date_start")
    private Date flyDateStart;

    @Column(name = "fly_date_finish")
    private Date flyDateFinish;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aircraft_type")
    private AircraftType aircraftType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_target")
    private FlightTarget flightTarget;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_state")
    private RequestState state;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contract_data")
    private ContractData contractData;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "duration_out")
    private Double durationOut;

    @Column(name = "round_digit")
    private Integer roundDigit;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "cost_out")
    private Double costOut;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emp_customer")
    private EmpCustomer empCustomer;

    @Column(name = "doc_code")
    private Integer docCode;

    @Column(name = "reject_note")
    private String rejectNote;

    @Column(name = "note")
    private String note;

}

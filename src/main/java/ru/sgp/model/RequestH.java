package ru.sgp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "fly_request_h", schema = "public")
public class RequestH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_airline")
    private Airline airline;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_work_type")
    private WorkType workType;

    @Column(name = "date")
    private Date date;

    @Column(name = "year")
    private Integer year;

    @Column(name = "fly_date_start")
    private Date flyDateStart;

    @Column(name = "fly_date_finish")
    private Date flyDateFinish;

}

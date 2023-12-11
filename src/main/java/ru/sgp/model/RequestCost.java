package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "fly_request_cost", schema = "public")
public class RequestCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_request")
    private Request request;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_filial")
    private Filial filial;

    @Column(name = "duration")
    private String duration;

    @Column(name = "duration_out")
    private String durationOut;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_org_customer")
    private EmpCustomer orgCustomer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_work_type")
    private WorkType workType;

}

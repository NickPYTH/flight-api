package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "fly_route_plan_helicopter", schema = "public")
public class RoutePlanHelicopter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_request")
    private RequestH idRequestH;

    @OneToOne
    @JoinColumn(name = "id_emp_resp")
    private EmployeeResponsible idEmpResp;

    @OneToOne
    @JoinColumn(name = "id_work_type")
    private WorkType idWorkType;

}

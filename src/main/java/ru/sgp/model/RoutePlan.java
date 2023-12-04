package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "fly_route_plan", schema = "public")
public class RoutePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_request")
    private Request idRequest;

    @OneToOne
    @JoinColumn(name = "id_emp_resp")
    private EmployeeResponsible idEmpResp;

    @OneToOne
    @JoinColumn(name = "id_work_type")
    private WorkType idWorkType;

}

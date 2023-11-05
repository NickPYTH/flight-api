package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "dict_emp_responsible", schema = "public")
public class EmployeeResponsible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_employee")
    private Employee idEmployee;

    @Column(name = "phone_number")
    private String phoneNumber;

}

package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "dict_employee", schema = "public") //CREATE SEQUENCE hibernate_sequence START 1;
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="id_filial")
    private Filial idFilial;

    @OneToOne
    @JoinColumn(name="id_department")
    private Department idDepartment;

    @Column( name = "tabnumber")
    private Integer tabnumber;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "secondname")
    private String secondname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "post_name")
    private String postname;

    @Column(name = "set_date")
    private Date setDate;

    @Column(name = "end_date")
    private Date endDate;
}

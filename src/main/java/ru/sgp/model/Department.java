package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "dict_department", schema = "public")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_parent")
    private Integer idParent;

    @OneToOne
    @JoinColumn(name="id_filial")
    private Filial idFilial;

    @Column(name = "name")
    private String name;

    @Column(name = "long_name")
    private String longName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "set_date")
    private Date setDate;

    @Column(name = "end_date")
    private Date endDate;
}

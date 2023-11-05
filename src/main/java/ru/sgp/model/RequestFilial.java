package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "fly_request_filial", schema = "public")
public class RequestFilial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_request_file")
    private FileStorage idRequestFile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_filial")
    private Filial idFilial;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_state")
    private RequestState idState;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "reject_note")
    private String rejectNote;

    @Column(name = "year")
    private Integer year;

}

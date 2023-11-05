package ru.sgp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "file_storage", schema = "public")
public class FileStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_name")
    private String name;

    @Column(name = "file_body")
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] fileBody;
}

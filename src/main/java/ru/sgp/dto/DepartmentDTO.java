package ru.sgp.dto;

import lombok.Data;

@Data
public class DepartmentDTO {
    private Long id;
    private Integer idParent;
    private Long filialId;
    private String name;
    private String longName;
    private String shortName;
    private String setDate;
    private String endDate;
}

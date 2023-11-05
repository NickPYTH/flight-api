package ru.sgp.dto;

import lombok.Data;

@Data
public class FilialDTO {
    private Long id;
    private String name;
    private String longName;
    private String shortName;
    private Integer sortOrder;
}

package ru.sgp.dto;

import lombok.Data;

@Data
public class CostDTO {
    private Long requestId;
    private Long costId;
    private Long filialId;
    private String filialName;
    private Long workTypeId;
    private String workTypeName;
    private Double duration;
    private Double cost;
}

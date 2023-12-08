package ru.sgp.dto;

import lombok.Data;

@Data
public class UpdateRequestDTO {
    private Long id;
    private String field;
    private String value;
    private String dateStart;
    private String dateFinish;
    private Long idContractData;
    private Long idTarget;
    private Long idEmpCustomer;
}

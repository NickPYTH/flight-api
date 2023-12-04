package ru.sgp.dto;

import lombok.Data;

@Data
public class EmpCustomerDTO {
    private Long id;
    private Long empId;
    private String empName;
    private String empLastName;
    private String empSecondName;
}

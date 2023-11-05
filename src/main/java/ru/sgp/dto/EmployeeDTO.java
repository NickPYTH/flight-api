package ru.sgp.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private Long filialId;
    private Long departmentId;
    private Integer tabnumber;
    private String firstname;
    private String secondname;
    private String lastname;
    private String postname;
}

package com.clearsolution.testjob.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExceptionDTO {
    private Integer statusCode;
    private String message;
    private LocalDate timestamp;
}

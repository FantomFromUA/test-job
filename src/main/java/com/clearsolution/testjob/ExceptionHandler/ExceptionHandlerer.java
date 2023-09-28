package com.clearsolution.testjob.ExceptionHandler;

import com.clearsolution.testjob.DTO.ExceptionDTO;
import com.clearsolution.testjob.Exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class ExceptionHandlerer {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionDTO> userExceptionHandler(UserException error, WebRequest request){
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
        exceptionDTO.setMessage(error.getMessage());
        exceptionDTO.setTimestamp(LocalDate.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDTO> wrongDataException(IllegalAccessError error, WebRequest request){
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
        exceptionDTO.setMessage(error.getMessage());
        exceptionDTO.setTimestamp(LocalDate.now());

        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
}

package com.clearsolution.testjob.Exception;

import java.io.Serial;

public class UserException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2;

    public UserException(String message){
        super(message);
    }
}

package com.mindiqo.backend.exception;

import com.mindiqo.backend.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        String errorMessage = e.getMessage();
        return Result.error("error: " + errorMessage);
    }
}

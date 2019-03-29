package com.example.testplatform.handler;

import com.example.testplatform.common.Response;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerAdviceErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Response<String> onConstraintValidationException(ConstraintViolationException e){

        List<String> errorMessages = new ArrayList<>();
        for(ConstraintViolation violation : e.getConstraintViolations()){
            errorMessages.add(violation.getMessage());

        }
        return new Response<String>(400,null,errorMessages.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Response<String> onMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<String> errorMessages = new ArrayList<>();
        for(FieldError fieldError : e.getBindingResult().getFieldErrors()){
            errorMessages.add(fieldError.getDefaultMessage());
        }
        return new Response<String>(400,null,errorMessages.toString());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Response<String> onMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        return new Response<String>(400,null,e.getMessage());
    }
}

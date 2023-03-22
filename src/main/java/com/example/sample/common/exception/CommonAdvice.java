package com.example.sample.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonAdvice {

    @ExceptionHandler(Common400Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ProblemDetail onException400 (
        Common400Exception exception
    ) {
        return ProblemDetail.forStatusAndDetail (
                HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage()
        );
    }

    @ExceptionHandler(Common500Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ProblemDetail onException500 (
            Common500Exception exception
    ) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception.getMessage()
        );
    }

}

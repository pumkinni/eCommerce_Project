package com.example.orderapi.exception;

import com.example.orderapi.exception.CustomException.CustomExceptionResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<CustomExceptionResponse> exceptionHandler(HttpServletRequest rep, final CustomException e){
        return ResponseEntity.status(e.getStatus())
            .body(CustomExceptionResponse.builder()
                .message(e.getMessage())
                .code(e.getErrorCode().name())
                .status(e.getStatus()).build());
    }
}

package org.scd.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.swing.plaf.nimbus.NimbusStyle;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity parseBusinessException(BusinessException ex){
        Map<String,Object> responseMessage = new HashMap<>();
        responseMessage.put("status",ex.getStatus());
        responseMessage.put("message",ex.getMessage());
        return new ResponseEntity(responseMessage, HttpStatus.valueOf(ex.getStatus()));
    }
}

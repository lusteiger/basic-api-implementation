package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.exceptions.CommonErr;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class BaseController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex){
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);

        if (responseStatus == null){
            return ResponseEntity.status(500).body(
                    ex.getMessage()
            );
        }

        return ResponseEntity.status(responseStatus.code())
                .body(responseStatus.reason());
    }


}

package com.thoughtworks.rslist.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonException {
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<CommonErr> IndexOutOfBoundsException(Exception e){
        CommonErr commonErr = new CommonErr();

        commonErr.setError(e.getMessage());
        return ResponseEntity.badRequest().body(commonErr);
    }


}

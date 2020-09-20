package com.thoughtworks.rslist.exceptions;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import javax.validation.ValidationException;


@ControllerAdvice
public class CommonException {
    @ExceptionHandler({IndexOutOfBoundsException.class, MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class})
    public ResponseEntity<CommonErr> invalidParamException(Exception e) {

        Logger logger = LoggerFactory.getLogger(CommonException.class);

        CommonErr commonErr = new CommonErr();

        if (e instanceof MethodArgumentNotValidException ||
                e instanceof MethodArgumentTypeMismatchException ||
                e instanceof IndexOutOfBoundsException
        )
            commonErr.setError("invalid request param");
        else {
            commonErr.setError(e.getMessage());
        }

        logger.error(e.getMessage());

        return ResponseEntity.badRequest().body(commonErr);
    }


}

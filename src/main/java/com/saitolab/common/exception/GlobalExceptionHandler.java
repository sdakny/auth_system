package com.saitolab.common.exception;

import com.saitolab.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public R handler(RuntimeException e){
        log.error("Runtime exception：----------------{}", e.getMessage());
        System.out.println("Runtime exception：");
        return R.error(e.getMessage());
    }





}

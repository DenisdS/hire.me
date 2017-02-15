package com.idshorten.exception;

import com.idshorten.controller.response.CustomerResponse;
import com.idshorten.model.ShortenURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import java.net.MalformedURLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        customerResponse.setResponseDescription("Internal error");

        return customerResponse;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleExceptionWithoutParameter(MissingServletRequestParameterException e) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setResponseCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        customerResponse.setResponseDescription("The parameter URL is required!");
        return customerResponse;
    }

    @ExceptionHandler(URLNotExists.class)
    public Object handlerURLNotExists(URLNotExists e) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setResponseCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        customerResponse.setResponseDescription("The URL is not mapped!");
        return customerResponse;
    }


    /*@ExceptionHandler(MalformedURLException.class)
    public Object handlerMalformedURLException(MalformedURLException e){
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setResponseCode(002);
        customerResponse.setResponseDescription("SHORTENED URL NOT FOUND");


        return customerResponse;
    }*/



}

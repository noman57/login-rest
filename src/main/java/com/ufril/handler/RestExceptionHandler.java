package com.ufril.handler;

import com.ufril.dto.common.Error;
import com.ufril.dto.common.Response;
import com.ufril.dto.common.ValidationError;
import com.ufril.enumeration.StatusType;
import com.ufril.exception.BadRequestException;
import com.ufril.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Noman
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request) {
        Error error = new Error();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTitle(HttpStatus.NOT_FOUND.getReasonPhrase());
        error.setDetail(rnfe.getMessage());
        error.setTimeStamp(new Date().getTime());
        error.setDeveloperMessage(rnfe.getClass().getName());
        return new ResponseEntity<>(new Response(StatusType.ERROR, null, error), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadRequestException(BadRequestException bre, HttpServletRequest request) {
        Error error = new Error();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setDetail(bre.getMessage());
        error.setTimeStamp(new Date().getTime());
        error.setDeveloperMessage(bre.getClass().getName());

        return new ResponseEntity<>(new Response(StatusType.ERROR, null, error), null, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error();
        error.setStatus(status.value());
        error.setTitle(status.getReasonPhrase());
        error.setDetail(ex.getMessage());
        error.setTimeStamp(new Date().getTime());
        error.setDeveloperMessage(ex.getClass().getName());

        return new ResponseEntity<Object>(new Response(StatusType.ERROR, null, error), headers, status); // handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error();
        error.setTimeStamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTitle("Validation Failed");
        error.setDetail("Input validation failed");
        error.setDeveloperMessage(ex.getClass().getName());

        List<FieldError> fieldErrors =  ex.getBindingResult().getFieldErrors();
        for(FieldError fe : fieldErrors) {
            logger.debug("FieldError : " + fe.toString());
            logger.debug("Errors : " + error.getErrors().toString());
//            List<ValidationError> validationErrorList = error.getErrors().get(fe.getField());
            List<ValidationError> validationErrorList = error.getErrors();
            if(validationErrorList == null) {
                validationErrorList = new ArrayList<ValidationError>();
//                error.getErrors().put(fe.getField(), validationErrorList);
            }
            ValidationError validationError = new ValidationError(fe.getField(), fe.getCode(), messageSource.getMessage(fe, null));
//            validationError.setCode(fe.getCode());
//            validationError.setMessage(messageSource.getMessage(fe, null));
            validationErrorList.add(validationError);
        }

        return new ResponseEntity<Object>(new Response(StatusType.ERROR, null, error), headers, status); // handleExceptionInternal(ex, error, headers, status, request);
    }

}

package com.example.raffle.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    List<String> listClear = new ArrayList<>();

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        List<String> listError = new ArrayList<>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        listError.add((e.getMessage()));
        var err = StandardError.builder()
                .status("Resource not found")
                .code(status.value())
                .message(listError)
                .result(listClear)
                .build();
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> dataBaseException(DatabaseException e, HttpServletRequest request) {
        List<String> listError = new ArrayList<>();
        listError.add((e.getMessage()));
        HttpStatus status = HttpStatus.BAD_REQUEST;
        var err = StandardError.builder()
                .status("Data base error")
                .code(status.value())
                .message(listError)
                .result(listClear)
                .build();
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<StandardError> validationException(ValidationException e, HttpServletRequest request) {
        List<String> listError = new ArrayList<>();
        listError.add((e.getMessage()));
        HttpStatus status = HttpStatus.BAD_REQUEST;
        var err = StandardError.builder()
                .status("Bad Request")
                .code(status.value())
                .message(listError)
                .result(listClear)
                .build();
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<StandardError> feignException(FeignException e, HttpServletRequest request) {
        List<String> listError = new ArrayList<>();
        listError.add((e.getMessage()));
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        var err = StandardError.builder()
                .status("Bad Request")
                .code(status.value())
                .message(listError)
                .result(listClear)
                .build();
        return ResponseEntity.status(status).body(err);

    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus statusHttp, WebRequest request) {
        String error = "Erro de Validação";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ErrorObject> errors = getErrors(ex);
        var err = ListError.builder()
                .status(error)
                .code(status.value())
                .messages(errors)
                .result(listClear)
                .build();
        return ResponseEntity.status(status).body(err);
    }

    private List<ErrorObject> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorObject(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                .collect(Collectors.toList());
    }
}

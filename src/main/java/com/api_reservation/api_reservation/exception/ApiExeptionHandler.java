package com.api_reservation.api_reservation.exception;

import com.api_reservation.api_reservation.dto.ErrorDTO;
import com.api_reservation.api_reservation.num.APIError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExeptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ReservationExeption.class)
  public ResponseEntity<ErrorDTO> duplicateResource(ReservationExeption exeption, WebRequest webRequest){
    return ResponseEntity.status(exeption.getStatus())
        .body(new ErrorDTO(exeption.getDescription(), exeption.getReasons()));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException methodArgumentNotValidException,
      HttpHeaders httpHeaders,
      HttpStatusCode httpStatusCode,
      WebRequest webRequest) {
    List<String> reasons = new ArrayList<>();
    for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()){
      reasons.add(String.format("%s - %s", fieldError.getField(), fieldError.getDefaultMessage()));
    }
    return ResponseEntity.status(APIError.VALIDATION_ERROR.getHttpStatus())
        .body(new ErrorDTO(APIError.VALIDATION_ERROR.getMessage(), reasons));
  }
}

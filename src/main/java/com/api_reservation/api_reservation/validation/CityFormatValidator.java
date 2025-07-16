package com.api_reservation.api_reservation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CityFormatValidator implements ConstraintValidator<CityFormatConstraint, String> {

  @Override
  public void initialize(CityFormatConstraint constraintAnnotation) { }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return s != null &&
        s.length() == 3 &&
        s.matches("[A-Z][a-z]+");
  }
}

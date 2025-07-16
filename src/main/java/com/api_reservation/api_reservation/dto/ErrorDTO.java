package com.api_reservation.api_reservation.dto;

import java.util.List;

public class ErrorDTO {
  private String description;
  private List<String> reasons;

  public ErrorDTO(String description, List<String> reasons) {
    this.description = description;
    this.reasons = reasons;
  }

  public List<String> getReasons() {
    return reasons;
  }

  public String getDescription() {
    return description;
  }
}
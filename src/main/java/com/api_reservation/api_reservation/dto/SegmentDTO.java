package com.api_reservation.api_reservation.dto;

import com.api_reservation.api_reservation.validation.CityFormatConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SegmentDTO {

  @CityFormatConstraint
  private String origin;

  @CityFormatConstraint
  private String destination;

  private String departure;

  private String arrival;

  private String carrier;

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getDeparture() {
    return departure;
  }

  public void setDeparture(String departure) {
    this.departure = departure;
  }

  public String getArrival() {
    return arrival;
  }

  public void setArrival(String arrival) {
    this.arrival = arrival;
  }

  public String getCarrier() {
    return carrier;
  }

  public void setCarrier(String carrier) {
    this.carrier = carrier;
  }
}

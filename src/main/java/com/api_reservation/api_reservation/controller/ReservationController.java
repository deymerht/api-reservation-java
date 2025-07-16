package com.api_reservation.api_reservation.controller;

import com.api_reservation.api_reservation.controller.resource.ReservationResource;
import com.api_reservation.api_reservation.dto.PassengerDTO;
import com.api_reservation.api_reservation.dto.ReservationDTO;
import com.api_reservation.api_reservation.exception.ReservationExeption;
import com.api_reservation.api_reservation.model.Passenger;
import com.api_reservation.api_reservation.num.APIError;
import com.api_reservation.api_reservation.service.ReservationService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Validated
@RestController
@RequestMapping("/reservation")
public class ReservationController implements ReservationResource {

  private ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping
  public ResponseEntity<List<ReservationDTO>> getReservations(){
    List<ReservationDTO> response = reservationService.getReservations();
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id){
    ReservationDTO response = reservationService.getReservationById(id);
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @PostMapping
  @RateLimiter(name = "post-reservation", fallbackMethod = "fallbackPost")
  public ResponseEntity<ReservationDTO> save(@Min(1) @Valid @RequestBody ReservationDTO reservation){
//    validateSave(reservation);
    return new ResponseEntity(reservationService.save(reservation), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReservationDTO> update(@Min(1) @PathVariable Long id, @Valid @RequestBody ReservationDTO reservation){
//    validateUpdate(reservation);
    return new ResponseEntity(reservationService.update(id, reservation), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@Min(1) @PathVariable Long id){
    reservationService.delete(id);
    return new ResponseEntity(HttpStatus.OK);
  }

  public ResponseEntity<ReservationDTO> fallbackPost(ReservationDTO reservation, RequestNotPermitted e){
    System.out.println("calling to fallbackPost");
    throw new ReservationExeption(APIError.EXCEED_NUMBER_OPERATIONS);
  }

//  private void validateSave(ReservationDTO reservation){
//    if (Objects.isNull(reservation.getId())
//        || Objects.isNull(reservation.getPassengers())
//        || Objects.isNull(reservation.getItinerary())){
//      throw new ReservationExeption("Something is not ok, please check the root nodes");
//    }
//  }
//
//  private void validateUpdate(ReservationDTO reservation){
//    if (reservation.getId() == null
//        || reservation.getPassengers() == null
//        || reservation.getItinerary() == null){
//      throw new ReservationExeption("Something is not ok, please check the root nodes");
//    }
//    validatePassengers(reservation.getPassengers());
// }

//  private void validatePassengers(List<PassengerDTO> passengers){
//    for (PassengerDTO passenger: passengers){
//      if (passenger.getFirstName() == null ||
//      passenger.getFirstName().isEmpty() ||
//      passenger.getFirstName().length() > 30){
//        throw new ReservationExeption("First name is wrong");
//      }
//    }
//  }

}

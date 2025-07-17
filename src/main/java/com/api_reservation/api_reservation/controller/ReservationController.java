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

  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ReservationController.class);

  private ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping
  public ResponseEntity<List<ReservationDTO>> getReservations() {
    logger.info("[getReservations] Consultando todas las reservas");
    List<ReservationDTO> response = reservationService.getReservations();
    logger.debug("[getReservations] Total de reservas encontradas: {}", response.size());
    return new ResponseEntity<List<ReservationDTO>>(response, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
    logger.info("[getReservationById] Consultando reserva con id: {}", id);
    ReservationDTO response = reservationService.getReservationById(id);
    if (response != null) {
      logger.debug("[getReservationById] Reserva encontrada: {}", response);
    } else {
      logger.warn("[getReservationById] No se encontró reserva con id: {}", id);
    }
    return new ResponseEntity<ReservationDTO>(response, HttpStatus.OK);
  }

  @PostMapping
  @RateLimiter(name = "post-reservation", fallbackMethod = "fallbackPost")
  public ResponseEntity<ReservationDTO> save(@Min(1) @Valid @RequestBody ReservationDTO reservation) {
    logger.info("[save] Creando nueva reserva: {}", reservation);
    ReservationDTO saved = reservationService.save(reservation);
    logger.debug("[save] Reserva creada con id: {}", saved != null ? saved.getId() : null);
    return new ResponseEntity<ReservationDTO>(saved, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReservationDTO> update(@Min(1) @PathVariable Long id, @Valid @RequestBody ReservationDTO reservation) {
    logger.info("[update] Actualizando reserva con id: {}", id);
    ReservationDTO updated = reservationService.update(id, reservation);
    logger.debug("[update] Reserva actualizada: {}", updated);
    return new ResponseEntity<ReservationDTO>(updated, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@Min(1) @PathVariable Long id) {
    logger.info("[delete] Eliminando reserva con id: {}", id);
    reservationService.delete(id);
    logger.debug("[delete] Reserva eliminada con id: {}", id);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  public ResponseEntity<ReservationDTO> fallbackPost(ReservationDTO reservation, RequestNotPermitted e) {
    logger.error("[fallbackPost] Se excedió el número de operaciones permitidas. Reserva: {}", reservation);
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

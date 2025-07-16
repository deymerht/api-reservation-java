package com.api_reservation.api_reservation.service;

import com.api_reservation.api_reservation.connector.CatalogConnector;
import com.api_reservation.api_reservation.connector.response.CityDTO;
import com.api_reservation.api_reservation.dto.ReservationDTO;
import com.api_reservation.api_reservation.dto.SegmentDTO;
import com.api_reservation.api_reservation.exception.ReservationExeption;
import com.api_reservation.api_reservation.model.Reservation;
import com.api_reservation.api_reservation.num.APIError;
import com.api_reservation.api_reservation.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {

  private IReservationRepository reservationRepository;
  private ConversionService conversionService;
  private CatalogConnector catalogConnector;

  @Autowired
  public ReservationService(IReservationRepository reservationRepository,
                            ConversionService conversionService,
                            CatalogConnector catalogConnector) {
    this.reservationRepository = reservationRepository;
    this.conversionService = conversionService;
    this.catalogConnector = catalogConnector;
  }

  public List<ReservationDTO> getReservations() {
    return conversionService.convert(reservationRepository.getReservations(), List.class);
  }

  public ReservationDTO getReservationById(Long id) {
    Optional<Reservation> result = reservationRepository.getReservationById(id);
    if(result.isEmpty()) {
      throw new ReservationExeption(APIError.RESERVATION_NOT_FOUND);
    }
    return conversionService.convert(result.get(), ReservationDTO.class);
  }

  public ReservationDTO save(ReservationDTO reservation) {
    if(Objects.nonNull(reservation.getId())) {
      throw new ReservationExeption(APIError.RESERVATION_NOT_FOUND);
    }
    checkCity(reservation);
    Reservation transformed = conversionService.convert(reservation, Reservation.class);
    Reservation result = reservationRepository.save(Objects.requireNonNull(transformed));
    return conversionService.convert(result, ReservationDTO.class);
  }

  public ReservationDTO update(Long id, ReservationDTO reservation) {
    if(getReservationById(id) == null) {
      throw new ReservationExeption(APIError.RESERVATION_NOT_FOUND);
    }
    checkCity(reservation);
    Reservation transformed = conversionService.convert(reservation, Reservation.class);
    Reservation result = reservationRepository.update(id, Objects.requireNonNull(transformed));
    return conversionService.convert(result, ReservationDTO.class);
  }

  public void delete(Long id) {
    if(getReservationById(id) == null) throw new ReservationExeption(APIError.RESERVATION_NOT_FOUND);
    reservationRepository.delete(id);
  }

  private void checkCity(ReservationDTO reservationDTO){
    for (SegmentDTO segmentDTO : reservationDTO.getItinerary().getSegment()){
      CityDTO origin = catalogConnector.getCity(segmentDTO.getOrigin());
      CityDTO destination = catalogConnector.getCity(segmentDTO.getDestination());

      if (origin == null || destination == null){
        throw new ReservationExeption(APIError.VALIDATION_ERROR);
      }else{
        System.out.println(origin.getName());
        System.out.println(destination.getName());
      }

    }
  }

}

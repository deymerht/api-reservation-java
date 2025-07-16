package com.api_reservation.api_reservation.mapper;

import com.api_reservation.api_reservation.dto.ReservationDTO;
import com.api_reservation.api_reservation.model.Reservation;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface ReservationDTOMapper extends Converter<Reservation, ReservationDTO> {

  @Override
  ReservationDTO convert(Reservation source);
}

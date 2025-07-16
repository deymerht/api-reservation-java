package com.api_reservation.api_reservation.repository;

import com.api_reservation.api_reservation.model.Reservation;
import java.util.List;
import java.util.Optional;

public interface IReservationRepository {
    
    /**
     * Obtiene todas las reservas
     * @return Lista de reservas
     */
    List<Reservation> getReservations();
    
    /**
     * Busca una reserva por su ID
     * @param id ID de la reserva
     * @return Optional con la reserva si existe
     */
    Optional<Reservation> getReservationById(Long id);
    
    /**
     * Guarda una nueva reserva
     * @param reservation Reserva a guardar
     * @return Reserva guardada con ID asignado
     */
    Reservation save(Reservation reservation);
    
    /**
     * Actualiza una reserva existente
     * @param id ID de la reserva a actualizar
     * @param reservation Nuevos datos de la reserva
     * @return Reserva actualizada
     */
    Reservation update(Long id, Reservation reservation);
    
    /**
     * Elimina una reserva por su ID
     * @param id ID de la reserva a eliminar
     */
    void delete(Long id);
} 
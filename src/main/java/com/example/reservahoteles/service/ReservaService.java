package com.example.reservahoteles.service;

import com.example.reservahoteles.model.Reserva;
import java.util.List;
import java.util.Optional;

public interface ReservaService {
    List<Reserva> getAllReservas();
    Optional<Reserva> getReservaById(Long id);
    Reserva createReserva(Reserva reserva);
    Reserva updateReserva(Long id,Reserva reserva);
    void deleteReserva(Long id);
    
} 

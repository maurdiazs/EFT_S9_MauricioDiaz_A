package com.example.reservahoteles.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reservahoteles.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    
} 
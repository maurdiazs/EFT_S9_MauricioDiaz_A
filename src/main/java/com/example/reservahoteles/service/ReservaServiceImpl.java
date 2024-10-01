package com.example.reservahoteles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reservahoteles.model.Reserva;
import com.example.reservahoteles.repository.ReservaRepository;

import java.util.List;
import java.util.Optional;


@Service
public class ReservaServiceImpl implements ReservaService{
    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public Optional<Reserva> getReservaById(Long id) {
        return reservaRepository.findById(id);
    }
    
    @Override
    public Reserva createReserva(Reserva reserva){
        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva updateReserva(Long id, Reserva reserva){
        if(reservaRepository.existsById(id)){
            reserva.setId(id);
            return reservaRepository.save(reserva);
        }   else {
                return null;
        }
    }

    @Override
    public void deleteReserva(Long id){
        reservaRepository.deleteById(id);
    }
}
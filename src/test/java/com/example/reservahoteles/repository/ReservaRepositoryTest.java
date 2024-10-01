package com.example.reservahoteles.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.reservahoteles.model.Reserva;
import com.example.reservahoteles.repository.ReservaRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Test
    public void testSaveReserva() {
        // Creamos una instancia de Reserva
        Reserva reserva = new Reserva();
        reserva.setNombreCliente("Juan Pérez");
        reserva.setHabitacion(101);
        reserva.setFechaInicio(LocalDate.now());
        reserva.setFechaFin(LocalDate.now().plusDays(2));
        reserva.setStatus("CONFIRMADA");

        // Guardamos la reserva
        Reserva savedReserva = reservaRepository.save(reserva);

        // Verificamos que la reserva se haya guardado correctamente
        assertEquals("Juan Pérez", savedReserva.getNombreCliente());
        assertEquals(101, savedReserva.getHabitacion());
        assertEquals("CONFIRMADA", savedReserva.getStatus());
        assertEquals(reserva.getFechaInicio(), savedReserva.getFechaInicio());
        assertEquals(reserva.getFechaFin(), savedReserva.getFechaFin());
    }

    @Test
    public void testFindById() {
        // Creamos y guardamos una reserva
        Reserva reserva = new Reserva();
        reserva.setNombreCliente("María López");
        reserva.setHabitacion(202);
        reserva.setFechaInicio(LocalDate.now());
        reserva.setFechaFin(LocalDate.now().plusDays(3));
        reserva.setStatus("CONFIRMADA");

        Reserva savedReserva = reservaRepository.save(reserva);

        // Buscamos la reserva por su ID
        Optional<Reserva> foundReserva = reservaRepository.findById(savedReserva.getId());

        // Verificamos que se haya encontrado la reserva
        assertTrue(foundReserva.isPresent());
        assertEquals("María López", foundReserva.get().getNombreCliente());
    }

    @Test
    public void testDeleteReserva() {
        // Creamos y guardamos una reserva
        Reserva reserva = new Reserva();
        reserva.setNombreCliente("Pedro González");
        reserva.setHabitacion(303);
        reserva.setFechaInicio(LocalDate.now());
        reserva.setFechaFin(LocalDate.now().plusDays(1));
        reserva.setStatus("CONFIRMADA");

        Reserva savedReserva = reservaRepository.save(reserva);

        // Eliminamos la reserva
        reservaRepository.deleteById(savedReserva.getId());

        // Verificamos que la reserva haya sido eliminada
        Optional<Reserva> deletedReserva = reservaRepository.findById(savedReserva.getId());
        assertTrue(deletedReserva.isEmpty());
    }
}
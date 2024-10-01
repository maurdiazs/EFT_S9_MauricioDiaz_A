package com.example.reservahoteles.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.reservahoteles.model.Reserva;
import com.example.reservahoteles.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {
    @InjectMocks
    private ReservaServiceImpl reservaServicio;

    @Mock
    private ReservaRepository reservaRepositoryMock;

    @Test
    public void guardarReservaTest() {
        // Dado
        Reserva reserva = new Reserva();
        reserva.setNombreCliente("Mauricio Diaz");
        reserva.setHabitacion(101);
        reserva.setFechaInicio(LocalDate.of(2024, 10, 1));
        reserva.setFechaFin(LocalDate.of(2024, 10, 5));
        reserva.setStatus("Confirmada");

        // Configuración del mock
        when(reservaRepositoryMock.save(any(Reserva.class))).thenReturn(reserva);

        // Cuando
        Reserva resultado = reservaServicio.createReserva(reserva);

        // Entonces
        assertEquals("Jose Rondon", resultado.getNombreCliente());
        assertEquals(101, resultado.getHabitacion());
        assertEquals(LocalDate.of(2024, 10, 1), resultado.getFechaInicio());
        assertEquals(LocalDate.of(2024, 10, 5), resultado.getFechaFin());
        assertEquals("Confirmada", resultado.getStatus());
    }

    // Prueba para el caso en que la reserva es nula
    @Test
    public void guardarReservaNulaTest() {
        // Configurar el mock para lanzar una excepción si se intenta guardar una reserva nula
        when(reservaRepositoryMock.save(null)).thenThrow(new IllegalArgumentException("La reserva no puede ser nula"));

        // Verificar que se lanza la excepción esperada
        assertThrows(IllegalArgumentException.class, () -> reservaServicio.createReserva(null));
    }

    //Prueba para actualizar una reserva existente
    @Test
    public void actualizarReservaTest() {
        // Dado
        Reserva reservaExistente = new Reserva();
        reservaExistente.setId(1L);
        reservaExistente.setNombreCliente("Maria Perez");
        reservaExistente.setHabitacion(202);
        reservaExistente.setFechaInicio(LocalDate.of(2024, 9, 1));
        reservaExistente.setFechaFin(LocalDate.of(2024, 9, 3));
        reservaExistente.setStatus("Pendiente");

        // Configuración del mock para encontrar la reserva existente
        when(reservaRepositoryMock.findById(1L)).thenReturn(Optional.of(reservaExistente));
        when(reservaRepositoryMock.save(any(Reserva.class))).thenReturn(reservaExistente);

        // Cuando
        reservaExistente.setNombreCliente("Maria Actualizada");
        Reserva resultado = reservaServicio.updateReserva(1L, reservaExistente);

        // Entonces
        assertEquals("Maria Actualizada", resultado.getNombreCliente());
        assertEquals("Pendiente", resultado.getStatus());
    }
}

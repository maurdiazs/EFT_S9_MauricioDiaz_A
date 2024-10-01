package com.example.reservahoteles.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.reservahoteles.model.Student;
import com.example.reservahoteles.repository.StudentRepository;
import com.example.reservahoteles.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {
    @InjectMocks
    private ReservaServiceImpl ReservaServicio;

    @Mock
    private ReservaRepository ReservaRepositoryMock;

    @Test
    public void guardarReservaTest() {

        Reserva reserva = new Reserva();
        reserva.setName("Jose Rondon");

        when(reservaRepositoryMock.save(any())).thenReturn(reserva);

        Reserva resultado = reservaServicio.createReserva(reserva);

        assertEquals("Jose Rondon", resultado.getName());
    }
}

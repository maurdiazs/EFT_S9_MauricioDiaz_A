package com.example.reservahoteles.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;
import com.example.reservahoteles.model.Reserva;
import com.example.reservahoteles.service.ReservaService;

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaServiceMock;

    @Test
    public void obtenerTodasReservasTest() throws Exception {
        Reserva reserva1 = new Reserva();
        reserva1.setNombreCliente("Mauricio Diaz");
        reserva1.setId(1L);

        Reserva reserva2 = new Reserva();
        reserva2.setNombreCliente("Julio Diaz");
        reserva2.setId(2L);

        List<Reserva> reservas = List.of(reserva1, reserva2);

        when(reservaServiceMock.getAllReservas()).thenReturn(reservas);

        mockMvc.perform(get("/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.reservas.length()").value(2))
                .andExpect(jsonPath("$._embedded.reservas[0].nombreCliente").value("Mauricio Diaz"))
                .andExpect(jsonPath("$._embedded.reservas[1].nombreCliente").value("Julio Diaz"))
                .andExpect(jsonPath("$._embedded.reservas[0]._links.self.href").value("http://localhost:8080/reservas/1"))
                .andExpect(jsonPath("$._embedded.reservas[1]._links.self.href").value("http://localhost:8080/reservas/2"));
    }
}



package com.example.reservahoteles.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservaTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testReservaValida() {
        // Creamos una instancia válida de Reserva
        Reserva reserva = new Reserva();
        reserva.setNombreCliente("Juan Pérez");
        reserva.setHabitacion(101);
        reserva.setFechaInicio(LocalDate.of(2024, 10, 1));
        reserva.setFechaFin(LocalDate.of(2024, 10, 10));
        reserva.setStatus("Confirmada");

        // Validamos la instancia
        Set<ConstraintViolation<Reserva>> violations = validator.validate(reserva);

        // No debe haber violaciones si la instancia es válida
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNombreClienteNoDebeSerVacio() {
        // Instancia de Reserva con un campo nombreCliente vacío
        Reserva reserva = new Reserva();
        reserva.setNombreCliente("");
        reserva.setHabitacion(101);
        reserva.setFechaInicio(LocalDate.of(2024, 10, 1));
        reserva.setFechaFin(LocalDate.of(2024, 10, 10));
        reserva.setStatus("Confirmada");

        // Validamos la instancia
        Set<ConstraintViolation<Reserva>> violations = validator.validate(reserva);

        // Debe haber una violación para el campo nombreCliente
        assertEquals(1, violations.size());
        assertEquals("No puede ingresar un nombre vacio", violations.iterator().next().getMessage());
    }

    @Test
    public void testHabitacionNoDebeSerNula() {
        // Instancia de Reserva con habitación en su valor por defecto (0), si se desea otro valor de nulo asegurate que 
        // se modifique la anotación
        Reserva reserva = new Reserva();
        reserva.setNombreCliente("Juan Pérez");
        reserva.setHabitacion(0); // No puede ser nulo en el contexto de JPA pero se usa en 0
        reserva.setFechaInicio(LocalDate.of(2024, 10, 1));
        reserva.setFechaFin(LocalDate.of(2024, 10, 10));
        reserva.setStatus("Confirmada");

        // Validamos la instancia
        Set<ConstraintViolation<Reserva>> violations = validator.validate(reserva);

        // Debe haber una violación para el campo habitacion
        assertEquals(1, violations.size());
    }

    @Test
    public void testFechaInicioNoDebeSerNula() {
        // Instancia de Reserva con fechaInicio nula
        Reserva reserva = new Reserva();
        reserva.setNombreCliente("Juan Pérez");
        reserva.setHabitacion(101);
        reserva.setFechaInicio(null);
        reserva.setFechaFin(LocalDate.of(2024, 10, 10));
        reserva.setStatus("Confirmada");

        // Validamos la instancia
        Set<ConstraintViolation<Reserva>> violations = validator.validate(reserva);

        // Debe haber una violación para el campo fechaInicio
        assertEquals(1, violations.size());
        assertEquals("La fecha de inicio no puede ser nula", violations.iterator().next().getMessage());
    }

    @Test
    public void testStatusNoDebeSerVacio() {
        // Instancia de Reserva con el campo status vacío
        Reserva reserva = new Reserva();
        reserva.setNombreCliente("Juan Pérez");
        reserva.setHabitacion(101);
        reserva.setFechaInicio(LocalDate.of(2024, 10, 1));
        reserva.setFechaFin(LocalDate.of(2024, 10, 10));
        reserva.setStatus("");

        // Validamos la instancia
        Set<ConstraintViolation<Reserva>> violations = validator.validate(reserva);

        // Debe haber una violación para el campo status
        assertEquals(1, violations.size());
        assertEquals("No puede ingresar un estado vacio", violations.iterator().next().getMessage());
    }
}

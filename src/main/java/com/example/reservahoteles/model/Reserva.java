package com.example.reservahoteles.model;
//import java.io.ObjectInputFilter.Status;
import java.time.LocalDate;
//import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "reserva")
public class Reserva extends RepresentationModel<Reserva>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "No puede ingresar un nombre vacio")
    @Column(name= "nombreCliente")
    private String nombreCliente;

    @Column(name = "habitacion")
    private int habitacion;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin no puede ser nula")
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @NotBlank(message = "No puede ingresar un estado vacio")
    @Column(name= "status")
    private String status;


    //Getters

    public Long getId() {
        return id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public int getHabitacion() {
        return habitacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getStatus() {
        return status;
    }



    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.example.reservahoteles.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.reservahoteles.model.Reserva;
import com.example.reservahoteles.service.ReservaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private static final Logger log = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public CollectionModel<EntityModel<Reserva>> getAllReservas() {
        List<Reserva> reservas = reservaService.getAllReservas();
        log.info("GET /reservas - Retornando todas las reservas");

        List<EntityModel<Reserva>> reservasResources = reservas.stream()
            .map(reserva -> EntityModel.of(reserva,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReservaById(reserva.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas());
        return CollectionModel.of(reservasResources, linkTo.withRel("reservas"));
    }

    @GetMapping("/{id}")
    public EntityModel<Reserva> getReservaById(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.getReservaById(id);

        if (reserva.isPresent()) {
            return EntityModel.of(reserva.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReservaById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas()).withRel("all-reservas"));
        } else {
            log.error("No se encontró la reserva con ID {}", id);
            throw new ReservaNotFoundException("Reserva not found with id: " + id);
        }
    }

    @PostMapping
    public EntityModel<Reserva> createReserva(@Validated @RequestBody Reserva reserva) {
        Reserva createdReserva = reservaService.createReserva(reserva);
        return EntityModel.of(createdReserva,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReservaById(createdReserva.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas()).withRel("all-reservas"));
    }

    @PutMapping("/{id}")
    public EntityModel<Reserva> updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Reserva updatedReserva = reservaService.updateReserva(id, reserva);
        return EntityModel.of(updatedReserva,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReservaById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas()).withRel("all-reservas"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }

    // Clase interna para manejar errores
    static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}


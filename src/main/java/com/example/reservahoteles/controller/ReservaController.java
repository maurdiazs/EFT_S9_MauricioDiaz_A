package com.example.reservahoteles.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
        log.info("GET /reservas");
        log.info("Retornando todas las reservas");
        List<EntityModel<Reserva>> reservasResources = reservas.stream()
            .map( reserva -> EntityModel.of(reserva,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReservaById(reserva.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas());
        CollectionModel<EntityModel<Reserva>> resources = CollectionModel.of(reservasResources, linkTo.withRel("reservas"));

        return resources;
    }
/* 
    public List<Student> getAllStudents(){
        log.info("GET /students");
        log.info("Retornando todos los estudiantes");
        return studentService.getAllStudents();
    }
    */    
    @GetMapping("/{id}")
    public EntityModel<Reserva> getReservaById(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.getReservaById(id);

        if (reserva.isPresent()) {
            return EntityModel.of(reserva.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReservaById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas()).withRel("all-reservas"));
        } else {
            throw new ReservaNotFoundException("Reserva not found with id: " + id);
        }
    }
    /*public ResponseEntity<Object> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            log.error("No se encontró el estudiante con ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontró el estudiante con ID " + id));
        }
        return ResponseEntity.ok(student);
    }
*/
    @PostMapping
    public EntityModel<Reserva> createReserva(@Validated @RequestBody Reserva reserva) {
        Reserva createdReserva = reservaService.createReserva(reserva);
            return EntityModel.of(createdReserva,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReservaById(createdReserva.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas()).withRel("all-reservas"));

    }
    /*public ResponseEntity<Object> createStudent(@Validated @RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        if (createdStudent == null) {
            log.error("Error al crear el estudiante {}", student);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error al crear el estudiante"));
        }
        return ResponseEntity.ok(createdStudent);
    }
    */
    @PutMapping("/{id}")
    public EntityModel<Reserva> updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Reserva updatedReserva = reservaService.updateReserva(id, reserva);
        return EntityModel.of(updatedReserva,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReservaById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReservas()).withRel("all-reservas"));

    }
    /*public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }
*/
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){
        reservaService.deleteReserva(id);
    }


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

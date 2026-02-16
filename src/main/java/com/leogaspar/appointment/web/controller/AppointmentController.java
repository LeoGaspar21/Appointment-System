package com.leogaspar.appointment.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leogaspar.appointment.domain.model.Appointment;
import com.leogaspar.appointment.domain.repository.AppointmentRepository;
import com.leogaspar.appointment.dto.AppointmentDTO;
import com.leogaspar.appointment.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService service;

	@Autowired
	private AppointmentRepository repository;

	@PostMapping
	public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO dto) {

		Appointment newAppointment = service.createAppointment(dto);

		AppointmentDTO response = new AppointmentDTO(newAppointment.getProfessionalId(), newAppointment.getClientId(),
				newAppointment.getDate(), newAppointment.getStartTime(), newAppointment.getEndTime());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<Appointment>> listAllAppointments() {
		List<Appointment> appointments = repository.findAll();

		return ResponseEntity.ok().body(appointments);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Appointment> findAppointmentById(@PathVariable String id) {
		return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

}

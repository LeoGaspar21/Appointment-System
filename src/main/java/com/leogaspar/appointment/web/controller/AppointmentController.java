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
import com.leogaspar.appointment.domain.model.AppointmentStatus;
import com.leogaspar.appointment.domain.repository.AppointmentRepository;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentRepository repository;

	@PostMapping
	public ResponseEntity<Appointment> creatAppointment(@RequestBody Appointment appointment) {
		Appointment newAppointment = new Appointment(appointment.getId(), appointment.getDate(),
				appointment.getStartTime(), appointment.getEndTime(), AppointmentStatus.SCHEDULED,
				appointment.getProfessionalId(), appointment.getClientId());
		
		repository.save(newAppointment);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newAppointment);
		
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

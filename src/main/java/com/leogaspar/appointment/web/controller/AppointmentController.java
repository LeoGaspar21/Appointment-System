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
import com.leogaspar.appointment.domain.repository.ClientRepository;
import com.leogaspar.appointment.domain.repository.ProfessionalRepository;
import com.leogaspar.appointment.exceptions.ClientNotFoundException;
import com.leogaspar.appointment.exceptions.InvalidAppointmentTimeException;
import com.leogaspar.appointment.exceptions.ProfessionalNotFoundException;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentRepository repository;
	
	
	@Autowired
	private ProfessionalRepository professionalRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	

	@PostMapping
	public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
		
		professionalRepository.findById(appointment.getProfessionalId()).orElseThrow(() -> new ProfessionalNotFoundException("Professional not Found"));
		
		clientRepository.findById(appointment.getClientId()).orElseThrow(() -> new ClientNotFoundException("Client not Found"));
		
		if (!appointment.getEndTime().isAfter(appointment.getStartTime())) {
			throw  new InvalidAppointmentTimeException("Start or End time invalid");
		}
		
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

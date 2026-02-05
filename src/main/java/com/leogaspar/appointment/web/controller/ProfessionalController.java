package com.leogaspar.appointment.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leogaspar.appointment.domain.model.Professional;
import com.leogaspar.appointment.domain.repository.ProfessionalRepository;

@RestController
@RequestMapping("/professional")
public class ProfessionalController {

	@Autowired
	private ProfessionalRepository repository;
	
	
	
	@PostMapping
	public ResponseEntity<Professional> createProfessionals(@RequestBody Professional professional) {
		Professional newProfessional = new Professional(professional.getId(), professional.getName(),
				professional.getSpecialty(), professional.getSchedule());
		
		repository.save(newProfessional);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newProfessional);

	}
	
	@GetMapping
	public ResponseEntity<List<Professional>> getAllProfessional() {
		
		List<Professional> professionals = repository.findAll(); 
		
		
		return ResponseEntity.ok().body(professionals);
	}

}

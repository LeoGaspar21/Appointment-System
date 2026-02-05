package com.leogaspar.appointment.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leogaspar.appointment.domain.model.Professional;

@RestController
@RequestMapping("/professional")
public class ProfessionalController {

	@PostMapping
	public ResponseEntity<Professional> createProfessional(@RequestBody Professional professional) {
		Professional newProfessional = new Professional(professional.getId(), professional.getName(),
				professional.getSpecialty(), professional.getSchedule());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newProfessional);

	}

}

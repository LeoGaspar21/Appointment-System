package com.leogaspar.appointment.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leogaspar.appointment.domain.model.Client;
import com.leogaspar.appointment.domain.repository.ClientRepository;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientRepository repository;

	@PostMapping
	public ResponseEntity<Client> createClient(@RequestBody Client client) {
		Client newClient = new Client(client.getId(), client.getName(), client.getEmail(), client.getPhone());
		
		repository.save(newClient);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
		
	}

}

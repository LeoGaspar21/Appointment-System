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

import com.leogaspar.appointment.domain.model.Client;
import com.leogaspar.appointment.domain.repository.ClientRepository;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientRepository repository;

	@PostMapping
	public ResponseEntity<Client> createClient(@RequestBody Client client) {
		Client newClient = new Client(null, client.getName(), client.getEmail(), client.getPhone());
		
		repository.save(newClient);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
		
	}
	
	@GetMapping
	public ResponseEntity<List<Client>> getAllClients() {
		List<Client> clients = repository.findAll();
		
		return ResponseEntity.ok().body(clients);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Client> getClientById(@PathVariable String id) {
		return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

}

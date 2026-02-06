package com.leogaspar.appointment.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.leogaspar.appointment.domain.model.Client;

public interface ClientRepository extends MongoRepository<Client, String>{
}

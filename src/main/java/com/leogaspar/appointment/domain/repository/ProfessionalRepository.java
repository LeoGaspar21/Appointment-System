package com.leogaspar.appointment.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.leogaspar.appointment.domain.model.Professional;

public interface ProfessionalRepository extends MongoRepository<Professional, String>{

}

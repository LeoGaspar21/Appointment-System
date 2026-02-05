package com.leogaspar.appointment.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.leogaspar.appointment.domain.model.Appointment;

public interface AppointmentRepository  extends MongoRepository<Appointment, String>{

}

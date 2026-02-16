package com.leogaspar.appointment.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.leogaspar.appointment.domain.model.Appointment;

public interface AppointmentRepository  extends MongoRepository<Appointment, String>{
	List<Appointment> findByProfessionalIdAndDate(String professionalId, LocalDate date);

}

package com.leogaspar.appointment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leogaspar.appointment.domain.model.Appointment;
import com.leogaspar.appointment.domain.model.AppointmentStatus;
import com.leogaspar.appointment.domain.repository.AppointmentRepository;
import com.leogaspar.appointment.domain.repository.ClientRepository;
import com.leogaspar.appointment.domain.repository.ProfessionalRepository;
import com.leogaspar.appointment.dto.AppointmentDTO;
import com.leogaspar.appointment.exceptions.AppointmentConflictException;
import com.leogaspar.appointment.exceptions.ClientNotFoundException;
import com.leogaspar.appointment.exceptions.InvalidAppointmentTimeException;
import com.leogaspar.appointment.exceptions.ProfessionalNotFoundException;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository repository;

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private ProfessionalRepository professionalRepository;
	
	
	public Appointment createAppointment(AppointmentDTO appointment) {
		
		professionalRepository.findById(appointment.getProfessionalId()).orElseThrow(() -> new ProfessionalNotFoundException("Professional Not Found"));
		
		clientRepository.findById(appointment.getClientId()).orElseThrow(() -> new ClientNotFoundException("Client Not Found"));
		
		if (!appointment.getEndTime().isAfter(appointment.getStartTime())) {
			throw new InvalidAppointmentTimeException("Start or End time invalid");
		}
		
		List<Appointment> existingAppointments = repository.findByProfessionalIdAndDate(appointment.getProfessionalId(), appointment.getDate());
		
		for (Appointment exist : existingAppointments) {
			if(appointment.getEndTime().isAfter(exist.getStartTime()) && appointment.getStartTime().isBefore(exist.getEndTime())) {
				throw new AppointmentConflictException("Appointment Conflict Error");
			}
		}
		
		
			
		Appointment newAppointment = new Appointment(null,appointment.getDate(),appointment.getStartTime(),appointment.getEndTime(),AppointmentStatus.SCHEDULED,appointment.getProfessionalId(),appointment.getClientId());
		
		repository.save(newAppointment);
		
		return newAppointment;
		
		
	}
	
	
	
	
	
	
}

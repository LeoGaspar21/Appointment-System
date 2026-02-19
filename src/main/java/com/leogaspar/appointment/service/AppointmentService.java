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
import com.leogaspar.appointment.exceptions.AppointmentNotFoundException;
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
	
	
	public Appointment createAppointment(AppointmentDTO request) {
		
		professionalRepository.findById(request.getProfessionalId()).orElseThrow(() -> new ProfessionalNotFoundException("Professional Not Found"));
		
		clientRepository.findById(request.getClientId()).orElseThrow(() -> new ClientNotFoundException("Client Not Found"));
		
		if (!request.getEndTime().isAfter(request.getStartTime())) {
			throw new InvalidAppointmentTimeException("Start or End time invalid");
		}
		
		List<Appointment> existingAppointments = repository.findByProfessionalIdAndDate(request.getProfessionalId(), request.getDate());
		
		for (Appointment exist : existingAppointments) {
			if(request.getEndTime().isAfter(exist.getStartTime()) && request.getStartTime().isBefore(exist.getEndTime())) {
				throw new AppointmentConflictException("Appointment Conflict Error");
			}
		}
		
		
			
		Appointment newAppointment = new Appointment(null,request.getDate(),request.getStartTime(),request.getEndTime(),AppointmentStatus.SCHEDULED,request.getProfessionalId(),request.getClientId());
		
		Appointment saved = repository.save(newAppointment);
		
		return saved;
		
		
	}
	
	
	public Appointment cancelAppointment(String id) {
		Appointment appointmentCancel = repository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment Not Found"));
		
		appointmentCancel.cancel();
		
		return repository.save(appointmentCancel);
	}
	
	
	
	
	
	
}

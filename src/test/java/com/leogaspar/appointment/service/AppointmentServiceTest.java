package com.leogaspar.appointment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.leogaspar.appointment.domain.model.Appointment;
import com.leogaspar.appointment.domain.model.AppointmentStatus;
import com.leogaspar.appointment.domain.model.Client;
import com.leogaspar.appointment.domain.model.Professional;
import com.leogaspar.appointment.domain.repository.AppointmentRepository;
import com.leogaspar.appointment.domain.repository.ClientRepository;
import com.leogaspar.appointment.domain.repository.ProfessionalRepository;
import com.leogaspar.appointment.dto.AppointmentDTO;
import com.leogaspar.appointment.exceptions.AppointmentNotFoundException;
import com.leogaspar.appointment.exceptions.ClientNotFoundException;
import com.leogaspar.appointment.exceptions.InvalidAppointmentStateException;
import com.leogaspar.appointment.exceptions.InvalidAppointmentTimeException;
import com.leogaspar.appointment.exceptions.ProfessionalNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
	
	@Mock
	AppointmentRepository repository;
	
	@Mock
	ProfessionalRepository professionalRepository;

	@Mock
	ClientRepository clientRepository;
	
	@InjectMocks
	AppointmentService service;
	
	
	@Test
	void shouldCreateAppointmentSuccessfully() {
		AppointmentDTO dto = new AppointmentDTO(
				"prof1",
				"client1",
				LocalDate.now(),
				LocalTime.of(10, 0),
				LocalTime.of(11, 0)
			);
		
		when(professionalRepository.findById(dto.getProfessionalId())).thenReturn(Optional.of(new Professional()));
		
		when(clientRepository.findById(dto.getClientId())).thenReturn(Optional.of(new Client()));
		
		when(repository.findByProfessionalIdAndDate(dto.getProfessionalId(), dto.getDate())).thenReturn(Collections.emptyList());
		
		when(repository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Appointment result = service.createAppointment(dto);
		
		assertNotNull(result);
		assertEquals(AppointmentStatus.SCHEDULED, result.getStatus());
		assertEquals("prof1", result.getProfessionalId());
		assertEquals("client1", result.getClientId());
		
		verify(repository, times(1)).save(any(Appointment.class));
		
		
	}
	
	@Test
	void shouldThrowAppointmentNotFoundException() {
		
		String id = "opadsiard456";
		
		when(repository.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(AppointmentNotFoundException.class, () -> service.cancelAppointment(id));
		
		verify(repository, never()).save(any());
	}
	
	@Test
	void shouldThrowWhenAppointmentIsAlreadyCancelled() {
		
		String id = "123";
		
		Appointment appointment = new Appointment();
		
		appointment.setId(id);
		appointment.setStatus(AppointmentStatus.CANCELED);
		
		when(repository.findById(id)).thenReturn(Optional.of(appointment));
		
		assertThrows(InvalidAppointmentStateException.class, () -> service.cancelAppointment(id));
		
		verify(repository, never()).save(any());
		
	}
	
	@Test
	void shouldThrowWhenAppointmentIsAlreadyCompleted() {
		
		String id = "321";
		
		Appointment appointment = new Appointment();
		
		appointment.setId(id);
		appointment.setStatus(AppointmentStatus.COMPLETED);
		
		when(repository.findById(id)).thenReturn(Optional.of(appointment));
		
		assertThrows(InvalidAppointmentStateException.class, () -> service.cancelAppointment(id));
		
		verify(repository, never()).save(any());
		
		
	}

	@Test 
	void shoultChangeStatusAfterCancelAppointment(){
		String id = "456";
		
		Appointment appointment = new Appointment();
		
		appointment.setId(id);
		appointment.setStatus(AppointmentStatus.SCHEDULED);
		
		when(repository.findById(id)).thenReturn(Optional.of(appointment));
		when(repository.save(appointment)).thenReturn(appointment);
		
		Appointment result = service.cancelAppointment(id);
		
		
		
		assertEquals(AppointmentStatus.CANCELED, result.getStatus());

	}
	
	@Test 
	void shouldThrowExceptionWhenProfessionalDoesNotExist() {
		
		
		String professionalId = "546";
		
		AppointmentDTO appointment = new AppointmentDTO();
		appointment.setProfessionalId(professionalId);
		
		when(professionalRepository.findById(professionalId)).thenReturn(Optional.empty());
		
		assertThrows(ProfessionalNotFoundException.class, () -> service.createAppointment(appointment));
	
		
		
		
		verify(repository, never()).save(any());
		
	}
	
	@Test
	void shouldThrowExceptionWhenClientDoesNotExist(){
		String clientId = "846";
		String professionalId = "546";
		
		AppointmentDTO appointment = new AppointmentDTO();
		appointment.setClientId(clientId);
		appointment.setProfessionalId(professionalId);
		
		when(professionalRepository.findById(professionalId)).thenReturn(Optional.of(new Professional()));
		when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
		
		assertThrows(ClientNotFoundException.class, () -> service.createAppointment(appointment));

		
		verify(repository, never()).save(any());
		
	}
	
	@Test
	void shouldThrowExceptionWhenAppointmentTimeConflicts() {
		String clientId = "846";
		String professionalId = "546";
		
		AppointmentDTO dto = new AppointmentDTO();
		dto.setClientId(clientId);
		dto.setProfessionalId(professionalId);
		dto.setDate(LocalDate.parse("2026-03-04"));
		dto.setStartTime(LocalTime.parse("13:00"));
		dto.setEndTime(LocalTime.parse("11:30"));
		
		when(professionalRepository.findById(dto.getProfessionalId())).thenReturn(Optional.of(new Professional()));
		when(clientRepository.findById(dto.getClientId())).thenReturn(Optional.of(new Client()));
		
		assertThrows(InvalidAppointmentTimeException.class, () -> service.createAppointment(dto));
		
		verify(repository, never()).save(any());
		
	}
	
	
	
	
}

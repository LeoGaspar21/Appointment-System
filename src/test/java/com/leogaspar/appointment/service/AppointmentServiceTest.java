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
import com.leogaspar.appointment.exceptions.AppointmentConflictException;
import com.leogaspar.appointment.exceptions.AppointmentNotFoundException;

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
		
		assertThrows(AppointmentConflictException.class, () -> service.cancelAppointment(id));
		
		verify(repository, never()).save(any());
		
	}
	
	@Test
	void shouldThrowWhenAppointmentIsAlreadyCompleted() {
		
		String id = "321";
		
		Appointment appointment = new Appointment();
		
		appointment.setId(id);
		appointment.setStatus(AppointmentStatus.COMPLETED);
		
		when(repository.findById(id)).thenReturn(Optional.of(appointment));
		
		assertThrows(AppointmentConflictException.class, () -> service.cancelAppointment(id));
		
		verify(repository, never()).save(any());
		
		
	}

	
	
}

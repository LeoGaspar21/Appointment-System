package com.leogaspar.appointment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
	
}

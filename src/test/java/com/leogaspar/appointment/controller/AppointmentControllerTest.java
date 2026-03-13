package com.leogaspar.appointment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.leogaspar.appointment.domain.model.Appointment;
import com.leogaspar.appointment.domain.repository.AppointmentRepository;
import com.leogaspar.appointment.exceptions.AppointmentNotFoundException;
import com.leogaspar.appointment.service.AppointmentService;
import com.leogaspar.appointment.web.controller.AppointmentController;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AppointmentService appointmentService;

	@MockitoBean
	private AppointmentRepository appointmentRepository;

	@Test
	void shouldReturnOkWhenCancelAppointmentSuccessfully() throws Exception {

		String id = "123";

		when(appointmentService.cancelAppointment(id)).thenReturn(new Appointment());

		mockMvc.perform(patch("/appointment/{id}/cancel", id)).andExpect(status().isOk());
	}

	@Test
	void shouldReturnNotFoundWhenCancelingNonExistingAppointment() throws Exception {

		String id = "123";

		when(appointmentService.cancelAppointment(id))
				.thenThrow(new AppointmentNotFoundException("Appointment not found"));

		mockMvc.perform(patch("/appointment/{id}/cancel", id)).andExpect(status().isNotFound());

	}

	@Test
	void shouldReturnCreatedWhenCreateAppointmentSuccessfully() throws Exception {
		String json = """
				{
				"professionalId": "prof1",
				"clientId": "client1",
				"date": "2025-03-10",
				"startTime": "10:00",
				"endTime": "11:00"
				}""";
		
		Appointment appointment = new Appointment();
		appointment.setProfessionalId("prof1");
		appointment.setClientId("client1");
		appointment.setId("123");
		
		
		when(appointmentService.createAppointment(any())).thenReturn(appointment);
		
		mockMvc.perform(post("/appointment").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());
		
		verify(appointmentService).createAppointment(any());
		
		
		
	}
	
	@Test
	void shouldReturnBadRequestWhenCreateAppointmentWithInvalidData() throws Exception {
		String json = """
				{
				"professionalId": "prof1",
				"clientId": "client1",
				"date": "025hjfj5-032-10",
				"startTime": "10:00",
				"endTime": "11:00"
				}""";
		

		
		mockMvc.perform(post("/appointment").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest());
		
		verify(appointmentService, never()).createAppointment(any());
		
	}
	
	@Test
	void shouldReturnOkWhenFindAppointmentById() throws Exception{
		String id = "123";
		when(appointmentRepository.findById(id)).thenReturn(Optional.of(new Appointment()));
		mockMvc.perform(get("/appointment/{id}", id)).andExpect(status().isOk());
		
	}
	
	
}
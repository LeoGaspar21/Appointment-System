package com.leogaspar.appointment.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.leogaspar.appointment.domain.model.Appointment;
import com.leogaspar.appointment.domain.repository.AppointmentRepository;
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

        when(appointmentService.cancelAppointment(id))
                .thenReturn(new Appointment());

        mockMvc.perform(patch("/appointment/{id}/cancel", id))
                .andExpect(status().isOk());
    }
}
package com.leogaspar.appointment.web.controller.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.leogaspar.appointment.exceptions.AppointmentConflictException;
import com.leogaspar.appointment.exceptions.AppointmentNotFoundException;
import com.leogaspar.appointment.exceptions.ClientNotFoundException;
import com.leogaspar.appointment.exceptions.InvalidAppointmentTimeException;
import com.leogaspar.appointment.exceptions.ProfessionalNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(AppointmentNotFoundException.class)
	public ResponseEntity<StandardError> appointmentNotFound(AppointmentNotFoundException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), e.getMessage(), "Appointment Not Found",
				request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(AppointmentConflictException.class)
	public ResponseEntity<StandardError> appointmentConflict(AppointmentConflictException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(Instant.now(), status.value(), e.getMessage(), "Appointment Conflict",
				request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(InvalidAppointmentTimeException.class)
	public ResponseEntity<StandardError> invalidAppointmentTime(InvalidAppointmentTimeException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), e.getMessage(), "Invalid Appointment Time",
				request.getRequestURI());

		return ResponseEntity.status(status).body(err);

	}
	
	@ExceptionHandler(ProfessionalNotFoundException.class)
	public ResponseEntity<StandardError> professionalNotFound(ProfessionalNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(
				Instant.now(), status.value(), e.getMessage(), "Professional Not Found", request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	@ExceptionHandler(ClientNotFoundException.class)
	public ResponseEntity<StandardError> clientNotFound(ClientNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), e.getMessage(), "Client Not Found", request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}

}

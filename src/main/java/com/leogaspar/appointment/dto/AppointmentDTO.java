package com.leogaspar.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDTO {
	private String professionalId;
	private String clientId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public AppointmentDTO() {
		
	}

	public AppointmentDTO(String professionalId, String clientId, LocalDate date, LocalTime startTime,
			LocalTime endTime) {
		super();
		this.professionalId = professionalId;
		this.clientId = clientId;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getProfessionalId() {
		return professionalId;
	}

	public void setProfessionalId(String professionalId) {
		this.professionalId = professionalId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	
	
}

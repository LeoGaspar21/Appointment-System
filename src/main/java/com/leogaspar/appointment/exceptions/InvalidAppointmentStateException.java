package com.leogaspar.appointment.exceptions;

public class InvalidAppointmentStateException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidAppointmentStateException(String msg) {
		super(msg);
	}
	
	
}

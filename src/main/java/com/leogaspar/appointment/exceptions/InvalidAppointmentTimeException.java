package com.leogaspar.appointment.exceptions;

public class InvalidAppointmentTimeException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidAppointmentTimeException(String msg) {
		super(msg);
	}

}

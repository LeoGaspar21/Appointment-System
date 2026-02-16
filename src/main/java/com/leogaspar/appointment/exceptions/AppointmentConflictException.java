package com.leogaspar.appointment.exceptions;

public class AppointmentConflictException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public AppointmentConflictException(String msg) {
		super(msg);
	}

}

package com.niit.backend.model;

import java.util.Date;

public class OutputMessage extends Message{

	public OutputMessage(Message original, Date time) {
		super(original.getId(),original.getMessage(),original.getUsername());
		this.setTime(time);
		// TODO Auto-generated constructor stub
	}
	private Date time;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	
	
	
}

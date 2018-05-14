package com.adhityavenancius.dibantuworker.Model;

import java.util.List;

public class ResponseAvailableJobs{
	private List<AvailablejobsItem> availablejobs;
	private String error;
	private String message;

	public void setAvailablejobs(List<AvailablejobsItem> availablejobs){
		this.availablejobs = availablejobs;
	}

	public List<AvailablejobsItem> getAvailablejobs(){
		return availablejobs;
	}

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseAvailableJobs{" + 
			"availablejobs = '" + availablejobs + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}
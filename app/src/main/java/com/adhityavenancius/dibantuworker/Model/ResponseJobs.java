package com.adhityavenancius.dibantuworker.Model;

import java.util.List;

public class ResponseJobs{
	private List<ActivejobsItem> activejobs;
	private String error;
	private String message;
	private List<HistoryjobsItem> historyjobs;

	public void setActivejobs(List<ActivejobsItem> activejobs){
		this.activejobs = activejobs;
	}

	public List<ActivejobsItem> getActivejobs(){
		return activejobs;
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

	public void setHistoryjobs(List<HistoryjobsItem> historyjobs){
		this.historyjobs = historyjobs;
	}

	public List<HistoryjobsItem> getHistoryjobs(){
		return historyjobs;
	}

	@Override
 	public String toString(){
		return 
			"ResponseJobs{" + 
			"activejobs = '" + activejobs + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			",historyjobs = '" + historyjobs + '\'' + 
			"}";
		}
}
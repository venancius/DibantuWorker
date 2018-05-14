package com.adhityavenancius.dibantuworker.Model;

import java.util.List;

public class ResponseCity{
	private List<CitydataItem> citydata;
	private String error;
	private String message;

	public void setCitydata(List<CitydataItem> citydata){
		this.citydata = citydata;
	}

	public List<CitydataItem> getCitydata(){
		return citydata;
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
			"ResponseCity{" + 
			"citydata = '" + citydata + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}
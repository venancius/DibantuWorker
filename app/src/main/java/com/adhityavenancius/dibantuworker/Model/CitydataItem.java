package com.adhityavenancius.dibantuworker.Model;

public class CitydataItem{
	private String name;
	private String id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"CitydataItem{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}

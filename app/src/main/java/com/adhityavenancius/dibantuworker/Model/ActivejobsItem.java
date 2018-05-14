package com.adhityavenancius.dibantuworker.Model;

public class ActivejobsItem{
	private String fare;
	private String notes;
	private String idCategory;
	private String workername;
	private String userpicture;
	private String idUser;
	private String startdate;
	private String workerpicture;
	private String enddate;
	private String location;
	private String categoryname;
	private String id;
	private String idWorker;
	private String time;
	private String status;
	private String username;

	public void setFare(String fare){
		this.fare = fare;
	}

	public String getFare(){
		return fare;
	}

	public void setNotes(String notes){
		this.notes = notes;
	}

	public String getNotes(){
		return notes;
	}

	public void setIdCategory(String idCategory){
		this.idCategory = idCategory;
	}

	public String getIdCategory(){
		return idCategory;
	}

	public void setWorkername(String workername){
		this.workername = workername;
	}

	public String getWorkername(){
		return workername;
	}

	public void setUserpicture(String userpicture){
		this.userpicture = userpicture;
	}

	public String getUserpicture(){
		return userpicture;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setStartdate(String startdate){
		this.startdate = startdate;
	}

	public String getStartdate(){
		return startdate;
	}

	public void setWorkerpicture(String workerpicture){
		this.workerpicture = workerpicture;
	}

	public String getWorkerpicture(){
		return workerpicture;
	}

	public void setEnddate(String enddate){
		this.enddate = enddate;
	}

	public String getEnddate(){
		return enddate;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setCategoryname(String categoryname){
		this.categoryname = categoryname;
	}

	public String getCategoryname(){
		return categoryname;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIdWorker(String idWorker){
		this.idWorker = idWorker;
	}

	public String getIdWorker(){
		return idWorker;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"ActivejobsItem{" + 
			"fare = '" + fare + '\'' + 
			",notes = '" + notes + '\'' + 
			",id_category = '" + idCategory + '\'' + 
			",workername = '" + workername + '\'' + 
			",userpicture = '" + userpicture + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",startdate = '" + startdate + '\'' + 
			",workerpicture = '" + workerpicture + '\'' + 
			",enddate = '" + enddate + '\'' + 
			",location = '" + location + '\'' + 
			",categoryname = '" + categoryname + '\'' + 
			",id = '" + id + '\'' + 
			",id_worker = '" + idWorker + '\'' + 
			",time = '" + time + '\'' + 
			",status = '" + status + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}

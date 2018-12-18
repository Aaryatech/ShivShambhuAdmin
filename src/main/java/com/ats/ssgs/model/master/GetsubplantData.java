package com.ats.ssgs.model.master;

public class GetsubplantData {
	
private int subplantId;

	
	private String subplantName;

	
	private String location;

	
	
      private int plantId;

	
	private int delStatus;
	
	private String plantName;
	
	public int getSubplantId() {
		return subplantId;
	}

	public void setSubplantId(int subplantId) {
		this.subplantId = subplantId;
	}

	public String getSubplantName() {
		return subplantName;
	}

	public void setSubplantName(String subplantName) {
		this.subplantName = subplantName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPlantId() {
		return plantId;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	@Override
	public String toString() {
		return "GetsubplantData [subplantId=" + subplantId + ", subplantName=" + subplantName + ", location=" + location
				+ ", plantId=" + plantId + ", delStatus=" + delStatus + ", plantName=" + plantName
				+ ", getSubplantId()=" + getSubplantId() + ", getSubplantName()=" + getSubplantName()
				+ ", getLocation()=" + getLocation() + ", getPlantId()=" + getPlantId() + ", getDelStatus()="
				+ getDelStatus() + ", getPlantName()=" + getPlantName() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	
	

}

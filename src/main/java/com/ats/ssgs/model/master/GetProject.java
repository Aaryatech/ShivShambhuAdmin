package com.ats.ssgs.model.master;

public class GetProject {
	private int projId;

	private String projName;

	private int custId;

	private String location;

	private String startDate;

	private String endDate;

	private int isUsed;

	private int delStatus;

	private String custName;

	private String plantName;
	private String contactPerName;
	private String contactPerMob;
	private String pincode;
	private float km;
	private String address;

	public int getProjId() {
		return projId;
	}

	public void setProjId(int projId) {
		this.projId = projId;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getContactPerName() {
		return contactPerName;
	}

	public void setContactPerName(String contactPerName) {
		this.contactPerName = contactPerName;
	}

	public String getContactPerMob() {
		return contactPerMob;
	}

	public void setContactPerMob(String contactPerMob) {
		this.contactPerMob = contactPerMob;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public float getKm() {
		return km;
	}

	public void setKm(float km) {
		this.km = km;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "GetProject [projId=" + projId + ", projName=" + projName + ", custId=" + custId + ", location="
				+ location + ", startDate=" + startDate + ", endDate=" + endDate + ", isUsed=" + isUsed + ", delStatus="
				+ delStatus + ", custName=" + custName + ", plantName=" + plantName + ", contactPerName="
				+ contactPerName + ", contactPerMob=" + contactPerMob + ", pincode=" + pincode + ", km=" + km
				+ ", address=" + address + "]";
	}

}

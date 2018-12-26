package com.ats.ssgs.model;




public class VehicleType {

	
	private int vehTypeId;

	private String vehTypeName;

	private String vehTypeShortName;

	private String date;

	private int delStatus;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;

	private String exDate1;

	private int exBool1;

	public int getVehTypeId() {
		return vehTypeId;
	}

	public void setVehTypeId(int vehTypeId) {
		this.vehTypeId = vehTypeId;
	}

	public String getVehTypeName() {
		return vehTypeName;
	}

	public void setVehTypeName(String vehTypeName) {
		this.vehTypeName = vehTypeName;
	}

	public String getVehTypeShortName() {
		return vehTypeShortName;
	}

	public void setVehTypeShortName(String vehTypeShortName) {
		this.vehTypeShortName = vehTypeShortName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public String getExDate1() {
		return exDate1;
	}

	public void setExDate1(String exDate1) {
		this.exDate1 = exDate1;
	}

	public int getExBool1() {
		return exBool1;
	}

	public void setExBool1(int exBool1) {
		this.exBool1 = exBool1;
	}

	@Override
	public String toString() {
		return "VehicleType [vehTypeId=" + vehTypeId + ", vehTypeName=" + vehTypeName + ", vehTypeShortName="
				+ vehTypeShortName + ", date=" + date + ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2="
				+ exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exDate1=" + exDate1 + ", exBool1=" + exBool1
				+ "]";
	}

}

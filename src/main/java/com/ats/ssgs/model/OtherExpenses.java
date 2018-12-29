package com.ats.ssgs.model;

public class OtherExpenses {

	private int otherExpId;

	private int plantId;

	private String date;

	private String reamrk;

	private int userId;

	private float amount;

	private int delStatus;

	private int exInt1;

	private int exInt2;

	private String exVarchar1;

	private String exDate1;

	private int exBool1;

	public int getOtherExpId() {
		return otherExpId;
	}

	public void setOtherExpId(int otherExpId) {
		this.otherExpId = otherExpId;
	}

	public int getPlantId() {
		return plantId;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReamrk() {
		return reamrk;
	}

	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
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

	public String getExVarchar1() {
		return exVarchar1;
	}

	public void setExVarchar1(String exVarchar1) {
		this.exVarchar1 = exVarchar1;
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
		return "OtherExpenses [otherExpId=" + otherExpId + ", plantId=" + plantId + ", date=" + date + ", reamrk="
				+ reamrk + ", userId=" + userId + ", amount=" + amount + ", delStatus=" + delStatus + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVarchar1=" + exVarchar1 + ", exDate1=" + exDate1 + ", exBool1="
				+ exBool1 + "]";
	}

}

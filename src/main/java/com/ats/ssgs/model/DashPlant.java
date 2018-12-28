package com.ats.ssgs.model;

public class DashPlant {

	private float totalBillAmount;
	private float contrExpenses;
	private float vehExpenses;
	private float totalBillAmtRubber;

	public float getTotalBillAmount() {
		return totalBillAmount;
	}

	public void setTotalBillAmount(float totalBillAmount) {
		this.totalBillAmount = totalBillAmount;
	}

	public float getContrExpenses() {
		return contrExpenses;
	}

	public void setContrExpenses(float contrExpenses) {
		this.contrExpenses = contrExpenses;
	}

	public float getVehExpenses() {
		return vehExpenses;
	}

	public void setVehExpenses(float vehExpenses) {
		this.vehExpenses = vehExpenses;
	}

	public float getTotalBillAmtRubber() {
		return totalBillAmtRubber;
	}

	public void setTotalBillAmtRubber(float totalBillAmtRubber) {
		this.totalBillAmtRubber = totalBillAmtRubber;
	}

	@Override
	public String toString() {
		return "DashPlant [totalBillAmount=" + totalBillAmount + ", contrExpenses=" + contrExpenses + ", vehExpenses="
				+ vehExpenses + ", totalBillAmtRubber=" + totalBillAmtRubber + "]";
	}

}

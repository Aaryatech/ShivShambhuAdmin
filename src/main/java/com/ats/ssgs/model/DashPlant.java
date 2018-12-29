package com.ats.ssgs.model;

public class DashPlant {

	private float totalBillAmount;
	private float contrExpenses;
	private float vehExpenses;
	private float totalBillAmtRubber;
	private float totalIssueExpenses;
	private float totalBillAmtRmc;
	private float totalIssueExpRmc;
	private float paymentRecPaid;
	private float paymentRecOutstandingPending;
	private float otherExpTotal;
	private float otherExpTotalRubber;
	private float otherExpTotalRmc;

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

	public float getTotalIssueExpenses() {
		return totalIssueExpenses;
	}

	public void setTotalIssueExpenses(float totalIssueExpenses) {
		this.totalIssueExpenses = totalIssueExpenses;
	}

	public float getTotalBillAmtRmc() {
		return totalBillAmtRmc;
	}

	public void setTotalBillAmtRmc(float totalBillAmtRmc) {
		this.totalBillAmtRmc = totalBillAmtRmc;
	}

	public float getTotalIssueExpRmc() {
		return totalIssueExpRmc;
	}

	public void setTotalIssueExpRmc(float totalIssueExpRmc) {
		this.totalIssueExpRmc = totalIssueExpRmc;
	}

	public float getPaymentRecPaid() {
		return paymentRecPaid;
	}

	public void setPaymentRecPaid(float paymentRecPaid) {
		this.paymentRecPaid = paymentRecPaid;
	}

	public float getPaymentRecOutstandingPending() {
		return paymentRecOutstandingPending;
	}

	public void setPaymentRecOutstandingPending(float paymentRecOutstandingPending) {
		this.paymentRecOutstandingPending = paymentRecOutstandingPending;
	}

	public float getOtherExpTotal() {
		return otherExpTotal;
	}

	public void setOtherExpTotal(float otherExpTotal) {
		this.otherExpTotal = otherExpTotal;
	}

	public float getOtherExpTotalRubber() {
		return otherExpTotalRubber;
	}

	public void setOtherExpTotalRubber(float otherExpTotalRubber) {
		this.otherExpTotalRubber = otherExpTotalRubber;
	}

	public float getOtherExpTotalRmc() {
		return otherExpTotalRmc;
	}

	public void setOtherExpTotalRmc(float otherExpTotalRmc) {
		this.otherExpTotalRmc = otherExpTotalRmc;
	}

	@Override
	public String toString() {
		return "DashPlant [totalBillAmount=" + totalBillAmount + ", contrExpenses=" + contrExpenses + ", vehExpenses="
				+ vehExpenses + ", totalBillAmtRubber=" + totalBillAmtRubber + ", totalIssueExpenses="
				+ totalIssueExpenses + ", totalBillAmtRmc=" + totalBillAmtRmc + ", totalIssueExpRmc=" + totalIssueExpRmc
				+ ", paymentRecPaid=" + paymentRecPaid + ", paymentRecOutstandingPending="
				+ paymentRecOutstandingPending + ", otherExpTotal=" + otherExpTotal + ", otherExpTotalRubber="
				+ otherExpTotalRubber + ", otherExpTotalRmc=" + otherExpTotalRmc + "]";
	}

}

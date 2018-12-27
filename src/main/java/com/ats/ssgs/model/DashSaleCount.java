package com.ats.ssgs.model;

public class DashSaleCount {

	private int totalEnq;
	private int totalQuotPending;
	private int totalQuotGenerated;
	private int totalPoPending;
	private int poGenerated;

	private float totalOrderAmount;
	private float totalBillAmount;

	private float totalTaxBillAmt;

	private float totalTaxableBillAmt;

	private float paymentRecPaid;

	private float paymentRecOutstandingPending;

	
	public int getPoGenerated() {
		return poGenerated;
	}

	public void setPoGenerated(int poGenerated) {
		this.poGenerated = poGenerated;
	}

	public int getTotalEnq() {
		return totalEnq;
	}

	public void setTotalEnq(int totalEnq) {
		this.totalEnq = totalEnq;
	}

	public int getTotalQuotPending() {
		return totalQuotPending;
	}

	public void setTotalQuotPending(int totalQuotPending) {
		this.totalQuotPending = totalQuotPending;
	}

	public int getTotalQuotGenerated() {
		return totalQuotGenerated;
	}

	public void setTotalQuotGenerated(int totalQuotGenerated) {
		this.totalQuotGenerated = totalQuotGenerated;
	}

	public int getTotalPoPending() {
		return totalPoPending;
	}

	public void setTotalPoPending(int totalPoPending) {
		this.totalPoPending = totalPoPending;
	}

	public float getTotalOrderAmount() {
		return totalOrderAmount;
	}

	public void setTotalOrderAmount(float totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}

	public float getTotalBillAmount() {
		return totalBillAmount;
	}

	public void setTotalBillAmount(float totalBillAmount) {
		this.totalBillAmount = totalBillAmount;
	}

	public float getTotalTaxBillAmt() {
		return totalTaxBillAmt;
	}

	public void setTotalTaxBillAmt(float totalTaxBillAmt) {
		this.totalTaxBillAmt = totalTaxBillAmt;
	}

	public float getTotalTaxableBillAmt() {
		return totalTaxableBillAmt;
	}

	public void setTotalTaxableBillAmt(float totalTaxableBillAmt) {
		this.totalTaxableBillAmt = totalTaxableBillAmt;
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

	@Override
	public String toString() {
		return "DashSaleCount [totalEnq=" + totalEnq + ", totalQuotPending=" + totalQuotPending
				+ ", totalQuotGenerated=" + totalQuotGenerated + ", totalPoPending=" + totalPoPending + ", poGenerated="
				+ poGenerated + ", totalOrderAmount=" + totalOrderAmount + ", totalBillAmount=" + totalBillAmount
				+ ", totalTaxBillAmt=" + totalTaxBillAmt + ", totalTaxableBillAmt=" + totalTaxableBillAmt
				+ ", paymentRecPaid=" + paymentRecPaid + ", paymentRecOutstandingPending="
				+ paymentRecOutstandingPending + "]";
	}

    
}

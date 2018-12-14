package com.ats.ssgs.model;

import java.io.Serializable;
import java.util.List;


public class GetBillHeader implements Serializable{

	private int billHeadId;
	
	private String billNo;
	
	private String billDate;

	private int custId;
	
	private String custName;
	
	private String plantName;
	
	private int projId;
	
	private String costSegment;
	
	private int paymentTermId;
	
	private int companyId;
	
	private String compName;
	
	private String challanId;
	
	private String deliveryTerm;
	
	private int poId;
	
	private float taxableAmt;
	
	private float taxAmt;
	
	private float totalAmt;
	
	private int accId;
	
	private String orderId;
	
	private int delStatus;
	
	private int exInt1;
	
	private int exInt2;
	
	private int exInt3;
	
	private String exVar1;
	
	private String exVar2;
	
	private String exVar3;
	
	private float exFloat1;
	
	private float exFloat2;

	List<GetBillDetail> getBillDetails;
	
	public List<GetBillDetail> getGetBillDetails() {
		return getBillDetails;
	}

	public void setGetBillDetails(List<GetBillDetail> getBillDetails) {
		this.getBillDetails = getBillDetails;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public int getBillHeadId() {
		return billHeadId;
	}

	public void setBillHeadId(int billHeadId) {
		this.billHeadId = billHeadId;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public int getProjId() {
		return projId;
	}

	public void setProjId(int projId) {
		this.projId = projId;
	}

	public String getCostSegment() {
		return costSegment;
	}

	public void setCostSegment(String costSegment) {
		this.costSegment = costSegment;
	}

	public int getPaymentTermId() {
		return paymentTermId;
	}

	public void setPaymentTermId(int paymentTermId) {
		this.paymentTermId = paymentTermId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getChallanId() {
		return challanId;
	}

	public void setChallanId(String challanId) {
		this.challanId = challanId;
	}

	public String getDeliveryTerm() {
		return deliveryTerm;
	}

	public void setDeliveryTerm(String deliveryTerm) {
		this.deliveryTerm = deliveryTerm;
	}

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(float taxAmt) {
		this.taxAmt = taxAmt;
	}

	public float getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(float totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public int getExInt3() {
		return exInt3;
	}

	public void setExInt3(int exInt3) {
		this.exInt3 = exInt3;
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

	public String getExVar3() {
		return exVar3;
	}

	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}

	public float getExFloat1() {
		return exFloat1;
	}

	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}

	public float getExFloat2() {
		return exFloat2;
	}

	public void setExFloat2(float exFloat2) {
		this.exFloat2 = exFloat2;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	@Override
	public String toString() {
		return "GetBillHeader [billHeadId=" + billHeadId + ", billNo=" + billNo + ", billDate=" + billDate + ", custId="
				+ custId + ", custName=" + custName + ", plantName=" + plantName + ", projId=" + projId
				+ ", costSegment=" + costSegment + ", paymentTermId=" + paymentTermId + ", companyId=" + companyId
				+ ", compName=" + compName + ", challanId=" + challanId + ", deliveryTerm=" + deliveryTerm + ", poId="
				+ poId + ", taxableAmt=" + taxableAmt + ", taxAmt=" + taxAmt + ", totalAmt=" + totalAmt + ", accId="
				+ accId + ", orderId=" + orderId + ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2="
				+ exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3
				+ ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2 + ", getBillDetails=" + getBillDetails + "]";
	}

	
	
	
}

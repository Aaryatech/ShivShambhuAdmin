package com.ats.ssgs.model.chalan;

import java.util.List;

public class ChalanHeader {
	
	private int chalanId;
	
	private String chalanNo;
	
	private int status;
	
	private String chalanDate;
	
	private int vehicleId;
	
	private int plantId;
	
	private int custId;
	
	private int projId;
	
	private int driverId;
	
	private String vehTimeOut;
	
	private String vehTimeIn;
	
	private String vehInDate;
	
	private float outKm;
	
	private float inKm;
	
	private String sitePersonName;
	
	private String sitePersonMob;
	
	private int orderId;
	
	private String orderNo;
	
	private String chalanRemark;
	
	private int exInt1;
	
	private float exFloat1;
	
	private String exVar1;
	
	private String exDate1;
	
	
	List<ChalanDetail> chalanDetailList;
	
	public List<ChalanDetail> getChalanDetailList() {
		return chalanDetailList;
	}

	public void setChalanDetailList(List<ChalanDetail> chalanDetailList) {
		this.chalanDetailList = chalanDetailList;
	}

	public int getChalanId() {
		return chalanId;
	}

	public void setChalanId(int chalanId) {
		this.chalanId = chalanId;
	}

	public String getChalanNo() {
		return chalanNo;
	}

	public void setChalanNo(String chalanNo) {
		this.chalanNo = chalanNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getChalanDate() {
		return chalanDate;
	}

	public void setChalanDate(String chalanDate) {
		this.chalanDate = chalanDate;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getPlantId() {
		return plantId;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getProjId() {
		return projId;
	}

	public void setProjId(int projId) {
		this.projId = projId;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public String getVehTimeOut() {
		return vehTimeOut;
	}

	public void setVehTimeOut(String vehTimeOut) {
		this.vehTimeOut = vehTimeOut;
	}

	public String getVehTimeIn() {
		return vehTimeIn;
	}

	public void setVehTimeIn(String vehTimeIn) {
		this.vehTimeIn = vehTimeIn;
	}

	public String getVehInDate() {
		return vehInDate;
	}

	public void setVehInDate(String vehInDate) {
		this.vehInDate = vehInDate;
	}

	public float getOutKm() {
		return outKm;
	}

	public void setOutKm(float outKm) {
		this.outKm = outKm;
	}

	public float getInKm() {
		return inKm;
	}

	public void setInKm(float inKm) {
		this.inKm = inKm;
	}

	public String getSitePersonName() {
		return sitePersonName;
	}

	public void setSitePersonName(String sitePersonName) {
		this.sitePersonName = sitePersonName;
	}

	public String getSitePersonMob() {
		return sitePersonMob;
	}

	public void setSitePersonMob(String sitePersonMob) {
		this.sitePersonMob = sitePersonMob;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getChalanRemark() {
		return chalanRemark;
	}

	public void setChalanRemark(String chalanRemark) {
		this.chalanRemark = chalanRemark;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public float getExFloat1() {
		return exFloat1;
	}

	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExDate1() {
		return exDate1;
	}

	public void setExDate1(String exDate1) {
		this.exDate1 = exDate1;
	}

	@Override
	public String toString() {
		return "ChalanHeader [chalanId=" + chalanId + ", chalanNo=" + chalanNo + ", status=" + status + ", chalanDate="
				+ chalanDate + ", vehicleId=" + vehicleId + ", plantId=" + plantId + ", custId=" + custId + ", projId="
				+ projId + ", driverId=" + driverId + ", vehTimeOut=" + vehTimeOut + ", vehTimeIn=" + vehTimeIn
				+ ", vehInDate=" + vehInDate + ", outKm=" + outKm + ", inKm=" + inKm + ", sitePersonName="
				+ sitePersonName + ", sitePersonMob=" + sitePersonMob + ", orderId=" + orderId + ", orderNo=" + orderNo
				+ ", chalanRemark=" + chalanRemark + ", exInt1=" + exInt1 + ", exFloat1=" + exFloat1 + ", exVar1="
				+ exVar1 + ", exDate1=" + exDate1 + ", chalanDetailList=" + chalanDetailList + "]";
	}

	

}

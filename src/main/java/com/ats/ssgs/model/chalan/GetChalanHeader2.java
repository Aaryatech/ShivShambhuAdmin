package com.ats.ssgs.model.chalan;

import java.util.Date;
import java.util.List;

public class GetChalanHeader2 {

	private int chalanId;

	private String chalanNo;

	private int status;

	private Date chalanDate;

	private int vehicleId;

	private int plantId;

	private int custId;

	private int projId;

	private int driverId;

	private String vehTimeOut;

	private String vehTimeIn;

	private Date vehInDate;

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

	private String costSegment;

	private String userName;

	private String poNo;

	// extra data

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private String vehNo;
	private String vehicleName;
	private String plantName;
	private int companyId;
	private String compName;

	private String custName;
	private String custMobNo;
	private String projName;
	private String projAddress;
	private String driverName;

	List<GetChalanDetail> detailList;

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

	public Date getChalanDate() {
		return chalanDate;
	}

	public void setChalanDate(Date chalanDate) {
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

	public Date getVehInDate() {
		return vehInDate;
	}

	public void setVehInDate(Date vehInDate) {
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

	public String getCostSegment() {
		return costSegment;
	}

	public void setCostSegment(String costSegment) {
		this.costSegment = costSegment;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getVehNo() {
		return vehNo;
	}

	public void setVehNo(String vehNo) {
		this.vehNo = vehNo;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustMobNo() {
		return custMobNo;
	}

	public void setCustMobNo(String custMobNo) {
		this.custMobNo = custMobNo;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getProjAddress() {
		return projAddress;
	}

	public void setProjAddress(String projAddress) {
		this.projAddress = projAddress;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public List<GetChalanDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<GetChalanDetail> detailList) {
		this.detailList = detailList;
	}

	@Override
	public String toString() {
		return "GetChalanHeader2 [chalanId=" + chalanId + ", chalanNo=" + chalanNo + ", status=" + status
				+ ", chalanDate=" + chalanDate + ", vehicleId=" + vehicleId + ", plantId=" + plantId + ", custId="
				+ custId + ", projId=" + projId + ", driverId=" + driverId + ", vehTimeOut=" + vehTimeOut
				+ ", vehTimeIn=" + vehTimeIn + ", vehInDate=" + vehInDate + ", outKm=" + outKm + ", inKm=" + inKm
				+ ", sitePersonName=" + sitePersonName + ", sitePersonMob=" + sitePersonMob + ", orderId=" + orderId
				+ ", orderNo=" + orderNo + ", chalanRemark=" + chalanRemark + ", exInt1=" + exInt1 + ", exFloat1="
				+ exFloat1 + ", exVar1=" + exVar1 + ", exDate1=" + exDate1 + ", costSegment=" + costSegment
				+ ", userName=" + userName + ", poNo=" + poNo + ", vehNo=" + vehNo + ", vehicleName=" + vehicleName
				+ ", plantName=" + plantName + ", companyId=" + companyId + ", compName=" + compName + ", custName="
				+ custName + ", custMobNo=" + custMobNo + ", projName=" + projName + ", projAddress=" + projAddress
				+ ", driverName=" + driverName + ", detailList=" + detailList + "]";
	}

}

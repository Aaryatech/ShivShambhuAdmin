package com.ats.ssgs.model.chalan;


import com.fasterxml.jackson.annotation.JsonFormat;

public class ChalanPrintItem {


	private int chalanDetailId;

	private int chalanId;

	private String chalanNo;
	private String chalanDate;
	
	private String custName;
	private String custMobNo;
	private String projName;
	private String address;


	private String itemName;
	private String itemCode;
	private String uom;

	private String usrName;
	private String vehNo;
	private String vehicleName;
	
	private String  devAddress;
	
	private String plantAddress1;

	private String batchNo;
	
	private float totalQuan;
	
	private String plantContactNo1;
	private String plantContactNo2;



	public String getDevAddress() {
		return devAddress;
	}

	public void setDevAddress(String devAddress) {
		this.devAddress = devAddress;
	}

	public String getPlantAddress1() {
		return plantAddress1;
	}

	public void setPlantAddress1(String plantAddress1) {
		this.plantAddress1 = plantAddress1;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public float getTotalQuan() {
		return totalQuan;
	}

	public void setTotalQuan(float totalQuan) {
		this.totalQuan = totalQuan;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
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

	public String getCustMobNo() {
		return custMobNo;
	}

	public void setCustMobNo(String custMobNo) {
		this.custMobNo = custMobNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	
	
	
	
	
	public String getPlantContactNo1() {
		return plantContactNo1;
	}

	public void setPlantContactNo1(String plantContactNo1) {
		this.plantContactNo1 = plantContactNo1;
	}

	public String getPlantContactNo2() {
		return plantContactNo2;
	}

	public void setPlantContactNo2(String plantContactNo2) {
		this.plantContactNo2 = plantContactNo2;
	}


	public String getChalanDate() {
		return chalanDate;
	}

	public void setChalanDate(String chalanDate) {
		this.chalanDate = chalanDate;
	}

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

	// detail
	private int itemId;

	private int itemUom;

	private float itemQty;

	private float itemLengthPlant;
	private float itemWidthPlant;
	private float itemHeightPlant;

	private float itemTotalPlant;

	private float itemLengthSite;
	private float itemWidthSite;
	private float itemHeightSite;

	private float itemTotalSite;

	private int status;
	private int delStatus;

	private int exInt1;
	private float exFloat1;
	private String exVar1;
	private String exVar2;
	private String exDate1;

	private int orderDetailId;

	public int getChalanDetailId() {
		return chalanDetailId;
	}

	public void setChalanDetailId(int chalanDetailId) {
		this.chalanDetailId = chalanDetailId;
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

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")

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

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemUom() {
		return itemUom;
	}

	public void setItemUom(int itemUom) {
		this.itemUom = itemUom;
	}

	public float getItemQty() {
		return itemQty;
	}

	public void setItemQty(float itemQty) {
		this.itemQty = itemQty;
	}

	public float getItemLengthPlant() {
		return itemLengthPlant;
	}

	public void setItemLengthPlant(float itemLengthPlant) {
		this.itemLengthPlant = itemLengthPlant;
	}

	public float getItemWidthPlant() {
		return itemWidthPlant;
	}

	public void setItemWidthPlant(float itemWidthPlant) {
		this.itemWidthPlant = itemWidthPlant;
	}

	public float getItemHeightPlant() {
		return itemHeightPlant;
	}

	public void setItemHeightPlant(float itemHeightPlant) {
		this.itemHeightPlant = itemHeightPlant;
	}

	public float getItemTotalPlant() {
		return itemTotalPlant;
	}

	public void setItemTotalPlant(float itemTotalPlant) {
		this.itemTotalPlant = itemTotalPlant;
	}

	public float getItemLengthSite() {
		return itemLengthSite;
	}

	public void setItemLengthSite(float itemLengthSite) {
		this.itemLengthSite = itemLengthSite;
	}

	public float getItemWidthSite() {
		return itemWidthSite;
	}

	public void setItemWidthSite(float itemWidthSite) {
		this.itemWidthSite = itemWidthSite;
	}

	public float getItemHeightSite() {
		return itemHeightSite;
	}

	public void setItemHeightSite(float itemHeightSite) {
		this.itemHeightSite = itemHeightSite;
	}

	public float getItemTotalSite() {
		return itemTotalSite;
	}

	public void setItemTotalSite(float itemTotalSite) {
		this.itemTotalSite = itemTotalSite;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	@Override
	public String toString() {
		return "ChalanPrintItem [chalanDetailId=" + chalanDetailId + ", chalanId=" + chalanId + ", chalanNo=" + chalanNo
				+ ", chalanDate=" + chalanDate + ", custName=" + custName + ", custMobNo=" + custMobNo + ", projName="
				+ projName + ", address=" + address + ", itemName=" + itemName + ", itemCode=" + itemCode + ", uom="
				+ uom + ", usrName=" + usrName + ", vehNo=" + vehNo + ", vehicleName=" + vehicleName + ", devAddress="
				+ devAddress + ", plantAddress1=" + plantAddress1 + ", batchNo=" + batchNo + ", totalQuan=" + totalQuan
				+ ", plantContactNo1=" + plantContactNo1 + ", plantContactNo2=" + plantContactNo2 + ", vehicleId="
				+ vehicleId + ", plantId=" + plantId + ", custId=" + custId + ", projId=" + projId + ", driverId="
				+ driverId + ", vehTimeOut=" + vehTimeOut + ", vehTimeIn=" + vehTimeIn + ", vehInDate=" + vehInDate
				+ ", outKm=" + outKm + ", inKm=" + inKm + ", sitePersonName=" + sitePersonName + ", sitePersonMob="
				+ sitePersonMob + ", orderId=" + orderId + ", orderNo=" + orderNo + ", chalanRemark=" + chalanRemark
				+ ", itemId=" + itemId + ", itemUom=" + itemUom + ", itemQty=" + itemQty + ", itemLengthPlant="
				+ itemLengthPlant + ", itemWidthPlant=" + itemWidthPlant + ", itemHeightPlant=" + itemHeightPlant
				+ ", itemTotalPlant=" + itemTotalPlant + ", itemLengthSite=" + itemLengthSite + ", itemWidthSite="
				+ itemWidthSite + ", itemHeightSite=" + itemHeightSite + ", itemTotalSite=" + itemTotalSite
				+ ", status=" + status + ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exFloat1=" + exFloat1
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exDate1=" + exDate1 + ", orderDetailId="
				+ orderDetailId + "]";
	}
	
	
	
	

}

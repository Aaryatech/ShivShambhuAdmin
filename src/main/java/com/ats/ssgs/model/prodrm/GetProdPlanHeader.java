package com.ats.ssgs.model.prodrm;

import java.util.List;

public class GetProdPlanHeader {
	
	private int productionHeaderId;

	private String productionDate;

	private String productionBatch;

	private int plantId;
	private int subPlantId;

	private int productionStatus;

	private int delStatus;

	private String productionStartDate;
	private String productionEndDate;

	private int userId;

	private int exInt1;
	private int exInt2;

	private String exVar1;
	private String exVar2;
	private String exVar3;
	
	//#
	private String plantName ;
	private String usrName;
	private String subplantName;
	
	
	List<GetProdPlanDetail> getProdPlanDetList;
	
	public List<GetProdPlanDetail> getGetProdPlanDetList() {
		return getProdPlanDetList;
	}
	public void setGetProdPlanDetList(List<GetProdPlanDetail> getProdPlanDetList) {
		this.getProdPlanDetList = getProdPlanDetList;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	public String getSubplantName() {
		return subplantName;
	}
	public void setSubplantName(String subplantName) {
		this.subplantName = subplantName;
	}
	public int getProductionHeaderId() {
		return productionHeaderId;
	}
	public void setProductionHeaderId(int productionHeaderId) {
		this.productionHeaderId = productionHeaderId;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public String getProductionBatch() {
		return productionBatch;
	}
	public void setProductionBatch(String productionBatch) {
		this.productionBatch = productionBatch;
	}
	public int getPlantId() {
		return plantId;
	}
	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}
	public int getSubPlantId() {
		return subPlantId;
	}
	public void setSubPlantId(int subPlantId) {
		this.subPlantId = subPlantId;
	}
	public int getProductionStatus() {
		return productionStatus;
	}
	public void setProductionStatus(int productionStatus) {
		this.productionStatus = productionStatus;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public String getProductionStartDate() {
		return productionStartDate;
	}
	public void setProductionStartDate(String productionStartDate) {
		this.productionStartDate = productionStartDate;
	}
	public String getProductionEndDate() {
		return productionEndDate;
	}
	public void setProductionEndDate(String productionEndDate) {
		this.productionEndDate = productionEndDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public String getExVar3() {
		return exVar3;
	}
	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}
	
	@Override
	public String toString() {
		return "GetProdPlanHeader [productionHeaderId=" + productionHeaderId + ", productionDate=" + productionDate
				+ ", productionBatch=" + productionBatch + ", plantId=" + plantId + ", subPlantId=" + subPlantId
				+ ", productionStatus=" + productionStatus + ", delStatus=" + delStatus + ", productionStartDate="
				+ productionStartDate + ", productionEndDate=" + productionEndDate + ", userId=" + userId + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3
				+ ", plantName=" + plantName + ", usrName=" + usrName + ", subplantName=" + subplantName
				+ ", getProdPlanDetList=" + getProdPlanDetList + "]";
	}
	
	
}

package com.ats.ssgs.model.prodrm;

import java.util.List;

public class ProdPlanHeader {

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

	private int int2;
	private int int3;

	private String varchar1;
	private String varchar2;
	private String varchar3;

	List<ProdPlanDetail> prodPlanDetailList;

	public List<ProdPlanDetail> getProdPlanDetailList() {
		return prodPlanDetailList;
	}

	public void setProdPlanDetailList(List<ProdPlanDetail> prodPlanDetailList) {
		this.prodPlanDetailList = prodPlanDetailList;
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

	public int getSubPlantId() {
		return subPlantId;
	}

	public void setSubPlantId(int subPlantId) {
		this.subPlantId = subPlantId;
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

	public int getInt2() {
		return int2;
	}

	public void setInt2(int int2) {
		this.int2 = int2;
	}

	public int getInt3() {
		return int3;
	}

	public void setInt3(int int3) {
		this.int3 = int3;
	}

	public String getVarchar1() {
		return varchar1;
	}

	public void setVarchar1(String varchar1) {
		this.varchar1 = varchar1;
	}

	public String getVarchar2() {
		return varchar2;
	}

	public void setVarchar2(String varchar2) {
		this.varchar2 = varchar2;
	}

	public String getVarchar3() {
		return varchar3;
	}

	public void setVarchar3(String varchar3) {
		this.varchar3 = varchar3;
	}

	@Override
	public String toString() {
		return "ProdPlanHeader [productionHeaderId=" + productionHeaderId + ", productionDate=" + productionDate
				+ ", productionBatch=" + productionBatch + ", plantId=" + plantId + ", subPlantId=" + subPlantId
				+ ", productionStatus=" + productionStatus + ", delStatus=" + delStatus + ", productionStartDate="
				+ productionStartDate + ", productionEndDate=" + productionEndDate + ", userId=" + userId + ", int2="
				+ int2 + ", int3=" + int3 + ", varchar1=" + varchar1 + ", varchar2=" + varchar2 + ", varchar3="
				+ varchar3 + ", prodPlanDetailList=" + prodPlanDetailList + "]";
	}

}

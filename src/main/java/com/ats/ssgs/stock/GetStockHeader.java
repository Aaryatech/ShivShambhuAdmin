package com.ats.ssgs.stock;

import java.util.List;

public class GetStockHeader {

	private int stockId;

	private int plantId;

	private int month;

	private String startDate;

	private int status;

	private int delStatus;

	private int userId;

	private String remark;

	private String closingDate;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;

	private String exDate1;

	private int exBool1;

	private String exFloat1;

	private int exFloat2;

	private String plantName;

	List<GetStockDetail> stockDetailList;

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public int getPlantId() {
		return plantId;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
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

	public String getExFloat1() {
		return exFloat1;
	}

	public void setExFloat1(String exFloat1) {
		this.exFloat1 = exFloat1;
	}

	public int getExFloat2() {
		return exFloat2;
	}

	public void setExFloat2(int exFloat2) {
		this.exFloat2 = exFloat2;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public List<GetStockDetail> getStockDetailList() {
		return stockDetailList;
	}

	public void setStockDetailList(List<GetStockDetail> stockDetailList) {
		this.stockDetailList = stockDetailList;
	}

	@Override
	public String toString() {
		return "GetStockHeader [stockId=" + stockId + ", plantId=" + plantId + ", month=" + month + ", startDate="
				+ startDate + ", status=" + status + ", delStatus=" + delStatus + ", userId=" + userId + ", remark="
				+ remark + ", closingDate=" + closingDate + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1="
				+ exVar1 + ", exVar2=" + exVar2 + ", exDate1=" + exDate1 + ", exBool1=" + exBool1 + ", exFloat1="
				+ exFloat1 + ", exFloat2=" + exFloat2 + ", plantName=" + plantName + ", stockDetailList="
				+ stockDetailList + "]";
	}

}

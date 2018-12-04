package com.ats.ssgs.model.mat;

import java.util.List;

public class GetMatIssueHeader {

	private int matHeaderId;

	private String issueNo;

	private int contrId;

	private String date;

	private int userId;

	private float total;

	private float qtyTotal;

	private int delStatus;

	private int exInt1;

	private int exInt2;

	private int exInt3;

	private String exVar1;

	private String exVar2;

	private String exVar3;

	private String exDate1;

	private String exDate2;

	private int exBool1;

	private int exBool2;

	private String contrName;

	private String contrMob;

	List<GetMatIssueDetail> matIssueDetailList;

	public int getMatHeaderId() {
		return matHeaderId;
	}

	public void setMatHeaderId(int matHeaderId) {
		this.matHeaderId = matHeaderId;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public int getContrId() {
		return contrId;
	}

	public void setContrId(int contrId) {
		this.contrId = contrId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getQtyTotal() {
		return qtyTotal;
	}

	public void setQtyTotal(float qtyTotal) {
		this.qtyTotal = qtyTotal;
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

	public String getExDate1() {
		return exDate1;
	}

	public void setExDate1(String exDate1) {
		this.exDate1 = exDate1;
	}

	public String getExDate2() {
		return exDate2;
	}

	public void setExDate2(String exDate2) {
		this.exDate2 = exDate2;
	}

	public int getExBool1() {
		return exBool1;
	}

	public void setExBool1(int exBool1) {
		this.exBool1 = exBool1;
	}

	public int getExBool2() {
		return exBool2;
	}

	public void setExBool2(int exBool2) {
		this.exBool2 = exBool2;
	}

	public String getContrName() {
		return contrName;
	}

	public void setContrName(String contrName) {
		this.contrName = contrName;
	}

	public String getContrMob() {
		return contrMob;
	}

	public void setContrMob(String contrMob) {
		this.contrMob = contrMob;
	}

	public List<GetMatIssueDetail> getMatIssueDetailList() {
		return matIssueDetailList;
	}

	public void setMatIssueDetailList(List<GetMatIssueDetail> matIssueDetailList) {
		this.matIssueDetailList = matIssueDetailList;
	}

	@Override
	public String toString() {
		return "GetMatIssueHeader [matHeaderId=" + matHeaderId + ", issueNo=" + issueNo + ", contrId=" + contrId
				+ ", date=" + date + ", userId=" + userId + ", total=" + total + ", qtyTotal=" + qtyTotal
				+ ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", exDate1=" + exDate1
				+ ", exDate2=" + exDate2 + ", exBool1=" + exBool1 + ", exBool2=" + exBool2 + ", contrName=" + contrName
				+ ", contrMob=" + contrMob + ", matIssueDetailList=" + matIssueDetailList + "]";
	}

}

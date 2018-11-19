package com.ats.ssgs.model.master;

public class GetBankDetail {
	private int bankDetId;

	private int companyId;

	private String bankName;

	private String bankIfsc;

	private String bankAddress;

	private int accType;

	private String accNo;

	private String timeStamp;

	private int delStatus;

	private int isUsed;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;

	private String compName;

	public int getBankDetId() {
		return bankDetId;
	}

	public void setBankDetId(int bankDetId) {
		this.bankDetId = bankDetId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankIfsc() {
		return bankIfsc;
	}

	public void setBankIfsc(String bankIfsc) {
		this.bankIfsc = bankIfsc;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public int getAccType() {
		return accType;
	}

	public void setAccType(int accType) {
		this.accType = accType;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
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

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	@Override
	public String toString() {
		return "GetBankDetail [bankDetId=" + bankDetId + ", companyId=" + companyId + ", bankName=" + bankName
				+ ", bankIfsc=" + bankIfsc + ", bankAddress=" + bankAddress + ", accType=" + accType + ", accNo="
				+ accNo + ", timeStamp=" + timeStamp + ", delStatus=" + delStatus + ", isUsed=" + isUsed + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", compName=" + compName
				+ "]";
	}

}

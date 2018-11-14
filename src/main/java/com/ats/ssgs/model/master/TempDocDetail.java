package com.ats.ssgs.model.master;

public class TempDocDetail {
	private int termDetailId;
	private String termDesc;

	private int sortNo;

	public int getTermDetailId() {
		return termDetailId;
	}

	public void setTermDetailId(int termDetailId) {
		this.termDetailId = termDetailId;
	}

	public String getTermDesc() {
		return termDesc;
	}

	public void setTermDesc(String termDesc) {
		this.termDesc = termDesc;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	@Override
	public String toString() {
		return "TempDocDetail [termDetailId=" + termDetailId + ", termDesc=" + termDesc + ", sortNo=" + sortNo + "]";
	}

}

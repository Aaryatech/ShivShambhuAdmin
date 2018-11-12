package com.ats.ssgs.model.enq;


public class EnqGenFact {

	
	private int enqGenId;

	private String enqGenBy;

	private String date;

	private int delStatus;

	public int getEnqGenId() {
		return enqGenId;
	}

	public void setEnqGenId(int enqGenId) {
		this.enqGenId = enqGenId;
	}

	public String getEnqGenBy() {
		return enqGenBy;
	}

	public void setEnqGenBy(String enqGenBy) {
		this.enqGenBy = enqGenBy;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "EnqGenFact [enqGenId=" + enqGenId + ", enqGenBy=" + enqGenBy + ", date=" + date + ", delStatus="
				+ delStatus + "]";
	}

}

package com.ats.ssgs.model;
 
import java.util.List;
  
public class GetPoHeader {
	 
	private int poId; 
	private String poNo; 
	private String poDate; 
	private int custId; 
	private String remark; 
	private int custProjectId; 
	private String poDocument; 
	private String poDocument1; 
	private int quatationId; 
	private String quatationNo; 
	private String poValidityDate; 
	private int poTermId; 
	private int delStatus; 
	private int plantId; 
	private int status; 
	private int extra1; 
	private int extra2; 
	private int bool1; 
	private int bool2; 
	private String varchar1; 
	private String varchar2; 
	private String extraDate1; 
	private String extraDate2; 
	private String custName; 
	private String plantName; 
	private String payTerm; 
	private String projName; 
	private String qutDate;
	
	List<GetPoDetail> getPoDetailList;
	List<GetPoDetail> poDetailList;
	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCustProjectId() {
		return custProjectId;
	}

	public void setCustProjectId(int custProjectId) {
		this.custProjectId = custProjectId;
	}

	public String getPoDocument() {
		return poDocument;
	}

	public void setPoDocument(String poDocument) {
		this.poDocument = poDocument;
	}

	public String getPoDocument1() {
		return poDocument1;
	}

	public void setPoDocument1(String poDocument1) {
		this.poDocument1 = poDocument1;
	}

	public int getQuatationId() {
		return quatationId;
	}

	public void setQuatationId(int quatationId) {
		this.quatationId = quatationId;
	}

	public String getQuatationNo() {
		return quatationNo;
	}

	public void setQuatationNo(String quatationNo) {
		this.quatationNo = quatationNo;
	}

	public String getPoValidityDate() {
		return poValidityDate;
	}

	public void setPoValidityDate(String poValidityDate) {
		this.poValidityDate = poValidityDate;
	}

	public int getPoTermId() {
		return poTermId;
	}

	public void setPoTermId(int poTermId) {
		this.poTermId = poTermId;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getPlantId() {
		return plantId;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getExtra1() {
		return extra1;
	}

	public void setExtra1(int extra1) {
		this.extra1 = extra1;
	}

	public int getExtra2() {
		return extra2;
	}

	public void setExtra2(int extra2) {
		this.extra2 = extra2;
	}

	public int getBool1() {
		return bool1;
	}

	public void setBool1(int bool1) {
		this.bool1 = bool1;
	}

	public int getBool2() {
		return bool2;
	}

	public void setBool2(int bool2) {
		this.bool2 = bool2;
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

	public String getExtraDate1() {
		return extraDate1;
	}

	public void setExtraDate1(String extraDate1) {
		this.extraDate1 = extraDate1;
	}

	public String getExtraDate2() {
		return extraDate2;
	}

	public void setExtraDate2(String extraDate2) {
		this.extraDate2 = extraDate2;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getPayTerm() {
		return payTerm;
	}

	public void setPayTerm(String payTerm) {
		this.payTerm = payTerm;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public List<GetPoDetail> getGetPoDetailList() {
		return getPoDetailList;
	}

	public void setGetPoDetailList(List<GetPoDetail> getPoDetailList) {
		this.getPoDetailList = getPoDetailList;
	}

	public String getQutDate() {
		return qutDate;
	}

	public void setQutDate(String qutDate) {
		this.qutDate = qutDate;
	}

	public List<GetPoDetail> getPoDetailList() {
		return poDetailList;
	}

	public void setPoDetailList(List<GetPoDetail> poDetailList) {
		this.poDetailList = poDetailList;
	}

	@Override
	public String toString() {
		return "GetPoHeader [poId=" + poId + ", poNo=" + poNo + ", poDate=" + poDate + ", custId=" + custId
				+ ", remark=" + remark + ", custProjectId=" + custProjectId + ", poDocument=" + poDocument
				+ ", poDocument1=" + poDocument1 + ", quatationId=" + quatationId + ", quatationNo=" + quatationNo
				+ ", poValidityDate=" + poValidityDate + ", poTermId=" + poTermId + ", delStatus=" + delStatus
				+ ", plantId=" + plantId + ", status=" + status + ", extra1=" + extra1 + ", extra2=" + extra2
				+ ", bool1=" + bool1 + ", bool2=" + bool2 + ", varchar1=" + varchar1 + ", varchar2=" + varchar2
				+ ", extraDate1=" + extraDate1 + ", extraDate2=" + extraDate2 + ", custName=" + custName
				+ ", plantName=" + plantName + ", payTerm=" + payTerm + ", projName=" + projName + ", qutDate="
				+ qutDate + ", getPoDetailList=" + getPoDetailList + ", poDetailList=" + poDetailList + "]";
	}
	
	

}

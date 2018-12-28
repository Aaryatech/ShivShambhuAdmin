package com.ats.ssgs.model.quot;

import java.util.List;

import com.ats.ssgs.model.master.BankDetail;
import com.ats.ssgs.model.master.Company;
import com.ats.ssgs.model.master.Cust;
import com.ats.ssgs.model.master.DocTermDetail;
import com.ats.ssgs.model.master.PaymentTerm;
import com.ats.ssgs.model.master.Project;



public class QuotPrintData {
	
	Company comp;
	
	Cust cust;
	
	List<GetQuotDetailPrint> quotDetPrint;
	
	List<DocTermDetail> docTermList;
	
	PaymentTerm payTerm;
	
	Project proj;
	
	BankDetail bank;
	
	

	public Company getComp() {
		return comp;
	}

	public void setComp(Company comp) {
		this.comp = comp;
	}

	public Cust getCust() {
		return cust;
	}

	public void setCust(Cust cust) {
		this.cust = cust;
	}

	public List<GetQuotDetailPrint> getQuotDetPrint() {
		return quotDetPrint;
	}

	public void setQuotDetPrint(List<GetQuotDetailPrint> quotDetPrint) {
		this.quotDetPrint = quotDetPrint;
	}

	public List<DocTermDetail> getDocTermList() {
		return docTermList;
	}

	public void setDocTermList(List<DocTermDetail> docTermList) {
		this.docTermList = docTermList;
	}

	public PaymentTerm getPayTerm() {
		return payTerm;
	}

	public void setPayTerm(PaymentTerm payTerm) {
		this.payTerm = payTerm;
	}

	public Project getProj() {
		return proj;
	}

	public void setProj(Project proj) {
		this.proj = proj;
	}

	public BankDetail getBank() {
		return bank;
	}

	public void setBank(BankDetail bank) {
		this.bank = bank;
	}

	@Override
	public String toString() {
		return "QuotPrintData [comp=" + comp + ", cust=" + cust + ", quotDetPrint=" + quotDetPrint + ", docTermList="
				+ docTermList + ", payTerm=" + payTerm + ", proj=" + proj + ", bank=" + bank + "]";
	}

}

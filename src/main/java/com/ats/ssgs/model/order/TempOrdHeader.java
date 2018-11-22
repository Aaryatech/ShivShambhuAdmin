package com.ats.ssgs.model.order;

import java.util.List;

public class TempOrdHeader {
	
	
	private float taxableValue;
	
	private float taxValue;
	
	private float otherCostAfterTax;
	
	private float orderTotal;
	
	List<TempOrdDetail> ordDetailList;

	public float getTaxableValue() {
		return taxableValue;
	}

	public void setTaxableValue(float taxableValue) {
		this.taxableValue = taxableValue;
	}

	public float getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(float taxValue) {
		this.taxValue = taxValue;
	}

	public float getOtherCostAfterTax() {
		return otherCostAfterTax;
	}

	public void setOtherCostAfterTax(float otherCostAfterTax) {
		this.otherCostAfterTax = otherCostAfterTax;
	}

	public float getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}

	public List<TempOrdDetail> getOrdDetailList() {
		return ordDetailList;
	}

	public void setOrdDetailList(List<TempOrdDetail> ordDetailList) {
		this.ordDetailList = ordDetailList;
	}

	@Override
	public String toString() {
		return "TempOrdHeader [taxableValue=" + taxableValue + ", taxValue=" + taxValue + ", otherCostAfterTax="
				+ otherCostAfterTax + ", orderTotal=" + orderTotal + ", ordDetailList=" + ordDetailList + "]";
	}
	

}

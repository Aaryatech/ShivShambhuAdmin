package com.ats.ssgs.model.mat;

public class MatIssueDetail {

	private int matDetailId;

	private int matHeaderId;

	private float quantity;

	private float itemRate;

	private float value;

	private int uomId;

	private int delStatus;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;

	private String exDate1;

	private int exBool1;

	public int getMatDetailId() {
		return matDetailId;
	}

	public void setMatDetailId(int matDetailId) {
		this.matDetailId = matDetailId;
	}

	public int getMatHeaderId() {
		return matHeaderId;
	}

	public void setMatHeaderId(int matHeaderId) {
		this.matHeaderId = matHeaderId;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getItemRate() {
		return itemRate;
	}

	public void setItemRate(float itemRate) {
		this.itemRate = itemRate;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public int getUomId() {
		return uomId;
	}

	public void setUomId(int uomId) {
		this.uomId = uomId;
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

	@Override
	public String toString() {
		return "MatIssueDetail [matDetailId=" + matDetailId + ", matHeaderId=" + matHeaderId + ", quantity=" + quantity
				+ ", itemRate=" + itemRate + ", value=" + value + ", uomId=" + uomId + ", delStatus=" + delStatus
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", exDate1=" + exDate1 + ", exBool1=" + exBool1 + "]";
	}
}

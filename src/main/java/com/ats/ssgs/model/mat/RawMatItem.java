package com.ats.ssgs.model.mat;

public class RawMatItem {

	private int rmId;

	private int catId;

	private String rmCode;

	private String rmName;

	private int uomId;

	private int taxId;

	private int itemLife;

	private int isCritical;

	private int itemScheduleDays;

	private String location;

	private float minLevel;

	private float maxLevel;

	private float reorderLevel;

	private float itemRate;

	private int delStatus;

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;

	private String exDate1;

	private int exBool1;

	public int getRmId() {
		return rmId;
	}

	public void setRmId(int rmId) {
		this.rmId = rmId;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getRmCode() {
		return rmCode;
	}

	public void setRmCode(String rmCode) {
		this.rmCode = rmCode;
	}

	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	public int getUomId() {
		return uomId;
	}

	public void setUomId(int uomId) {
		this.uomId = uomId;
	}

	public int getTaxId() {
		return taxId;
	}

	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}

	public int getItemLife() {
		return itemLife;
	}

	public void setItemLife(int itemLife) {
		this.itemLife = itemLife;
	}

	public int getIsCritical() {
		return isCritical;
	}

	public void setIsCritical(int isCritical) {
		this.isCritical = isCritical;
	}

	public int getItemScheduleDays() {
		return itemScheduleDays;
	}

	public void setItemScheduleDays(int itemScheduleDays) {
		this.itemScheduleDays = itemScheduleDays;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(float minLevel) {
		this.minLevel = minLevel;
	}

	public float getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(float maxLevel) {
		this.maxLevel = maxLevel;
	}

	public float getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(float reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public float getItemRate() {
		return itemRate;
	}

	public void setItemRate(float itemRate) {
		this.itemRate = itemRate;
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
		return "RawMatItem [rmId=" + rmId + ", catId=" + catId + ", rmCode=" + rmCode + ", rmName=" + rmName
				+ ", uomId=" + uomId + ", taxId=" + taxId + ", itemLife=" + itemLife + ", isCritical=" + isCritical
				+ ", itemScheduleDays=" + itemScheduleDays + ", location=" + location + ", minLevel=" + minLevel
				+ ", maxLevel=" + maxLevel + ", reorderLevel=" + reorderLevel + ", itemRate=" + itemRate
				+ ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exDate1=" + exDate1 + ", exBool1=" + exBool1 + "]";
	}

}

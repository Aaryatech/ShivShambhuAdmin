package com.ats.ssgs.model;


public class GetItemsForBill {

    private int chalanDetailId;
	
	private int chalanId;
	
	private String chalanNo;
	
	private String chalanDate;
	
	private int itemId;
	
	private String itemCode;
	
	private String itemName;
	
	private String itemUom;
	
	private int uomId;
	
	private String hsnCode;
	
	private float itemQty;
	
	private float orderRate;
	
	private int orderId;
	
	private float cgstPer;
	
	private float sgstPer;
	
	private float igstPer;
	
	private int poTermId;
	
	private String deliveryTerm;
	
	private int isTaxIncluding;
	
	

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getIsTaxIncluding() {
		return isTaxIncluding;
	}

	public void setIsTaxIncluding(int isTaxIncluding) {
		this.isTaxIncluding = isTaxIncluding;
	}

	public int getChalanDetailId() {
		return chalanDetailId;
	}

	public void setChalanDetailId(int chalanDetailId) {
		this.chalanDetailId = chalanDetailId;
	}

	public int getChalanId() {
		return chalanId;
	}

	public void setChalanId(int chalanId) {
		this.chalanId = chalanId;
	}

	public String getChalanNo() {
		return chalanNo;
	}

	public void setChalanNo(String chalanNo) {
		this.chalanNo = chalanNo;
	}

	public String getChalanDate() {
		return chalanDate;
	}

	public void setChalanDate(String chalanDate) {
		this.chalanDate = chalanDate;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemUom() {
		return itemUom;
	}

	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
	}

	public int getUomId() {
		return uomId;
	}

	public void setUomId(int uomId) {
		this.uomId = uomId;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public float getItemQty() {
		return itemQty;
	}

	public void setItemQty(float itemQty) {
		this.itemQty = itemQty;
	}

	public float getOrderRate() {
		return orderRate;
	}

	public void setOrderRate(float orderRate) {
		this.orderRate = orderRate;
	}

	public float getCgstPer() {
		return cgstPer;
	}

	public void setCgstPer(float cgstPer) {
		this.cgstPer = cgstPer;
	}

	public float getSgstPer() {
		return sgstPer;
	}

	public void setSgstPer(float sgstPer) {
		this.sgstPer = sgstPer;
	}

	public float getIgstPer() {
		return igstPer;
	}

	public void setIgstPer(float igstPer) {
		this.igstPer = igstPer;
	}

	public int getPoTermId() {
		return poTermId;
	}

	public void setPoTermId(int poTermId) {
		this.poTermId = poTermId;
	}

	public String getDeliveryTerm() {
		return deliveryTerm;
	}

	public void setDeliveryTerm(String deliveryTerm) {
		this.deliveryTerm = deliveryTerm;
	}

	@Override
	public String toString() {
		return "GetItemsForBill [chalanDetailId=" + chalanDetailId + ", chalanId=" + chalanId + ", chalanNo=" + chalanNo
				+ ", chalanDate=" + chalanDate + ", itemId=" + itemId + ", itemCode=" + itemCode + ", itemName="
				+ itemName + ", itemUom=" + itemUom + ", uomId=" + uomId + ", hsnCode=" + hsnCode + ", itemQty="
				+ itemQty + ", orderRate=" + orderRate + ", orderId=" + orderId + ", cgstPer=" + cgstPer + ", sgstPer="
				+ sgstPer + ", igstPer=" + igstPer + ", poTermId=" + poTermId + ", deliveryTerm=" + deliveryTerm
				+ ", isTaxIncluding=" + isTaxIncluding + "]";
	}
      

}

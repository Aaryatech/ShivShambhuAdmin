package com.ats.ssgs.model.mat;

public class PoklenReport {

	private int vehicleId;

	private String vehNo;
	private String vehicleName;

	private float totalConsumption;

	private String totalLoadingHr;

	private String totalBreakingHr;

	private float totalQtyLoad;

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehNo() {
		return vehNo;
	}

	public void setVehNo(String vehNo) {
		this.vehNo = vehNo;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public float getTotalConsumption() {
		return totalConsumption;
	}

	public void setTotalConsumption(float totalConsumption) {
		this.totalConsumption = totalConsumption;
	}

	public String getTotalLoadingHr() {
		return totalLoadingHr;
	}

	public void setTotalLoadingHr(String totalLoadingHr) {
		this.totalLoadingHr = totalLoadingHr;
	}

	public String getTotalBreakingHr() {
		return totalBreakingHr;
	}

	public void setTotalBreakingHr(String totalBreakingHr) {
		this.totalBreakingHr = totalBreakingHr;
	}

	public float getTotalQtyLoad() {
		return totalQtyLoad;
	}

	public void setTotalQtyLoad(float totalQtyLoad) {
		this.totalQtyLoad = totalQtyLoad;
	}

	@Override
	public String toString() {
		return "PoklenReport [vehicleId=" + vehicleId + ", vehNo=" + vehNo + ", vehicleName=" + vehicleName
				+ ", totalConsumption=" + totalConsumption + ", totalLoadingHr=" + totalLoadingHr + ", totalBreakingHr="
				+ totalBreakingHr + ", totalQtyLoad=" + totalQtyLoad + "]";
	}

}

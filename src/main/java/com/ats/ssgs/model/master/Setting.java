package com.ats.ssgs.model.master;


public class Setting {
	
	
	private int settingId;

	private int settingKey;
	
	private String settingValue;
	
	private int delStatus;

	public int getSettingId() {
		return settingId;
	}

	public void setSettingId(int settingId) {
		this.settingId = settingId;
	}

	public int getSettingKey() {
		return settingKey;
	}

	public void setSettingKey(int settingKey) {
		this.settingKey = settingKey;
	}

	public String getSettingValue() {
		return settingValue;
	}

	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "Setting [settingId=" + settingId + ", settingKey=" + settingKey + ", settingValue=" + settingValue
				+ ", delStatus=" + delStatus + "]";
	}

}

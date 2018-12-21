package com.ats.ssgs.model;

import java.util.List;

import com.ats.ssgs.model.master.Dept;
import com.ats.ssgs.model.master.Info;

public class DepartmentList {

	List<Dept> departmentList;

	Info info;

	public List<Dept> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Dept> departmentList) {
		this.departmentList = departmentList;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "DepartMentList [departmentList=" + departmentList + ", info=" + info + "]";
	}

}

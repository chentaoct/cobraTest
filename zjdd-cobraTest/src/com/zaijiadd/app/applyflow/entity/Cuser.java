package com.zaijiadd.app.applyflow.entity;

import java.util.List;

public class Cuser {

	private Integer userid;

	private String name;
	private List<Corder> corderList;

	public List<Corder> getCorderList() {
		return corderList;
	}

	public void setCorderList(List<Corder> corderList) {
		this.corderList = corderList;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

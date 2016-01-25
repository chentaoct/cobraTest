package com.zaijiadd.app.applyflow.entity.sys;

import java.io.Serializable;

public class VOAnnouncement extends Announcement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3881750451330972912L;
	
	private Integer alreadyLook;

	public Integer getAlreadyLook() {
		return alreadyLook;
	}

	public void setAlreadyLook(Integer alreadyLook) {
		this.alreadyLook = alreadyLook;
	}
}

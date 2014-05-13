package com.galaksiya.social.entity;

public class Music {
	private String ID;
	private String name;
	private String date;

	public Music(String iD, String name, String date) {
		this.ID = iD;
		this.name = name;
		this.date = date;
	}

	public String getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return this.date;
	}

}

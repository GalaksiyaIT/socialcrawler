package com.galaksiya.social.entity;

/**
 * Group class holds information about id,name,bookmarkOrder, and version data
 * of Facebook User
 * 
 * @author Burak YÖNYÜL
 * 
 */
public class Group {

	private String ID;
	private String name;

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	public Group() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public Group(String iD, String name) {
		super();
		ID = iD;
		this.name = name;
	}

	public Group(String name) {
		super();
		this.name = name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}

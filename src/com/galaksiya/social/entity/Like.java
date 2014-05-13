package com.galaksiya.social.entity;

/**
 * Like class holds information about likes of a Facebook User
 * (id,category,name,createdTime data)
 * 
 * @author Burak YÖNYÜL
 * 
 */
public class Like {

	/**
	 * id of Like
	 */
	private String id;
	/**
	 * category of Like
	 */
	private String category;
	/**
	 * name of Like
	 */
	private String name;

	/**
	 * crated date of like
	 */
	private String date;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Constructor of Like
	 * 
	 * @param id
	 * @param category
	 * @param name
	 * @param date
	 * @param createdTime
	 */
	public Like(String id, String category, String name, String date) {
		super();
		this.id = id;
		this.category = category;
		this.name = name;
		this.date = date;
	}

	public String toString() {
		String stringTo = "Category : " + category + "	<--> Name : " + name;
		return stringTo;

	}

	public String getDate() {
		return this.date;
	}

}

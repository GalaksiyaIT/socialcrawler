package com.galaksiya.social.entity;

/**
 * Venue information holder class for Facebook
 * 
 * @author etmen
 * 
 */
public class FacebookVenue {

	private String id;
	private String street;
	private String city;
	private String state;
	private String country;
	private String zip;
	private Double latitude;
	private Double longitude;

	public FacebookVenue() {
		super();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZip() {
		return zip;
	}

}

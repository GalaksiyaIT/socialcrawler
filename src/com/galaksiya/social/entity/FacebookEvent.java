package com.galaksiya.social.entity;

import java.util.Date;

import com.restfb.types.Event.Owner;
import com.restfb.types.FacebookType.Metadata;

/**
 * Event information holder for Facebook
 * 
 * @author etmen
 * 
 */
public class FacebookEvent {

	private String id;
	private String name;
	private Owner owner;
	private String description;
	private Date startTime;
	private Date endTime;
	private String location;
	private String rsvpStatus;
	private FacebookVenue venue;
	private String privacy;
	private Date updatedTime;
	private String type;
	private Metadata metadata;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public FacebookEvent() {
		super();
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRsvpStatus() {
		return rsvpStatus;
	}

	public void setRsvpStatus(String rsvpStatus) {
		this.rsvpStatus = rsvpStatus;
	}

	public FacebookVenue getVenue() {
		return venue;
	}

	public void setVenue(FacebookVenue venue) {
		this.venue = venue;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}

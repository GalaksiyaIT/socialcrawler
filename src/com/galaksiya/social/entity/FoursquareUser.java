package com.galaksiya.social.entity;

import java.util.ArrayList;

import fi.foyt.foursquare.api.entities.Checkin;
import fi.foyt.foursquare.api.entities.CompactUser;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteTip;
import fi.foyt.foursquare.api.entities.CompleteUser;
import fi.foyt.foursquare.api.entities.Todo;

public class FoursquareUser {

	private CompleteUser user;

	private String id;

	private String accessToken;

	private ArrayList<Checkin> checkins;

	private ArrayList<CompactUser> friends;

	private ArrayList<CompactVenue> mayorships;

	private ArrayList<CompleteTip> tips;

	private ArrayList<Todo> todos;

	public FoursquareUser(CompleteUser user, String id, String accessToken) {
		super();
		this.user = user;
		this.id = id;
		this.accessToken = accessToken;
	}

	public FoursquareUser(CompleteUser user, String accessToken) {
		this(user, user.getId(), accessToken);
	}

	public FoursquareUser(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public void setUser(CompleteUser user) {
		this.user = user;
	}

	public CompleteUser getUser() {
		return user;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FoursquareUser() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setCheckins(ArrayList<Checkin> checkins) {
		this.checkins = checkins;
	}

	public ArrayList<Checkin> getCheckins() {
		return checkins;
	}

	public ArrayList<CompactUser> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<CompactUser> friends) {
		this.friends = friends;
	}

	public ArrayList<CompactVenue> getMayorships() {
		return mayorships;
	}

	public void setMayorships(ArrayList<CompactVenue> mayorships) {
		this.mayorships = mayorships;
	}

	public ArrayList<CompleteTip> getTips() {
		return tips;
	}

	public void setTips(ArrayList<CompleteTip> tips) {
		this.tips = tips;
	}

	public ArrayList<Todo> getTodos() {
		return todos;
	}

	public void setTodos(ArrayList<Todo> todos) {
		this.todos = todos;
	}

	/**
	 * Returns the social network identifier of the user.
	 * 
	 * @return social network identifier.
	 */
	public String getSocialNetworkId() {
		return getUser().getId();
	}
}

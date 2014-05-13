package com.galaksiya.social.entity;

import java.text.MessageFormat;

import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.schema.Person;

/**
 * Entity class that holds {@link LinkedInApiClient}, {@link Person}
 * informations and some variables {@literal String} id, {@link String} token
 * value, and {@link String} token secret value
 * 
 * @author etmen
 * 
 */
public class LinkedInPerson {

	private LinkedInApiClient client;

	private String id;

	private Person person;

	private String tokenSecret;

	private String tokenValue;

	/**
	 * Creates a linkedin person which uses LinkedIn Social Network ID as
	 * resource identifier.
	 * 
	 * @param client
	 *            Linkedin API client.
	 * @param person
	 *            API person to retrieve its properties from LinkedIn.
	 */
	public LinkedInPerson(LinkedInApiClient client, Person person) {
		this(client, person, person.getId());
	}

	/**
	 * Creates a person whose resource identifier is given.
	 * 
	 * @param client
	 *            LinkedIn API client.
	 * @param person
	 *            API person.
	 * @param id
	 *            specific identifier for the person resource.
	 */
	public LinkedInPerson(LinkedInApiClient client, Person person, String id) {
		super();
		this.client = client;
		this.person = person;
		this.tokenValue = client.getAccessToken().getToken();
		this.tokenSecret = client.getAccessToken().getTokenSecret();
		this.id = id;
	}

	public LinkedInApiClient getClient() {
		return client;
	}

	public String getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setClient(LinkedInApiClient client) {
		this.client = client;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	@Override
	public String toString() {
		return MessageFormat.format("LinkedInPerson[id={0},name={1}]", getId(),
				getName());
	}

	/**
	 * Constructs full name of the linked in person.
	 * 
	 * @return full name which is constructed from the first and the last names.
	 */
	private String getName() {
		if (getPerson() != null) {
			return getPerson().getFirstName() + " " + getPerson().getLastName();
		}
		return null;
	}

	/**
	 * Returns the social network identifier of the user.
	 * 
	 * @return social network identifier.
	 */
	public String getSocialNetworkId() {
		return getPerson().getId();
	}
}

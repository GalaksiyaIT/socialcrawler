package com.galaksiya.social.crawler;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import com.galaksiya.social.entity.LinkedInPerson;
import com.galaksiya.social.exception.LinkedinAuthenticationException;
import com.galaksiya.social.fetcher.LinkedinIndividualCreator;
import com.galaksiya.social.fetcher.SocialAPIException;
import com.galaksiya.social.utility.SocialNetworkUtil;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientException;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.schema.Person;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * This crawler is used to retrieve data of a Facebook user whose access token
 * is given.
 * 
 * @author galaksiya
 * 
 */
public class LinkedinUserCrawler {

	// private static Logger logger = Logger
	// .getLogger(FoursquareUserCrawler.class);

	public static final ProfileField[] linkedinProfileFields = {
			ProfileField.FIRST_NAME,
			ProfileField.LAST_NAME,
			ProfileField.ID,
			ProfileField.LANGUAGES,
			ProfileField.INDUSTRY,
			ProfileField.INTERESTS,
			ProfileField.LOCATION,
			// ProfileField.LOCATION_NAME, ProfileField.LOCATION_COUNTRY,
			ProfileField.MAIN_ADDRESS, ProfileField.POSITIONS,
			ProfileField.EDUCATIONS, ProfileField.SKILLS,
			ProfileField.HEADLINE, ProfileField.SPECIALTIES,
			ProfileField.SUMMARY, ProfileField.CURRENT_STATUS_TIMESTAMP,
			ProfileField.DISTANCE, ProfileField.NUM_CONNECTIONS,
			ProfileField.NUM_RECOMMENDERS,
			ProfileField.RECOMMENDATIONS_RECEIVED,
			ProfileField.RELATION_TO_VIEWER, ProfileField.CONNECTIONS,
			ProfileField.PHONE_NUMBERS, ProfileField.HONORS,
			ProfileField.DATE_OF_BIRTH, ProfileField.PICTURE_URL };

	/**
	 * Enumeration of data of user to be fetched from linkedIn
	 */
	private EnumSet<ProfileField> LINKEDIN_PROFILE_INFO_SET = EnumSet
			.of(ProfileField.PUBLIC_PROFILE_URL);

	private Resource indv;

	private LinkedinIndividualCreator indvCreator;

	private String resourceId;

	private LinkedInPerson user;

	private HashMap<String, ProfileField> profileFieldMap;

	public LinkedinUserCrawler(String baseURI) {
		this(baseURI, null);
	}

	public LinkedinUserCrawler(String baseURI, String resourceId) {
		this(baseURI, resourceId, null);
	}

	public LinkedinUserCrawler(String baseURI, String resourceId,
			String permissions) {
		this.resourceId = resourceId;
		this.indvCreator = new LinkedinIndividualCreator(baseURI);
		// construct profile info set
		constructProfileInfoSet(SocialNetworkUtil
				.generatePermissionList(permissions));
		// for (ProfileField field : LINKEDIN_PROFILE_INFO_SET) {
		// System.out.println(field.fieldName());
		// }
	}

	/**
	 * This method constructs profile info set by filling using required
	 * permissionList.
	 * 
	 * @param permissionList
	 */
	private void constructProfileInfoSet(List<String> permissionList) {
		// check if permission list is empty
		if (permissionList.isEmpty()) {
			// add all profile fields
			for (ProfileField field : linkedinProfileFields) {
				LINKEDIN_PROFILE_INFO_SET.add(field);
			}
		} else {
			// construct field map to get required field quickly
			constructFieldMap();
			// get required permission from map and add it to the permission set
			for (String permission : permissionList) {
				ProfileField field = profileFieldMap.get(permission);
				LINKEDIN_PROFILE_INFO_SET.add(field);
			}
		}
	}

	private void constructFieldMap() {
		profileFieldMap = new HashMap<String, ProfileField>();
		for (ProfileField profileField : linkedinProfileFields) {
			profileFieldMap.put(profileField.fieldName(), profileField);
		}
	}

	public Model crawl(String apiKey, String apiSecret, String token,
			String tokenSecret, Date tokenExpTime) throws Exception {
		readData(getUser(apiKey, apiSecret, token, tokenSecret, tokenExpTime));
		return indvCreator.getModel();
	}

	public LinkedInPerson getUser(String apiKey, String apiSecret,
			String token, String tokenSecret, Date tokenExpTime)
			throws LinkedinAuthenticationException {
		try {
			// create access token...
			LinkedInAccessToken newAT = new LinkedInAccessToken(token,
					tokenSecret);
			newAT.setExpirationTime(tokenExpTime);
			// create new LinkedIn Client
			LinkedInApiClientFactory factory = LinkedInApiClientFactory
					.newInstance(apiKey, apiSecret);
			LinkedInApiClient client = factory.createLinkedInApiClient(newAT);
			// create LinkedIn person after authenticating
			Person person = client
					.getProfileForCurrentUser(LINKEDIN_PROFILE_INFO_SET);
			// create LinkedIn person
			if (getResourceId() == null) {
				user = new LinkedInPerson(client, person);
				this.resourceId = person.getId();
			} else {
				user = new LinkedInPerson(client, person, getResourceId());
			}
		} catch (IllegalArgumentException iae) {
			throw new LinkedinAuthenticationException(iae.getMessage(),
					iae.getCause());
		} catch (LinkedInApiClientException lace) {
			throw new LinkedinAuthenticationException(lace.getMessage(),
					lace.getCause());
		}
		return user;
	}

	public Resource readData(LinkedInPerson person) throws SocialAPIException {
		indv = indvCreator.createPersonIndividual(person);
		return indv;
	}

	private String getResourceId() {
		return resourceId;
	}
}

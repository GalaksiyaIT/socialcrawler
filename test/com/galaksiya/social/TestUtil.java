package com.galaksiya.social;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.galaksiya.social.entity.FacebookUser;
import com.galaksiya.social.entity.FoursquareUser;
import com.galaksiya.social.entity.LinkedInPerson;
import com.galaksiya.social.fetcher.FacebookDataFetcher;
import com.galaksiya.social.fetcher.FoursquareDataFetcher;
import com.galaksiya.social.vocabulary.FoursquareTestVocabulary;
import com.galaksiya.social.vocabulary.LinkedinTestVocabulary;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.schema.Person;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class TestUtil {

	/**
	 * creates a Foursquare user
	 * 
	 * @return
	 * @throws Exception
	 */
	public static FoursquareUser createFoursquareUser() throws Exception {
		FoursquareUser foursquareUser = new FoursquareUser(
				FoursquareTestVocabulary.ACCESS_TOKEN_COPIED);
		FoursquareDataFetcher foursquareRetriever = new FoursquareDataFetcher();
		foursquareRetriever.complete(foursquareUser);
		return foursquareUser;
	}

	/**
	 * This class creates an FacebookUser object with given access token
	 * 
	 * @param accessToken
	 *            accessToken of user to be created
	 * @return
	 */
	public static FacebookUser createFacebookUser(String accessToken) {
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		User user = facebookClient.fetchObject("me", User.class);

		FacebookUser facebookUser = new FacebookUser(facebookClient, user,
				accessToken);
		FacebookDataFetcher dataFetcher = new FacebookDataFetcher(facebookUser);
		dataFetcher.complete();
		facebookUser.setFriendList(retrieveFriends(facebookUser));
		assertNotNull(facebookUser);
		return facebookUser;
	}

	/**
	 * It retrieves the friends of facebookUser
	 * 
	 * @return
	 */
	private static ArrayList<User> retrieveFriends(FacebookUser facebookUser) {

		ArrayList<User> friends = new ArrayList<User>();
		try {

			FacebookClient client = facebookUser.getClient();

			// fetch friends from facebook
			Connection<User> myFriends = client.fetchConnection("me/friends",
					User.class);
			List<User> friList = myFriends.getData();
			Iterator<User> iterator = friList.iterator();

			// iterate on friend list
			while (iterator.hasNext()) {
				User friend = iterator.next();
				friends.add(friend);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friends;
	}

	/**
	 * creates a sample Linkedin person
	 * 
	 * @return
	 */
	public static LinkedInPerson createLinkedinPersonZiya() {
		LinkedInAccessToken accessToken = new LinkedInAccessToken(
				LinkedinTestVocabulary.TOKEN_VALUE_FOR_ZIYA,
				LinkedinTestVocabulary.TOKEN_SECRET_FOR_ZIYA);
		LinkedInApiClientFactory apiClientFactory = LinkedInApiClientFactory
				.newInstance(TestUtil.LINKEDIN_API_KEY,
						TestUtil.LINKEDIN_SECRET_KEY);
		LinkedInApiClient client = apiClientFactory
				.createLinkedInApiClient(accessToken);
		Person person = client
				.getProfileForCurrentUser(TestUtil.LINKEDIN_PROFILE_INFO_SET);
		LinkedInPerson linkedInPerson = new LinkedInPerson(client, person);
		return linkedInPerson;
	}

	/**
	 * creates a sample Linkedin person
	 * 
	 * @return
	 */
	public static LinkedInPerson createLinkedinPersonBurak() {
		LinkedInAccessToken accessToken = new LinkedInAccessToken(
				LinkedinTestVocabulary.TOKEN_VALUE_FOR_BURAK,
				LinkedinTestVocabulary.TOKEN_SECRET_FOR_BURAK);
		LinkedInApiClientFactory apiClientFactory = LinkedInApiClientFactory
				.newInstance(TestUtil.LINKEDIN_API_KEY,
						TestUtil.LINKEDIN_SECRET_KEY);
		LinkedInApiClient client = apiClientFactory
				.createLinkedInApiClient(accessToken);
		Person person = client
				.getProfileForCurrentUser(TestUtil.LINKEDIN_PROFILE_INFO_SET);
		LinkedInPerson linkedInPerson = new LinkedInPerson(client, person);
		return linkedInPerson;
	}

	/**
	 * creates a sample Linkedin person
	 * 
	 * @return
	 */
	public static LinkedInPerson createLinkedinPersonGeorge() {
		LinkedInAccessToken accessToken = new LinkedInAccessToken(
				LinkedinTestVocabulary.TOKEN_VALUE_FOR_GEORGE,
				LinkedinTestVocabulary.TOKEN_SECRET_FOR_GEORGE);
		LinkedInApiClientFactory apiClientFactory = LinkedInApiClientFactory
				.newInstance(TestUtil.LINKEDIN_CRAWLER_API_KEY,
						TestUtil.LINKEDIN_CRAWLER_SECRET_KEY);
		LinkedInApiClient client = apiClientFactory
				.createLinkedInApiClient(accessToken);
		Person person = client
				.getProfileForCurrentUser(TestUtil.LINKEDIN_PROFILE_INFO_SET);
		LinkedInPerson linkedInPerson = new LinkedInPerson(client, person);
		return linkedInPerson;
	}

	/**
	 * This method checks whether given object value contained in given list
	 * 
	 * @param message
	 * @param valueList
	 * @param value
	 */
	public static <T> void assertContains(String message, List<T> valueList,
			T value) {
		boolean contains = valueList.contains(value);
		if (!contains)
			fail(message + ":" + value + " not found in "
					+ valueList.toString());
	}

	public static final String LINKEDIN_API_KEY = "dnwb8zje4bb3";
	public static final String LINKEDIN_SECRET_KEY = "tSsac2CbLfEGT6gn";
	public static final String LINKEDIN_CRAWLER_API_KEY = "77t7wsd19hl6pz";
	public static final String LINKEDIN_CRAWLER_SECRET_KEY = "ab0tu96RBQBoB4z4";
	public static final Set<ProfileField> LINKEDIN_PROFILE_INFO_SET = EnumSet
			.of(ProfileField.FIRST_NAME, ProfileField.LAST_NAME,
					ProfileField.ID, ProfileField.LANGUAGES,
					ProfileField.INDUSTRY, ProfileField.INTERESTS,
					ProfileField.LOCATION, ProfileField.LOCATION_NAME,
					ProfileField.LOCATION_COUNTRY, ProfileField.MAIN_ADDRESS,
					ProfileField.POSITIONS, ProfileField.SKILLS,
					ProfileField.HEADLINE, ProfileField.SPECIALTIES,
					ProfileField.SUMMARY,
					ProfileField.CURRENT_STATUS_TIMESTAMP,
					ProfileField.DISTANCE, ProfileField.NUM_CONNECTIONS,
					ProfileField.NUM_RECOMMENDERS,
					ProfileField.RECOMMENDATIONS_RECEIVED,
					ProfileField.RELATION_TO_VIEWER, ProfileField.CONNECTIONS,
					ProfileField.PHONE_NUMBERS, ProfileField.HONORS,
					ProfileField.DATE_OF_BIRTH, ProfileField.PICTURE_URL,
					ProfileField.PUBLIC_PROFILE_URL);

}

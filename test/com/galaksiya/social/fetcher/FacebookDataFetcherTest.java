package com.galaksiya.social.fetcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.galaksiya.social.entity.FacebookUser;
import com.galaksiya.social.entity.Group;
import com.galaksiya.social.entity.Interest;
import com.galaksiya.social.entity.Like;
import com.galaksiya.social.vocabulary.FacebookTestVocabulary;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.User;
import com.restfb.types.User.Education;
import com.restfb.types.User.Work;

public class FacebookDataFetcherTest {

	/**
	 * {@link FacebookUser} which contains Facebook information of user
	 */
	private FacebookUser facebookUser;

	/**
	 * {@link FacebookDataFetcher} which will contact with Facebook and fetch
	 * user data from facebook
	 */
	private FacebookDataFetcher fetcher;

	/**
	 * create facebook user and fetcher
	 */
	@Before
	public void before() {
		// initialize facebook user
		initializeUser();
		// create data fetcher with no permission restriction
		fetcher = new FacebookDataFetcher(facebookUser);
	}

	/**
	 * This test checks that it is fetched all user information if no permission
	 * list is given, and if a permission list given it will be fetched only
	 * these granted properties.
	 * 
	 * @throws Exception
	 */
	@Test
	public void fetchGivenPermissionsOnly() throws Exception {

		// first complete user without any permission restriction
		fetcher.complete();
		assertFalse(facebookUser.getEducations().isEmpty());

		assertFalse(facebookUser.getLikes().isEmpty());

		assertFalse(facebookUser.getWorks().isEmpty());

		assertFalse(facebookUser.getGroups().isEmpty());

		// initialize Facebook user again
		initializeUser();
		// create data fetcher with specific permissions
		fetcher = new FacebookDataFetcher(facebookUser,
				FacebookTestVocabulary.LACK_PERMISSONS);
		fetcher.complete();
		// complete user with specific permissions
		assertTrue(facebookUser.getEducations().isEmpty());

		assertTrue(facebookUser.getLikes().isEmpty());

		assertTrue(facebookUser.getWorks().isEmpty());

		assertFalse(facebookUser.getGroups().isEmpty());

	}

	/**
	 * This test checks fetcher completes all user information if it is not
	 * granted any permission to it.
	 * 
	 * @throws Exception
	 */
	@Test
	public void completeUser() throws Exception {
		// check that Facebook user has no data before fetching operation
		assertNull(facebookUser.getLikes());

		assertNull(facebookUser.getEducations());

		assertNull(facebookUser.getWorks());

		assertNull(facebookUser.getEvents());

		assertNull(facebookUser.getGroups());

		assertNull(facebookUser.getInterests());

		// complete...
		fetcher.complete();

		// check Facebook user data has been retrieved after completing
		// operation

		assertLikes(facebookUser.getLikes());

		assertEducations(facebookUser.getEducations());

		assertWorks(facebookUser.getWorks());

		assertInterests(facebookUser.getInterests());

		checkIfListIsEmpty(facebookUser.getEvents());

		assertUserGroups(facebookUser.getGroups());

	}

	private void assertInterests(List<NamedFacebookType> interests) {
		checkIfListIsNotEmpty(interests);
		for (NamedFacebookType interest : interests) {
			assertNotNull(interest);
			assertNotNull(interest.getId());
			assertNotNull(interest.getName());
		}
	}

	/**
	 * Check education properties
	 * 
	 * @param educations
	 */
	private void assertEducations(ArrayList<Education> educations) {
		checkIfListIsNotEmpty(educations);
		for (Education education : educations) {
			assertNotNull(education);
			assertNotNull(education.getWith());
			assertNotNull(education.getType());
			assertNotNull(education.getSchool());
			assertNotNull(education.getConcentration());
			assertNotNull(education.getClasses());
		}
	}

	/**
	 * Check like properties
	 * 
	 * @param likes
	 */
	private void assertLikes(ArrayList<Like> likes) {
		checkIfListIsNotEmpty(likes);
		for (Like like : likes) {
			assertNotNull(like);
			assertNotNull(like.getId());
			assertNotNull(like.getName());
			assertNotNull(like.getCategory());
			assertNotNull(like.getDate());
		}
	}

	/**
	 * Check user group properties
	 * 
	 * @param groups
	 */
	private void assertUserGroups(ArrayList<Group> groups) {
		checkIfListIsNotEmpty(groups);
		for (Group group : groups) {
			assertNotNull(group);
			assertNotNull(group.getID());
			assertNotNull(group.getName());
		}
	}

	/**
	 * Check work properties
	 * 
	 * @param works
	 */
	private void assertWorks(ArrayList<Work> works) {
		checkIfListIsNotEmpty(works);
		for (Work work : works) {
			assertNotNull(work);
			assertNotNull(work.getEmployer());
			assertNotNull(work.getStartDate());
			assertNotNull(work.getPosition());
		}
	}

	/**
	 * Checks that a given list is empty
	 * 
	 * @param list
	 */
	private <T> void checkIfListIsEmpty(List<T> list) {
		assertNotNull(list);
		assertTrue(list.isEmpty());
	}

	/**
	 * Checks that a given list is not empty
	 * 
	 * @param list
	 */
	private <T> void checkIfListIsNotEmpty(List<T> list) {
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}

	/**
	 * initialize Facebook user
	 */
	private void initializeUser() {
		FacebookClient facebookClient = new DefaultFacebookClient(
				FacebookTestVocabulary.ACCESS_TOKEN_CONTAINS_ALL_INFORMATION);
		facebookUser = new FacebookUser(facebookClient,
				facebookClient.fetchObject("me", User.class),
				FacebookTestVocabulary.ACCESS_TOKEN_CONTAINS_ALL_INFORMATION);
	}
}

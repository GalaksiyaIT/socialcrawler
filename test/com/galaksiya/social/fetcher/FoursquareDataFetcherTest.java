package com.galaksiya.social.fetcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.galaksiya.social.entity.FoursquareUser;
import com.galaksiya.social.exception.FoursquareAuthenticationException;
import com.galaksiya.social.vocabulary.FoursquareTestVocabulary;

import fi.foyt.foursquare.api.entities.Checkin;
import fi.foyt.foursquare.api.entities.CompactUser;
import fi.foyt.foursquare.api.entities.CompleteTip;

public class FoursquareDataFetcherTest {
	/**
	 * {@link FoursquareDataFetcher} which will contact with Foursquare and
	 * fetch user data from Foursquare
	 */
	private FoursquareDataFetcher fetcher;

	/**
	 * {@link FoursquareUser} which contains Foursquare information of user
	 */
	private FoursquareUser foursquareUser;

	/**
	 * create Foursquare user and fetcher
	 * 
	 * @throws FoursquareAuthenticationException
	 */
	@Before
	public void beforeClass() throws Exception {
		// initialize user
		initializeUser();
		// create data fetcher with no permission restriction
		fetcher = new FoursquareDataFetcher();
	}

	private void initializeUser() {
		// initialize foursquare user
		foursquareUser = new FoursquareUser(
				FoursquareTestVocabulary.ACCESS_TOKEN_COPIED);
	}

	/**
	 * This test checks fetcher completes all user information if it is not
	 * granted any permission to it.
	 * 
	 * @throws Exception
	 */
	@Test
	public void completeUser() throws Exception {
		// check that Foursquare user has no data before fetching operation
		assertNull(foursquareUser.getUser());
		assertNull(foursquareUser.getFriends());
		assertNull(foursquareUser.getCheckins());
		assertNull(foursquareUser.getTips());
		// Todos and mayorships will not be fetched because of restrictions on
		// fetcher
		assertNull(foursquareUser.getTodos());
		assertNull(foursquareUser.getMayorships());

		// fetch all Foursquare information of fetcher
		fetcher.complete(foursquareUser);

		// check that Foursquare user completed after fetching operation
		assertNotNull(foursquareUser.getUser());
		checkFriends(foursquareUser.getFriends());
		checkCheckins(foursquareUser.getCheckins());
		checkTips(foursquareUser.getTips());
		// Todos and mayorships was not fetched.
		assertNull(foursquareUser.getTodos());
		assertNull(foursquareUser.getMayorships());
	}

	/**
	 * Check friends properties
	 * 
	 * @param friends
	 * @throws Exception
	 */
	private void checkFriends(List<CompactUser> friends) throws Exception {
		checkIfListIsNotEmpty(friends);
		for (CompactUser friend : friends) {
			assertNotNull(friend);
			assertNotNull(friend.getFirstName());
			assertNotNull(friend.getLastName());
			assertNotNull(friend.getGender());
			assertNotNull(friend.getId());
			assertNotNull(friend.getHomeCity());
			assertNotNull(friend.getPhoto());
			assertNotNull(friend.getRelationship());
		}
	}

	/**
	 * Check checkin properties
	 * 
	 * @param checkins
	 * @throws Exception
	 */
	private void checkCheckins(List<Checkin> checkins) throws Exception {
		checkIfListIsNotEmpty(checkins);
		for (Checkin checkin : checkins) {
			assertNotNull(checkin);
			assertNotNull(checkin.getId());
			assertNotNull(checkin.getVenue());
		}
	}

	/**
	 * Check tips properties
	 * 
	 * @param tips
	 * @throws Exception
	 */
	private void checkTips(List<CompleteTip> tips) throws Exception {
		checkIfListIsNotEmpty(tips);
		for (CompleteTip tip : tips) {
			assertNotNull(tip);
			assertNotNull(tip.getId());
			assertNotNull(tip.getCreatedAt());
			assertNotNull(tip.getText());
			assertNotNull(tip.getVenue());
		}
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
		fetcher.complete(foursquareUser);
		assertNotNull(foursquareUser.getUser());
		assertNotNull(foursquareUser.getFriends());
		assertNotNull(foursquareUser.getCheckins());
		assertNotNull(foursquareUser.getTips());

		// initialize Foursquare user again
		initializeUser();

		// create a fetcher that has restricted fetching permissions
		fetcher = new FoursquareDataFetcher(
				FoursquareTestVocabulary.LACK_PERMISSONS);

		// complete user with restricted permissions
		fetcher.complete(foursquareUser);

		// check user is fetched
		assertNotNull(foursquareUser.getUser());
		// check that friends anc checkins are not fetched because permission is
		// not given
		checkIfListIsEmpty(foursquareUser.getFriends());
		checkIfListIsEmpty(foursquareUser.getCheckins());
		// check tips are fetched because the permissions is given
		checkTips(foursquareUser.getTips());

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
	 * Checks that a given list is empty
	 * 
	 * @param list
	 */
	private <T> void checkIfListIsEmpty(List<T> list) {
		assertNotNull(list);
		assertTrue(list.isEmpty());
	}

}

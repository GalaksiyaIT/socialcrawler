package com.galaksiya.social.entity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.User;
import com.restfb.types.User.Education;
import com.restfb.types.User.Work;

/**
 * FacebookUser class holds information about the facebook user and its data
 */
public class FacebookUser {

	/**
	 * Access token of the client
	 */
	private String accessToken;
	/**
	 * FacebookClient that uses the application
	 */
	private FacebookClient client;
	/**
	 * Educations of the facebook client
	 */
	private ArrayList<Education> educations;
	/**
	 * events that facebook user will join
	 */
	private ArrayList<FacebookEvent> events;
	private ArrayList<FamilyBond> familyBonds;

	private List<NamedFacebookType> interests;

	public List<NamedFacebookType> getInterests() {
		return interests;
	}

	public void setInterests(List<NamedFacebookType> interests) {
		this.interests = interests;
	}

	/**
	 * Friend list of the client
	 */
	private ArrayList<User> friendList;

	/**
	 * Likes of the facebook client
	 */
	private ArrayList<Group> groups;

	private String id;

	/**
	 * Likes of the facebook client
	 */
	private ArrayList<Like> likes;

	/**
	 * Movies of the facebook client
	 */
	private ArrayList<Movie> movies;

	/**
	 * Musics of the facebook client
	 */
	private ArrayList<Music> musics;

	private List<JsonObject> friendProfiles;

	/**
	 * Facebook user of the client above
	 */
	private User user;

	/**
	 * Works of the facebook client
	 */
	private ArrayList<Work> works;
	public static final String GET_PROFILE_QUERY = "SELECT url,pic FROM profile WHERE id = me()";

	public static final String FRIEND_PROFILES_FQL_QUERY = "SELECT id, pic, name, url FROM profile WHERE id IN (SELECT uid2 FROM friend where uid1=me())";

	public FacebookUser() {
		super();
	}

	public FacebookUser(FacebookClient client, User user, String accessToken) {
		this(client, user, accessToken, user.getId());
	}

	/**
	 * @param client
	 * @param user
	 * @param accessToken
	 * @param id
	 *            individual identifier if it is desired to save the user with
	 *            an identifier other than his/her social network identifier.
	 */
	public FacebookUser(FacebookClient client, User user, String accessToken,
			String id) {
		super();
		this.client = client;
		this.user = user;
		this.accessToken = accessToken;
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public FacebookClient getClient() {
		return client;
	}

	public ArrayList<Education> getEducations() {
		return educations;
	}

	public ArrayList<FacebookEvent> getEvents() {
		return events;
	}

	public ArrayList<FamilyBond> getFamilyBonds() {
		return familyBonds;
	}

	public ArrayList<User> getFriendList() {
		return friendList;
	}

	/**
	 * Retrieves profile information of friends with an FQL query.
	 * 
	 * @return list of profile JSON objects if there are friends or, an empty
	 *         list if there is no friend profile.
	 */
	public List<JsonObject> getFriendProfiles() {
		// try to get friend profiles vie FQL query
		if (friendProfiles == null) {
			friendProfiles = getClient().executeFqlQuery(
					FacebookUser.FRIEND_PROFILES_FQL_QUERY, JsonObject.class);
			// it may be return null after executing FQL query, be sure to
			// prevent that the list is null
			if (friendProfiles == null) {
				friendProfiles = new ArrayList<JsonObject>();
			}
		}
		return friendProfiles;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	/**
	 * Returns the individual identifier of the facebook user.
	 * 
	 * @return individual identifier in the social linker.
	 */
	public String getId() {
		return id;
	}

	public ArrayList<Like> getLikes() {
		return likes;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public ArrayList<Music> getMusics() {
		return musics;
	}

	/**
	 * Returns the social network identifier of the facebook user.
	 * 
	 * @return social network identifier.
	 */
	public String getSocialNetworkId() {
		return getUser().getId();
	}

	public User getUser() {
		return user;
	}

	public ArrayList<Work> getWorks() {
		return works;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setClient(FacebookClient client) {
		this.client = client;
	}

	public void setEducations(ArrayList<Education> educations) {
		this.educations = educations;
	}

	public void setEvents(ArrayList<FacebookEvent> events) {
		this.events = events;
	}

	public void setFamilyBonds(ArrayList<FamilyBond> familyBonds) {
		this.familyBonds = familyBonds;
	}

	public void setFriendList(ArrayList<User> friendList) {
		this.friendList = friendList;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLikes(ArrayList<Like> likes) {
		this.likes = likes;
	}

	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}

	public void setMusics(ArrayList<Music> musics) {
		this.musics = musics;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setWorks(ArrayList<Work> works) {
		this.works = works;
	}

	@Override
	public String toString() {
		// get username...
		String userName = "nullUser";
		if (user != null) {
			userName = user.getName();
		}
		return MessageFormat.format(
				"FacebookUser[id={0},name={1},accessToken={2}]", getId(),
				userName, getAccessToken());
	}

	public List<JsonObject> getProfileInfo() {
		return getClient().executeFqlQuery(GET_PROFILE_QUERY, JsonObject.class);
	}
}

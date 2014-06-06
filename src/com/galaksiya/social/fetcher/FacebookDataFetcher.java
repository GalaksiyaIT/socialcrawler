package com.galaksiya.social.fetcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.galaksiya.social.entity.FacebookEvent;
import com.galaksiya.social.entity.FacebookUser;
import com.galaksiya.social.entity.FacebookVenue;
import com.galaksiya.social.entity.FamilyBond;
import com.galaksiya.social.entity.Group;
import com.galaksiya.social.entity.Interest;
import com.galaksiya.social.entity.Like;
import com.galaksiya.social.entity.Movie;
import com.galaksiya.social.entity.Music;
import com.galaksiya.social.utility.SocialNetworkUtil;
import com.restfb.Connection;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;
import com.restfb.types.Event;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.User.Education;
import com.restfb.types.User.Work;

/**
 * DataFetcher class fetches the data of facebook user: --> friends, likes,
 * groups, feeds, movies, musics, books, photos, albums, videos, events
 */
public class FacebookDataFetcher {

	public static final String EDUCATION_FQL_QUERY = "SELECT education FROM user WHERE uid=me()";

	public static final String INTEREST_FQL_QUERY = "SELECT user_interests FROM user WHERE uid=me()";

	public static final String FAMILY_GET_QUERY = "SELECT uid,profile_id, name ,relationship, birthday FROM family WHERE profile_id = ";

	public static final String WORK_FQL_QUERY = "SELECT work FROM user WHERE uid=me()";

	// public static final String FRIENDS_GET_QUERY="SELECT "

	/**
	 * Facebook individual whose information to be fetched.
	 */
	private FacebookUser facebookUser;

	private Logger logger = Logger.getLogger(FacebookDataFetcher.class);

	private String permissions;

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	private List<String> permissonList = new ArrayList<String>();

	public List<String> getPermissonList() {
		return permissonList;
	}

	public FacebookDataFetcher(FacebookUser user) {
		this.facebookUser = user;
	}

	public FacebookDataFetcher(FacebookUser user, String permissons) {
		this(user);
		this.permissions = permissons;
		this.permissonList = SocialNetworkUtil
				.generatePermissionList(permissions);
	}

	/**
	 * Fetches all information of facebook user.
	 */
	public void complete() {
		if (facebookUser != null) {
			// facebookUser.setFriendList(retrieveFriends());
			facebookUser.setLikes(retrieveLikes());
			facebookUser.setGroups(retrieveGroups());
			// facebookUser.setMovies(retrieveMovies());
			// facebookUser.setMusics(retrieveMusics());
			facebookUser.setInterests(retrieveInterests());
			facebookUser.setEvents(retrieveEvents());
			facebookUser.setWorks(retrieveWorks());
			facebookUser.setEducations(retrieveEducations());
			// facebookUser.setFamilyBonds(retrievefamilyBonds());
		}
	}

	private List<NamedFacebookType> retrieveInterests() {

		List<NamedFacebookType> interests = new ArrayList<NamedFacebookType>();
		if (!SocialNetworkUtil.isRetrievable(permissonList,
				FacebookPermissions.user_education_history.name())) {
			return interests;
		}
		Connection<NamedFacebookType> interestsConn = facebookUser.getClient()
				.fetchConnection("me/interests", NamedFacebookType.class);
		interests = interestsConn.getData();
		return interests;
	}

	/**
	 * fetching related venue object from facebook using id holder
	 * {@link JsonObject} instance
	 * 
	 * @param idHolderObject
	 * @return
	 */
	private JsonObject fetchJsonObjectVenue(JsonObject idHolderObject) {
		try {

			if (idHolderObject != null) {
				// getting id of venue
				String venueId = idHolderObject.getString("id");
				if (venueId != null) {
					// fetching related venue from facebook
					return facebookUser.getClient().fetchObject(venueId,
							JsonObject.class);
				}
			}
		} catch (Exception e) {
			logger.error(
					"Data could not be fetched. Operation will continue without this data.",
					e);
		}
		return null;
	}

	/**
	 * This method fetches objects from Facebook with given paremeters, and
	 * fills given list with fetched objects.
	 * 
	 * @param objectList
	 *            object list to be fetched.
	 * @param fqlQuery
	 *            fetching query
	 * @param jsonParameter
	 *            parameter required to get {@link JsonObject}s after fetching
	 *            operation
	 * @param javaObjClass
	 *            java class that {@link JsonObject} instance will be turned
	 *            into
	 */
	@SuppressWarnings("unchecked")
	private void fetchObject(@SuppressWarnings("rawtypes") List objectList,
			String fqlQuery, String jsonParameter, Class<?> javaObjClass) {
		// define json mapper
		DefaultJsonMapper jsonMapper = new DefaultJsonMapper();
		try {
			// get facebook client
			FacebookClient client = facebookUser.getClient();
			// exevute fql query tor etrieve objects
			List<JsonObject> jsonObjList = client.executeFqlQuery(fqlQuery,
					JsonObject.class);
			// iterate on returned results
			for (JsonObject jsonObjParent : jsonObjList) {
				// get json array of retrieved result with parameter value
				JsonArray jsonArrayObj = jsonObjParent
						.getJsonArray(jsonParameter);
				// iterate on array to map Json objects into given java class
				// type
				for (int i = 0; i < jsonArrayObj.length(); i++) {
					JsonObject json = jsonArrayObj.getJsonObject(i);
					objectList.add(jsonMapper.toJavaObject(json.toString(),
							javaObjClass));
				}
			}
			logger.info(jsonParameter + " ojects has been generated");
		} catch (Exception e) {
			logger.error(jsonParameter + " objects cannot be retrieved");
			e.printStackTrace();
		}
	}

	/**
	 * generating facebook event object using {@link Event} object
	 * 
	 * @param eventData
	 * @return
	 */
	private FacebookEvent generateFacebookEvent(Event eventData) {
		FacebookEvent fbEvent = new FacebookEvent();
		// setting FacebookEvent object values using Event object value
		fbEvent.setId(eventData.getId());
		fbEvent.setName(eventData.getName());
		fbEvent.setOwner(eventData.getOwner());
		fbEvent.setStartTime(eventData.getStartTime());
		fbEvent.setEndTime(eventData.getEndTime());
		fbEvent.setDescription(eventData.getDescription());
		fbEvent.setLocation(eventData.getLocation());
		fbEvent.setPrivacy(eventData.getPrivacy());
		fbEvent.setRsvpStatus(eventData.getRsvpStatus());
		fbEvent.setUpdatedTime(eventData.getUpdatedTime());
		fbEvent.setType(eventData.getType());
		fbEvent.setMetadata(eventData.getMetadata());
		fbEvent.setVenue(generateFacebookVenue(eventData));

		return fbEvent;
	}

	/**
	 * generating venue value of an {@link Event} object
	 * 
	 * @param eventData
	 * @return
	 */
	private FacebookVenue generateFacebookVenue(Event eventData) {
		// getting venue value of event
		List<JsonObject> jsonVenues = facebookUser.getClient().executeFqlQuery(
				"select venue from event where eid=" + eventData.getId(),
				JsonObject.class);
		if (jsonVenues != null) {
			// getting related json object that holds venue information
			JsonObject jsonVenue = jsonVenues.get(0);
			// getting related json object holding id of venue information
			JsonObject venueIdHolderObject = jsonVenue.getJsonObject("venue");
			// fetching json object that holds actual information about venue
			JsonObject fetchedJsonVenue = fetchJsonObjectVenue(venueIdHolderObject);
			// parsing actual json object
			return parseJsonVenue(fetchedJsonVenue);
		}
		return null;
	}

	/**
	 * getting location value of venue with a given key parameter
	 * 
	 * @param jsonVenueLocation
	 * @param key
	 * @return
	 */
	private Object getLocationValue(JsonObject jsonVenueLocation, String key) {
		try {
			return jsonVenueLocation.get(key);
		} catch (JsonException e) {
			logger.warn("Exception message : " + e.getMessage()
					+ "Exception cause : " + e.getCause());
		}
		return null;
	}

	/**
	 * parsing values contained in fetchedJsonObject
	 * 
	 * @param fetchedJsonVenue
	 * @return
	 */
	private FacebookVenue parseJsonVenue(JsonObject fetchedJsonVenue) {
		if (fetchedJsonVenue != null) {
			// parsing related venue data of fetchedJsonObject
			String id = fetchedJsonVenue.getString("id");
			JsonObject jsonVenueLocation = fetchedJsonVenue
					.getJsonObject("location");
			String city = (String) getLocationValue(jsonVenueLocation, "city");
			String state = (String) getLocationValue(jsonVenueLocation, "state");
			String country = (String) getLocationValue(jsonVenueLocation,
					"country");
			String street = (String) getLocationValue(jsonVenueLocation,
					"street");
			String zip = (String) getLocationValue(jsonVenueLocation, "zip");
			Double latitude = (Double) getLocationValue(jsonVenueLocation,
					"latitude");
			Double longitude = (Double) getLocationValue(jsonVenueLocation,
					"longitude");

			// setting fields of Facebook Venue instance with parsed data
			FacebookVenue fbVenue = new FacebookVenue();
			fbVenue.setId(id);
			fbVenue.setCity(city);
			fbVenue.setState(state);
			fbVenue.setCountry(country);
			fbVenue.setStreet(street);
			fbVenue.setZip(zip);
			fbVenue.setLatitude(latitude);
			fbVenue.setLongitude(longitude);

			return fbVenue;
		}
		return null;
	}

	private ArrayList<Education> retrieveEducations() {
		ArrayList<Education> educationList = new ArrayList<Education>();
		if (!SocialNetworkUtil.isRetrievable(permissonList,
				FacebookPermissions.user_education_history.name())) {
			return educationList;
		}
		fetchObject(educationList, EDUCATION_FQL_QUERY, "education",
				Education.class);
		return educationList;
	}

	/**
	 * fetching user events from Facebook
	 * 
	 * @return
	 */
	private ArrayList<FacebookEvent> retrieveEvents() {

		ArrayList<FacebookEvent> fbEvents = new ArrayList<FacebookEvent>();
		if (!SocialNetworkUtil.isRetrievable(permissonList,
				FacebookPermissions.user_events.name())) {
			return fbEvents;
		}
		// fetching events that user will join
		Connection<Event> eventConnections = facebookUser.getClient()
				.fetchConnection("me/events", Event.class);

		if (eventConnections != null) {
			// getting data (events) containing connection object
			List<Event> eventsData = eventConnections.getData();
			for (Event eventData : eventsData) {
				// generating facebook event objects of user
				FacebookEvent fbEvent = generateFacebookEvent(eventData);
				fbEvents.add(fbEvent);
			}
		}

		return fbEvents;
	}

	@SuppressWarnings("unused")
	private ArrayList<FamilyBond> retrievefamilyBonds() {
		ArrayList<FamilyBond> familyBonds = new ArrayList<FamilyBond>();
		try {
			FacebookClient client = facebookUser.getClient();
			// fetch family bonds from Facebook as JsonObject format
			List<JsonObject> jsonFamilyBondList = client
					.executeFqlQuery(FacebookDataFetcher.FAMILY_GET_QUERY
							+ facebookUser.getId(), JsonObject.class);
			Iterator<JsonObject> iterator = jsonFamilyBondList.iterator();

			// generate family bond list
			while (iterator.hasNext()) {
				JsonObject jsonFamilyBond = (JsonObject) iterator.next();
				String userId = jsonFamilyBond.getString("uid");
				String profileId = jsonFamilyBond.getString("profile_id");
				String name = jsonFamilyBond.getString("name");
				String birthday = jsonFamilyBond.getString("birthday");
				String relationship = jsonFamilyBond.getString("relationship");
				FamilyBond familyBond = new FamilyBond(profileId, userId, name,
						birthday, relationship);
				familyBonds.add(familyBond);
			}

		} catch (Exception e) {
			logger.error("Family bonds cannot be retrieved");
			e.printStackTrace();
		}
		return familyBonds;

	}

	/**
	 * this method retrieves groups of Facebook user
	 * 
	 * @return
	 */
	private ArrayList<Group> retrieveGroups() {

		ArrayList<Group> myGroups = new ArrayList<Group>();
		if (!SocialNetworkUtil.isRetrievable(permissonList,
				FacebookPermissions.user_groups.name())) {
			return myGroups;
		}
		try {
			FacebookClient client = facebookUser.getClient();

			// fetch groups from Facebook as JsonObject format
			JsonObject jsonGroups = client.fetchObject("me/groups",
					JsonObject.class);

			// parse json object
			JsonArray groups = jsonGroups.getJsonArray("data");
			int groupSize = groups.length();

			// generate group list
			for (int i = 0; i < groupSize; i++) {
				JsonObject groupObject = groups.getJsonObject(i);
				String id = groupObject.getString("id");
				String name = groupObject.getString("name");
				Group group = new Group(id, name);
				myGroups.add(group);
			}
			logger.info("groups have been generated");
		} catch (Exception e) {
			logger.error("groups cannot be retrieved");
			e.printStackTrace();
		}
		return myGroups;
	}

	/**
	 * this method retrieves likes of facebook user
	 * 
	 * @return
	 */
	private ArrayList<Like> retrieveLikes() {

		ArrayList<Like> myLikes = new ArrayList<Like>();
		if (!SocialNetworkUtil.isRetrievable(permissonList,
				FacebookPermissions.user_likes.name())) {
			return myLikes;
		}
		try {

			FacebookClient client = facebookUser.getClient();

			// fetch likes from facebook as JsonObject format
			JsonObject jsonLikes = client.fetchObject("me/likes",
					JsonObject.class);

			// parse json object
			JsonArray likes = jsonLikes.getJsonArray("data");

			int likeSize = likes.length();

			// generate like list
			for (int i = 0; i < likeSize; i++) {
				JsonObject likeObject = likes.getJsonObject(i);
				String id = likeObject.getString("id");
				String category = likeObject.getString("category");
				String name = likeObject.getString("name");
				String date = likeObject.getString("created_time");
				Like like = new Like(id, category, name, date);
				myLikes.add(like);
			}
			logger.info("likes have been generated");
		} catch (Exception e) {
			logger.error("likes cannot be retrieved");
			e.printStackTrace();
		}
		return myLikes;
	}

	/**
	 * this method retrieves movies of facebook user
	 * 
	 * @return
	 */
	private ArrayList<Movie> retrieveMovies() {

		ArrayList<Movie> movies = new ArrayList<Movie>();
		if (!SocialNetworkUtil.isRetrievable(permissonList,
				FacebookPermissions.user_likes.name())) {
			return movies;
		}
		try {

			// fetch movies from facebook as JsonObject format
			JsonObject info = facebookUser.getClient().fetchObject("me/movies",
					JsonObject.class);

			// parse json object
			JsonArray movieArray = info.getJsonArray("data");
			logger.info("Movies are beginning to fetch from facebook");
			int length = movieArray.length();

			// generate movie list
			for (int i = 0; i < length; i++) {
				JsonObject movieObject = movieArray.getJsonObject(i);
				logger.info("Movie jsonObject is beginning to fetch : "
						+ movieObject.toString());
				String id = movieObject.getString("id");
				String name = movieObject.getString("name");
				String date = movieObject.getString("created_time");
				Movie movie = new Movie(id, name, date);
				movies.add(movie);
				logger.info("Life movie object has been created : "
						+ movie.getName());
			}
			logger.info("Movies have been fetched from facebook");
		} catch (Exception e) {
			logger.error("Movies cannot be retrieved");
			e.printStackTrace();
		}
		return movies;
	}

	private ArrayList<Music> retrieveMusics() {
		ArrayList<Music> musics = new ArrayList<Music>();
		if (!SocialNetworkUtil.isRetrievable(permissonList,
				FacebookPermissions.user_likes.name())) {
			return musics;
		}
		try {
			// fetch musics from facebook as JsonObject format
			JsonObject info = facebookUser.getClient().fetchObject("me/music",
					JsonObject.class);

			// parse json object
			JsonArray musicArray = info.getJsonArray("data");
			logger.info("Musics are beginning to fetch from facebook");
			int length = musicArray.length();

			// generate music list
			for (int i = 0; i < length; i++) {
				JsonObject musicObject = musicArray.getJsonObject(i);
				logger.info("Music jsonObject is beginning to fetch : "
						+ musicObject.toString());
				String id = musicObject.getString("id");
				String name = musicObject.getString("name");
				String date = musicObject.getString("created_time");
				Music music = new Music(id, name, date);
				musics.add(music);
				logger.info("Life music object has been created : "
						+ music.getName());
			}
			logger.info("Musics have been fetched from facebook");
		} catch (Exception e) {
			logger.error("Musics cannot be retrieved");
			e.printStackTrace();
		}
		return musics;
	}

	private ArrayList<Work> retrieveWorks() {
		ArrayList<Work> workList = new ArrayList<Work>();
		if (!SocialNetworkUtil.isRetrievable(permissonList,
				FacebookPermissions.user_work_history.name())) {
			return workList;
		}
		fetchObject(workList, WORK_FQL_QUERY, "work", Work.class);
		return workList;
	}
}

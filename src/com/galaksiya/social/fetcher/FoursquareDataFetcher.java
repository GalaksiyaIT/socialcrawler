package com.galaksiya.social.fetcher;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.galaksiya.social.entity.FoursquareUser;
import com.galaksiya.social.exception.FoursquareAuthenticationException;
import com.galaksiya.social.utility.SocialNetworkUtil;

import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.FoursquareEntity;
import fi.foyt.foursquare.api.JSONFieldParser;
import fi.foyt.foursquare.api.entities.Checkin;
import fi.foyt.foursquare.api.entities.CompactUser;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteTip;
import fi.foyt.foursquare.api.entities.CompleteUser;
import fi.foyt.foursquare.api.entities.Todo;
import fi.foyt.foursquare.api.io.DefaultIOHandler;
import fi.foyt.foursquare.api.io.Method;
import fi.foyt.foursquare.api.io.Response;

public class FoursquareDataFetcher {

	private Logger logger = Logger.getLogger(FoursquareDataFetcher.class);

	private String permissions;

	private List<String> permissionList = new ArrayList<String>();

	public List<String> getPermissionList() {
		return permissionList;
	}

	/**
	 * URL for foursquare base api
	 */
	public static final String FOURSQUARE_API_REQUEST_BASE_URL = "https://api.foursquare.com/v2/";

	/**
	 * checkins of user URL for foursquare api
	 */
	public static final String FOURSQUARE_CHECKINS_URL = FOURSQUARE_API_REQUEST_BASE_URL
			+ "users/self/checkins?v=20140321&oauth_token=";

	public static final String FOURSQUARE_FRIENDS_URL = FOURSQUARE_API_REQUEST_BASE_URL
			+ "users/self/friends?v=20140321&oauth_token=";

	public static final String FOURSQUARE_MAYORSHIPS_URL = FOURSQUARE_API_REQUEST_BASE_URL
			+ "users/self/mayorships?v=20140321&oauth_token=";

	/**
	 * checkins of user URL for foursquare api
	 */
	public static final String FOURSQUARE_TIPS_URL = FOURSQUARE_API_REQUEST_BASE_URL
			+ "users/self/tips?v=20140321&oauth_token=";

	/**
	 * checkins of user URL for foursquare api
	 */
	public static final String FOURSQUARE_TODOS_URL = FOURSQUARE_API_REQUEST_BASE_URL
			+ "users/self/todos?v=20140321&sort=recent&oauth_token=";

	/**
	 * user URL for foursquare api
	 */
	public static final String FOURSQUARE_USER_URL = FOURSQUARE_API_REQUEST_BASE_URL
			+ "users/self?v=20140321&oauth_token=";

	public FoursquareDataFetcher() {
		this.permissions = null;
	}

	public FoursquareDataFetcher(String permissions) {
		this.permissions = permissions;
		this.permissionList = SocialNetworkUtil
				.generatePermissionList(permissions);
	}

	public void complete(FoursquareUser foursquareUser)
			throws FoursquareAuthenticationException {
		try {
			CompleteUser retrievedUser = retrieveUser(foursquareUser
					.getAccessToken());
			foursquareUser.setUser(retrievedUser);
			foursquareUser.setId(foursquareUser.getUser().getId());
			foursquareUser.setCheckins(retrieveCheckins(foursquareUser
					.getAccessToken()));
			foursquareUser.setFriends(retrieveFriends(foursquareUser
					.getAccessToken()));
			foursquareUser
					.setTips(retrieveTips(foursquareUser.getAccessToken()));
			// foursquareUser.setMayorships(retrieveMayorships(foursquareUser
			// .getAccessToken()));
			// foursquareUser.setTodos(retrieveTodos(foursquareUser
			// .getAccessToken()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (FoursquareApiException e) {
			e.printStackTrace();
		}

	}

	public String getPermissions() {
		return permissions;
	}

	/**
	 * connects foursquare and retrieves checkins of an user
	 * 
	 * @param accessToken
	 * @return
	 * @throws JSONException
	 * @throws FoursquareApiException
	 */
	public ArrayList<Checkin> retrieveCheckins(String accessToken)
			throws JSONException, FoursquareApiException {
		ArrayList<Checkin> returnList = new ArrayList<Checkin>();
		if (SocialNetworkUtil.isRetrievable(permissionList,
				FoursquarePermissions.checkins.name())) {
			Response responseCheckins = getResponse(accessToken,
					FoursquareDataFetcher.FOURSQUARE_CHECKINS_URL);
			if (responseCheckins.getResponseCode() == 200) {
				ArrayList<Object> retrievedCheckins = retrieveArrayTypeJsonObject(
						responseCheckins,
						FoursquarePermissions.checkins.name(), Checkin.class);
				// cast all objects...
				for (Object checkin : retrievedCheckins) {
					returnList.add((Checkin) checkin);
				}
			}
		}
		return returnList;
	}

	public ArrayList<CompactUser> retrieveFriends(String accessToken)
			throws JSONException, FoursquareApiException {
		ArrayList<CompactUser> friendList = new ArrayList<CompactUser>();
		if (SocialNetworkUtil.isRetrievable(permissionList,
				FoursquarePermissions.friends.name())) {
			Response response = getResponse(accessToken,
					FoursquareDataFetcher.FOURSQUARE_FRIENDS_URL);
			if (response.getResponseCode() == 200) {
				ArrayList<Object> retrievedFriends = retrieveArrayTypeJsonObject(
						response, FoursquarePermissions.friends.name(),
						CompactUser.class);
				// cast all objects
				for (Object friend : retrievedFriends) {
					friendList.add((CompactUser) friend);
				}
			}
		}
		return friendList;
	}

	public ArrayList<CompactVenue> retrieveMayorships(String accessToken)
			throws JSONException, FoursquareApiException {
		ArrayList<CompactVenue> mayorshipList = new ArrayList<CompactVenue>();
		if (SocialNetworkUtil.isRetrievable(permissionList,
				FoursquarePermissions.mayorships.name())) {
			Response response = getResponse(accessToken,
					FoursquareDataFetcher.FOURSQUARE_MAYORSHIPS_URL);
			if (response.getResponseCode() == 200) {
				ArrayList<Object> retrievedMayorships = retrieveArrayTypeJsonObject(
						response, FoursquarePermissions.mayorships.name(),
						CompactVenue.class);
				// cast all objects
				for (Object mayorship : retrievedMayorships) {
					mayorshipList.add((CompactVenue) mayorship);
				}
			}
		}
		return mayorshipList;
	}

	public Object retrieveSingleJSONObject(Response responseUser, Class<?> clazz)
			throws JSONException, FoursquareApiException {
		logger.trace("Valid response has been got");
		String content = responseUser.getResponseContent();
		logger.trace("Response content has been got with value : " + content);
		JSONObject jsonObject = new JSONObject(content);
		logger.trace("Content object has been created with value : "
				+ jsonObject);
		JSONObject jsonObjResponse = jsonObject.getJSONObject("response");
		logger.trace("Response object has been got with value : "
				+ jsonObjResponse);
		JSONObject jsonObjectUser = jsonObjResponse.getJSONObject("user");
		logger.trace("object has been got with value : " + jsonObjectUser);
		FoursquareEntity fsEntity = JSONFieldParser.parseEntity(
				CompleteUser.class, jsonObjectUser, true);
		logger.trace("object has been parsed to FoursquareEntity object with value : "
				+ fsEntity);
		logger.debug("New Foursquare entity " + fsEntity
				+ " has been retrieved");
		return fsEntity;
	}

	public ArrayList<CompleteTip> retrieveTips(String accessToken)
			throws JSONException, FoursquareApiException {
		ArrayList<CompleteTip> tipList = new ArrayList<CompleteTip>();
		if (SocialNetworkUtil.isRetrievable(permissionList,
				FoursquarePermissions.tips.name())) {
			Response response = getResponse(accessToken,
					FoursquareDataFetcher.FOURSQUARE_TIPS_URL);
			if (response.getResponseCode() == 200) {
				ArrayList<Object> retrievedTips = retrieveArrayTypeJsonObject(
						response, FoursquarePermissions.tips.name(),
						CompleteTip.class);
				// cast all objects
				for (Object tip : retrievedTips) {
					tipList.add((CompleteTip) tip);
				}
			}
		}
		return tipList;
	}

	public ArrayList<Todo> retrieveTodos(String accessToken)
			throws JSONException, FoursquareApiException {
		ArrayList<Todo> todoList = new ArrayList<Todo>();
		if (SocialNetworkUtil.isRetrievable(permissionList,
				FoursquarePermissions.todos.name())) {
			Response response = getResponse(accessToken,
					FoursquareDataFetcher.FOURSQUARE_TODOS_URL);
			if (response.getResponseCode() == 200) {
				ArrayList<Object> retrievedTodos = retrieveArrayTypeJsonObject(
						response, FoursquarePermissions.todos.name(),
						Todo.class);
				// cast all objects
				for (Object todo : retrievedTodos) {
					todoList.add((Todo) todo);
				}
			}
		}
		return todoList;
	}

	/**
	 * connects foursquare and retrieves user
	 * 
	 * @param accessToken
	 * @return
	 * @throws JSONException
	 * @throws FoursquareApiException
	 * @throws FoursquareAuthenticationException
	 */
	public CompleteUser retrieveUser(String accessToken) throws JSONException,
			FoursquareApiException, FoursquareAuthenticationException {
		Response responseUser = getResponse(accessToken,
				FoursquareDataFetcher.FOURSQUARE_USER_URL);
		if (responseUser.getResponseCode() == 200) {
			return (CompleteUser) retrieveSingleJSONObject(responseUser,
					CompleteUser.class);
		} else {
			throw new FoursquareAuthenticationException(
					responseUser.getMessage());
		}
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	private ArrayList<Object> generateArray(JSONArray itemArray,
			Class<?> objectClass) throws JSONException, FoursquareApiException {
		ArrayList<Object> checkinList = new ArrayList<Object>();
		for (int i = 0; i < itemArray.length(); i++) {
			JSONObject checkinJSONObject = itemArray.getJSONObject(i);

			logger.trace(objectClass + " object has been got with value : "
					+ itemArray);
			FoursquareEntity objectEntity = JSONFieldParser.parseEntity(
					objectClass, checkinJSONObject, true);
			logger.trace(objectClass
					+ " object has been parsed to FoursquareEntity object with value : "
					+ objectEntity);
			logger.debug("New " + objectClass + " " + objectEntity
					+ " has been retrieved");
			checkinList.add(objectEntity);
		}
		return checkinList;
	}

	private Response getResponse(String accessToken, String objectFetchURL) {
		String completedURL = objectFetchURL + accessToken;
		DefaultIOHandler defaultIOHandler = new DefaultIOHandler();
		logger.trace("data is beginning to GET");
		Response response = defaultIOHandler
				.fetchData(completedURL, Method.GET);
		logger.trace("data has been got with response object :  " + response);
		logger.trace("Response code : " + response.getResponseCode());
		return response;
	}

	private ArrayList<Object> retrieveArrayTypeJsonObject(Response response,
			String arrayName, Class<?> clazz) throws JSONException,
			FoursquareApiException {
		logger.trace("Valid response has been got");
		String content = response.getResponseContent();
		logger.trace("Response content has been got with value : " + content);
		JSONObject jsonObject = new JSONObject(content);
		logger.trace("Content object has been created with value : "
				+ jsonObject);
		JSONObject responseObj = jsonObject.getJSONObject("response");
		logger.trace("Response object has been got with value : " + responseObj);

		JSONObject arrayObject = responseObj.getJSONObject(arrayName);
		JSONArray itemArray = arrayObject.getJSONArray("items");
		ArrayList<Object> list = generateArray(itemArray, clazz);
		return list;
	}

}

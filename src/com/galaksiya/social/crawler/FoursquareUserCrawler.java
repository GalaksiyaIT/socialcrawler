package com.galaksiya.social.crawler;

import org.json.JSONException;

import com.galaksiya.social.entity.FoursquareUser;
import com.galaksiya.social.exception.FoursquareAuthenticationException;
import com.galaksiya.social.fetcher.FoursquareDataFetcher;
import com.galaksiya.social.fetcher.FoursquareIndividualCreator;
import com.galaksiya.social.fetcher.SocialAPIException;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import fi.foyt.foursquare.api.FoursquareApiException;

/**
 * This crawler is used to retrieve data of a Facebook user whose access token
 * is given.
 * 
 * @author galaksiya
 * 
 */
public class FoursquareUserCrawler {

	// private static Logger logger = Logger
	// .getLogger(FoursquareUserCrawler.class);

	private Resource foursquareIndv;

	private FoursquareIndividualCreator indvCreator;

	private String resourceId;

	private FoursquareUser user;

	private String permissions;

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public FoursquareUserCrawler(String baseURI) {
		this(baseURI, null);
	}

	public FoursquareUserCrawler(String baseURI, String resourceId) {
		this.resourceId = resourceId;
		this.indvCreator = new FoursquareIndividualCreator(baseURI);
	}

	public FoursquareUserCrawler(String baseURI, String resourceId,
			String permissons) {
		this.resourceId = resourceId;
		this.permissions = permissons;
		this.indvCreator = new FoursquareIndividualCreator(baseURI);
	}

	public Model crawl(String accessToken) throws Exception {
		readData(getUser(accessToken));
		return indvCreator.getModel();
	}

	public FoursquareUser getUser(String accessToken) throws JSONException,
			FoursquareApiException {
		user = new FoursquareUser(accessToken);
		user.setId(getResourceId());
		return user;
	}

	public Resource readData(FoursquareUser user) throws SocialAPIException,
			FoursquareAuthenticationException {
		if (this.user == null) {
			this.user = user;
		}
		new FoursquareDataFetcher(permissions).complete(user);
		foursquareIndv = indvCreator.createPersonIndividual(user);
		return foursquareIndv;
	}

	private String getResourceId() {
		return resourceId;
	}
}

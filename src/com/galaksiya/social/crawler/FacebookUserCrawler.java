package com.galaksiya.social.crawler;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.galaksiya.social.entity.FacebookUser;
import com.galaksiya.social.exception.FacebookAccessExpirationException;
import com.galaksiya.social.exception.FacebookAuthenticationException;
import com.galaksiya.social.fetcher.FacebookDataFetcher;
import com.galaksiya.social.fetcher.FacebookIndividualCreator;
import com.galaksiya.social.fetcher.SocialAPIException;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.restfb.DefaultFacebookClient;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

/**
 * This crawler is used to retrieve data of a Facebook user whose access token
 * is given. It constructs a {@link Model} including the user's data in RDF
 * form.
 * <p>
 * A base URI should be given to this crawler to create resource URIs. Also, an
 * ID can be given to the crawler to be used as resource ID of the Facebook
 * user. If no resource ID is given to the crawler, social network ID of the
 * user is used as resource ID.
 * </p>
 * <p>
 * {@link #crawl(String)} is the main method which retrieves data from Facebook
 * and converts it to RDF. It only requires the access token of the user, and
 * returns the RDF model.
 * </p>
 * <p>
 * {@link #createFriends(Model)} method can be used to create friendship
 * relationships where resource IDs of users are different from social network
 * ID. If resources are created using IDs different from social network ID,
 * {@link #crawl(String, Model)} method is used to discover existent users and
 * create friendship relations using their URIs.
 * </p>
 * 
 * @author galaksiya
 * 
 */
public class FacebookUserCrawler {

	private Resource facebookIndv;

	private FacebookIndividualCreator indvCreator;

	private String permissons;

	private String resourceId;

	private FacebookUser user;

	private static Logger logger = Logger.getLogger(FacebookUserCrawler.class);

	/**
	 * Creates the crawler instance with the given base URI.
	 * <p>
	 * <b>NOTE:</b> Resource URIs of users are created using social network IDs
	 * when this constructor is used.
	 * </p>
	 * 
	 * @param baseURI
	 *            resource URI prefix for retrieved Facebook data.
	 */
	public FacebookUserCrawler(String baseURI) {
		this(baseURI, null);
	}

	/**
	 * Creates the crawler instance with the given base URI and resource ID for
	 * the user whom the access token belongs to.
	 * 
	 * @param baseURI
	 *            resource URI prefix for retrieved Facebook data.
	 * @param resourceId
	 *            ID for the user URI.
	 */
	public FacebookUserCrawler(String baseURI, String resourceId) {
		this(baseURI, resourceId, null);
	}

	/**
	 * Creates the crawler instance with the given base URI, resource ID and
	 * permissions for the user whom the access token belongs to.
	 * 
	 * @param baseURI
	 *            resource URI prefix for retrieved Facebook data.
	 * @param resourceId
	 *            ID for the user URI.
	 * @param permissions
	 *            permissions indicates which properties should be fetched from
	 *            Facebook.
	 */
	public FacebookUserCrawler(String baseURI, String resourceId,
			String permissions) {
		this.resourceId = resourceId;
		this.permissons = permissions;
		this.indvCreator = new FacebookIndividualCreator(baseURI);
	}

	/**
	 * Crawls user data and converts to RDF.
	 * <p>
	 * This method creates friend resource with their social network IDs and
	 * basic profile data.
	 * </p>
	 * 
	 * @param accessToken
	 *            the access token that belongs to the user whose data is
	 *            desired to be retrieved.
	 * @return model that contains all data of the user.
	 * @throws SocialAPIException
	 */
	public Model crawl(String accessToken) throws SocialAPIException,
			FacebookAuthenticationException {
		return crawl(accessToken, indvCreator.getModel());
	}

	/**
	 * Crawls user data and converts to RDF.
	 * <p>
	 * Given model is used to discover existent user resources. It is checked by
	 * searching the resource according to their social network IDs. If the user
	 * does not already exist, only basic profile information is fetched and a
	 * resource is created with the user's social network ID.
	 * </p>
	 * 
	 * @param accessToken
	 *            the access token that belongs to the user whose data is
	 *            desired to be retrieved.
	 * @param friendModel
	 *            Model to search existent resources.
	 * @return model that contains all data of the user.
	 * @throws SocialAPIException
	 * @throws FacebookAccessExpirationException
	 */
	public Model crawl(String accessToken, Model friendModel)
			throws SocialAPIException, FacebookAuthenticationException {
		try {
			readData(getUser(accessToken));
			createFriends(friendModel);
		} catch (FacebookOAuthException foe) {
			if (foe.getErrorSubcode() != null && foe.getErrorSubcode() == 463) {
				throw new FacebookAccessExpirationException(foe.getMessage(),
						foe.getCause());
			} else {
				throw new FacebookAuthenticationException(foe.getMessage(),
						foe.getCause());
			}
		}
		return indvCreator.getModel();
	}

	/**
	 * This method creates friendship relations by checking existent users in
	 * the given model. If the user is not already exist, this method retrieves
	 * the user's basic profile information.
	 * 
	 * @param targetModel
	 *            model to search for existent user resources.
	 */
	public void createFriends(Model targetModel) {
		indvCreator.createFriendProperties(user, facebookIndv, targetModel);
	}

	public String getPermissons() {
		return permissons;
	}

	/**
	 * This method creates a {@link FacebookUser} for authorized user's token.
	 * 
	 * @param accessToken
	 *            token to retrieve Facebook data of the user.
	 * @return
	 */
	public FacebookUser getUser(String accessToken) {
		DefaultFacebookClient client = new SocialCrawlerFacebookClient(
				accessToken);
		user = new FacebookUser(client, client.fetchObject("me", User.class),
				accessToken);
		user.setId(getResourceId());
		return user;
	}

	/**
	 * This method retrieves the data of the given facebook user and converts it
	 * to RDF format.
	 * 
	 * @param facebookUser
	 *            authenticated user whose data is desired to be converted to
	 *            RDF.
	 * @return resource which corresponds to the given user.
	 * @throws SocialAPIException
	 */
	public Resource readData(FacebookUser facebookUser)
			throws SocialAPIException {
		if (this.user == null) {
			this.user = facebookUser;
		}

		new FacebookDataFetcher(facebookUser, permissons).complete();
		logger.info(MessageFormat.format(
				"User individual is being created for {0} ", facebookUser));

		facebookIndv = indvCreator.createPersonIndividual(facebookUser);

		logger.info(MessageFormat.format(
				"User individual has been created for {0} ", facebookUser));
		return facebookIndv;
	}

	public void setPermissons(String permissons) {
		this.permissons = permissons;
	}

	/**
	 * Returns the resource ID of the crawled user.
	 * 
	 * @return ID value which is given in the constructor. If no ID is given in
	 *         constructor, this method returns the social network ID of the
	 *         user.
	 */
	private String getResourceId() {
		if (resourceId == null) {
			return user.getSocialNetworkId();
		}
		return resourceId;
	}
}

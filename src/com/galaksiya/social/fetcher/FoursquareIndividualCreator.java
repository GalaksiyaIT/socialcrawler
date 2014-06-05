package com.galaksiya.social.fetcher;

import java.util.ArrayList;
import java.util.UUID;

import com.galaksiya.social.entity.FoursquareUser;
import com.galaksiya.social.linker.BankPersonLinker;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.Checkin;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteUser;
import fi.foyt.foursquare.api.entities.Contact;
import fi.foyt.foursquare.api.entities.Location;

/**
 * TODO Bir venue'ye yakın diğer venue'lar foursquare'den cekilebilir mi?
 * 
 * @author etmen
 * 
 */
public class FoursquareIndividualCreator extends IndividualCreator {

	public static final String SPACE = " ";
	public static final String UNDERLINE = "_";
	private static final String FOURSQUARE_USER_PROFILE_PATTERN = "https://foursquare.com/user/";

	public FoursquareIndividualCreator(String baseURI) {
		super(baseURI);
	}

	public Resource createPersonIndividual(FoursquareUser foursquareUser)
			throws SocialAPIException {
		getLogger().info("Foursquare Individual ontology is being created");
		if (foursquareUser.getId() == null) {
			throw new SocialAPIException(
					"User ID for the foursquare user is null");
		}

		checkNotNull(foursquareUser,
				"Individual ontology could not be created because Foursquare user is null");

		CompleteUser user = foursquareUser.getUser();
		checkNotNull(user,
				"Individual ontology could not be created because user is null");
		getLogger().debug("Individual is being created");
		Resource fsIndv = getModel().createResource(
				createUserURI(foursquareUser),
				getModel().getResource(
						CommonOntologyVocabulary.FOAF_PERSON_RSC_URI));

		getLogger().trace("Name property is being created");
		createProperty(fsIndv, CommonOntologyVocabulary.FOAF_NAME_URI,
				createFullName(user));
		getLogger().trace(
				"Name property has been created : " + createFullName(user));

		getLogger().trace("Gender property is being created");
		createProperty(fsIndv, CommonOntologyVocabulary.FOAF_GENDER_URI,
				user.getGender());
		getLogger().trace(
				"Gender property has been created : " + user.getGender());

		getLogger().trace("Relationship property is being created");
		createProperty(fsIndv,
				FoursquareOntologyVocabulary.RELATIONSHIP_PRP_URI,
				user.getRelationship());
		getLogger().trace(
				"Relationship property has been created : "
						+ user.getRelationship());

		createBadgesProperty(user, fsIndv);

		createFollowerCountProperty(user, fsIndv);

		createFollowingCountProperty(user, fsIndv);

		createHometownPrp(fsIndv, UUID.randomUUID().toString(), foursquareUser
				.getUser().getHomeCity());

		createRequestCountProperty(user, fsIndv);

		createCheckins(foursquareUser, fsIndv);

		// creates account property for foursquare individual
		createAccountProperty(fsIndv, foursquareUser.getUser().getId(),
				FoursquareOntologyVocabulary.FOURSQUARE_HOMEPAGE_RSC);

		// creating profile, picture url properties and label property of
		// foursquare individual
		createFoursquareLabels(foursquareUser, fsIndv);

		Resource contactIndv = createContactIndividual(fsIndv,
				user.getContact());
		createProperty(fsIndv, FoursquareOntologyVocabulary.CONTACT_PRP_URI,
				contactIndv);

		getLogger().trace("Pings property is being created");
		createProperty(fsIndv, FoursquareOntologyVocabulary.PINGS_PRP_URI,
				user.getPings());
		getLogger().trace(
				"Pings property has been created : " + user.getPings());

		getLogger().debug("Individual has been created : " + fsIndv);
		getLogger().info("Foursquare Individual ontology has been created");

		new BankPersonLinker().linkPersonToBankDataset(fsIndv);

		return fsIndv;
	}

	private void checkNotNull(Object object, String message)
			throws SocialAPIException {
		if (object == null) {
			getLogger().error(message);
			throw new SocialAPIException(message);
		}
	}

	private void createBadgesProperty(CompleteUser user, Resource fsIndv) {
		if (user.getBadges() != null) {
			getLogger().trace("Badge count property is being created");
			createProperty(fsIndv,
					FoursquareOntologyVocabulary.BADGE_COUNT_PRP_URI, user
							.getBadges().getCount());
			getLogger().trace(
					"Badge count property has been created : "
							+ user.getBadges().getCount());
		}
		getLogger().warn("User badges is null");
	}

	private Resource createCategoryIndividual(Category category) {
		getLogger().debug("category individual is being created");
		Resource categoryIndv = getModel().createResource(
				getBaseURI() + "Category"
						+ category.getName().replace(SPACE, UNDERLINE),
				getModel().getResource(
						FoursquareOntologyVocabulary.CATEGORY_URI));

		getLogger().trace("name property is being created");
		String name = category.getName();
		createProperty(categoryIndv, CommonOntologyVocabulary.FOAF_NAME_URI,
				name);
		getLogger().trace("name property has been created : " + name);

		getLogger().trace("icon property is being created");
		String icon = category.getIcon();
		com.restfb.json.JsonObject iconJson = new com.restfb.json.JsonObject(
				icon);
		createProperty(
				categoryIndv,
				FoursquareOntologyVocabulary.ICON_PRP_URI,
				ResourceFactory.createResource(iconJson.getString("prefix")
						+ "32" + iconJson.getString("suffix")));
		getLogger().trace("icon property has been created : " + icon);

		getLogger().trace("pluralName property is being created");
		String pluralName = category.getPluralName();
		createProperty(categoryIndv,
				FoursquareOntologyVocabulary.PLURAL_NAME_PRP_URI, pluralName);
		getLogger().trace(
				"pluralName property has been created : " + pluralName);

		getLogger().trace("primary property is being created");
		Boolean primary = category.getPrimary();
		createProperty(categoryIndv,
				FoursquareOntologyVocabulary.PRIMARY_PRP_URI, primary);
		getLogger().trace("primary property has been created : " + primary);

		getLogger().debug(
				"category individual has been created : " + categoryIndv);
		return categoryIndv;
	}

	private void createCategoryProperties(CompactVenue venue, Resource venueIndv) {
		getLogger().debug("Category individuals are being created");
		Category[] categories = venue.getCategories();
		if (categories != null) {
			for (int i = 0; i < categories.length; i++) {
				Category category = categories[i];
				Resource categoryIndv = createCategoryIndividual(category);
				getLogger().trace("Category property is being created");
				createProperty(venueIndv,
						FoursquareOntologyVocabulary.CATEGORY_PRP_URI,
						categoryIndv);
				getLogger().trace("Category property has been created");
			}
			getLogger().debug("Category individuals have been created");
		} else {
			getLogger().warn("Categories variable has null value");
		}
	}

	private Resource createCheckinIndividual(Checkin checkin) {
		getLogger().debug("Checkin individual is being created");
		if (checkin != null) {
			String id = checkin.getId();
			Resource checkinIndv = getModel().createResource(
					getBaseURI() + "checkin" + id,
					getModel().createResource(
							FoursquareOntologyVocabulary.CHECKIN_URI));

			// creating location property
			// getLogger().debug("Location property is being created");
			// Location location = checkin.getLocation();
			// Individual locationIndv = createLocationIndividual(location);
			// createProperty(checkinIndv,
			// CommonOntologyVocabulary.LOCATION_URI,
			// locationIndv);
			// getLogger().debug("Location property has been created");

			// creating venue property
			getLogger().debug("Venue property is being created");
			CompactVenue venue = checkin.getVenue();
			Resource venueIndv = createCompactVenueIndividual(venue);
			createProperty(checkinIndv,
					FoursquareOntologyVocabulary.VENUE_PRP_URI, venueIndv);
			getLogger().debug("Venue property has been created");

			// crating createdAt property
			getLogger().trace("Location property is being created");
			Long createdAt = checkin.getCreatedAt();
			createProperty(checkinIndv,
					CommonOntologyVocabulary.CREATED_AT_PRP_URI, createdAt);
			getLogger().trace("Location property has been created");

			// creating isMayor property
			getLogger().trace("Location property is being created");
			Boolean isMayor = checkin.getIsMayor();
			createProperty(checkinIndv,
					FoursquareOntologyVocabulary.IS_MAYOR_PRP_URI, isMayor);
			getLogger().trace("Location property has been created");

			// creating timeZone property
			getLogger().trace("Location property is being created");
			String timeZone = checkin.getTimeZone();
			createProperty(checkinIndv,
					FoursquareOntologyVocabulary.TIME_ZONE_PRP_URI, timeZone);
			getLogger().trace("Location property has been created");

			// creating shout property
			getLogger().trace("Location property is being created");
			String shout = checkin.getShout();
			createProperty(checkinIndv,
					FoursquareOntologyVocabulary.SHOUT_PRP_URI, shout);
			getLogger().trace("Location property has been created");

			// creating type property
			getLogger().trace("Location property is being created");
			String type = checkin.getType();
			createProperty(checkinIndv, CommonOntologyVocabulary.TYPE_PRP_URI,
					type);
			getLogger().trace("Location property has been created");

			getLogger().debug("Checkin individual has been created");
			return checkinIndv;

		}
		getLogger().warn(
				"Checkin individual could not be created and has null value");
		return null;
	}

	private void createCheckins(FoursquareUser foursquareUser, Resource fsIndv) {
		getLogger().debug("Checkin properties are being created");
		ArrayList<Checkin> checkins = foursquareUser.getCheckins();
		if (checkins != null) {
			for (Checkin checkin : checkins) {
				if (checkin == null) {
					continue;
				}
				getLogger().debug("Checkin property is being created");
				Resource checkinIndv = createCheckinIndividual(checkin);
				createProperty(fsIndv,
						FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
						checkinIndv);
				getLogger().debug("Checkin property has been created");
			}
		}
		getLogger().debug("Checkin properties have been created");
	}

	private Resource createCompactVenueIndividual(CompactVenue venue) {
		if (venue != null) {
			getLogger().debug("Venue individual is being created");
			String id = venue.getId();
			Resource venueIndv = getModel().createResource(
					getBaseURI() + "venue" + id,
					getModel().getResource(
							FoursquareOntologyVocabulary.VENUE_URI));

			getLogger().trace("Name property is being created");
			String name = venue.getName();
			createProperty(venueIndv, CommonOntologyVocabulary.FOAF_NAME_URI,
					name);
			getLogger().trace("Name property has been created");

			getLogger().trace("URL property is being created");
			String url = venue.getUrl();
			createProperty(venueIndv, CommonOntologyVocabulary.FOAF_URL, url);
			getLogger().trace("URL property has been created");

			getLogger().trace("Verified property is being created");
			Boolean verified = venue.getVerified();
			createProperty(venueIndv,
					FoursquareOntologyVocabulary.IS_VERIFIED_PRP_URI, verified);
			getLogger().trace("Verified property has been created");

			getLogger().debug("Contact property is being created");
			Contact contact = venue.getContact();
			Resource contactIndv = createContactIndividual(null, contact);
			createProperty(venueIndv,
					FoursquareOntologyVocabulary.CONTACT_PRP_URI, contactIndv);
			getLogger().debug("Contact property has been created");

			getLogger().debug("Contact property is being created");
			Location location = venue.getLocation();
			Resource locationIndv = createVenueLocationIndividual(location);
			createProperty(venueIndv, CommonOntologyVocabulary.LOCATION_URI,
					locationIndv);
			getLogger().debug("Contact property has been created");

			createCategoryProperties(venue, venueIndv);

			getLogger().debug("Venue individual has been created");
			return venueIndv;
		}
		getLogger().warn(
				"Venue individual cannot be created and has null value");
		return null;
	}

	private Resource createContactIndividual(Resource personIndv,
			Contact contact) {
		if (contact != null
				&& (contact.getEmail() != null || contact.getFacebook() != null
						|| contact.getPhone() != null || contact.getTwitter() != null)) {
			getLogger().debug("contact individual is being created");
			Resource contactIndv = getModel().createResource(
					getBaseURI() + "contact" + UUID.randomUUID(),
					getModel().getResource(
							FoursquareOntologyVocabulary.CONTACT_URI));

			getLogger().trace("email property is being created");
			String email = contact.getEmail();
			createProperty(personIndv, CommonOntologyVocabulary.SIOC_EMAIL_URI,
					email);
			getLogger().trace("email property has been created" + email);

			getLogger().trace("phone property is being created");
			String phone = contact.getPhone();
			createProperty(contactIndv,
					FoursquareOntologyVocabulary.PHONE_PRP_URI, phone);
			getLogger().trace("phone property has been created" + phone);

			getLogger().trace("facebook property is being created");
			String facebook = contact.getFacebook();
			createProperty(contactIndv, FoursquareOntologyVocabulary.FACEBOOK,
					facebook);
			getLogger().trace("facebook property has been created" + facebook);

			getLogger().trace("twitter property is being created");
			String twitter = contact.getTwitter();
			createProperty(contactIndv, FoursquareOntologyVocabulary.TWITTER,
					twitter);
			getLogger().trace("twitter property has been created" + twitter);

			getLogger().debug("contact individual has been created");
			return contactIndv;
		}
		getLogger().warn("contact variable has null value");
		return null;
	}

	private void createFollowerCountProperty(CompleteUser user, Resource fsIndv) {
		if (user.getFollowers() != null) {
			getLogger().trace("Follower count property is being created");
			createProperty(fsIndv,
					FoursquareOntologyVocabulary.FOLLOWER_COUNT_PRP_URI, user
							.getFollowers().getCount());
			getLogger().trace(
					"Follower count property has been created : "
							+ user.getFollowers().getCount());
		}
		getLogger().warn("Followers is null");
	}

	private void createFollowingCountProperty(CompleteUser user, Resource fsIndv) {
		if (user.getFollowing() != null) {
			getLogger().trace("Following count property is being created");
			createProperty(fsIndv,
					FoursquareOntologyVocabulary.FOLLOWING_COUNT_PRP_URI, user
							.getFollowing().getCount());
			getLogger().trace(
					"Following count property has been created : "
							+ user.getFollowing().getCount());
		}
		getLogger().warn("Following is null");
	}

	/**
	 * creating RDFS label, profile URL, and profile pricute URL properties of
	 * foursquare individual
	 * 
	 * @param foursquareUser
	 * @param fsIndv
	 */
	private void createFoursquareLabels(FoursquareUser foursquareUser,
			Resource fsIndv) {

		// generating profile URL of foursquare user
		String FOURSQUARE_PROFILE_URL = FOURSQUARE_USER_PROFILE_PATTERN
				+ foursquareUser.getUser().getId();
		// creating foaf:page property of foursquare user
		createProperty(fsIndv, CommonOntologyVocabulary.FOAF_PAGE_PRP_URI,
				createDocumentIndv(FOURSQUARE_PROFILE_URL));

		// getting photo URL of foursquare user
		String photoURL = foursquareUser.getUser().getPhoto();
		com.restfb.json.JsonObject photoJson = new com.restfb.json.JsonObject(
				photoURL);
		// creating foaf:picture URL of foursquare user
		createProperty(fsIndv, CommonOntologyVocabulary.FOAF_PICTURE_PRP_URI,
				createImageIndv(photoJson.getString("prefix") + "512x512"
						+ photoJson.getString("suffix")));

		// generating full name of foursquare user
		String userName = foursquareUser.getUser().getFirstName() + SPACE
				+ foursquareUser.getUser().getLastName();
		// creating rdfs:label property of foursquare user
		createProperty(fsIndv, CommonOntologyVocabulary.RDFS_LABEL_PRP_URI,
				userName);

	}

	private String createFullName(CompleteUser user) {
		return user.getFirstName() + SPACE + user.getLastName();
	}

	private void createRequestCountProperty(CompleteUser user, Resource fsIndv) {
		if (user.getRequests() != null) {
			getLogger().trace("Request count property is being created");
			createProperty(fsIndv,
					FoursquareOntologyVocabulary.REQUEST_COUNT_PRP_URI, user
							.getRequests().getCount());
			getLogger().trace(
					"Request count property has been created : "
							+ user.getRequests().getCount());
		}
		getLogger().warn("Requests variable is null");
	}

	private String createUserURI(FoursquareUser foursquareUser) {
		return getBaseURI() + foursquareUser.getId();
	}

	private Resource createVenueLocationIndividual(Location location) {
		if (location != null) {

			getLogger().info("creating location individual is beginning");
			Resource locationIndividual = getModel().createResource(
					getBaseURI() + "location" + UUID.randomUUID(),
					getModel().getResource(
							CommonOntologyVocabulary.GEODDATA_PLACE_URI));
			String address = location.getAddress();

			getLogger().trace("ADDRESS creating with value : " + address);
			createProperty(locationIndividual,
					CommonOntologyVocabulary.ADDRESS_PRP_URI, address);

			// create city resource
			String city = location.getCity();
			getLogger().trace("CITY creating with value : " + city);
			Resource cityRsc = createCityResource(city);

			// creating city property
			createProperty(locationIndividual,
					CommonOntologyVocabulary.CITY_PRP_URI, cityRsc);

			// create country resource
			String country = location.getCountry();
			getLogger().trace("COUNTRY creating with value : " + country);
			Resource countryRsc = createCountryResource(country);

			// creating country property
			createProperty(locationIndividual,
					CommonOntologyVocabulary.COUNTRY_PRP_URI, countryRsc);

			String crossStreet = location.getCrossStreet();

			getLogger().trace("STREET creating with value : " + crossStreet);
			createProperty(locationIndividual,
					CommonOntologyVocabulary.CROSS_STREET_PRP_URI, crossStreet);
			Double distance = location.getDistance();
			getLogger().trace("DISTANCE creating with value : " + distance);
			createProperty(locationIndividual,
					FoursquareOntologyVocabulary.DISTANCE_PRP_URI, distance);
			Double lat = location.getLat();
			getLogger().trace("LATITUDE creating with value : " + lat);
			createProperty(locationIndividual,
					CommonOntologyVocabulary.LAT_PRP_URI, lat);
			Double lng = location.getLng();
			getLogger().trace("LONGITUDE creating with value : " + lng);
			createProperty(locationIndividual,
					CommonOntologyVocabulary.LNG_PRP_URI, lng);
			String name = location.getName();
			getLogger().trace("NAME creating with value : " + name);
			createProperty(locationIndividual,
					CommonOntologyVocabulary.FOAF_NAME_URI, name);
			String postalCode = location.getPostalCode();
			getLogger().trace("POSTALCODE creating with value : " + postalCode);
			createProperty(locationIndividual,
					CommonOntologyVocabulary.POSTALCODE_PRP_URI, postalCode);
			String state = location.getState();
			getLogger().trace("STATE creating with value : " + state);
			createProperty(locationIndividual,
					CommonOntologyVocabulary.STATE_PRP_URI, state);
			getLogger().info(
					"creating location individual has ended : "
							+ locationIndividual);
			return locationIndividual;
		}
		getLogger().warn("location variable has null value : " + location);
		return null;
	}
}

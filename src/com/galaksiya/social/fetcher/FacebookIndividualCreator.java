package com.galaksiya.social.fetcher;

import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.galaksiya.social.entity.FacebookEvent;
import com.galaksiya.social.entity.FacebookUser;
import com.galaksiya.social.entity.FacebookVenue;
import com.galaksiya.social.entity.FamilyBond;
import com.galaksiya.social.entity.Like;
import com.galaksiya.social.entity.Movie;
import com.galaksiya.social.entity.Music;
import com.galaksiya.social.linker.BankPersonLinker;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.FileUtils;
import com.hp.hpl.jena.util.PrintUtil;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.restfb.json.JsonObject;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.User.Education;
import com.restfb.types.User.Work;
import com.restfb.util.DateUtils;

/**
 * OWLIndividualCreator class generates new individual ontology for related
 * facebook user
 * 
 */
public class FacebookIndividualCreator extends IndividualCreator {

	private static final String MOVIE_RSC_CONSTANT = "movie";
	private static final String LIKE_RSC_CONSTANT = "like";
	private static final String MUSIC_RSC_CONSTANT = "music";
	private static final String COMMUNITY_RSC_CONSTANT = "community";
	private static final String AUTHOR_RSC_CONSTANT = "author";
	private static final String BOOK_RSC_CONSTANT = "book";
	private static final String TEAM_RSC_CONSTANT = "team";
	private static final String TV_SHOW_RSC_CONSTANT = "tv-show";
	private static final String PRODUCER_RSC_CONSTANT = "producer";
	private static final String ACTOR_DIRECTOR_RSC_CONSTANT = "actor/director";

	public FacebookIndividualCreator(String baseURI) {
		super(baseURI);
	}

	public void createFamilyBondProperties(FacebookUser facebookUser,
			Resource fbIndv) {
		ArrayList<FamilyBond> familyBonds = facebookUser.getFamilyBonds();
		if (familyBonds != null) {
			Iterator<FamilyBond> iterator = familyBonds.iterator();
			while (iterator.hasNext()) {
				FamilyBond familyBond = (FamilyBond) iterator.next();
				createFamilyBondProperty(familyBond, fbIndv);
			}

		}
	}

	public Resource createFriendIndividual(JsonObject friendProfile) {
		String friendId = friendProfile.getString("id");
		String friendPic = friendProfile.getString("pic");
		String friendName = friendProfile.getString("name");
		String friendURL = friendProfile.getString("url");

		// creating FOAF PERSON individual from friend user
		Resource friendIndv = getModel().createResource(
				createIndividualURI(friendId),
				getModel().getResource(
						CommonOntologyVocabulary.FOAF_PERSON_RSC_URI));

		// create foaf name property of Friend
		createProperty(friendIndv, CommonOntologyVocabulary.FOAF_NAME_URI,
				friendName);
		// create foaf page property of Friend
		createProperty(friendIndv, CommonOntologyVocabulary.FOAF_PAGE_PRP_URI,
				createDocumentIndv(friendURL));
		// create foaf pricture property of Friend
		createProperty(friendIndv,
				CommonOntologyVocabulary.FOAF_PICTURE_PRP_URI,
				createImageIndv(friendPic));
		// create RDFS label property of Friend
		createProperty(friendIndv, CommonOntologyVocabulary.RDFS_LABEL_PRP_URI,
				friendName);
		return friendIndv;
	}

	/**
	 * This method creates knows properties of facebook individual using
	 * friendlist {@link FacebookUser} instance.
	 * 
	 * @param facebookUser
	 *            facebook user to fetch her friends.
	 * @param fbUserIndv
	 *            corresponding individual.
	 */
	public void createFriendProperties(FacebookUser facebookUser,
			Resource fbUserIndv) {
		createFriendProperties(facebookUser, fbUserIndv, null);
	}

	/**
	 * This method creates knows properties of facebook individual using
	 * friendlist {@link FacebookUser} instance.
	 * 
	 * @param facebookUser
	 *            facebook user to fetch her friends.
	 * @param fbUserIndv
	 *            corresponding individual.
	 * @param targetModel
	 *            target model to check.
	 */
	public void createFriendProperties(FacebookUser facebookUser,
			Resource fbUserIndv, Model targetModel) {
		// fetch friendlist of user from Facebook...
		List<JsonObject> friendProfiles = facebookUser.getFriendProfiles();

		for (JsonObject friendProfile : friendProfiles) {
			if (friendProfile != null) {
				Resource friendIndv = null;
				String friendFacebookId = friendProfile.getString("id");
				if (targetModel != null
						&& getOwnerURI(friendFacebookId, targetModel) != null) {
					friendIndv = ResourceFactory.createResource(getOwnerURI(
							friendFacebookId, targetModel));
					String logPtrn = "Friend whose id is \"{0}\" exists in the social linker, and he/she will be linked to user \"{1}\".";
					getLogger().info(
							MessageFormat.format(logPtrn, friendFacebookId,
									fbUserIndv));
				} else {
					// create friend individual...
					friendIndv = createFriendIndividual(friendProfile);
				}
				// create knows property of fbUserIndv...
				createProperty(fbUserIndv, FOAF.knows.getURI(), friendIndv);
			}
		}
	}

	/**
	 * Creating individual for the given facebook user.
	 * 
	 * @param facebookUser
	 *            Facebook user whose
	 *            tr.edu.ege.seagent.linkeddata.facebook.ontology is requested
	 *            to be created.
	 * @return
	 * @throws SocialAPIException
	 */
	public Resource createPersonIndividual(FacebookUser facebookUser)
			throws SocialAPIException {
		// check that the user is not null...
		if (facebookUser.getId() == null) {
			throw new SocialAPIException(
					"User ID for the facebook user is null");
		}

		// create individual
		Resource fbUserIndv = getModel().createResource(
				creeateUserURI(facebookUser),
				getModel().createResource(
						CommonOntologyVocabulary.FOAF_PERSON_RSC_URI));

		// Create singular properties...
		if (facebookUser.getUser().getHometown() != null) {
			createHometownPrp(fbUserIndv, generateId(facebookUser.getUser()
					.getHometown().getId()), facebookUser.getUser()
					.getHometown().getName());
		}

		if (facebookUser.getUser().getLocation() != null) {
			createLocationProperty(fbUserIndv, generateId(facebookUser
					.getUser().getLocation().getId()), facebookUser.getUser()
					.getHometown().getName());
		}

		createProperty(fbUserIndv, FacebookOntologyVocabulary.RELIGION_URI,
				facebookUser.getUser().getReligion());
		createProperty(fbUserIndv, CommonOntologyVocabulary.FOAF_NAME_URI,
				facebookUser.getUser().getName());
		createProperty(fbUserIndv, CommonOntologyVocabulary.FOAF_GENDER_URI,
				facebookUser.getUser().getGender());

		createProperty(fbUserIndv, FOAF.birthday.getURI(),
				getLiteralBirthday(facebookUser));
		createProperty(fbUserIndv, CommonOntologyVocabulary.WEBLOG_URI,
				facebookUser.getUser().getWebsite());
		createProperty(fbUserIndv, CommonOntologyVocabulary.SIOC_EMAIL_URI,
				facebookUser.getUser().getEmail());

		// create facebook account property
		createAccountProperty(fbUserIndv, facebookUser.getUser().getId(),
				FacebookOntologyVocabulary.FACEBOOK_HOMEPAGE_RSC);

		// adding likes
		// createMovieProperties(facebookUser, fbUserIndv);
		//
		// createMusicProperties(facebookUser, fbUserIndv);

		createLikeProperties(facebookUser, fbUserIndv);

		// adding favourite team properties
		createFavouriteTeamProperties(facebookUser, fbUserIndv);

		// Multiple Property Creation...
		createEducationProperties(facebookUser, fbUserIndv);
		createWorkProperties(facebookUser, fbUserIndv);

		// adding event properties
		createEventProperties(facebookUser, fbUserIndv);

		// createFamilyBondProperties(facebookUser, fbUserIndv);

		// creating facebook labels of facebook user
		createProfileForFacebookUser(facebookUser, fbUserIndv);

		// creating knows properties of facebook user using friendlist
		// createFriendProperties(facebookUser, fbUserIndv);

		new BankPersonLinker().linkPersonToBankDataset(fbUserIndv);

		return fbUserIndv;
	}

	private void createConcentrationProperties(Education education,
			Resource eduIndv) {
		List<NamedFacebookType> concentration = education.getConcentration();
		if (concentration != null) {
			Iterator<NamedFacebookType> conIterator = concentration.iterator();
			while (conIterator.hasNext()) {
				NamedFacebookType conDesc = (NamedFacebookType) conIterator
						.next();
				createEduConcentrationProperty(eduIndv, conDesc);
			}
		}
	}

	private void createEducationProperties(FacebookUser facebookUser,
			Resource fbOntIndv) {
		List<Education> eduList = facebookUser.getEducations();
		if (eduList != null) {
			Iterator<Education> iterator = eduList.iterator();
			while (iterator.hasNext()) {
				Education education = (Education) iterator.next();
				createEducationProperty(fbOntIndv, education);
			}
		}
	}

	private void createEducationProperty(Resource fbOntIndv, Education education) {
		if (education != null) {
			Resource eduIndv = getModel().createResource(
					getBaseURI() + "education/" + UUID.randomUUID(),
					getModel().createResource(
							CommonOntologyVocabulary.EDUCATION_URI));
			createConcentrationProperties(education, eduIndv);

			createEduSchoolProperty(education, eduIndv);
			// creating education date property
			createEduYearProperty(education, eduIndv);

			createProperty(fbOntIndv, CommonOntologyVocabulary.CV_EDU_PRP_URI,
					eduIndv);
		}
	}

	private void createEduConcentrationProperty(Resource eduIndv,
			NamedFacebookType conDesc) {
		// creating major property
		if (conDesc != null) {
			createProperty(eduIndv, CommonOntologyVocabulary.EDU_MAJOR_PRP_URI,
					conDesc.getName());
		}
	}

	private void createEduSchoolProperty(Education education, Resource eduIndv) {
		NamedFacebookType school = education.getSchool();
		if (school != null) {
			Resource schoolIndv = getModel().createResource(
					getBaseURI() + "school/" + school.getId(),
					getModel().createResource(
							CommonOntologyVocabulary.EDUCATIONAL_ORG));
			createProperty(schoolIndv, CommonOntologyVocabulary.FOAF_NAME_URI,
					school.getName());
			// creating studied in property
			createProperty(eduIndv,
					CommonOntologyVocabulary.CV_STUDIED_IN_PRP_URI, schoolIndv);
		}
	}

	private void createEduYearProperty(Education education, Resource eduIndv) {
		NamedFacebookType year = education.getYear();
		if (year != null) {
			createProperty(eduIndv, CommonOntologyVocabulary.GRAD_DATE_PRP_URI,
					year.getName());
		}
	}

	/**
	 * creating event individual
	 * 
	 * @param event
	 * @return
	 */
	private Resource createEventIndividual(FacebookEvent event) {
		Resource eventIndv = getModel()
				.createResource(
						getBaseURI() + "event/" + event.getId(),
						ResourceFactory
								.createResource(FacebookOntologyVocabulary.EVENT_RSC_URI));
		// creating name property of event individual
		createProperty(eventIndv, CommonOntologyVocabulary.FOAF_NAME_URI,
				event.getName());
		return eventIndv;
	}

	/**
	 * creates event properties of Facebook users that she will join
	 * 
	 * @param facebookUser
	 * @param fbUserIndv
	 */
	private void createEventProperties(FacebookUser facebookUser,
			Resource fbUserIndv) {

		// getting events of user
		List<FacebookEvent> events = facebookUser.getEvents();
		if (events != null) {
			for (FacebookEvent event : events) {
				if (event == null) {
					continue;
				}
				// creating an event individual
				Resource eventIndv = createEventIndividual(event);
				// creating event properties
				createProperty(fbUserIndv,
						FacebookOntologyVocabulary.JOINS_PRP_URI, eventIndv);
				// getting venue of event
				FacebookVenue eventVenue = event.getVenue();
				if (eventVenue != null) {
					// add venue property to the given event
					createVenueProperty(eventIndv, eventVenue);
				}
			}
		}

	}

	private void createVenueProperty(Resource eventIndv,
			FacebookVenue eventVenue) {

		// create venue individual for event
		Resource venueIndv = createVenueIndividual(eventVenue);

		// adding venue property to event individual
		createProperty(eventIndv, FoursquareOntologyVocabulary.VENUE_PRP_URI,
				venueIndv);
	}

	private Resource createVenueIndividual(FacebookVenue eventVenue) {
		// create venue individual
		Resource venueIndv = getModel().createResource(
				getBaseURI() + "venue/" + generateId(eventVenue.getId()),
				getModel().createResource(
						FoursquareOntologyVocabulary.VENUE_URI));

		// create city resource for venue
		Resource cityRsc = createCityResource(eventVenue.getCity());

		// create country resource for venue
		Resource countryRsc = createCountryResource(eventVenue.getCountry());

		// creating location individual
		Resource locationIndv = createLocationIndividualOfEvent(eventVenue,
				cityRsc, countryRsc);
		// adding location property to event individual
		createProperty(venueIndv, CommonOntologyVocabulary.LOCATION_URI,
				locationIndv);
		return venueIndv;
	}

	private void createFamilyBondProperty(FamilyBond familyBond, Resource fbIndv) {
		String userId = familyBond.getUserId();
		String name = familyBond.getName();
		String birthDay = familyBond.getBirthDay();
		String relationship = familyBond.getRelationship();

		Resource familyMemberIndv = getModel().createResource(
				createIndividualURI(userId),
				getModel().getResource(
						CommonOntologyVocabulary.FOAF_PERSON_RSC_URI));
		createProperty(familyMemberIndv,
				CommonOntologyVocabulary.FOAF_NAME_URI, name);
		createProperty(familyMemberIndv, CommonOntologyVocabulary.FOAF_BASE_URI
				+ "birthday", birthDay);
		String relationshipUri = selectRelationshipType(relationship);
		createProperty(fbIndv, relationshipUri, familyMemberIndv);

		reasonFamilyBond(fbIndv, familyMemberIndv);

	}

	private Resource createFavouriteTeamIndv(NamedFacebookType favouriteTeam) {
		// create favourite team individual
		Resource favouriteTeamIndv = getModel()
				.createResource(
						getBaseURI() + "team/" + favouriteTeam.getId(),
						getModel().getResource(
								FacebookOntologyVocabulary.TEAM_RSC_URI));

		// creating properties
		createProperty(favouriteTeamIndv,
				CommonOntologyVocabulary.FOAF_NAME_URI, favouriteTeam.getName());
		return favouriteTeamIndv;
	}

	private void createFavouriteTeamProperties(FacebookUser facebookUser,
			Resource fbIndv) {
		List<NamedFacebookType> favoriteTeams = facebookUser.getUser()
				.getFavoriteTeams();
		if (favoriteTeams != null) {
			for (NamedFacebookType favouriteTeam : favoriteTeams) {
				if (favouriteTeam == null) {
					continue;
				}
				// creating favourite team individual
				Resource favouriteTeamIndv = createFavouriteTeamIndv(favouriteTeam);
				// creating favourite team property for facebook individual
				createProperty(fbIndv,
						FacebookOntologyVocabulary.FAVOURITE_TEAM_PRP_URI,
						favouriteTeamIndv);
			}
		}

	}

	/**
	 * creating location individual for event with found city and country
	 * resources on DBpedia
	 * 
	 * @param facebookVenue
	 * @param cityRsc
	 * @param countryRsc
	 * @return
	 */
	private Resource createLocationIndividualOfEvent(
			FacebookVenue facebookVenue, Resource cityRsc, Resource countryRsc) {
		// creating location individual
		Resource locationIndv = getModel()
				.createResource(
						createIndividualURI(facebookVenue.getId()),
						ResourceFactory
								.createResource(CommonOntologyVocabulary.GEODDATA_PLACE_URI));
		// creating city property of location
		createProperty(locationIndv, CommonOntologyVocabulary.CITY_PRP_URI,
				cityRsc);
		// creating country property of location
		createProperty(locationIndv, CommonOntologyVocabulary.COUNTRY_PRP_URI,
				countryRsc);
		return locationIndv;
	}

	private void createDateTypeProperty(Resource subject, String predicate,
			String dateText) {
		if (dateText != null) {
			// try to parse and add given date value
			try {
				addDateTypeProperty(subject, predicate, dateText);
			} catch (Exception e) {
				getLogger()
						.warn(MessageFormat
								.format("Given date value:\"{0}\" cannot be parsed: \"{1}\"",
										dateText, e.getMessage()));
			}
		}
	}

	/**
	 * This method adds date type property to given resource using given
	 * property uri
	 * 
	 * @param subject
	 * @param predicate
	 * @param dateText
	 */
	private void addDateTypeProperty(Resource subject, String predicate,
			String dateText) {
		Calendar calendar = Calendar.getInstance();
		// convert date text to date
		Date date = DateUtils.toDateFromLongFormat(dateText);
		// check if date is null
		if (date != null) {
			// set date of calendar instance
			calendar.setTime(date);
			// create property with edited calendar value
			createProperty(subject, predicate,
					ResourceFactory.createTypedLiteral(calendar));
		}
	}

	/**
	 * This method creates like properties of given Facebook {@link Resource}
	 * 
	 * @param facebookUser
	 *            {@link FacebookUser} instance contains Facebook user likes
	 * @param fbUserIndv
	 *            {@link Resource} instance that like properties will be added
	 *            to
	 */
	private void createLikeProperties(FacebookUser facebookUser,
			Resource fbUserIndv) {
		Logger logger = Logger.getLogger(FacebookIndividualCreator.class
				.getSimpleName());
		logger.log(Level.INFO, "likes are begginning to get");
		// get user likes
		ArrayList<Like> likes = facebookUser.getLikes();
		if ((likes != null) && (!likes.isEmpty())) {
			logger.log(Level.INFO, "likes has been got");
			Iterator<Like> iterator = likes.iterator();
			// iterate on each like
			while (iterator.hasNext()) {
				logger.log(Level.INFO, "like individual is beginning to create");
				Like like = (Like) iterator.next();
				// create like individual
				Resource likeIndv = createLikeIndividual(like);
				// add like property to the user
				createProperty(fbUserIndv, FacebookOntologyVocabulary.LIKES,
						likeIndv);
				// log...
				String ptrn = "Like individual has been created Like[id={0},category={1},name={2},date={3}]";
				String logMsg = MessageFormat.format(ptrn, like.getId(),
						like.getCategory(), like.getName(), like.getDate());
				logger.log(Level.INFO, logMsg);
			}
		} else {
			logger.log(Level.INFO, "likes has not been got");
		}
	}

	/**
	 * This method creates like resource using category value of given
	 * {@link Like} object
	 * 
	 * @param like
	 * @return
	 */
	private Resource createLikeIndividual(Like like) {

		/**
		 * create like individual
		 */
		// get like catogory and create like individual selecting correct
		// category type.
		String likeCategory = like.getCategory();
		Resource likeIndv = null;
		if (likeCategory != null) {
			if (likeCategory.equals("Musician/band")
					|| likeCategory.equals("Music")) {
				likeIndv = createLikeIndividualWithType(like,
						MUSIC_RSC_CONSTANT, CommonOntologyVocabulary.MUSIC_URI);
			} else if (likeCategory.equals("Movie")) {
				likeIndv = createLikeIndividualWithType(like,
						MOVIE_RSC_CONSTANT, CommonOntologyVocabulary.MOVIE_URI);
			} else if (likeCategory.equals("Community")) {
				likeIndv = createLikeIndividualWithType(like,
						COMMUNITY_RSC_CONSTANT,
						CommonOntologyVocabulary.SIOC_COMMUNITY);
			} else if (likeCategory.equals("Author")) {
				likeIndv = createLikeIndividualWithType(like,
						AUTHOR_RSC_CONSTANT,
						FacebookOntologyVocabulary.AUTHOR_URI);
			} else if (likeCategory.equals("Book")) {
				likeIndv = createLikeIndividualWithType(like,
						BOOK_RSC_CONSTANT, CommonOntologyVocabulary.BOOK_URI);
			} else if (likeCategory.equals("Professional sports team")) {
				likeIndv = createLikeIndividualWithType(like,
						TEAM_RSC_CONSTANT,
						FacebookOntologyVocabulary.TEAM_RSC_URI);
			} else if (likeCategory.equals("Tv show")) {
				likeIndv = createLikeIndividualWithType(like,
						TV_SHOW_RSC_CONSTANT,
						FacebookOntologyVocabulary.TV_SHOW_RSC_URI);
			} else if (likeCategory.equals("Producer")) {
				likeIndv = createLikeIndividualWithType(like,
						PRODUCER_RSC_CONSTANT,
						FacebookOntologyVocabulary.PRODUCER_RSC_URI);
			} else if (likeCategory.equals("Actor/director")) {
				likeIndv = createLikeIndividualWithType(like,
						ACTOR_DIRECTOR_RSC_CONSTANT,
						FacebookOntologyVocabulary.ACTOR_DIRECTOR_RSC_URI);
			} else {
				likeIndv = createLikeIndividualWithType(like,
						LIKE_RSC_CONSTANT, RDFS.Resource.getURI());
			}
		}

		// creating name and date properties of like individual
		createProperty(likeIndv, CommonOntologyVocabulary.FOAF_NAME_URI,
				like.getName());
		createDateTypeProperty(likeIndv,
				CommonOntologyVocabulary.DC_TERMS_DATE_PRP_URI, like.getDate());

		return likeIndv;
	}

	private Resource createLikeIndividualWithType(Like like, String category,
			String type) {
		Resource likeIndv = getModel().createResource(
				getBaseURI() + category + like.getId(),
				getModel().getResource(type));
		return likeIndv;
	}

	/**
	 * creating RDFS label, profile URL, and profile pricute URL properties of
	 * facebook individual
	 * 
	 * @param facebookUser
	 * @param facebookIndv
	 */
	private void createProfileForFacebookUser(FacebookUser facebookUser,
			Resource facebookIndv) {
		// getting profile info of facebook user
		List<JsonObject> profileSet = facebookUser.getProfileInfo();

		for (JsonObject profile : profileSet) {
			// getting profile url
			String userProfileURL = profile.getString("url");
			// creating foaf:page property with profile url value
			createProperty(facebookIndv,
					CommonOntologyVocabulary.FOAF_PAGE_PRP_URI,
					createDocumentIndv(userProfileURL));

			// creating foaf:depiction property with picture url value
			String pictureURL = profile.getString("pic");
			createProperty(facebookIndv,
					CommonOntologyVocabulary.FOAF_PICTURE_PRP_URI,
					createImageIndv(pictureURL));

			// creating rdfs:label property with name property value
			String userName = facebookUser.getUser().getName();
			createProperty(facebookIndv,
					CommonOntologyVocabulary.RDFS_LABEL_PRP_URI, userName);
		}

	}

	private void createWorkEmployerPrp(Resource workIndv,
			NamedFacebookType employer) {
		if (employer != null) {
			Resource employerIndv = getModel().createResource(
					getBaseURI() + employer.getId(),
					getModel().getResource(
							CommonOntologyVocabulary.FOAF_PERSON_RSC_URI));
			createProperty(employerIndv,
					CommonOntologyVocabulary.FOAF_NAME_URI, employer.getName());
			createProperty(workIndv, CommonOntologyVocabulary.EMPLOYER_PRP_URI,
					employerIndv);
		}
	}

	private Resource createWorkIndividual(Resource fbOntIndv, Work work) {
		Resource workIndv = null;
		if (work != null) {
			workIndv = getModel().createResource(
					getBaseURI() + "workhistory/" + UUID.randomUUID(),
					getModel().getResource(
							CommonOntologyVocabulary.WORK_HISTORY_URI));
			String description = work.getDescription();
			createProperty(workIndv,
					CommonOntologyVocabulary.CV_JOB_DESCRIPTION_PRP_URI,
					description);
			NamedFacebookType employer = work.getEmployer();
			createWorkEmployerPrp(workIndv, employer);
			createProperty(workIndv, CommonOntologyVocabulary.CV_START_DATE,
					work.getStartDate());
			createProperty(workIndv, CommonOntologyVocabulary.CV_END_DATE,
					work.getEndDate());

			NamedFacebookType position = work.getPosition();
			if (position != null) {
				createProperty(workIndv,
						CommonOntologyVocabulary.WORK_POSITION_PRP_URI,
						position.getName());
			}

			createWorkWithPrps(work, workIndv);
		}
		return workIndv;

	}

	private void createWorkProperties(FacebookUser facebookUser,
			Resource fbOntIndv) {
		List<Work> workList = facebookUser.getWorks();
		if (workList != null) {
			Iterator<Work> iterator = workList.iterator();
			while (iterator.hasNext()) {
				Work work = (Work) iterator.next();
				// creating work property
				Resource workIndv = createWorkIndividual(fbOntIndv, work);
				createProperty(fbOntIndv,
						CommonOntologyVocabulary.CV_HAS_WORK_HISTORY_PRP_URI,
						workIndv);
			}
		}
	}

	private void createWorkWithPrp(Resource workIndv, NamedFacebookType with) {
		if (with != null) {
			Resource withIndv = getModel().createResource(
					getBaseURI() + with.getId(),
					getModel().getResource(
							CommonOntologyVocabulary.FOAF_PERSON_RSC_URI));
			createProperty(withIndv, CommonOntologyVocabulary.FOAF_NAME_URI,
					with.getName());
			createProperty(workIndv, CommonOntologyVocabulary.WITH_PRP_URI,
					withIndv);
		}
	}

	private void createWorkWithPrps(Work work, Resource workIndv) {
		List<NamedFacebookType> withList = work.getWith();
		Iterator<NamedFacebookType> iterator = withList.iterator();
		while (iterator.hasNext()) {
			NamedFacebookType with = (NamedFacebookType) iterator.next();
			createWorkWithPrp(workIndv, with);
		}
	}

	private String creeateUserURI(FacebookUser facebookUser) {
		return createIndividualURI(facebookUser.getId());
	}

	/**
	 * This method returns Literal birthday value of {@link FacebookUser}
	 * instance
	 * 
	 * @param facebookUser
	 * @return
	 */
	private Literal getLiteralBirthday(FacebookUser facebookUser) {
		// transform birthdate To calendar format
		Calendar calendarBirthDate = DateTransformer
				.transformAmericanTextdateToCalendar(facebookUser.getUser()
						.getBirthday());
		// create typed literal value
		Literal birthDateLiteral = ResourceFactory
				.createTypedLiteral(calendarBirthDate);
		return birthDateLiteral;
	}

	/**
	 * Searches for an individual in the target model whose account name is
	 * given identifier.
	 * 
	 * @param facebookId
	 *            searched facebook identifier.
	 * @param targetModel
	 *            target model to check the already registered user.
	 * @return URI of the existent resource. <code>null</code> if there is no
	 *         such a resource.
	 */
	private String getOwnerURI(String facebookId, Model targetModel) {
		String queryPtrn = "PREFIX foaf:<%s> SELECT * {?user foaf:account ?account. ?account foaf:accountServiceHomepage <%s>. ?account foaf:accountName \"%s\".}";
		String queryString = String.format(queryPtrn, FOAF.getURI(),
				FacebookOntologyVocabulary.FACEBOOK_HOMEPAGE_URI, facebookId);
		// create query execution.
		QueryExecution execution = QueryExecutionFactory.create(queryString,
				targetModel);
		ResultSet resultSet = execution.execSelect();
		if (resultSet.hasNext()) {
			String userURI = resultSet.nextSolution().getResource("user")
					.getURI();
			String logPtrn = "Already registered friend search result has been obtained with value: {0}";
			getLogger().info(MessageFormat.format(logPtrn, userURI));
			return userURI;
		}
		return null;
	}

	/**
	 * checks for the created rule
	 * 
	 * @return
	 */
	private Model reason(Reasoner reasoner) throws Exception {
		// Get model from individual creator
		Model rawData = getModel();
		// Get InfModel
		InfModel infModel = ModelFactory.createInfModel(reasoner, rawData);
		return infModel;
	}

	private void reasonFamilyBond(Resource fbIndv, Resource familyMemberIndv) {
		Model schema = FileManager.get().loadModel(
				CommonOntologyVocabulary.FAMILY_BOND_URI);

		PrintUtil.registerPrefix("f", CommonOntologyVocabulary.FAMILY_BOND_URI);
		// define rule
		String rules = "[rule1: (?a f:has_son ?b) (?b f:has_brother ?c) -> (?a f:has_son ?c)]";
		Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
		reasoner.setDerivationLogging(true);
		// finds red mullet depends to alg
		Model reasonedModel;
		try {
			reasonedModel = reason(reasoner);
			reasoner = ReasonerRegistry.getOWLReasoner();
			reasoner.bindSchema(schema);
			ModelFactory.createInfModel(reasoner, reasonedModel);
			reasonedModel.write(new FileOutputStream("files/infSocsem2.owl"),
					FileUtils.langXMLAbbrev);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String selectRelationshipType(String relationship) {
		FamilyBond.RELATIONSHIPS relationshipValue = FamilyBond.RELATIONSHIPS
				.valueOf(relationship);
		switch (relationshipValue) {
		case husband: {
			return FacebookOntologyVocabulary.HUSBAND_PRP_URI;
		}
		case brother: {
			return FacebookOntologyVocabulary.BROTHER_PRP_URI;
		}
		case father: {
			return FacebookOntologyVocabulary.FATHER_PRP_URI;
		}
		case son: {
			return FacebookOntologyVocabulary.CHILD_PRP_URI;
		}
		case uncle: {
			return FacebookOntologyVocabulary.UNCLE_PRP_URI;
		}
		case nephew: {
			return FacebookOntologyVocabulary.NEPHEW_PRP_URI;
		}
		case grandfather: {
			return FacebookOntologyVocabulary.GRANDFATHER_PRP_URI;
		}
		case grandson: {
			return FacebookOntologyVocabulary.GRANDCHILD_PRP_URI;
		}
		case wife: {
			return FacebookOntologyVocabulary.WIFE_PRP_URI;
		}
		case sister: {
			return FacebookOntologyVocabulary.SISTER_PRP_URI;
		}
		case daughter: {
			return FacebookOntologyVocabulary.CHILD_PRP_URI;
		}
		case aunt: {
			return FacebookOntologyVocabulary.AUNT_PRP_URI;
		}
		case niece: {
			return FacebookOntologyVocabulary.NIECE_PRP_URI;
		}
		case grandmother: {
			return FacebookOntologyVocabulary.GRANDMOTHER_PRP_URI;
		}
		case granddaughter: {
			return FacebookOntologyVocabulary.GRANDCHILD_PRP_URI;
		}
		case partner: {
			return FacebookOntologyVocabulary.PARTNER_PRP_URI;
		}
		case cousin: {
			return FacebookOntologyVocabulary.COUSIN_PRP_URI;
		}
		default:
			break;
		}
		return null;
	}

}

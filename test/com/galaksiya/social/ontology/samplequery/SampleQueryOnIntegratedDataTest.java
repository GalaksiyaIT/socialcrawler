package com.galaksiya.social.ontology.samplequery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.galaksiya.social.query.SampleQueries;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

/**
 * This class tests example complex sparql queries contained in query page.
 * <p>
 * FIXME: Sorgular ve senaryo triple store'a kaydedildiği şekilde düzenlecek.
 * 
 * @author galaksiya
 * 
 */
public class SampleQueryOnIntegratedDataTest {

	private static final String ALTAY = "Altay";
	private static final String BUCA_GOLET = "Buca Gölet";
	private static final String BUCASPOR = "Bucaspor";
	// names for cities
	private static final String BURSA = "Bursa";
	// names for teams
	private static final String BURSASPOR = "Bursaspor";
	private static final String CANDAN_ERCETIN = "Candan Erçetin";
	// auto increasing id for checkins
	private static int checkinId = 0;
	private static final String FENERBAHCE = "Fenerbahçe";

	private static final String FORUM_BORNOVA = "Forum Bornova";
	private static final String GALATASARAY = "Galatasaray";
	private static final String INDIVIDUAL_BASE_URI = "http://sociallinker.galaksiya.com/resource";
	private static final String INDV_PATTERN_OF_ERDEM = "erdemekinci";
	// individual patterns for persons
	private static final String INDV_PATTERN_OF_TAYFUN = "tayfungokmenhalac";
	private static final String INDV_PATTERN_OF_ZIYA = "ziyaakar";
	private static final String INPUT_NAME = "Input Name";

	private static final String IZMIR = "İzmir";
	// names for music
	private static final String LINKIN_PARK = "Linkin Park";
	private static final String MOTLEY_CRUE = "Mötley Crüe";
	private static final String NAME_OF_ERDEM = "Erdem Eser Ekinci";
	private static final String NAME_OF_TAYFUN = "Tayfun Gökmen Halaç";

	private static final String NAME_OF_ZIYA = "Ziya Akar";
	private static final String ORHAN_BABA = "Orhan Gencebay";

	// names for venues
	private static final String SAAT_KULESI = "Saat Kulesi";
	private static final String SILA = "Sıla";
	private static final String TELEFERIK = "Teleferik";
	private static final String WAFFLECI_AKIN = "Wafflecı Akın";
	private static final String YASAR = "Yaşar";

	private static final String YILDIZ_TILBE = "Yıldız Tilbe";

	/**
	 * Creates a property for the given propURI and generates a statement for
	 * this property with subject indv and object value.
	 * 
	 * @param subjectIndv
	 *            Subject of the statement
	 * @param propURI
	 *            Predicate of the statement
	 * @param objectValue
	 *            Object literal of the statement.
	 */
	public static void createProperty(Resource subjectIndv, String propURI,
			Object objectValue) {
		if (subjectIndv != null && objectValue != null) {
			Property retrievedPrp = ResourceFactory.createProperty(propURI);
			subjectIndv.addProperty(retrievedPrp, "" + objectValue);
		}
	}

	/**
	 * Creates a property for the given propURI and generates a statement for
	 * this property with subject indv and object indv.
	 * 
	 * @param subjectIndv
	 * @param propUri
	 * @param objectIndv
	 */
	public static void createProperty(Resource subjectIndv, String propUri,
			Resource objectIndv) {
		if (subjectIndv != null && objectIndv != null) {
			Property property = ResourceFactory.createProperty(propUri);
			subjectIndv.addProperty(property, objectIndv);
		}
	}

	private Logger logger = Logger.getLogger(this.getClass());

	// individual ontology model
	private OntModel ontModel;

	/**
	 * asserts the sparql query that retrieves friends that have been checked in
	 * one of my checkin places
	 * 
	 * @throws Exception
	 */
	@Test
	public void assertAllQueries() throws Exception {
		// generates query scenario on ontModel
		generateSampleScenarioOnOntModel();
		// asserting all queries
		assertVenuesThatCheckedInBySomeone();
		assertFriendWhoCheckedInSamePlaceWithMe();
		assertLikedMusicByFriendsWhoCheckedInSamePlaceWithMe();
		assertFriendsFootballFanOfSameTeamWithMe();
		assertFriendsFootballFanOfSameTeamAndLocationWithMe();
		assertCheckinsOfFriendsFootballFanOfSameTeamAndLocationWithMe();

	}

	public void assertCheckinsOfFriendsFootballFanOfSameTeamAndLocationWithMe()
			throws Exception {
		logger.info("Checkins of friends who is both fan of same football teams and lives same location with me: ");
		String query = SampleQueries.CHECKINS_OF_FRIENDS_BOTH_FOOTBALL_FAN_OF_SAME_TEAM_AND_LOCATION_SELECT_QUERY
				.replace(INPUT_NAME, NAME_OF_ERDEM);
		ArrayList<QuerySolution> solutionList = getSolutionList(query);
		// generating real value list
		ArrayList<String> realValueList = new ArrayList<String>();
		realValueList.add(SAAT_KULESI);
		realValueList.add(BUCA_GOLET);
		realValueList.add(WAFFLECI_AKIN);
		// asserting query retrieves values correctly
		assertListWithRealValues(solutionList, realValueList, "venueName");
	}

	public void assertFriendsFootballFanOfSameTeamAndLocationWithMe()
			throws Exception {
		logger.info("Friend names who is both fan of same football teams and lives same location with me: ");

		String query = SampleQueries.FRIENDS_BOTH_FOOTBALL_FAN_OF_SAME_TEAM_AND_LOCATION_SELECT_QUERY
				.replace(INPUT_NAME, NAME_OF_ERDEM);
		ArrayList<QuerySolution> solutionList = getSolutionList(query);
		// generating real value list
		ArrayList<String> realValueList = new ArrayList<String>();
		realValueList.add(NAME_OF_TAYFUN);
		// asserting query retrieves values correctly
		assertListWithRealValues(solutionList, realValueList, "friendName");
	}

	public void assertFriendsFootballFanOfSameTeamWithMe() throws Exception {
		logger.info("Friend names who is fan of same football teams with me: ");
		String query = SampleQueries.FRIENDS_FOOTBALL_FAN_OF_SAME_TEAM_SELECT_QUERY
				.replace(INPUT_NAME, NAME_OF_ERDEM);
		ArrayList<QuerySolution> solutionList = getSolutionList(query);
		// generating real value list
		ArrayList<String> realValueList = new ArrayList<String>();
		realValueList.add(NAME_OF_ZIYA);
		realValueList.add(NAME_OF_TAYFUN);
		// asserting query retrieves values correctly
		assertListWithRealValues(solutionList, realValueList, "friendName");
	}

	public void assertFriendWhoCheckedInSamePlaceWithMe() throws Exception {
		logger.info("Friend names who checked in same place with me : ");
		String query = SampleQueries.FRIENDS_WHO_CHECKED_IN_SAME_PLACE_WITH_ME
				.replace(INPUT_NAME, NAME_OF_ERDEM);
		ArrayList<QuerySolution> actualList = getSolutionList(query);
		// generating real value list
		ArrayList<String> expectedList = new ArrayList<String>();
		expectedList.add(NAME_OF_ZIYA);
		expectedList.add(NAME_OF_TAYFUN);
		// asserting query retrieves values correctly
		assertListWithRealValues(actualList, expectedList, "friendName");
	}

	public void assertLikedMusicByFriendsWhoCheckedInSamePlaceWithMe()
			throws Exception {
		logger.info("Liked of friends who checked in same place with me : ");
		String query = SampleQueries.LIKED_MUSICS_BY_FRIENDS_WHO_CHECKED_IN_SAME_PLACE_WITH_ME
				.replace(INPUT_NAME, NAME_OF_ERDEM);
		ArrayList<QuerySolution> solutionList = getSolutionList(query);
		// generating real value list
		ArrayList<String> realValueList = new ArrayList<String>();
		realValueList.add(CANDAN_ERCETIN);
		realValueList.add(YASAR);
		realValueList.add(ORHAN_BABA);
		realValueList.add(MOTLEY_CRUE);
		realValueList.add(SILA);
		// asserting query retrieves values correctly
		assertListWithRealValues(solutionList, realValueList, "musicName");
	}

	public void assertVenuesThatCheckedInBySomeone() throws Exception {
		logger.info("Venues that I've checked in : ");
		String query = SampleQueries.CHECKIN_VENUE_SELECT_QUERY.replace(
				INPUT_NAME, NAME_OF_TAYFUN);
		ArrayList<QuerySolution> actualList = getSolutionList(query);

		// generating real value list
		ArrayList<String> expectedList = new ArrayList<String>();
		expectedList.add(SAAT_KULESI);
		expectedList.add(BUCA_GOLET);
		expectedList.add(WAFFLECI_AKIN);
		// asserting query retrieves values correctly
		assertListWithRealValues(actualList, expectedList, "venueName");
	}

	public void createCheckinProperties(Individual personIndv,
			ArrayList<Individual> venueList) {
		// creating checkin properties with given venue list
		for (Individual venueIndv : venueList) {
			Individual checkinIndv = createCheckinIndividual("checkin"
					+ (++checkinId), venueIndv);
			createProperty(personIndv,
					FoursquareOntologyVocabulary.CHECKIN_PRP_URI, checkinIndv);
		}
	}

	/**
	 * creates a person individual with given uriPattern, name and checkinList
	 * 
	 * @param uriPattern
	 * @param name
	 * @param venueList
	 * @param locationIndv
	 * @return
	 */
	public Individual createFoursquarePersonIndividual(String uriPattern,
			String name, ArrayList<Individual> venueList) {
		// creating self foursquare individual
		Individual foursquarePersonIndv = ontModel.createIndividual(
				INDIVIDUAL_BASE_URI + uriPattern,
				CommonOntologyVocabulary.FOAF_PERSON_RSC);

		// creating name property
		createProperty(foursquarePersonIndv,
				CommonOntologyVocabulary.FOAF_NAME_URI, name);

		createCheckinProperties(foursquarePersonIndv, venueList);

		return foursquarePersonIndv;
	}

	/**
	 * initializes individual ontModel environment for sample queries
	 */
	public void generateSampleScenarioOnOntModel() {

		// creating sample locations
		Individual izmir = createLocationIndividual("loc1", IZMIR);
		Individual bursa = createLocationIndividual("loc2", BURSA);

		// create sample team individuals
		Individual galatasaray = createTeamIndividual("team1", GALATASARAY);
		Individual fenerbahce = createTeamIndividual("team2", FENERBAHCE);
		Individual bucaspor = createTeamIndividual("team3", BUCASPOR);
		Individual altay = createTeamIndividual("team4", ALTAY);
		Individual bursaspor = createTeamIndividual("team5", BURSASPOR);

		// creating sample venue individuals
		Individual forumBornova = createVenueIndividual("venue1", FORUM_BORNOVA);
		Individual teleferik = createVenueIndividual("venue2", TELEFERIK);
		Individual bucaGolet = createVenueIndividual("venue3", BUCA_GOLET);
		Individual waffleciAkin = createVenueIndividual("venue5", WAFFLECI_AKIN);
		Individual saatKulesi = createVenueIndividual("venue4", SAAT_KULESI);

		// creating checkin list for self
		ArrayList<Individual> checkinVenueListOfMine = new ArrayList<Individual>();
		checkinVenueListOfMine.add(forumBornova);
		checkinVenueListOfMine.add(teleferik);
		checkinVenueListOfMine.add(bucaGolet);

		// creating foursquare individual for self
		Individual selfFoursquareIndv = createFoursquarePersonIndividual(
				INDV_PATTERN_OF_ERDEM, NAME_OF_ERDEM, checkinVenueListOfMine);

		// creating checkin list for friend-1
		ArrayList<Individual> checkinVenueListOfFriend1 = new ArrayList<Individual>();
		checkinVenueListOfFriend1.add(saatKulesi);
		checkinVenueListOfFriend1.add(forumBornova);
		checkinVenueListOfFriend1.add(saatKulesi);
		// creating foursquare individual for friend-1
		Individual friendFoursquareIndv1 = createFoursquarePersonIndividual(
				INDV_PATTERN_OF_ZIYA, NAME_OF_ZIYA, checkinVenueListOfFriend1);

		// creating checkin list for friend-2
		ArrayList<Individual> checkinVenueListOfFriend2 = new ArrayList<Individual>();
		checkinVenueListOfFriend2.add(waffleciAkin);
		checkinVenueListOfFriend2.add(saatKulesi);
		checkinVenueListOfFriend2.add(bucaGolet);

		// creating foursquare individual for friend-2
		Individual friendFoursquareIndv2 = createFoursquarePersonIndividual(
				INDV_PATTERN_OF_TAYFUN, NAME_OF_TAYFUN,
				checkinVenueListOfFriend2);

		// adding foaf:knows properties to self individual for Foursquare
		createProperty(selfFoursquareIndv,
				CommonOntologyVocabulary.FOAF_KNOWS_URI, friendFoursquareIndv1);
		createProperty(selfFoursquareIndv,
				CommonOntologyVocabulary.FOAF_KNOWS_URI, friendFoursquareIndv2);

		Individual montleyCrue = createMusicLikeIndividual("music1",
				MOTLEY_CRUE);
		Individual orhanBaba = createMusicLikeIndividual("music2", ORHAN_BABA);
		Individual sila = createMusicLikeIndividual("music3", SILA);
		Individual candanErcetin = createMusicLikeIndividual("music4",
				CANDAN_ERCETIN);
		Individual yasar = createMusicLikeIndividual("music5", YASAR);
		Individual yildizTilbe = createMusicLikeIndividual("music6",
				YILDIZ_TILBE);
		Individual linkinPark = createMusicLikeIndividual("music7", LINKIN_PARK);

		// creating music likes of mine
		ArrayList<Individual> musicLikeListOfMine = new ArrayList<Individual>();
		musicLikeListOfMine.add(yasar);
		musicLikeListOfMine.add(yildizTilbe);
		musicLikeListOfMine.add(linkinPark);
		musicLikeListOfMine.add(orhanBaba);

		// creating favourite teams of mine
		ArrayList<Individual> teamListOfMine = new ArrayList<Individual>();
		teamListOfMine.add(galatasaray);
		teamListOfMine.add(bucaspor);

		// creating facebook individual for self
		Individual selfFacebookIndv = createFacebookPersonIndividual(
				INDV_PATTERN_OF_ERDEM, NAME_OF_ERDEM, musicLikeListOfMine,
				teamListOfMine, izmir);

		// creating music likes of friend-1
		ArrayList<Individual> musicLikeListOfFriend1 = new ArrayList<Individual>();
		musicLikeListOfFriend1.add(yasar);
		musicLikeListOfFriend1.add(montleyCrue);
		musicLikeListOfFriend1.add(orhanBaba);

		// creating favourite teams of friend-1
		ArrayList<Individual> teamListOfFriend1 = new ArrayList<Individual>();
		teamListOfFriend1.add(galatasaray);
		teamListOfFriend1.add(bursaspor);

		// creating facebook individual for self
		Individual friendFacebookIndv1 = createFacebookPersonIndividual(
				INDV_PATTERN_OF_ZIYA, NAME_OF_ZIYA, musicLikeListOfFriend1,
				teamListOfFriend1, bursa);

		// creating music likes of friend-2
		ArrayList<Individual> musicLikeListOfFriend2 = new ArrayList<Individual>();
		musicLikeListOfFriend2.add(sila);
		musicLikeListOfFriend2.add(candanErcetin);

		// creating favourite teams of friend-2
		ArrayList<Individual> teamListOfFriend2 = new ArrayList<Individual>();
		teamListOfFriend2.add(fenerbahce);
		teamListOfFriend2.add(altay);
		teamListOfFriend2.add(bucaspor);

		// creating facebook individual for self
		Individual friendFacebookIndv2 = createFacebookPersonIndividual(
				INDV_PATTERN_OF_TAYFUN, NAME_OF_TAYFUN, musicLikeListOfFriend2,
				teamListOfFriend2, izmir);

		// adding foaf:knows properties to self individual for Facebook
		createProperty(selfFacebookIndv,
				CommonOntologyVocabulary.FOAF_KNOWS_URI, friendFacebookIndv1);
		createProperty(selfFacebookIndv,
				CommonOntologyVocabulary.FOAF_KNOWS_URI, friendFacebookIndv2);
	}

	/**
	 * initialize ontModel
	 */
	@Before
	public void initializeTestEnvironment() {
		this.ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
	}

	/**
	 * asserts solution list with real value list
	 * 
	 * @param actualList
	 *            list of query solutions
	 * @param expectedList
	 *            list of real values
	 * @param queryParam
	 *            key value for query solution
	 */
	private void assertListWithRealValues(ArrayList<QuerySolution> actualList,
			ArrayList<String> expectedList, String queryParam) {

		// assert both solution list and real value list are not null
		assertNotNull(actualList);
		assertNotNull(expectedList);

		// assert solution list and real value list are same size at the start
		assertEquals(expectedList.size(), actualList.size());

		// traverse on solution list
		for (QuerySolution solution : actualList) {
			// get value for given parameter
			RDFNode solutionNode = solution.get(queryParam);
			// assert for to be not null
			assertNotNull(solutionNode);
			// get string value of solutionNode
			String solutionString = solutionNode.toString();
			// control to real value list contains solution String
			assertTrue(expectedList.contains(solutionString));
			// remove solution String from real value list
			expectedList.remove(solutionString);
		}
		// assert real value list is empty at the and
		assertTrue(expectedList.isEmpty());
	}

	/**
	 * creates checkin individual with given id and venue individual
	 * 
	 * @param id
	 * @param venueIndv
	 * @return
	 */
	private Individual createCheckinIndividual(String id, Individual venueIndv) {
		Individual checkinIndv = ontModel.createIndividual(INDIVIDUAL_BASE_URI
				+ id, FoursquareOntologyVocabulary.CHECKIN_RSC);
		createProperty(checkinIndv, FoursquareOntologyVocabulary.VENUE_PRP_URI,
				venueIndv);
		return checkinIndv;
	}

	private Individual createFacebookPersonIndividual(String uriPattern,
			String name, ArrayList<Individual> musicLikeList,
			ArrayList<Individual> teamList, Individual locationIndv) {

		// creating self facebook individual
		Individual facebookPersonIndv = ontModel.createIndividual(
				INDIVIDUAL_BASE_URI + uriPattern,
				CommonOntologyVocabulary.FOAF_PERSON_RSC);

		// creating name property
		createProperty(facebookPersonIndv,
				CommonOntologyVocabulary.FOAF_NAME_URI, name);

		// creating checkin properties with given venue list
		for (Individual musicIndv : musicLikeList) {
			createProperty(facebookPersonIndv,
					FacebookOntologyVocabulary.LIKES, musicIndv);
		}

		// creating favourite team properties with given team list
		for (Individual teamIndv : teamList) {
			createProperty(facebookPersonIndv,
					FacebookOntologyVocabulary.FAVOURITE_TEAM_PRP_URI, teamIndv);
		}

		createProperty(facebookPersonIndv,
				CommonOntologyVocabulary.LOCATION_URI, locationIndv);
		return facebookPersonIndv;
	}

	/**
	 * creates a location individual with given id and name values
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	private Individual createLocationIndividual(String id, String name) {
		Individual locationIndv = ontModel.createIndividual(INDIVIDUAL_BASE_URI
				+ id, CommonOntologyVocabulary.GEODATA_PLACE_RSC);
		createProperty(locationIndv, CommonOntologyVocabulary.FOAF_NAME_URI,
				name);
		return locationIndv;
	}

	private Individual createMusicLikeIndividual(String id, String name) {
		Individual musicIndv = ontModel.createIndividual(INDIVIDUAL_BASE_URI
				+ id, CommonOntologyVocabulary.MUSIC_RSC);
		createProperty(musicIndv, CommonOntologyVocabulary.FOAF_NAME_URI, name);
		return musicIndv;
	}

	/**
	 * creates a team individual with given id and name values
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	private Individual createTeamIndividual(String id, String name) {
		Individual teamIndv = ontModel.createIndividual(INDIVIDUAL_BASE_URI
				+ id, FacebookOntologyVocabulary.TEAM_RSC);
		createProperty(teamIndv, CommonOntologyVocabulary.FOAF_NAME_URI, name);
		return teamIndv;
	}

	/**
	 * creates venue individual with given id and name
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	private Individual createVenueIndividual(String id, String name) {
		Individual venueIndv = ontModel.createIndividual(INDIVIDUAL_BASE_URI
				+ id, FoursquareOntologyVocabulary.VENUE_RSC);
		createProperty(venueIndv, CommonOntologyVocabulary.FOAF_NAME_URI, name);
		return venueIndv;
	}

	/**
	 * Asserts given select query on an ontology model
	 * 
	 * @param queryToBeSelected
	 * @throws Exception
	 */
	private ArrayList<QuerySolution> getSolutionList(String queryToBeSelected)
			throws Exception {
		// creating query operation
		QueryExecution qexec = QueryExecutionFactory.create(
				QueryFactory.create(queryToBeSelected), ontModel);
		// getting result set with a given query on an ontModel
		ResultSet nameResultSetActual = qexec.execSelect();

		ArrayList<QuerySolution> solutionList = new ArrayList<QuerySolution>();
		// iterating on result set
		while (nameResultSetActual.hasNext()) {
			// getting query solution
			QuerySolution solution = (QuerySolution) nameResultSetActual.next();
			logger.info(MessageFormat.format("Query solution : {0}", solution));
			solutionList.add(solution);
		}
		return solutionList;
	}

}

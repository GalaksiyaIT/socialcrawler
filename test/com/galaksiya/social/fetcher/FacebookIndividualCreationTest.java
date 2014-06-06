package com.galaksiya.social.fetcher;

import static org.junit.Assert.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.galaksiya.social.TestUtil;
import com.galaksiya.social.entity.FacebookUser;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.galaksiya.social.vocabulary.FacebookTestVocabulary;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.User;

public class FacebookIndividualCreationTest {

	private static FacebookIndividualCreator creator;
	private static Resource facebookIndividual;
	private static FacebookUser facebookUser;
	private static Logger logger = Logger
			.getLogger(FacebookIndividualCreationTest.class);

	/**
	 * Creates a {@link FacebookIndividualCreator} and creates individual for
	 * Facebook social network by turning social data into rdf.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void beforeClass() throws Exception {
		// create an individual creator
		creator = new FacebookIndividualCreator(CommonTestVocabulary.BASE_URI);
		// create facebook individual
		createFacebookIndividual();
	}

	@Test
	public void checkEducationsOfSamePersonsAreSame() throws Exception {

		// get education statements of first created user
		StmtIterator firstEduStmtIter = facebookIndividual
				.listProperties(CommonOntologyVocabulary.EDUCATION_PRP);
		// get statement list
		List<Statement> firstEduStmtList = turnIntoList(firstEduStmtIter);

		// create new the same facebook individual again and so her eduaction
		// properties
		Resource newFbIndv = createNewFacebookIndividual();

		// get education statements of first created user
		StmtIterator secondEduStmtIter = newFbIndv
				.listProperties(CommonOntologyVocabulary.EDUCATION_PRP);
		// get statement list
		List<Statement> secondEduStmtList = turnIntoList(secondEduStmtIter);

		// check list sizes
		assertEquals(firstEduStmtList.size(), secondEduStmtList.size());

		// assert education resources of statements are same in the two list
		for (int i = 0; i < firstEduStmtList.size(); i++) {
			assertEquals(firstEduStmtList.get(i).getObject(), secondEduStmtList
					.get(i).getObject());
		}

	}
	
	@Test
	public void checkWorksOfSamePersonsAreSame() throws Exception {

		// get work statements of first created user
		StmtIterator firstWorkStmtIter = facebookIndividual
				.listProperties(CommonOntologyVocabulary.WORK_HISTORY_PRP);
		// get statement list
		List<Statement> firstWorkStmtList = turnIntoList(firstWorkStmtIter);

		// create new the same facebook individual again and so her work
		// properties
		Resource newFbIndv = createNewFacebookIndividual();

		// get work statements of first created user
		StmtIterator secondWorkStmtIter = newFbIndv
				.listProperties(CommonOntologyVocabulary.WORK_HISTORY_PRP);
		// get statement list
		List<Statement> secondWorkStmtList = turnIntoList(secondWorkStmtIter);

		// check list sizes
		assertEquals(firstWorkStmtList.size(), secondWorkStmtList.size());

		// assert work resources of statements are same in the two list
		for (int i = 0; i < firstWorkStmtList.size(); i++) {
			assertEquals(firstWorkStmtList.get(i).getObject(), secondWorkStmtList
					.get(i).getObject());
		}

	}

	private Resource createNewFacebookIndividual() throws SocialAPIException {
		FacebookIndividualCreator creator = new FacebookIndividualCreator(
				CommonTestVocabulary.BASE_URI);
		// creating FacebookUser instance
		FacebookUser facebookUser = TestUtil
				.createFacebookUser(FacebookTestVocabulary.ACCESS_TOKEN_CONTAINS_ALL_INFORMATION);
		facebookUser.setId(FacebookTestVocabulary.AMELIA_FACEBOOK_ID);
		Resource facebookIndividual = creator
				.createPersonIndividual(facebookUser);
		creator.createFriendProperties(facebookUser, facebookIndividual);
		return facebookIndividual;
	}

	private List<Statement> turnIntoList(StmtIterator stmtIter) {
		List<Statement> stmtList = new ArrayList<Statement>();

		if (stmtIter != null) {
			while (stmtIter.hasNext()) {
				Statement statement = (Statement) stmtIter.next();
				stmtList.add(statement);
			}
		}
		return stmtList;
	}

	/**
	 * this method checks whether user individual has been created correctly.
	 * 
	 * @throws Exception
	 */
	@Test
	public void userCreation() throws Exception {

		/**
		 * check user URI is correct
		 */
		assertEquals(CommonTestVocabulary.BASE_URI
				+ FacebookTestVocabulary.AMELIA_FACEBOOK_ID,
				facebookIndividual.getURI());

		/**
		 * check type property...
		 */
		Statement typeStmt = facebookIndividual.getProperty(RDF.type);
		assertNotNull(typeStmt);
		RDFNode typeValue = typeStmt.getObject();
		assertNotNull(typeValue);
		assertEquals(FOAF.Person, typeValue.asResource());

		/**
		 * check name property...
		 */
		Statement nameStmt = facebookIndividual.getProperty(FOAF.name);
		assertNotNull(nameStmt);
		RDFNode nameValue = nameStmt.getObject();
		assertNotNull(nameValue);
		String name = nameValue.asLiteral().getString();
		assertEquals(FacebookTestVocabulary.PERSON_NAME, name);

		/**
		 * check depiction property...
		 */

		StmtIterator depictionPrpIter = facebookIndividual
				.listProperties(ResourceFactory
						.createProperty(CommonOntologyVocabulary.FOAF_PICTURE_PRP_URI));
		// generating actual depiction value list
		ArrayList<Object> depictionList = new ArrayList<Object>();
		depictionList.add(ResourceFactory
				.createResource(FacebookTestVocabulary.DEPICTION_1));
		depictionList.add(ResourceFactory
				.createResource(FacebookTestVocabulary.DEPICTION_2));

		// iterating on depiction statements and controlling whether expected
		// depiction list contains property value
		checkDepictions(depictionPrpIter, depictionList);

		/**
		 * check label property...
		 */
		Statement labelStmt = facebookIndividual.getProperty(RDFS.label);
		assertNotNull(labelStmt);
		RDFNode labelValue = labelStmt.getObject();
		assertNotNull(labelValue);
		String label = labelValue.asLiteral().getString();
		assertEquals(FacebookTestVocabulary.LABEL, label);

		/**
		 * check page property...
		 */
		Statement pageStmt = facebookIndividual.getProperty(FOAF.page);
		assertNotNull(labelStmt);
		RDFNode pageValue = pageStmt.getObject();
		assertNotNull(labelValue);
		Resource pageActualRsc = pageValue.asResource();
		Resource pageExpectedRsc = ResourceFactory
				.createResource(FacebookTestVocabulary.PAGE_URI);
		assertEquals(pageExpectedRsc, pageActualRsc);

		/**
		 * userCreation check hometown property...
		 */

		Statement hometownStmt = facebookIndividual.getProperty(ResourceFactory
				.createProperty(CommonOntologyVocabulary.HOMETOWN_URI));
		assertNotNull(hometownStmt);
		RDFNode hometownValue = hometownStmt.getObject();
		assertNotNull(hometownValue);
		Resource hometownRsc = hometownValue.asResource();
		// asserting hometown resource
		logger.info(MessageFormat.format("Hometown resource URI is: {0}",
				hometownRsc.getURI()));
		assertEquals(CommonOntologyVocabulary.PLACE_RSC,
				hometownRsc.getPropertyResourceValue(RDF.type));
		assertEquals(FacebookTestVocabulary.AMSTERDAM,
				hometownRsc.getProperty(FOAF.name).getObject().asLiteral()
						.getString());

		/**
		 * check location property...
		 */

		Statement locationStmt = facebookIndividual.getProperty(ResourceFactory
				.createProperty(CommonOntologyVocabulary.LOCATION_URI));
		assertNotNull(locationStmt);
		RDFNode locationValue = locationStmt.getObject();
		assertNotNull(locationValue);
		Resource locationRsc = locationValue.asResource();
		// asserting location resource
		assertEquals(CommonOntologyVocabulary.PLACE_RSC,
				locationRsc.getPropertyResourceValue(RDF.type));
		assertEquals(FacebookTestVocabulary.CALIFORNIA, locationRsc
				.getProperty(FOAF.name).getObject().asLiteral().getString());

		/**
		 * check email property...
		 */

		Statement emailStmt = facebookIndividual.getProperty(ResourceFactory
				.createProperty(CommonOntologyVocabulary.SIOC_EMAIL_URI));
		assertNotNull(emailStmt);
		RDFNode emailValue = emailStmt.getObject();
		assertNotNull(emailValue);
		String email = emailValue.asLiteral().getString();
		assertEquals(FacebookTestVocabulary.EMAIL_VALUE, email);

		/**
		 * check gender property...
		 */

		Statement genderStmt = facebookIndividual.getProperty(FOAF.gender);
		assertNotNull(genderStmt);
		RDFNode genderValue = genderStmt.getObject();
		assertNotNull(genderValue);
		String gender = genderValue.asLiteral().getString();
		assertEquals(FacebookTestVocabulary.FEMALE_GENDER, gender);

		/**
		 * check user has an facebook account
		 */
		Statement accountStmt = facebookIndividual.getProperty(ResourceFactory
				.createProperty(CommonOntologyVocabulary.ACCOUNT_PRP_URI));

		// assert got account individual is not null
		RDFNode accountValue = accountStmt.getObject();
		assertNotNull(accountValue);
		// get account resource
		Resource accountRsc = accountValue.asResource();
		// get FOAF:accountServiceHomepage property value of account resource
		Resource accountHomepageRsc = accountRsc
				.getPropertyResourceValue(FOAF.accountServiceHomepage);
		// check FOAF:accountServiceHomepage property value
		assertNotNull(accountHomepageRsc);
		assertEquals(FacebookOntologyVocabulary.FACEBOOK_HOMEPAGE_RSC,
				accountHomepageRsc);

		// get FOAF:accountName value
		RDFNode accountNameObject = accountRsc.getProperty(FOAF.accountName)
				.getObject();
		// check object is not null
		assertNotNull(accountNameObject);
		// get literal value and compare with control value
		String accountIdValue = accountNameObject.asLiteral().getString();
		assertEquals(facebookUser.getUser().getId(), accountIdValue);
	}

	private void checkDepictions(StmtIterator depictionPrpIter,
			ArrayList<Object> depictionList) {
		while (depictionPrpIter.hasNext()) {
			Statement depictionStmt = (Statement) depictionPrpIter.next();
			assertNotNull(depictionStmt);
			RDFNode depictionValue = depictionStmt.getObject();
			assertNotNull(depictionValue);
			TestUtil.assertContains("In the list of depiction", depictionList,
					depictionValue.asResource());
		}
	}

	/**
	 * This method asserts event properties that user will join
	 * 
	 * @throws Exception
	 */
	@Test
	public void eventsCreation() throws Exception {

		// add expected event names into the control list
		ArrayList<String> eventNames = new ArrayList<String>();
		eventNames.add(FacebookTestVocabulary.EVENT_1);

		// get joins statements for event properties
		StmtIterator joinsStmtIter = facebookIndividual
				.listProperties(ResourceFactory
						.createProperty(FacebookOntologyVocabulary.JOINS_PRP_URI));
		assertNotNull(joinsStmtIter);

		// iterate on each joining event statement
		while (joinsStmtIter.hasNext()) {
			// get next statement
			Statement joinsStmt = (Statement) joinsStmtIter.next();
			// get event resource
			RDFNode eventNode = joinsStmt.getObject();
			assertNotNull(eventNode);
			// get event name and check if it is contained in expected list
			Statement namePrpStmt = eventNode.asResource().getProperty(
					FOAF.name);
			assertNotNull(namePrpStmt);
			RDFNode nameNode = namePrpStmt.getObject();
			assertNotNull(nameNode);
			String eventName = nameNode.asLiteral().getString();
			assertTrue(eventNames.contains(eventName));
		}

	}

	@Test
	public void interestsCreation() throws Exception {
		// add expected like names into the control list
		ArrayList<Object> interestNames = new ArrayList<Object>();
		interestNames.add("Java");

		// get user likes
		StmtIterator interestedInStmtIter = facebookIndividual
				.listProperties(ResourceFactory
						.createProperty(FacebookOntologyVocabulary.INTERESTED_IN_PRP_URI));
		assertNotNull(interestedInStmtIter);
		assertTrue(interestedInStmtIter.hasNext());
		// iterate on each like statement
		while (interestedInStmtIter.hasNext()) {
			// get next statement
			Statement interestedInStmt = (Statement) interestedInStmtIter
					.next();
			// get interest resource
			RDFNode interestedInNode = interestedInStmt.getObject();
			assertNotNull(interestedInNode);
			// check interest name is contained in the given control list.
			Resource interestedInRsc = interestedInNode.asResource();
			assertEquals(
					ResourceFactory
							.createResource(FacebookOntologyVocabulary.INTEREST_RSC_URI),
					interestedInRsc.getProperty(RDF.type).getObject());
			Statement nameStmt = interestedInRsc.getProperty(FOAF.name);
			assertNotNull(nameStmt);
			RDFNode nameNode = nameStmt.getObject();
			assertNotNull(nameNode);
			String nameValue = nameNode.asLiteral().getString();
			TestUtil.assertContains("In list of likeNames", interestNames,
					nameValue);
		}
	}

	/**
	 * This method asserts team properties that user is fan of it.
	 * 
	 * @throws Exception
	 */
	@Test
	public void teamsCreation() throws Exception {

		// add expected team names into the control list
		ArrayList<String> teamNames = new ArrayList<String>();
		teamNames.add(FacebookTestVocabulary.AJAX);

		// get favourite team statements
		StmtIterator favTeamStmtIter = facebookIndividual
				.listProperties(ResourceFactory
						.createProperty(FacebookOntologyVocabulary.FAVOURITE_TEAM_PRP_URI));
		assertNotNull(favTeamStmtIter);
		// iterate on each favourite team statements
		while (favTeamStmtIter.hasNext()) {
			// get next statement
			Statement favTeamStmt = (Statement) favTeamStmtIter.next();
			// get team resource
			RDFNode teamNode = favTeamStmt.getObject();
			assertNotNull(teamNode);
			// check that name of team resource is contained in expected list
			Statement namePrpStmt = teamNode.asResource()
					.getProperty(FOAF.name);
			assertNotNull(namePrpStmt);
			RDFNode nameNode = namePrpStmt.getObject();
			assertNotNull(nameNode);
			String teamName = nameNode.asLiteral().getString();
			assertTrue(teamNames.contains(teamName));
		}
	}

	/**
	 * This method asserts like properties of user on Facebook
	 * 
	 * @throws Exception
	 */
	@Test
	public void likesCreation() throws Exception {
		// add expected like names into the control list
		ArrayList<Object> likeNames = new ArrayList<Object>();
		likeNames.add(FacebookTestVocabulary.MUSIC_LIKE_1);
		likeNames.add(FacebookTestVocabulary.MOVIE_LIKE_1);
		likeNames.add(FacebookTestVocabulary.MOVIE_LIKE_2);
		likeNames.add(FacebookTestVocabulary.MOVIE_LIKE_3);
		likeNames.add(FacebookTestVocabulary.MOVIE_LIKE_4);
		likeNames.add(FacebookTestVocabulary.COMMUNITY_LIKE_1);
		likeNames.add(FacebookTestVocabulary.COMMUNITY_LIKE_2);
		likeNames.add(FacebookTestVocabulary.AUTHOR_LIKE_1);
		likeNames.add(FacebookTestVocabulary.TV_SHOW_LIKE_1);
		likeNames.add(FacebookTestVocabulary.TV_SHOW_LIKE_2);
		likeNames.add(FacebookTestVocabulary.TEAM_LIKE_1);
		likeNames.add(FacebookTestVocabulary.BOOK_LIKE_1);
		likeNames.add(FacebookTestVocabulary.PRODUCER_LIKE_1);
		likeNames.add(FacebookTestVocabulary.ACTOR_DIRECTOR_LIKE_1);
		likeNames.add(FacebookTestVocabulary.UNCATEGORİZED_LIKE_1);
		likeNames.add(FacebookTestVocabulary.UNCATEGORİZED_LIKE_2);

		// get user likes
		StmtIterator likeStmtIter = facebookIndividual
				.listProperties(ResourceFactory
						.createProperty(FacebookOntologyVocabulary.LIKES));
		assertNotNull(likeStmtIter);
		// iterate on each like statement
		while (likeStmtIter.hasNext()) {
			// get next statement
			Statement likeStmt = (Statement) likeStmtIter.next();
			// get like resource
			RDFNode likeNode = likeStmt.getObject();
			assertNotNull(likeNode);
			// check like name is contained in the given control list.
			Resource likeRsc = likeNode.asResource();
			Statement createdAtStmt = likeRsc
					.getProperty(CommonOntologyVocabulary.DC_TERMS_DATE_PRP);
			assertNotNull(createdAtStmt);
			RDFNode date = createdAtStmt.getObject();
			assertNotNull(date);
			Statement namePrpStmt = likeRsc.getProperty(FOAF.name);
			assertNotNull(namePrpStmt);
			RDFNode nameNode = namePrpStmt.getObject();
			assertNotNull(nameNode);
			String likeName = nameNode.asLiteral().getString();
			TestUtil.assertContains("In list of likeNames", likeNames, likeName);
		}

	}

	/**
	 * This method asserts education properties of person
	 * 
	 * @throws Exception
	 */
	@Test
	public void educationCreation() throws Exception {
		// add expected education major names into the edu major control list
		ArrayList<String> eduMajorNames = new ArrayList<String>();
		eduMajorNames.add("Computer Science");
		// add expected school names into the school control list
		ArrayList<String> schoolNames = new ArrayList<String>();
		schoolNames.add("ETH Zurich");
		// get user educations
		StmtIterator hasEduStmtIter = facebookIndividual
				.listProperties(ResourceFactory
						.createProperty(CommonOntologyVocabulary.CV_EDU_PRP_URI));
		assertNotNull(hasEduStmtIter);
		// iterate on each education statement
		while (hasEduStmtIter.hasNext()) {
			// get next statement
			Statement hasEduStmt = (Statement) hasEduStmtIter.next();

			// get education node...
			RDFNode hasEduNode = hasEduStmt.getObject();
			assertNotNull(hasEduNode);
			assertTrue(hasEduNode instanceof Resource);
			Resource hasEduRsc = hasEduNode.asResource();
			// check type property
			assertTrue(facebookIndividual.getModel().contains(hasEduRsc,
					RDF.type, CommonOntologyVocabulary.EDUCATION_RSC));
			// get major object and check if the edu major list contains the
			// name
			Statement eduMayorPrpStmt = hasEduRsc
					.getProperty(CommonOntologyVocabulary.EDU_MAJOR_PRP);
			RDFNode eduMajorNode = eduMayorPrpStmt.getObject();
			assertNotNull(eduMajorNode);
			assertTrue(eduMajorNames.contains(eduMajorNode.asLiteral()
					.getString()));
			// get studied school resource object
			Statement studiedInStmt = hasEduRsc
					.getProperty(CommonOntologyVocabulary.CV_STUDIED_IN_PRP);
			RDFNode schoolNode = studiedInStmt.getObject();
			assertNotNull(schoolNode);
			assertTrue(schoolNode instanceof Resource);
			Resource schoolRsc = schoolNode.asResource();
			// check type of school resource and check if its name contained in
			// the school name list
			assertTrue(facebookIndividual.getModel().contains(schoolRsc,
					RDF.type, CommonOntologyVocabulary.EDUCATIONAL_ORG_RSC));
			Statement schoolNameStmt = schoolRsc.getProperty(FOAF.name);
			assertNotNull(schoolNameStmt);
			RDFNode schoolNameNode = schoolNameStmt.getObject();
			assertNotNull(schoolNameNode);
			assertTrue(schoolNames.contains(schoolNameNode.asLiteral()
					.getString()));

		}
	}

	/**
	 * This method asserts education properties of person
	 * 
	 * @throws Exception
	 */
	@Test
	public void workCreation() throws Exception {
		// add expected work position names into the position control list
		ArrayList<String> positionNames = new ArrayList<String>();
		positionNames.add("Software Engineer");
		// add expected company into the company control list
		ArrayList<String> employerNames = new ArrayList<String>();
		employerNames.add("Apple Inc.");
		// get user work statements
		StmtIterator hasWorkHistoryStmtIter = facebookIndividual
				.listProperties(ResourceFactory
						.createProperty(CommonOntologyVocabulary.CV_HAS_WORK_HISTORY_PRP_URI));
		assertNotNull(hasWorkHistoryStmtIter);
		// iterate on each work statement
		while (hasWorkHistoryStmtIter.hasNext()) {

			// check work history property
			Statement hasWorkHistoryStmt = (Statement) hasWorkHistoryStmtIter
					.next();
			RDFNode hasWorkHistoryNode = hasWorkHistoryStmt.getObject();
			assertNotNull(hasWorkHistoryNode);
			// get work position and check if its name is contained by expected
			// position list
			Statement workPositionPrpStmt = hasWorkHistoryNode
					.asResource()
					.getProperty(
							ResourceFactory
									.createProperty(CommonOntologyVocabulary.WORK_POSITION_PRP_URI));
			assertNotNull(workPositionPrpStmt);
			RDFNode workPositionNode = workPositionPrpStmt.getObject();
			assertNotNull(workPositionNode);
			String workPosition = workPositionNode.asLiteral().getString();
			assertTrue(positionNames.contains(workPosition));

			// get employer (company) and check if its name is contained by the
			// employer list
			Statement employerPrpStmt = hasWorkHistoryNode
					.asResource()
					.getProperty(
							ResourceFactory
									.createProperty(CommonOntologyVocabulary.EMPLOYER_PRP_URI));
			assertNotNull(employerPrpStmt);
			RDFNode employerNode = employerPrpStmt.getObject();
			assertNotNull(employerNode);

			Statement employerNamePrpStmt = employerNode.asResource()
					.getProperty(FOAF.name);
			assertNotNull(employerNamePrpStmt);
			String employerName = employerNamePrpStmt.getObject().asLiteral()
					.getString();
			assertTrue(employerNames.contains(employerName));

		}
	}

	/**
	 * This method asserts knows (to have friend) properties of Facebook
	 * individual
	 * 
	 * @throws Exception
	 */
	@Test
	public void friendsCreation() throws Exception {
		// getting friend list of facebook user
		ArrayList<User> friendList = facebookUser.getFriendList();

		// listing statements of facebook individual
		List<Statement> knowsStmts = facebookIndividual.listProperties(
				FOAF.knows).toList();
		// creating resource of facebook user
		Resource selfRsc = ResourceFactory.createResource(facebookIndividual
				.getURI());

		for (User friend : friendList) {
			// creating friend individual URI
			String friendIndvURI = CommonTestVocabulary.BASE_URI
					+ friend.getId();

			// creating resource of friend
			Resource friendRsc = ResourceFactory.createResource(friendIndvURI);

			// asserting created statement is contained by all knows statements
			// of facebook user
			StatementImpl stmt = new StatementImpl(selfRsc, FOAF.knows,
					friendRsc);
			assertTrue(knowsStmts.contains(stmt));
		}

	}

	/**
	 * It creates facebook individual using {@link FacebookUser} object for
	 * {@link FacebookTestVocabulary#ACCESS_TOKEN}.
	 * 
	 * @return
	 * @throws SocialAPIException
	 */
	private static void createFacebookIndividual() throws SocialAPIException {
		// creating FacebookUser instance
		facebookUser = TestUtil
				.createFacebookUser(FacebookTestVocabulary.ACCESS_TOKEN_CONTAINS_ALL_INFORMATION);
		facebookUser.setId(FacebookTestVocabulary.AMELIA_FACEBOOK_ID);
		facebookIndividual = creator.createPersonIndividual(facebookUser);
		creator.createFriendProperties(facebookUser, facebookIndividual);
	}

}

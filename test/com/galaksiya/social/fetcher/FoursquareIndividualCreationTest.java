package com.galaksiya.social.fetcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.galaksiya.social.TestUtil;
import com.galaksiya.social.entity.FoursquareUser;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.galaksiya.social.vocabulary.FoursquareTestVocabulary;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author Galaksiya
 * 
 */
public class FoursquareIndividualCreationTest {

	/**
	 * Individual creator
	 */
	private static FoursquareIndividualCreator fsIndvCreator;

	private static Resource foursquareIndividual;

	private static FoursquareUser foursquareUser;

	@BeforeClass
	public static void beforeClass() throws Exception {
		fsIndvCreator = new FoursquareIndividualCreator(
				CommonTestVocabulary.BASE_URI);
		foursquareUser = TestUtil.createFoursquareUser();
		foursquareUser.setId(FoursquareTestVocabulary.SAMPLE_FOURSQUARE_ID);
		// creating Foursquare individual
		foursquareIndividual = fsIndvCreator
				.createPersonIndividual(foursquareUser);
	}

	/**
	 * asserts individual of Foursquare user properties
	 * 
	 * @throws Exception
	 */
	@Test
	public void userCreationTest() throws Exception {
		assertNotNull(foursquareIndividual);

		/**
		 * check user URI is correct
		 */
		assertEquals(CommonTestVocabulary.BASE_URI
				+ FoursquareTestVocabulary.SAMPLE_FOURSQUARE_ID,
				foursquareIndividual.getURI());

		// assert RDF:type property
		Statement typeStmt = foursquareIndividual.getProperty(RDF.type);
		assertNotNull(typeStmt);
		RDFNode typeNode = typeStmt.getObject();
		assertNotNull(typeNode);
		Resource actualTypeRsc = typeNode.asResource();
		assertNotNull(actualTypeRsc);
		assertEquals(FOAF.Person, actualTypeRsc);

		// assert foaf:name property
		Statement nameStmt = foursquareIndividual.getProperty(FOAF.name);
		assertNotNull(nameStmt);
		RDFNode nameNode = nameStmt.getObject();
		assertNotNull(nameNode);
		String nameValue = nameNode.asLiteral().getString();
		assertNotNull(nameValue);
		assertEquals(FoursquareTestVocabulary.PERSON_NAME, nameValue);

		// assert sioc:email property
		Statement emailStmt = foursquareIndividual.getProperty(ResourceFactory
				.createProperty(CommonOntologyVocabulary.SIOC_EMAIL_URI));
		assertNotNull(emailStmt);
		RDFNode emailNode = emailStmt.getObject();
		assertNotNull(nameNode);
		String emailValue = emailNode.asLiteral().getString();
		assertNotNull(emailValue);
		assertEquals(FoursquareTestVocabulary.EMAIL_VALUE, emailValue);

		// assert foaf:depiction property
		Statement depictionStmt = foursquareIndividual
				.getProperty(FOAF.depiction);
		assertNotNull(depictionStmt);
		RDFNode depictionNode = depictionStmt.getObject();
		assertNotNull(depictionNode);
		assertEquals(FoursquareTestVocabulary.DEPICTION_URI, depictionNode
				.asResource().getURI());

		// assert foursquare:relationship property
		Statement relationshipStmt = foursquareIndividual
				.getProperty(ResourceFactory
						.createProperty(FoursquareOntologyVocabulary.RELATIONSHIP_PRP_URI));
		assertNotNull(relationshipStmt);
		RDFNode relationshipNode = relationshipStmt.getObject();
		assertNotNull(relationshipNode);
		String relationshipValue = relationshipNode.asLiteral().getString();
		assertNotNull(relationshipValue);
		assertEquals(FoursquareOntologyVocabulary.RELATIONSHIP_VALUE,
				relationshipValue);

		// assert foaf:page property
		Statement pageStmt = foursquareIndividual.getProperty(FOAF.page);
		assertNotNull(pageStmt);
		RDFNode pageNode = pageStmt.getObject();
		assertNotNull(pageNode);
		assertEquals(FoursquareTestVocabulary.PAGE_URI, pageNode.asResource()
				.getURI());

		// assert foaf:gender property
		Statement genderStmt = foursquareIndividual.getProperty(FOAF.gender);
		assertNotNull(genderStmt);
		RDFNode genderNode = genderStmt.getObject();
		assertNotNull(genderNode);
		String genderValue = genderNode.asLiteral().getString();
		assertNotNull(genderValue);
		assertEquals(FoursquareTestVocabulary.GENDER_VALUE, genderValue);

		// assert foursquare:badge_count property
		Statement badgeCountStmt = foursquareIndividual
				.getProperty(ResourceFactory
						.createProperty(FoursquareOntologyVocabulary.BADGE_COUNT_PRP_URI));
		assertNotNull(badgeCountStmt);
		RDFNode badgeCountNode = badgeCountStmt.getObject();
		assertNotNull(badgeCountNode);
		int badgeCountValue = badgeCountNode.asLiteral().getInt();
		assertNotNull(badgeCountValue);
		assertEquals(FoursquareTestVocabulary.BADGE_COUNT_VALUE,
				badgeCountValue);

		// assert foursquare:following_count property
		Statement followingCountStmt = foursquareIndividual
				.getProperty(ResourceFactory
						.createProperty(FoursquareOntologyVocabulary.FOLLOWING_COUNT_PRP_URI));
		assertNotNull(followingCountStmt);
		RDFNode followingCountNode = followingCountStmt.getObject();
		assertNotNull(followingCountNode);
		int followingCountValue = followingCountNode.asLiteral().getInt();
		assertNotNull(followingCountValue);
		assertEquals(FoursquareTestVocabulary.FOLLOWING_COUNT_VALUE,
				followingCountValue);

		// assert foursquare:pings property
		Statement pingsStmt = foursquareIndividual.getProperty(ResourceFactory
				.createProperty(FoursquareOntologyVocabulary.PINGS_PRP_URI));
		assertNotNull(pingsStmt);
		RDFNode pingsNode = pingsStmt.getObject();
		assertNotNull(pingsNode);
		boolean pingsValue = pingsNode.asLiteral().getBoolean();
		assertNotNull(pingsValue);
		assertEquals(FoursquareTestVocabulary.PINGS_VALUE, pingsValue);

		// assert foursquare:contact property
		Statement contactStmt = foursquareIndividual
				.getProperty(ResourceFactory
						.createProperty(FoursquareOntologyVocabulary.CONTACT_PRP_URI));
		assertNotNull(contactStmt);
		RDFNode contactNode = contactStmt.getObject();
		assertNotNull(contactNode);

		// assert phone property value
		Statement phoneStmt = contactNode.asResource().getProperty(FOAF.phone);
		assertNull(phoneStmt);
		// assertNotNull(phoneStmt);
		// RDFNode phoneNode = phoneStmt.getObject();
		// assertNotNull(phoneNode);
		// String phoneValue = phoneNode.asLiteral().getString();
		// assertEquals(FoursquareTestVocabulary.PHONE_VALUE, phoneValue);

		// assert Facebook property value
		Statement facebookStmt = contactNode.asResource().getProperty(
				ResourceFactory
						.createProperty(FoursquareOntologyVocabulary.FACEBOOK));
		assertNotNull(facebookStmt);
		assertEquals(FoursquareTestVocabulary.FACEBOOK_VALUE, facebookStmt
				.getObject().asLiteral().getString());

		// assert Twitter property value
		Statement twitterStmt = contactNode.asResource().getProperty(
				ResourceFactory
						.createProperty(FoursquareOntologyVocabulary.TWITTER));
		assertNull(twitterStmt);

		/**
		 * check user has an foursquare account
		 */
		Resource accountRsc = foursquareIndividual
				.getPropertyResourceValue(CommonOntologyVocabulary.ACCOUNT_PRP);
		assertNotNull(accountRsc);

		// get FOAF:accountServiceHomepage property value of account and check
		// it with a control value
		Resource accountHomepageRsc = accountRsc
				.getPropertyResourceValue(FOAF.accountServiceHomepage);
		assertNotNull(accountHomepageRsc);
		assertEquals(FoursquareOntologyVocabulary.FOURSQUARE_HOMEPAGE_RSC,
				accountHomepageRsc);

		// get FOAF.accountName property value of account and check it as object
		// and check after converting literal value.
		RDFNode accountIdRsc = accountRsc.getProperty(FOAF.accountName)
				.getObject();
		assertNotNull(accountIdRsc);
		String accountIdValue = accountIdRsc.asLiteral().getString();
		assertEquals(foursquareUser.getUser().getId(), accountIdValue);

	}

	/**
	 * Asserts checkin properties of Foursquare user
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkinsCreation() throws Exception {

		assertNotNull(foursquareIndividual);

		// creating expected venue name list
		List<String> venueNameList = Arrays
				.asList(FoursquareTestVocabulary.VENUE_NAMES);

		// assert foursquare:has_checkin property
		StmtIterator checkinStmtIter = foursquareIndividual
				.listProperties(ResourceFactory
						.createProperty(FoursquareOntologyVocabulary.CHECKIN_PRP_URI));
		assertNotNull(checkinStmtIter);
		while (checkinStmtIter.hasNext()) {
			Statement checkinStmt = (Statement) checkinStmtIter.next();
			RDFNode chekcinNode = checkinStmt.getObject();
			assertNotNull(chekcinNode);
			// get venue of checkin
			Statement venueStmt = chekcinNode
					.asResource()
					.getProperty(
							ResourceFactory
									.createProperty(FoursquareOntologyVocabulary.VENUE_PRP_URI));
			assertNotNull(venueStmt);
			RDFNode venueNode = venueStmt.getObject();
			assertNotNull(venueNode);
			// get venue name of checkin
			Statement venueNameStmt = venueNode.asResource().getProperty(
					FOAF.name);
			assertNotNull(venueNameStmt);
			RDFNode venueNameNode = venueNameStmt.getObject();
			assertNotNull(venueNameNode);
			String venueName = venueNameNode.asLiteral().getString();
			assertTrue(venueNameList.contains(venueName));
		}

	}

}

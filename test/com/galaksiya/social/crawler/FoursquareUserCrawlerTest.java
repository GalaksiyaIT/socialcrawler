package com.galaksiya.social.crawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.galaksiya.social.exception.FoursquareAuthenticationException;
import com.galaksiya.social.fetcher.FoursquareDataFetcher;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.galaksiya.social.vocabulary.FoursquareTestVocabulary;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;

public class FoursquareUserCrawlerTest {
	/**
	 * crawler which will turn foursquare user information into RDF data
	 * {@link Model}.
	 */
	private FoursquareUserCrawler crawler;

	/**
	 * Initialize {@link FoursquareUserCrawler}
	 */
	@Before
	public void before() {
		// create a new crawler before each test
		crawler = new FoursquareUserCrawler(CommonTestVocabulary.BASE_URI);
	}

	/**
	 * This test checks that if a permission list is given into crawler, then it
	 * is expected that {@link FoursquareDataFetcher} will not fetch any
	 * property other than given property list.
	 * 
	 * @throws Exception
	 */
	@Test
	public void grantingPermissionTest() throws Exception {
		// create a new crawler with given permission properties
		crawler = new FoursquareUserCrawler(CommonTestVocabulary.BASE_URI,
				null, FoursquareTestVocabulary.LACK_PERMISSONS);

		// crawl with lack permissioned crawler
		Model model = crawler
				.crawl(FoursquareTestVocabulary.ACCESS_TOKEN_COPIED);

		// check granted properties are fetched
		checkBasicProfileProperties(model,
				FoursquareTestVocabulary.FOURSQUARE_USER_RSC);

		// check if not granted properties are not fetched
		assertFalse(model.contains(
				FoursquareTestVocabulary.FOURSQUARE_USER_RSC,
				FoursquareOntologyVocabulary.CHECKIN_PRP, (RDFNode) null));

	}

	/**
	 * This test ensures that all user data could be fetched successfully.
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveAllUserData() throws Exception {
		// get user model that contains fetched properties
		Model model = crawler
				.crawl(FoursquareTestVocabulary.ACCESS_TOKEN_COPIED);
		Resource resource = FoursquareTestVocabulary.FOURSQUARE_USER_RSC;
		// check if model contains requested properties
		// check basic profile properties of user
		checkBasicProfileProperties(model, resource);
		// foursquare:has_checkin
		assertTrue(model.contains(resource,
				FoursquareOntologyVocabulary.CHECKIN_PRP, (RDFNode) null));
		List<RDFNode> checkins = model.listObjectsOfProperty(resource,
				FoursquareOntologyVocabulary.CHECKIN_PRP).toList();
		assertEquals(4, checkins.size());
	}

	private void checkBasicProfileProperties(Model model, Resource resource) {
		// check requested properties are contained by the model

		// rdf:type
		assertTrue(model.contains(resource, RDF.type, (RDFNode) null));
		// hometown
		assertTrue(model.contains(resource,
				CommonOntologyVocabulary.HOMETOWN_PRP, (RDFNode) null));
		// foaf:name
		assertTrue(model.contains(resource, FOAF.name, (RDFNode) null));
		// foaf:depiction
		assertTrue(model.contains(resource, FOAF.depiction, (RDFNode) null));
		// foaf:page
		assertTrue(model.contains(resource, FOAF.page, (RDFNode) null));

		// foursquare:relationship
		assertTrue(model.contains(resource,
				FoursquareOntologyVocabulary.RELATIONSHIP_PRP, (RDFNode) null));

		// foaf:gender
		assertTrue(model.contains(resource, FOAF.gender, (RDFNode) null));

		// foursquare:badge_count
		assertTrue(model.contains(resource,
				FoursquareOntologyVocabulary.BADGE_COUNT_PRP, (RDFNode) null));

		// foursquare:following_count
		assertTrue(model.contains(resource,
				FoursquareOntologyVocabulary.FOLLOWING_COUNT_PRP,
				(RDFNode) null));

		// foursquare:pings
		assertTrue(model.contains(resource,
				FoursquareOntologyVocabulary.PINGS_PRP, (RDFNode) null));

		// foursquare:contact
		assertTrue(model.contains(resource,
				FoursquareOntologyVocabulary.CONTACT_PRP, (RDFNode) null));

		// check that requested email property which is a singular data is
		// contained too.
		assertTrue(model.contains(FoursquareTestVocabulary.FOURSQUARE_USER_RSC,
				CommonOntologyVocabulary.SIOC_EMAIL_PRP, (RDFNode) null));
	}

	/**
	 * This test controls when a not valid access token is given to the crawler,
	 * it is expected that to throwing a
	 * {@link FoursquareAuthenticationException} .
	 * 
	 * @throws Exception
	 */
	@Test(expected = FoursquareAuthenticationException.class)
	public void crawlUsingNotValidToken() throws Exception {
		// create a Foursquare User Crawler to get user data
		FoursquareUserCrawler crawler = new FoursquareUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Foursquare data of a person whose access token
		// has been expried
		crawler.crawl(FoursquareTestVocabulary.ACCESS_TOKEN_NOT_VALID);
	}

	/**
	 * This test controls when a defected access token is given to the crawler,
	 * it is expected that to throwing a
	 * {@link FoursquareAuthenticationException} .
	 * 
	 * @throws Exception
	 */
	@Test(expected = FoursquareAuthenticationException.class)
	public void crawlUsingDefectedToken() throws Exception {
		// create a Foursquare User Crawler to get user data
		FoursquareUserCrawler crawler = new FoursquareUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Foursquare data of a person whose access token
		// has been expried
		crawler.crawl(FoursquareTestVocabulary.ACCESS_TOKEN_DEFECTED);
	}

	/**
	 * This test controls when a null access token is given to the crawler, it
	 * is expected that to throwing a {@link FoursquareAuthenticationException}.
	 * 
	 * @throws Exception
	 */
	@Test(expected = FoursquareAuthenticationException.class)
	public void crawlUsingNullToken() throws Exception {
		// create a Foursquare User Crawler to get user data
		FoursquareUserCrawler crawler = new FoursquareUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Foursquare data of a person whose access token
		// has been expried
		crawler.crawl(null);
	}

}

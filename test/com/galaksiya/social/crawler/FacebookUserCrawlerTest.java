package com.galaksiya.social.crawler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.galaksiya.social.exception.FacebookAccessExpirationException;
import com.galaksiya.social.exception.FacebookAuthenticationException;
import com.galaksiya.social.fetcher.FacebookDataFetcher;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.galaksiya.social.vocabulary.FacebookTestVocabulary;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class FacebookUserCrawlerTest {

	/**
	 * crawler which will turn facebook user information into RDF data
	 * {@link Model}.
	 */
	private FacebookUserCrawler crawler;

	/**
	 * Initialize {@link FacebookUserCrawler}
	 */
	@Before
	public void before() {
		// create a new crawler before each test
		crawler = new FacebookUserCrawler(CommonTestVocabulary.BASE_URI);
	}

	/**
	 * This test checks that if a permission list is given into crawler, then it
	 * is expected that {@link FacebookDataFetcher} will not fetch any property
	 * other than given property list.
	 * 
	 * @throws Exception
	 */
	@Test
	public void grantingPermissionTest() throws Exception {
		// create a new crawler with given permission properties
		crawler = new FacebookUserCrawler(CommonTestVocabulary.BASE_URI, null,
				FacebookTestVocabulary.LACK_PERMISSONS);

		// crawl with lack permissioned crawler
		Model model = crawler
				.crawl(FacebookTestVocabulary.ACCESS_TOKEN_CONTAINS_ALL_INFORMATION);

		// check granted properties are fetched
		checkBasicProfileProperties(model,
				FacebookTestVocabulary.FACEBOOK_COMPLETE_DATA_USER_RSC);

		// check if not granted properties are not fetched
		assertFalse(model.contains(
				FacebookTestVocabulary.FACEBOOK_COMPLETE_DATA_USER_RSC,
				CommonOntologyVocabulary.WORK_HISTORY_PRP, (RDFNode) null));

		assertFalse(model.contains(
				FacebookTestVocabulary.FACEBOOK_COMPLETE_DATA_USER_RSC,
				CommonOntologyVocabulary.EDUCATION_PRP, (RDFNode) null));

		assertFalse(model.contains(
				FacebookTestVocabulary.FACEBOOK_COMPLETE_DATA_USER_RSC,
				FacebookOntologyVocabulary.LIKES_PRP, (RDFNode) null));

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
				.crawl(FacebookTestVocabulary.ACCESS_TOKEN_CONTAINS_ALL_INFORMATION);
		// check if model contains requested properties
		checkIfRequestedPropertiesExist(model,
				FacebookTestVocabulary.FACEBOOK_COMPLETE_DATA_USER_RSC);
		// check that requested email property which is a singular data is
		// contained too.
		assertTrue(model.contains(
				FacebookTestVocabulary.FACEBOOK_COMPLETE_DATA_USER_RSC,
				CommonOntologyVocabulary.SIOC_EMAIL_PRP, (RDFNode) null));
		// check that requested like property which contains plural data is
		// contained too.
		assertTrue(model.contains(
				FacebookTestVocabulary.FACEBOOK_COMPLETE_DATA_USER_RSC,
				CommonOntologyVocabulary.WORK_HISTORY_PRP, (RDFNode) null));
	}

	/**
	 * This test checks that if it is requested from user that fetching less
	 * data than it could be, then if unrequested properties won't be fetched
	 * successfully.
	 * 
	 * @throws Exception
	 */
	@Test
	public void dontTryToRetrieveMissingUserData() throws Exception {
		// create a Facebook User Crawler to get user data
		FacebookUserCrawler crawler = new FacebookUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// get user model that containes fetched properties
		Model model = crawler
				.crawl(FacebookTestVocabulary.ACCESS_TOKEN_MISSING_MAIL_AND_LIKES);
		// check if model not containes not requested properties
		checkIfNotRequestedPropertiesNotExist(model,
				FacebookTestVocabulary.FACEBOOK_LESS_DATA_USER_RSC);
		// check if model contains requested properties
		checkIfRequestedPropertiesExist(model,
				FacebookTestVocabulary.FACEBOOK_LESS_DATA_USER_RSC);
	}

	private void checkIfNotRequestedPropertiesNotExist(Model model,
			Resource resource) {
		// check that not requested email property which is a singular data is
		// not contained
		assertFalse(model.contains(resource,
				CommonOntologyVocabulary.SIOC_EMAIL_PRP, (RDFNode) null));
		// check that not requested like property which contains plural data is
		// not contained.
		assertFalse(model.contains(resource,
				CommonOntologyVocabulary.WORK_HISTORY_PRP, (RDFNode) null));
	}

	private void checkIfRequestedPropertiesExist(Model model, Resource resource) {
		// check basic profile properties of user
		checkBasicProfileProperties(model, resource);
		// cv:hasEducation
		assertTrue(model.contains(resource,
				CommonOntologyVocabulary.EDUCATION_PRP, (RDFNode) null));
		// sociallinker:likes
		assertTrue(model.contains(resource,
				FacebookOntologyVocabulary.LIKES_PRP, (RDFNode) null));
	}

	private void checkBasicProfileProperties(Model model, Resource resource) {
		// check requested properties are contained by the model
		// rdf:type
		assertTrue(model.contains(resource, RDF.type, (RDFNode) null));
		// rdfs:label
		assertTrue(model.contains(resource, RDFS.label, (RDFNode) null));
		// hometown
		assertTrue(model.contains(resource,
				CommonOntologyVocabulary.HOMETOWN_PRP, (RDFNode) null));
		// foaf:name
		assertTrue(model.contains(resource, FOAF.name, (RDFNode) null));
		// foaf:birthday
		assertTrue(model.contains(resource, FOAF.birthday, (RDFNode) null));
		// foaf:depiction
		assertTrue(model.contains(resource, FOAF.depiction, (RDFNode) null));
		// foaf:page
		assertTrue(model.contains(resource, FOAF.page, (RDFNode) null));
		// location
		assertTrue(model.contains(resource,
				CommonOntologyVocabulary.LOCATION_PRP, (RDFNode) null));
		// hometown
		assertTrue(model.contains(resource,
				CommonOntologyVocabulary.HOMETOWN_PRP, (RDFNode) null));
	}

	/**
	 * TODO: Facebook expired tokenları bir süre sonra validation fail oluyor,
	 * bu expired exception kaldırılıp tüm authentication exceptionlar
	 * teklenebilir.
	 * 
	 * @throws Exception
	 */
	@Ignore
	@Test(expected = FacebookAccessExpirationException.class)
	public void crawlUsingExpiredToken() throws Exception {
		// create a Facebook User Crawler to get user data
		FacebookUserCrawler crawler = new FacebookUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve facebook data of a person whose access token has
		// been expried
		crawler.crawl(FacebookTestVocabulary.ACCESS_TOKEN_EXPIRED);
	}

	/**
	 * This test controls when a not valid access token is given to the crawler,
	 * it is expected that to throwing a {@link FacebookAuthenticationException}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test(expected = FacebookAuthenticationException.class)
	public void crawlUsingNotValidToken() throws Exception {
		// create a Facebook User Crawler to get user data
		FacebookUserCrawler crawler = new FacebookUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve facebook data of a person whose access token has
		// been expried
		crawler.crawl(FacebookTestVocabulary.ACCESS_TOKEN_NOT_VALID);
	}

	/**
	 * This test controls when a defected access token is given to the crawler,
	 * it is expected that to throwing a {@link FacebookAuthenticationException}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test(expected = FacebookAuthenticationException.class)
	public void crawlUsingDefectedToken() throws Exception {
		// create a Facebook User Crawler to get user data
		FacebookUserCrawler crawler = new FacebookUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve facebook data of a person whose access token has
		// been expried
		crawler.crawl(FacebookTestVocabulary.ACCESS_TOKEN_DEFECTED);
	}

	/**
	 * This test controls when a null access token is given to the crawler, it
	 * is expected that to throwing a {@link FacebookAuthenticationException}.
	 * 
	 * @throws Exception
	 */
	@Test(expected = FacebookAuthenticationException.class)
	public void crawlUsingNullToken() throws Exception {
		// create a Facebook User Crawler to get user data
		FacebookUserCrawler crawler = new FacebookUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve facebook data of a person whose access token has
		// been expried
		crawler.crawl(null);
	}

}

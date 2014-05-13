package com.galaksiya.social.crawler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.galaksiya.social.TestUtil;
import com.galaksiya.social.exception.LinkedinAuthenticationException;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.LinkedinOntologyVocabulary;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.galaksiya.social.vocabulary.LinkedinTestVocabulary;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class LinkedinUserCrawlerTest {
	/**
	 * crawler which will turn linkedin user information into RDF data
	 * {@link Model}.
	 */
	private LinkedinUserCrawler crawler;

	/**
	 * Initialize {@link LinkedinUserCrawler}
	 */
	@Before
	public void before() {
		// create a new crawler before each test
		crawler = new LinkedinUserCrawler(CommonTestVocabulary.BASE_URI);
	}

	@Test
	public void retrieveAllUserData() throws Exception {
		Model model = crawler.crawl(TestUtil.LINKEDIN_CRAWLER_API_KEY,
				TestUtil.LINKEDIN_CRAWLER_SECRET_KEY,
				LinkedinTestVocabulary.TOKEN_VALUE_FOR_GEORGE,
				LinkedinTestVocabulary.TOKEN_SECRET_FOR_GEORGE, null);

		/**
		 * check all requested properties are fetched
		 */

		// check basic properties are fetched
		checkBasicProfilePropertiesLackIndustryAndLocation(model,
				LinkedinTestVocabulary.LINKEDIN_USER_RESOURCE);

		// cv:industry
		assertTrue(model.contains(
				LinkedinTestVocabulary.LINKEDIN_USER_RESOURCE,
				CommonOntologyVocabulary.CV_INDUSTRY_PRP, (RDFNode) null));

		// socialcrawler:location
		assertTrue(model.contains(
				LinkedinTestVocabulary.LINKEDIN_USER_RESOURCE,
				CommonOntologyVocabulary.LOCATION_PRP, (RDFNode) null));

		// sociallinker:hasWorkHistory
		assertTrue(model.contains(
				LinkedinTestVocabulary.LINKEDIN_USER_RESOURCE,
				CommonOntologyVocabulary.WORK_HISTORY_PRP, (RDFNode) null));
	}

	@Test
	public void grantingPermissionTest() throws Exception {
		// create a new crawler with given permission properties
		crawler = new LinkedinUserCrawler(CommonTestVocabulary.BASE_URI, null,
				LinkedinTestVocabulary.generateLackPermissionText());

		Model model = crawler.crawl(TestUtil.LINKEDIN_CRAWLER_API_KEY,
				TestUtil.LINKEDIN_CRAWLER_SECRET_KEY,
				LinkedinTestVocabulary.TOKEN_VALUE_FOR_GEORGE,
				LinkedinTestVocabulary.TOKEN_SECRET_FOR_GEORGE, null);

		// check requested properties are fetched
		checkBasicProfilePropertiesLackIndustryAndLocation(model,
				LinkedinTestVocabulary.LINKEDIN_USER_RESOURCE);

		/**
		 * check unrequested properties are not fetched
		 */

		// cv:industry
		assertFalse(model.contains(
				LinkedinTestVocabulary.LINKEDIN_USER_RESOURCE,
				CommonOntologyVocabulary.CV_INDUSTRY_PRP, (RDFNode) null));

		// socialcrawler:location
		assertFalse(model.contains(
				LinkedinTestVocabulary.LINKEDIN_USER_RESOURCE,
				CommonOntologyVocabulary.LOCATION_PRP, (RDFNode) null));

		// sociallinker:hasWorkHistory
		assertFalse(model.contains(
				LinkedinTestVocabulary.LINKEDIN_USER_RESOURCE,
				CommonOntologyVocabulary.WORK_HISTORY_PRP, (RDFNode) null));

	}

	private void checkBasicProfilePropertiesLackIndustryAndLocation(
			Model model, Resource resource) {
		// check requested properties are contained by the model

		// rdf:type
		assertTrue(model.contains(resource, RDF.type, (RDFNode) null));

		// foaf:name
		assertTrue(model.contains(resource, FOAF.name, (RDFNode) null));

		// foaf:depiction
		assertTrue(model.contains(resource, FOAF.depiction, (RDFNode) null));

		// rdfs:label
		assertTrue(model.contains(resource, RDFS.label, (RDFNode) null));

		// linkedin:headline
		assertTrue(model.contains(resource,
				LinkedinOntologyVocabulary.HEADLINE_PRP, (RDFNode) null));

		// foaf:page
		assertTrue(model.contains(resource, FOAF.page, (RDFNode) null));

		// foaf:account
		assertTrue(model.contains(resource,
				CommonOntologyVocabulary.FOAF_ACCOUNT_PRP, (RDFNode) null));

	}

	/**
	 * This test controls when a not valid access token value is given to the
	 * crawler, it is expected that to throwing a
	 * {@link LinkedinAuthenticationException} .
	 * 
	 * @throws Exception
	 */
	@Test(expected = LinkedinAuthenticationException.class)
	public void crawlUsingNotValidTokenValue() throws Exception {
		// create a Linkedin User Crawler to get user data
		LinkedinUserCrawler crawler = new LinkedinUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Linkedin data of a person whose access token
		// has been expried
		crawler.crawl(TestUtil.LINKEDIN_CRAWLER_API_KEY,
				TestUtil.LINKEDIN_CRAWLER_SECRET_KEY,
				LinkedinTestVocabulary.NOT_VALID_TOKEN_VALUE,
				LinkedinTestVocabulary.TOKEN_SECRET_FOR_GEORGE, null);
	}

	/**
	 * This test controls when a defected access token value is given to the
	 * crawler, it is expected that to throwing a
	 * {@link LinkedinAuthenticationException} .
	 * 
	 * @throws Exception
	 */
	@Test(expected = LinkedinAuthenticationException.class)
	public void crawlUsingDefectedTokenValue() throws Exception {
		// create a Linkedin User Crawler to get user data
		LinkedinUserCrawler crawler = new LinkedinUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Linkedin data of a person whose access token
		// has been expried
		crawler.crawl(TestUtil.LINKEDIN_CRAWLER_API_KEY,
				TestUtil.LINKEDIN_CRAWLER_SECRET_KEY,
				LinkedinTestVocabulary.DEFECTED_TOKEN_VALUE,
				LinkedinTestVocabulary.TOKEN_SECRET_FOR_GEORGE, null);
	}

	/**
	 * This test controls when a not valid access token secret is given to the
	 * crawler, it is expected that to throwing a
	 * {@link LinkedinAuthenticationException} .
	 * 
	 * @throws Exception
	 */
	@Test(expected = LinkedinAuthenticationException.class)
	public void crawlUsingNotValidTokenSecret() throws Exception {
		// create a Linkedin User Crawler to get user data
		LinkedinUserCrawler crawler = new LinkedinUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Linkedin data of a person whose access token
		// has been expried
		crawler.crawl(TestUtil.LINKEDIN_CRAWLER_API_KEY,
				TestUtil.LINKEDIN_CRAWLER_SECRET_KEY,
				LinkedinTestVocabulary.TOKEN_VALUE_FOR_GEORGE,
				LinkedinTestVocabulary.NOT_VALID_TOKEN_SECRET, null);
	}

	/**
	 * This test controls when a defected access token secret is given to the
	 * crawler, it is expected that to throwing a
	 * {@link LinkedinAuthenticationException} .
	 * 
	 * @throws Exception
	 */
	@Test(expected = LinkedinAuthenticationException.class)
	public void crawlUsingDefectedTokenSecret() throws Exception {
		// create a Linkedin User Crawler to get user data
		LinkedinUserCrawler crawler = new LinkedinUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Linkedin data of a person whose access token
		// has been expried
		crawler.crawl(TestUtil.LINKEDIN_CRAWLER_API_KEY,
				TestUtil.LINKEDIN_CRAWLER_SECRET_KEY,
				LinkedinTestVocabulary.TOKEN_VALUE_FOR_GEORGE,
				LinkedinTestVocabulary.DEFECTED_TOKEN_SECRET, null);
	}

	/**
	 * This test controls when a not valid api key value is given to the
	 * crawler, it is expected that to throwing a
	 * {@link LinkedinAuthenticationException} .
	 * 
	 * @throws Exception
	 */
	@Test(expected = LinkedinAuthenticationException.class)
	public void crawlUsingNotValidApiKey() throws Exception {
		// create a Linkedin User Crawler to get user data
		LinkedinUserCrawler crawler = new LinkedinUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Linkedin data of a person whose access token
		// has been expried
		crawler.crawl(LinkedinTestVocabulary.LINKEDIN_NOT_VALID_API_KEY,
				TestUtil.LINKEDIN_CRAWLER_SECRET_KEY,
				LinkedinTestVocabulary.NOT_VALID_TOKEN_VALUE,
				LinkedinTestVocabulary.TOKEN_SECRET_FOR_GEORGE, null);
	}

	/**
	 * This test controls when a defected api key value is given to the crawler,
	 * it is expected that to throwing a {@link LinkedinAuthenticationException}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test(expected = LinkedinAuthenticationException.class)
	public void crawlUsingDefectedApiKey() throws Exception {
		// create a Linkedin User Crawler to get user data
		LinkedinUserCrawler crawler = new LinkedinUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Linkedin data of a person whose access token
		// has been expried
		crawler.crawl(LinkedinTestVocabulary.LINKEDIN_DEFECTED_API_KEY,
				TestUtil.LINKEDIN_CRAWLER_SECRET_KEY,
				LinkedinTestVocabulary.DEFECTED_TOKEN_VALUE,
				LinkedinTestVocabulary.TOKEN_SECRET_FOR_GEORGE, null);
	}

	/**
	 * This test controls when a not valid api secret value is given to the
	 * crawler, it is expected that to throwing a
	 * {@link LinkedinAuthenticationException} .
	 * 
	 * @throws Exception
	 */
	@Test(expected = LinkedinAuthenticationException.class)
	public void crawlUsingNotValidApiSecret() throws Exception {
		// create a Linkedin User Crawler to get user data
		LinkedinUserCrawler crawler = new LinkedinUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Linkedin data of a person whose access token
		// has been expried
		crawler.crawl(TestUtil.LINKEDIN_CRAWLER_API_KEY,
				LinkedinTestVocabulary.LINKEDIN_NOT_VALID_SECRET_KEY,
				LinkedinTestVocabulary.TOKEN_VALUE_FOR_GEORGE,
				LinkedinTestVocabulary.NOT_VALID_TOKEN_SECRET, null);
	}

	/**
	 * This test controls when a defected api secret value is given to the
	 * crawler, it is expected that to throwing a
	 * {@link LinkedinAuthenticationException} .
	 * 
	 * @throws Exception
	 */
	@Test(expected = LinkedinAuthenticationException.class)
	public void crawlUsingDefectedApiSecret() throws Exception {
		// create a Linkedin User Crawler to get user data
		LinkedinUserCrawler crawler = new LinkedinUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Linkedin data of a person whose access token
		// has been expried
		crawler.crawl(TestUtil.LINKEDIN_CRAWLER_API_KEY,
				LinkedinTestVocabulary.LINKEDIN_DEFECTED_SECRET_KEY,
				LinkedinTestVocabulary.TOKEN_VALUE_FOR_GEORGE,
				LinkedinTestVocabulary.DEFECTED_TOKEN_SECRET, null);
	}

	/**
	 * This test controls when a null api key and secret is given to the
	 * crawler, it is expected that to throwing a
	 * {@link LinkedinAuthenticationException}.
	 * 
	 * @throws Exception
	 */
	@Test(expected = LinkedinAuthenticationException.class)
	public void crawlUsingNullApiKeyAndSecret() throws Exception {
		// create a Linkedin User Crawler to get user data
		LinkedinUserCrawler crawler = new LinkedinUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Linkedin data of a person whose access token
		// has been expried
		crawler.crawl(null, null,
				LinkedinTestVocabulary.TOKEN_VALUE_FOR_GEORGE,
				LinkedinTestVocabulary.TOKEN_SECRET_FOR_GEORGE, null);
	}

	/**
	 * This test controls when a null access token value and secret is given to
	 * the crawler, it is expected that to throwing a
	 * {@link LinkedinAuthenticationException}.
	 * 
	 * @throws Exception
	 */
	@Test(expected = LinkedinAuthenticationException.class)
	public void crawlUsingNullTokenValueAndSecret() throws Exception {
		// create a Linkedin User Crawler to get user data
		LinkedinUserCrawler crawler = new LinkedinUserCrawler(
				CommonTestVocabulary.BASE_URI);
		// try to get retrieve Linkedin data of a person whose access token
		// has been expried
		crawler.crawl(TestUtil.LINKEDIN_CRAWLER_API_KEY,
				TestUtil.LINKEDIN_CRAWLER_SECRET_KEY, null, null, null);
	}

}

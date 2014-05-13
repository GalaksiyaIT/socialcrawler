package com.galaksiya.social.crawler;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SocialCrawlerFacebookClientTest {

	private static final String FACEBOOK_GRAPH_ENDPOINT_URL = "https://graph.facebook.com";
	private static final String FACEBOOK_GRAPH_VIDEO_ENDPOINT_URL = "https://graph-video.facebook.com";
	private static final String FACEBOOK_READ_ONLY_ENDPOINT_URL = "https://api-read.facebook.com/method";

	private static final String TEST_GRAPH_ENDPOINT = "http://example.com/graphEndpoint";

	private static final String TEST_GRAPH_VIDEO_ENDPOINT = "http://example.com/graphVideoEndpoint";

	private static final String TEST_READ_ONLY_ENDPOINT = "http://example.com/readonlyEndpoint";

	@Test
	public void configure() throws Exception {
		SocialCrawlerFacebookClient
				.configure(new FacebookEndpointConfiguration(
						TEST_GRAPH_ENDPOINT, TEST_GRAPH_VIDEO_ENDPOINT,
						TEST_READ_ONLY_ENDPOINT));
		SocialCrawlerFacebookClient client = new SocialCrawlerFacebookClient(
				"token");
		assertEquals(client.getFacebookGraphEndpointUrl(), TEST_GRAPH_ENDPOINT);
		assertEquals(client.getFacebookGraphVideoEndpointUrl(),
				TEST_GRAPH_VIDEO_ENDPOINT);
		assertEquals(client.getFacebookReadOnlyEndpointUrl(),
				TEST_READ_ONLY_ENDPOINT);
	}

	@Test
	public void configureGraphEndpoint() throws Exception {
		SocialCrawlerFacebookClient.configureGraphEndpoint(TEST_GRAPH_ENDPOINT);
		SocialCrawlerFacebookClient client = new SocialCrawlerFacebookClient(
				"token");
		assertEquals(client.getFacebookGraphEndpointUrl(), TEST_GRAPH_ENDPOINT);
		assertEquals(client.getFacebookGraphVideoEndpointUrl(),
				FACEBOOK_GRAPH_VIDEO_ENDPOINT_URL);
		assertEquals(client.getFacebookReadOnlyEndpointUrl(),
				FACEBOOK_READ_ONLY_ENDPOINT_URL);
	}

	@After
	public void after() {
		SocialCrawlerFacebookClient.configure(null);
	}

	@Before
	public void before() {
		SocialCrawlerFacebookClient.configure(null);
	}

	@Test
	public void dontConfigure() throws Exception {
		SocialCrawlerFacebookClient client = new SocialCrawlerFacebookClient(
				"token");
		assertEquals(client.getFacebookGraphEndpointUrl(),
				FACEBOOK_GRAPH_ENDPOINT_URL);
		assertEquals(client.getFacebookGraphVideoEndpointUrl(),
				FACEBOOK_GRAPH_VIDEO_ENDPOINT_URL);
		assertEquals(client.getFacebookReadOnlyEndpointUrl(),
				FACEBOOK_READ_ONLY_ENDPOINT_URL);
	}
}

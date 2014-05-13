package com.galaksiya.social.crawler;

import com.restfb.DefaultFacebookClient;

public class SocialCrawlerFacebookClient extends DefaultFacebookClient {

	private static FacebookEndpointConfiguration configuration = null;

	public static void configure(FacebookEndpointConfiguration configuration) {
		SocialCrawlerFacebookClient.configuration = configuration;
	}

	public static void configureGraphEndpoint(String graphEndpointUrl) {
		SocialCrawlerFacebookClient.configuration = new FacebookEndpointConfiguration(
				graphEndpointUrl);
	}

	public SocialCrawlerFacebookClient(String accessToken) {
		super(accessToken);
	}

	@Override
	protected String getFacebookGraphEndpointUrl() {
		if (configuration != null
				&& configuration.getGraphEndpointUrl() != null) {
			return configuration.getGraphEndpointUrl();
		}
		return super.getFacebookGraphEndpointUrl();
	}

	@Override
	protected String getFacebookGraphVideoEndpointUrl() {
		if (configuration != null
				&& configuration.getGraphVideoEndpointUrl() != null) {
			return configuration.getGraphVideoEndpointUrl();
		}
		return super.getFacebookGraphVideoEndpointUrl();
	}

	@Override
	protected String getFacebookReadOnlyEndpointUrl() {
		if (configuration != null
				&& configuration.getReadOnlyEndpointUrl() != null) {
			return configuration.getReadOnlyEndpointUrl();
		}
		return super.getFacebookReadOnlyEndpointUrl();
	}

}

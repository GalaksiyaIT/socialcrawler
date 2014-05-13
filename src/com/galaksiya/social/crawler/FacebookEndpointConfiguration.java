package com.galaksiya.social.crawler;

public class FacebookEndpointConfiguration {

	private String graphEndpointUrl;

	private String graphVideoEndpointUrl;

	private String readOnlyEndpointUrl;

	public FacebookEndpointConfiguration(String graphEndpointUrl) {
		this(graphEndpointUrl, null, null);
	}

	public FacebookEndpointConfiguration(String graphEndpointUrl,
			String graphVideoEndpointUrl, String readOnlyEndpointUrl) {
		this.graphEndpointUrl = graphEndpointUrl;
		this.graphVideoEndpointUrl = graphVideoEndpointUrl;
		this.readOnlyEndpointUrl = readOnlyEndpointUrl;
	}

	public String getGraphEndpointUrl() {
		return graphEndpointUrl;
	}

	public String getGraphVideoEndpointUrl() {
		return graphVideoEndpointUrl;
	}

	public String getReadOnlyEndpointUrl() {
		return readOnlyEndpointUrl;
	}

}

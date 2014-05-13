package com.galaksiya.social.ontology.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class FoursquareOntologyVocabulary {

	public static final String FOURSQUARE_FILE_PATTERN = "semanticFS.owl";
	public static final String FOURSQUARE_SCHEMA_BASE_URI = CommonOntologyVocabulary.CUSTOM_ONTOLOGY_FOLDER_PATH
			+ FOURSQUARE_FILE_PATTERN + CommonOntologyVocabulary.DIEZ;
	public static final String CHECKIN_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "Checkin";
	public static final String VENUE_URI = FOURSQUARE_SCHEMA_BASE_URI + "Venue";
	public static final String CONTACT_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "Contact";
	public static final String SCORES_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "Scores";
	public static final String TIP_URI = FOURSQUARE_SCHEMA_BASE_URI + "Tip";
	public static final String TODO_URI = FOURSQUARE_SCHEMA_BASE_URI + "Todo";
	public static final String COMMENT_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "Comment";
	public static final String CATEGORY_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "Category";
	public static final String SOURCE_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "Source";
	public static final String SPECIAL_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "Special";
	public static final String STATISTICS_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "Statistics";
	public static final String RELATED_CHECKIN_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "related_checkin";
	public static final String HAS_SOURCE_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "has_source";
	public static final String VENUE_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "venue";
	public static final String HAS_TIP = FOURSQUARE_SCHEMA_BASE_URI + "has_tip";
	public static final String DISTANCE_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "distance";
	public static final String SHOUT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "shout";
	public static final String TIME_ZONE_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "timezone";
	public static final String HAS_COMMENT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "has_comment";
	public static final String IS_MAYOR_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "is_mayor";
	public static final String OVERLAPS_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "overlaps";
	public static final String IS_PRIVATE = FOURSQUARE_SCHEMA_BASE_URI
			+ "is_private";
	public static final String TEXT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "text";
	public static final String CATEGORY_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "category";
	public static final String CONTACT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "contact";
	public static final String HAS_SPECIAL_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "has_special";
	public static final String HAS_STAT_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "stats";
	public static final String HAS_TODO = FOURSQUARE_SCHEMA_BASE_URI
			+ "has_todo";
	public static final String IS_VERIFIED_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "is_verified";
	public static final String ICON_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "icon";
	public static final String PLURAL_NAME_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "pluralName";
	public static final String HAS_PARENT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "has_parent";
	public static final String PRIMARY_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "primary";
	public static final String FACEBOOK = FOURSQUARE_SCHEMA_BASE_URI
			+ "Facebook";
	public static final String TWITTER = FOURSQUARE_SCHEMA_BASE_URI + "Twitter";
	public static final String PHONE_PRP_URI = CommonOntologyVocabulary.FOAF_BASE_URI
			+ "phone";
	public static final String CHECKIN_COUNT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "checkin_count";
	public static final String USER_COUNT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "user_count";
	public static final String FINE_PRINT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "finePrint";
	public static final String MESSAGE_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "message";
	public static final String PROVIDER_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "provider";
	public static final String UNLOCKED_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "unlocked";
	public static final String GOAL_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "goal";
	public static final String MAX_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI + "max";
	public static final String RECENT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "recent";
	public static final String CHECKIN_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "has_checkin";
	public static final String BADGE_COUNT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "badge_count";
	public static final String FOLLOWER_COUNT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "follower_count";
	public static final String FOLLOWING_COUNT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "following_count";
	public static final String HAS_MAYORSHIP_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "has_mayorship";
	public static final String PINGS_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "pings";
	public static final String RELATIONSHIP_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "relationship";
	public static final String REQUEST_COUNT_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "request_count";
	public static final String HAS_SCORE_PRP_URI = FOURSQUARE_SCHEMA_BASE_URI
			+ "has_score";
	public static final String RELATIONSHIP_VALUE = "self";

	public static final Resource CONTACT_RSC = ResourceFactory
			.createResource(CONTACT_URI);
	public static final Resource CATEGORY_RSC = ResourceFactory
			.createResource(CATEGORY_URI);
	public static final Resource VENUE_RSC = ResourceFactory
			.createResource(VENUE_URI);
	public static final Resource CHECKIN_RSC = ResourceFactory
			.createResource(CHECKIN_URI);
	public static final String FOURSQUARE_HOMEPAGE_URI = "http://www.foursquare.com";
	public static final Resource FOURSQUARE_HOMEPAGE_RSC = ResourceFactory
			.createResource(FOURSQUARE_HOMEPAGE_URI);
	public static final Property CHECKIN_PRP = ResourceFactory
			.createProperty(CHECKIN_PRP_URI);
	public static final Property VENUE_PRP = ResourceFactory
			.createProperty(VENUE_PRP_URI);
	public static final Property RELATIONSHIP_PRP = ResourceFactory
			.createProperty(RELATIONSHIP_PRP_URI);
	public static final Property BADGE_COUNT_PRP = ResourceFactory
			.createProperty(BADGE_COUNT_PRP_URI);
	public static final Property FOLLOWING_COUNT_PRP = ResourceFactory
			.createProperty(FOLLOWING_COUNT_PRP_URI);
	public static final Property PINGS_PRP = ResourceFactory
			.createProperty(PINGS_PRP_URI);
	public static final Property CONTACT_PRP = ResourceFactory
			.createProperty(CONTACT_PRP_URI);
	public static final Property FACEBOOK_PRP = ResourceFactory
			.createProperty(FACEBOOK);
	public static final Property TWITTER_PRP = ResourceFactory
			.createProperty(TWITTER);

}

package com.galaksiya.social.ontology.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class FacebookOntologyVocabulary {

	public static final String FACEBOOK_FILE_PATTERN = "semanticFB.owl";
	public static final String FACEBOOK_SCHEMA_BASE_URI = CommonOntologyVocabulary.CUSTOM_ONTOLOGY_FOLDER_PATH
			+ FACEBOOK_FILE_PATTERN + CommonOntologyVocabulary.DIEZ;
	public static final String NEWS_FEED_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "NewsFeed";
	public static final String PROFILE_FEED_RSC_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "ProfileFeed";
	public static final String PHOTO_ALBUM_RSC_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "PhotoAlbum";
	public static final String EVENT_RSC_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "Event";
	public static final String VIDEO_RSC_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "Video";
	public static final String TEAM_RSC_URI = FACEBOOK_SCHEMA_BASE_URI + "Team";
	public static final String POST_RSC_URI = CommonOntologyVocabulary.SIOC_BASE_URI
			+ "Post";
	public static final String LIKES = FACEBOOK_SCHEMA_BASE_URI + "likes";
	public static final String HAS_PHOTO_ALBUM_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "has_photoAlbum";
	public static final String JOINS_PRP_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "joins";

	public static final String INTERESTED_IN_PRP_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "interestedIn";
	public static final String INTEREST_RSC_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "Interest";

	public static final String HAS_VIDEO_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "has_video";
	public static final String HAS_POST_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "has_post";
	public static final String FAVOURITE_TEAM_PRP_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "favouriteTeam";
	public static final String RELIGION_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "religion";
	public static final String ORGANIZED_IN_PRP_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "organized_in";
	public static final String APSIS_PRP_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "apsis";
	public static final String ORDINATE_PRP_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "ordinate";
	/**
	 * Family Bond URIs
	 */
	public static final String HUSBAND_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasHusband";
	public static final String BROTHER_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasBrother";
	public static final String FATHER_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasFather";
	public static final String SON_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasSon";
	public static final String UNCLE_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasUncle";
	public static final String NEPHEW_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "isNephewOf";
	public static final String GRANDFATHER_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasGrandfather";
	public static final String WIFE_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasWife";
	public static final String SISTER_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasSister";
	public static final String MOTHER_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasMother";
	public static final String DAUGHTER_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasDaughter";
	public static final String AUNT_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasAunt";
	public static final String NIECE_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "isNieceOf";
	public static final String GRANDMOTHER_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasGrandmother";
	public static final String PARTNER_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "isSpouseOf";
	public static final String COUSIN_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "isFirstCousinOf";
	public static final String PARENT_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasParent";
	public static final String GRANDPARENT_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasGrandParent";
	public static final String CHILD_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "hasChild";
	public static final String GRANDCHILD_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "isAncestorOf";
	public static final String IS_DIRECT_SIBLING_OF_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "isDirectSiblingOf";
	public static final String IS_PARENT_OF_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "isParentOf";
	public static final String IS_BROTHER_OF_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "isBrotherOf";
	public static final String IS_SISTER_OF_PRP_URI = CommonOntologyVocabulary.FAMILY_BOND_URI
			+ "isSisterOf";
	public static final Resource TEAM_RSC = ResourceFactory
			.createResource(TEAM_RSC_URI);
	public static final Resource EVENT_RSC = ResourceFactory
			.createResource(EVENT_RSC_URI);
	public static final String FACEBOOK_HOMEPAGE_URI = "http://www.facebook.com";
	public static final Resource FACEBOOK_HOMEPAGE_RSC = ResourceFactory
			.createResource(FACEBOOK_HOMEPAGE_URI);
	public static final Property LIKES_PRP = ResourceFactory
			.createProperty(LIKES);
	public static final Property JOINS_PRP = ResourceFactory
			.createProperty(JOINS_PRP_URI);
	public static final String MUSIC_ARTIST_RSC_URI = "http://purl.org/ontology/mo/MusicArtist";
	public static final String RECORD_RSC_URI = "http://purl.org/ontology/mo/Record";
	public static final String TRACK_RSC_URI = "http://purl.org/ontology/mo/Track";
	public static final String TV_SHOW_RSC_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "TVShow";
	public static final String AUTHOR_URI = FACEBOOK_SCHEMA_BASE_URI + "Author";
	public static final String PRODUCER_RSC_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "Producer";
	public static final String ACTOR_DIRECTOR_RSC_URI = FACEBOOK_SCHEMA_BASE_URI
			+ "Actor/Director";
}

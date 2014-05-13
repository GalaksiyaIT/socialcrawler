package com.galaksiya.social.query;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.hp.hpl.jena.vocabulary.OWL;

public class SampleQueries {

	public static final String ALL_USER_DATA_SELECT = "PREFIX rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ "> \n \t SELECT * \n \t WHERE {?person rdf:type foaf:Person. \n \t \t?person ?predicate ?object} LIMIT 30";

	/**
	 * Retrieves person instances who have an account information.
	 */
	public static final String REGISTERED_USER_DATA_SELECT = "PREFIX rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ "> \n \t SELECT DISTINCT ?person \n \t WHERE {?person rdf:type foaf:Person. \n \t \t?person foaf:account ?account. } LIMIT 30";

	public static final String ALL_USER_DATA_CONSTRUCT = "PREFIX rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ "> \n \t CONSTRUCT {?person ?predicate ?object}  \n \t WHERE {?person rdf:type foaf:Person. \n \t \t?person ?predicate ?object} LIMIT 30";

	// lists venues of checkins that I've done.
	public static final String CHECKIN_VENUE_SELECT_QUERY = "PREFIX rdf: <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX foursquare: <"
			+ FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI
			+ ">\nPREFIX foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ "> \n \tSELECT DISTINCT ?venueName \n\tWHERE { ?person foaf:name \"Input Name\".\n \t \t ?person foursquare:has_checkin ?checkin.\n \t \t ?checkin foursquare:venue ?venue.\n \t \t ?venue foaf:name ?venueName.}";

	// lists friends who have checked in the venues that I've checked in there.
	public static final String FRIENDS_WHO_CHECKED_IN_SAME_PLACE_WITH_ME = "PREFIX rdf: <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX owl: <"
			+ OWL.getURI()
			+ ">\nprefix foursquare: <"
			+ FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI
			+ ">\nprefix foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ "> \n \tSELECT DISTINCT ?friendName ?venueName \n\tWHERE { ?person foaf:name \"Input Name\".\n \t \t ?person foaf:knows ?friend.\n \t \t ?person foursquare:has_checkin ?selfCheckin.\n \t \t ?selfCheckin foursquare:venue ?venue.\n \t \t ?friend foaf:name ?friendName.\n \t \t ?friend foursquare:has_checkin ?friendCheckin.\n \t \t ?friendCheckin foursquare:venue ?venue. \n \t \t ?venue foaf:name ?venueName.}";

	// lists likes friends who have checked in the venues that I've checked in
	// there.
	public static final String LIKED_MUSICS_BY_FRIENDS_WHO_CHECKED_IN_SAME_PLACE_WITH_ME = "prefix rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nprefix foursquare: <"
			+ FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI
			+ ">\nprefix facebook: <"
			+ FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI
			+ ">\nprefix foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ ">\nprefix dbpedia: <"
			+ CommonOntologyVocabulary.DBPEDIA_BASE_URI
			+ ">\nprefix owl: <"
			+ OWL.getURI()
			+ "> \n \tSELECT DISTINCT ?friendName ?musicName ?venueName \n\tWHERE {?person foaf:name \"Input Name\".\n \t \t ?person foaf:knows ?friend.\n \t \t ?person foursquare:has_checkin ?selfCheckin.\n \t \t ?selfCheckin foursquare:venue ?venue.\n \t \t ?friend foaf:name ?friendName.\n \t \t ?friend foursquare:has_checkin ?friendCheckin.\n \t \t ?friendCheckin foursquare:venue ?venue. \n \t \t ?venue foaf:name ?venueName.\n \t \t ?friend facebook:likes ?music.\n \t \t ?music foaf:name ?musicName.\n \t \t ?music rdf:type dbpedia:Music.\n \t \t ?friend foaf:name ?friendName. }";

	// lists venues of checkins that I've done.
	public static final String FRIENDS_FOOTBALL_FAN_OF_SAME_TEAM_SELECT_QUERY = "prefix rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nprefix facebook: <"
			+ FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI
			+ ">\nprefix foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ "> \n \tSELECT DISTINCT ?friendName ?teamName \n\tWHERE { ?person foaf:name \"Input Name\".\n \t \t ?person facebook:favouriteTeam ?team.\n \t \t ?person foaf:knows ?friend.\n \t \t ?friend facebook:favouriteTeam ?team.\n \t \t ?team foaf:name ?teamName.\n \t \t ?friend foaf:name ?friendName.}";

	// lists venues of checkins that I've done.
	public static final String FRIENDS_BOTH_FOOTBALL_FAN_OF_SAME_TEAM_AND_LOCATION_SELECT_QUERY = "prefix rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nprefix exlgdo: <"
			+ CommonOntologyVocabulary.LGDO_EXTENDING_ONTOLOGY_URI
			+ ">\nprefix facebook: <"
			+ FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI
			+ ">\nprefix foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ "> \n \tSELECT DISTINCT ?friendName ?teamName ?location \n\tWHERE { ?person foaf:name \"Input Name\".\n \t \t ?person facebook:favouriteTeam ?team.\n \t \t ?person foaf:knows ?friend.\n \t \t ?person exlgdo:location ?location.\n \t \t ?friend foaf:name ?friendName.\n \t \t ?friend facebook:favouriteTeam ?team.\n \t \t ?team foaf:name ?teamName.\n \t \t ?friend exlgdo:location ?location.}";

	// lists venues of checkins that I've done.
	public static final String CHECKINS_OF_FRIENDS_BOTH_FOOTBALL_FAN_OF_SAME_TEAM_AND_LOCATION_SELECT_QUERY = "PREFIX rdf: <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX exlgdo: <"
			+ CommonOntologyVocabulary.LGDO_EXTENDING_ONTOLOGY_URI
			+ ">\nPREFIX facebook: <"
			+ FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI
			+ ">\nPREFIX foursquare: <"
			+ FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI
			+ ">\nPREFIX foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ ">\nPREFIX owl: <"
			+ OWL.getURI()
			+ "> \n \tSELECT DISTINCT ?friendName ?venueName ?teamName ?location  \n\tWHERE { ?person foaf:name \"Input Name\".\n \t \t ?person facebook:favouriteTeam ?team.\n \t \t ?person foaf:knows ?friend.\n \t \t ?person exlgdo:location ?location.\n \t \t ?friend foaf:name ?friendName.\n \t \t ?friend facebook:favouriteTeam ?team.\n \t \t ?team foaf:name ?teamName.\n \t \t ?friend exlgdo:location ?location.\n \t \t ?friend foursquare:has_checkin ?checkin.\n \t \t ?checkin foursquare:venue ?venue.\n \t \t ?venue foaf:name ?venueName.}";

	// lists checkins of a person with venue names
	public static final String SIMPLE_CHECKIN_SELECT_QUERY = "prefix rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX foursquare: <"
			+ FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI
			+ ">\nPREFIX foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ "> \n \tSELECT ?person ?personName ?venueName ?categoryName \n\tWhere {?person foaf:name ?personName.\n \t \t?person foursquare:has_checkin ?checkin.\n\t \t?checkin foursquare:venue ?venue.\n\t \t?venue foaf:name ?venueName. \n\t \t?venue foursquare:category ?category. \n\t \t?category foaf:name ?categoryName.}";

	public static final String SIMPLE_CHECKIN_CONSTRUCT_QUERY = "PREFIX rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX foursquare: <"
			+ FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI
			+ ">\nPREFIX foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ ">\n\tCONSTRUCT {?person foaf:name ?personName.\n\t\t?person foursquare:has_checkin ?checkin. \n\t\t?checkin foursquare:venue ?venue.\n\t\t?venue foursquare:category ?category.\n\t\t?venue foaf:name ?venueName. \n\t\t?category foaf:name ?categoryName.} \n \t Where {?person foaf:name ?personName.\n \t \t?person foursquare:has_checkin ?checkin.\n\t \t?checkin foursquare:venue ?venue.\n\t \t?venue foaf:name ?venueName. \n\t \t?venue foursquare:category ?category. \n\t \t?category foaf:name ?categoryName.}";

	public static final String MOVIE_LIKE_QUERY_SELECT = "prefix rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX facebook: <"
			+ FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI
			+ ">\nPREFIX foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ ">\nPREFIX dbpedia: <"
			+ CommonOntologyVocabulary.DBPEDIA_BASE_URI
			+ "> \n \tSELECT ?person ?personName ?movieName  \n\tWHERE { ?person foaf:name ?personName.\n \t \t ?person facebook:likes ?movie.\n\t \t ?movie foaf:name ?movieName.\n\t \t ?movie rdf:type dbpedia:Film }";

	public static final String MOVIE_LIKE_QUERY_CONSTRUCT = "prefix rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX facebook: <"
			+ FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI
			+ ">\nPREFIX foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ ">\nPREFIX dbpedia: <"
			+ CommonOntologyVocabulary.DBPEDIA_BASE_URI
			+ ">\n\tCONSTRUCT {?person foaf:name ?personName.\n\t?person facebook:likes ?movie. \n\t?movie foaf:name ?movieName} \n \t Where { ?person foaf:name ?personName.\n \t \t ?person facebook:likes ?movie.\n\t \t ?movie foaf:name ?movieName.\n\t \t ?movie rdf:type dbpedia:Film }";

	public static final String POSITIONS_SELECT_QUERY = "prefix rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nprefix foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ ">\nprefix  cv:  <"
			+ CommonOntologyVocabulary.CV_RDFS_URI
			+ "> "
			+ "\n\tSelect ?person ?personName ?companyName ?title ?startDate ?endDate \n\tWhere { ?person foaf:name ?personName. \n\t\t?person cv:hasWorkHistory ?workHistory. \n\t\t?workHistory cv:employedIn ?company. \n\t\t?company foaf:name ?companyName. \n\t\t?workHistory cv:jobTitle ?title. \n\t\t?workHistory cv:startDate ?startDate. \n\t\t?workHistory cv:endDate ?endDate.}";

	public static final String POSITIONS_CONSTRUCT_QUERY = "prefix rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ ">\nPREFIX  cv:  <"
			+ CommonOntologyVocabulary.CV_RDFS_URI
			+ "> "
			+ "\n\t CONSTRUCT {?person foaf:name  ?personName. \n\t\t?person cv:hasWorkHistory ?workHistory.\n\t\t?workHistory cv:employedIn ?company. \n\t\t?company foaf:name ?companyName. \n\t\t?workHistory cv:jobTitle ?title. \n\t\t?workHistory cv:startDate ?startDate. \n\t\t?workHistory cv:endDate ?endDate.} \n\tWhere { ?person foaf:name ?personName. \n\t\t?person cv:hasWorkHistory ?workHistory. \n\t\t?workHistory cv:employedIn ?company. \n\t\t?company foaf:name ?companyName. \n\t\t?workHistory cv:jobTitle ?title. \n\t\t?workHistory cv:startDate ?startDate. \n\t\t?workHistory cv:endDate ?endDate.}";

	public static final String MAX_CHECKIN_SELECT_QUERY = "PREFIX rdf:  <"
			+ CommonOntologyVocabulary.RDF_URI
			+ ">\nPREFIX foursquare: <"
			+ FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI
			+ ">\nprefix foaf: <"
			+ CommonOntologyVocabulary.FOAF_BASE_URI
			+ "> SELECT ?venueName (MAX(?count) AS ?maxCount) WHERE {SELECT ?venueName (COUNT(?venueName) AS ?count) WHERE {  ?checkin foursquare:venue ?venue.  ?checkin rdf:type foursquare:Checkin. ?venue foaf:name ?venueName. } GROUP BY ?venueName} ";

}

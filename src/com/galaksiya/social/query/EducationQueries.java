package com.galaksiya.social.query;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.LinkedinOntologyVocabulary;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

/**
 * This class contains Education queries.
 * 
 * @author etmen
 * 
 */
public class EducationQueries {

	/**
	 * Retrieves friends of the person with given name.
	 */
	public static final String SELECT_FRIENDS_OF_SOMEONE_QUERY = String
			.format("PREFIX foaf: <%s> \n \t Select ?friendName \n \t Where {?person foaf:name \"Input Name\". \n \t \t?person foaf:knows ?friend. \n \t\t?friend foaf:name ?friendName. } ",
					FOAF.getURI());

	/**
	 * Asks for a friend with the same team with a specific person.
	 */
	public static final String ASK_FRIENDS_WITH_SAME_TEAM_QUERY = String
			.format("PREFIX foaf: <%s> \nPREFIX facebook: <%s> \n\tASK WHERE {?person foaf:name \"Input Name\". \n \t \t?person facebook:favouriteTeam ?team. \n \t\t?friend facebook:favouriteTeam ?team. \n\t\t?person foaf:knows ?friend.} ",
					FOAF.getURI(),
					FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI);

	/**
	 * Query that returns the description of a specific person.
	 */
	public static final String DESCRIBE_SOMEONE_QUERY = String
			.format("PREFIX foaf: <%s> \n\tDESCRIBE ?person WHERE {?person foaf:name \"Input Name\". } ",
					FOAF.getURI());

	/**
	 * Query that returns a graph containing person names for a specific school.
	 */
	public static final String CONSTRUCT_PERSONS_WITH_SAME_SCHOOL_QUERY = String
			.format("PREFIX foaf: <%s> \nPREFIX cv: <%s> \n\tCONSTRUCT {?person foaf:name ?personName. } \n\tWHERE {?school foaf:name \"Ege Üniversitesi\". \n\t\t?edu cv:studiedIn ?school. \n\t\t?person cv:hasEducation ?edu. \n\t\t?person foaf:name ?personName. } ",
					FOAF.getURI(), CommonOntologyVocabulary.CV_RDFS_URI);

	/**
	 * Query that selects venues that checked in by someone and their address if
	 * specified.
	 */
	public static final String SELECT_VENUES_AND_OPTIONALLY_ADDRESSES = String
			.format("PREFIX foaf: <%s> \nPREFIX foursquare: <%s> \nPREFIX exlgdo: <%s> \nPREFIX social: <%s> \n\tSELECT ?venueName ?address \n\tWHERE {?person foaf:name \"Input Name\". \n\t\t?person foursquare:has_checkin ?checkin. \n\t\t?checkin foursquare:venue ?venue. \n\t\t?venue foaf:name ?venueName. \n\t\tOPTIONAL{?venue exlgdo:location ?location. \n\t\t\t?location social:has_address ?address. } \n\t\t} ",
					FOAF.getURI(),
					FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI,
					CommonOntologyVocabulary.LGDO_EXTENDING_ONTOLOGY_URI,
					CommonOntologyVocabulary.COSOSE_BASE_URI);

	/**
	 * Query that selects union of person who like Inception or another.
	 */
	public static final String SELECT_UNION_OF_FILM_LOVERS = String
			.format("PREFIX foaf: <%s> \nPREFIX facebook: <%s> \nPREFIX dc: <%s> \n\tSELECT ?personName \n\tWHERE {?person foaf:name ?personName. \n\t\t?person facebook:likes ?movie. \n\t\t{ {?movie foaf:name \"Cedric\".} UNION {?movie foaf:name \"Step Up Movie\".} } \n}",
					FOAF.getURI(),
					FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI,
					CommonOntologyVocabulary.LGDO_EXTENDING_ONTOLOGY_URI,
					CommonOntologyVocabulary.COSOSE_BASE_URI);

	/**
	 * Query that selects persons ordered by their badge counts.
	 */
	public static final String SELECT_PERSONS_ORDERED_BY_BADGE_COUNT = String
			.format("PREFIX foaf: <%s> \nPREFIX foursquare: <%s> \nPREFIX xsd: <%s> \n\tSELECT ?personName ?badgeCount \n\tWHERE {?person foaf:name ?personName. \n\t\t?person foursquare:badge_count ?badgeCount. } \nt\tORDER BY DESC(xsd:int(?badgeCount))",
					FOAF.getURI(),
					FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI,
					XSD.getURI());

	/**
	 * Query that selects persons ordered by their badge counts with limits.
	 */
	public static final String SELECT_PERSONS_ORDERED_BY_BADGE_COUNT_LIMIT_OFFSET = String
			.format("PREFIX foaf: <%s> \nPREFIX foursquare: <%s> \n\tSELECT ?personName ?badgeCount \n\tWHERE {?person foaf:name ?personName. \n\t\t?person foursquare:badge_count ?badgeCount.} \n\t\tORDER BY DESC(?badgeCount) OFFSET 2 LIMIT 1",
					FOAF.getURI(),
					FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI);

	/**
	 * Query that
	 */
	public static final String FILTER_BY_GRADUATION_YEAR_QUERY = getPrefixes()
			+ "SELECT ?personName \nWHERE {\n\t?person foaf:name ?personName. \n\t?person cv:hasEducation ?edu. \n\t?edu cv:eduGradDate ?year. \n\tFILTER(?year=\"2010\")\n}";

	/**
	 * Query that
	 */
	public static final String FILTER_BY_VENUE_NAME = getPrefixes()
			+ "SELECT ?personName ?venueName \nWHERE { \n\t?person foursquare:has_checkin ?checkin. \n\t?checkin foursquare:venue ?venue. \n\t?venue foaf:name ?venueName. \n\t?person foaf:name ?personName. \n\tFILTER regex(?venueName, \"laboratory\", \"i\") \n}";

	/**
	 * Query that
	 */
	public static final String FILTER_BY_YEAR_OR = getPrefixes()
			+ "SELECT ?personName ?year ?major \nWHERE {\n\t?person foaf:name ?personName. \n\t?person cv:hasEducation ?edu. \n\t?edu cv:eduGradDate ?year. \n\t?education cv:eduMajor ?major. \n\tFILTER(?year=\"2010\" || ?year=\"2011\")\n} ";

	/**
	 * Query that
	 */
	public static final String SELECT_CITY_REGION_SERVICE = getPrefixes()
			+ getPrefixDef("yago", "http://dbpedia.org/class/yago/")
			+ "SELECT DISTINCT ?region \nWHERE { \n\tSERVICE <http://sociallinker.galaksiya.com/sparql> { \n\t\t?person foaf:name ?personName. \n\t\t?person social:hometown ?city. \n\t} \n\tSERVICE <http://dbpedia.org/sparql> { \n\t\t?city dbpedia-owl:isPartOf ?region. \n\t\t?region rdf:type yago:RegionsOfTurkey \n\t} \n}";

	private static String getPrefixes() {
		String prefixes = getPrefixDef("rdf", RDF.getURI())
				+ getPrefixDef("rdfs", RDFS.getURI())
				+ getPrefixDef("foaf", FOAF.getURI())
				+ getPrefixDef("facebook",
						FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI)
				+ getPrefixDef("foursquare",
						FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI)
				+ getPrefixDef("linkedin",
						LinkedinOntologyVocabulary.LINKEDIN_SCHEMA_BASE_URI)
				+ getPrefixDef("social",
						CommonOntologyVocabulary.COSOSE_BASE_URI)
				+ getPrefixDef("cv", CommonOntologyVocabulary.CV_RDFS_URI)
				+ getPrefixDef("exlgdo",
						CommonOntologyVocabulary.LGDO_EXTENDING_ONTOLOGY_URI)
				+ getPrefixDef("sioc", CommonOntologyVocabulary.SIOC_BASE_URI)
				+ getPrefixDef("dbpedia-owl",
						CommonOntologyVocabulary.DBPEDIA_BASE_URI) + "\n";
		return prefixes;
	}

	private static String getPrefixDef(String prefix, String uri) {
		return String.format("PREFIX %s: <%s>\n", prefix, uri);
	}
}

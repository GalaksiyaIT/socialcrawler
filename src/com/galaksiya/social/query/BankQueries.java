package com.galaksiya.social.query;

import com.galaksiya.social.ontology.vocabulary.BankVocabulary;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;

public class BankQueries {
	public static final String PREFIXES = String
			.format("PREFIX owl: <%s> \nPREFIX rdf: <%s> \nPREFIX foaf: <%s> \nPREFIX foursquare: <%s> \nPREFIX exlgdo: <%s> \nPREFIX dbp-ont: <%s> \nPREFIX dbp-rsc: <%s> \nPREFIX finance: <%s> \nPREFIX bank-product-rsc: <%s> \nPREFIX cv:<%s> \n\n",
					OWL.getURI(), RDF.getURI(), FOAF.getURI(),
					FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI,
					CommonOntologyVocabulary.LGDO_EXTENDING_ONTOLOGY_URI,
					CommonOntologyVocabulary.DBPEDIA_BASE_URI,
					"http://dbpedia.org/resource/",
					BankVocabulary.FINANCE_ONTOLOGY_URI,
					"http://galaksiya.com:8090/resource/product/",
					CommonOntologyVocabulary.CV_RDFS_URI);

	public static final String CREDIT_CARD_BLOCKED = PREFIXES
			+ "SELECT DISTINCT ?person WHERE {\n\tSERVICE <http://galaksiya.com:8080/sociallinker/sparql> {\n\t\t?person rdf:type foaf:Person. \n\t\t?person owl:sameAs ?bankPerson. \n\t\t?person exlgdo:location ?livesIn. \n\t\t?person foursquare:has_checkin ?checkin. \n\t\t?checkin foursquare:venue ?venue. \n\t\t?venue exlgdo:location ?location.\n\t\t?location dbp-ont:city ?city.\n\t}\n\tSERVICE <http://dbpedia.org/sparql> {\n\t\t?city dbp-ont:isPartOf dbp-rsc:Aegean_Region. \n\t\t?livesIn dbp-ont:isPartOf dbp-rsc:Marmara_Region. \n\t} \n\tSERVICE <http://galaksiya.com:8090/sparql> {\n\t\t ?account finance:hasCustomer ?bankPerson. \n\t\t?account finance:hasProduct bank-product-rsc:3000220000. \n\t\t?account finance:blocked \"false\". \n\t} \n} ";

	public static final String EGE_UNIVERSITY_DEPOSIT_ACCOUNT = PREFIXES
			+ "SELECT DISTINCT ?person ?avg WHERE {\n\tSERVICE <http://galaksiya.com:8080/sociallinker/sparql> {\n\t\t?person rdf:type foaf:Person. \n\t\t?person owl:sameAs ?bankPerson. \n\t\t?person cv:hasEducation ?edu. \n\t\t?edu cv:studiedIn ?school. \n\t\t?school foaf:name \"Ege University\". \n\t}\n\tSERVICE <http://galaksiya.com:8090/sparql> {\n\t\t ?account finance:hasCustomer ?bankPerson. \n\t\t?account finance:hasProduct bank-product-rsc:3000201000. \n\t\t?account finance:average ?avg. \n\t} \n} ";

	public static final String EGE_UNIVERSITY_HGS = PREFIXES
			+ "SELECT DISTINCT ?person WHERE {\n\tSERVICE <http://galaksiya.com:8080/sociallinker/sparql> {\n\t\t?person rdf:type foaf:Person. \n\t\t?person owl:sameAs ?bankPerson. \n\t\t?person cv:hasEducation ?edu. \n\t\t?edu cv:studiedIn ?school. \n\t\t?school foaf:name \"Ege University\". \n\t}\n\tSERVICE <http://galaksiya.com:8090/sparql> {\n\t\t ?account finance:hasCustomer ?bankPerson. \n\t\tFILTER NOT EXISTS { ?account finance:hasProduct bank-product-rsc:3043000000. } \n\t} \n} ";
}

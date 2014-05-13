package com.galaksiya.social.ontology.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class LinkedinOntologyVocabulary {

	public static final String LINKEDIN_FILE_PATH = "semanticLI.owl";
	public static final String LINKEDIN_SCHEMA_BASE_URI = CommonOntologyVocabulary.CUSTOM_ONTOLOGY_FOLDER_PATH
			+ LINKEDIN_FILE_PATH + CommonOntologyVocabulary.DIEZ;
	public static final String HEADLINE_URI = LINKEDIN_SCHEMA_BASE_URI
			+ "headline";
	public static final String SKILLS_EXPERIENCE_URI = LINKEDIN_SCHEMA_BASE_URI
			+ "skills_experience";
	public static final String JOB_FUNCTION_URI = LINKEDIN_SCHEMA_BASE_URI
			+ "jobFunction";
	public static final String WEBSITE_URI = LINKEDIN_SCHEMA_BASE_URI
			+ "website";
	public static final String SPECIALITY_URI = LINKEDIN_SCHEMA_BASE_URI
			+ "speciality";
	public static final String STOCK_EXCHANGE_URI = LINKEDIN_SCHEMA_BASE_URI
			+ "stock_exchange";
	public static final String NUM_FOLLOWERS_URI = LINKEDIN_SCHEMA_BASE_URI
			+ "num_followers";
	public static final String EMPLOYEE_COUNT_RANGE_URI = LINKEDIN_SCHEMA_BASE_URI
			+ "employee_count_range";
	public static final String LINKEDIN_HOMEPAGE_URI = "http://www.linkedin.com";
	public static final Resource LINKEDIN_HOMEPAGE_RSC = ResourceFactory
			.createResource(LINKEDIN_HOMEPAGE_URI);
	public static final Property HEADLINE_PRP = ResourceFactory
			.createProperty(HEADLINE_URI);

}

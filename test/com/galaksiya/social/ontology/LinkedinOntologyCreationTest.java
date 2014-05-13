package com.galaksiya.social.ontology;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.LinkedinOntologyVocabulary;
import com.hp.hpl.jena.vocabulary.XSD;

public class LinkedinOntologyCreationTest extends OntologyCreationTest {

	@Test
	public void createOntologySchema() throws Exception {

		// assert ontmodel is not null
		assertNotNull(getSchemaOntModel());

		// assert that ontology contains Linkedin schema classes...
		ontologyTestUtility.assertDataTypeProperty(
				LinkedinOntologyVocabulary.HEADLINE_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				LinkedinOntologyVocabulary.EMPLOYEE_COUNT_RANGE_URI,
				CommonOntologyVocabulary.COMPANY_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				LinkedinOntologyVocabulary.JOB_FUNCTION_URI,
				CommonOntologyVocabulary.WORK_HISTORY_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				LinkedinOntologyVocabulary.NUM_FOLLOWERS_URI,
				CommonOntologyVocabulary.COMPANY_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				LinkedinOntologyVocabulary.SKILLS_EXPERIENCE_URI,
				CommonOntologyVocabulary.WORK_HISTORY_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				LinkedinOntologyVocabulary.SPECIALITY_URI,
				CommonOntologyVocabulary.COMPANY_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				LinkedinOntologyVocabulary.STOCK_EXCHANGE_URI,
				CommonOntologyVocabulary.COMPANY_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				LinkedinOntologyVocabulary.WEBSITE_URI,
				CommonOntologyVocabulary.COMPANY_URI, XSD.xstring);
	}

	@Override
	protected String getOntURI() {
		return AltEntryManager.getLinkedinOntURI();
	}

	@Override
	protected OntologyCreator getOntologyCreator() {
		return new LinkedinOntologyCreator();
	}
}

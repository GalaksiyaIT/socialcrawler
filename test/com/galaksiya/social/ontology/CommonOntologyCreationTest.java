package com.galaksiya.social.ontology;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.hp.hpl.jena.ontology.OntModel;

/**
 * This class creates common concepts and properties used by both Facebook,
 * Foursquare and Linkedin
 * 
 * @author galaksiya
 * 
 */
public class CommonOntologyCreationTest extends OntologyCreationTest {

	/**
	 * returns schema ontology URI of {@link OntologyCreator} instance
	 * 
	 * @return {@link OntologyCreator#getCreatedSchema()}
	 */
	OntModel getSchemaOntmodel() {
		return creator.getCreatedSchema();
	}

	/**
	 * creating concepts contained in common ontology and asserting whether they
	 * are contained in ontology of creator.
	 * 
	 * @throws Exception
	 */
	@Test
	public void create() throws Exception {

		// asserting schema
		assertNotNull(getSchemaOntmodel());

		// assert that ontology contains common schema classes
		ontologyTestUtility.assertOntClass(CommonOntologyVocabulary.PHOTO_URI);

		// assert that ontology contains properties correctly

		// assert datatype properties
		ontologyTestUtility.assertDataTypeProperty(
				CommonOntologyVocabulary.DESCRIPTION_URI, null, null);
		ontologyTestUtility.assertDataTypeProperty(
				CommonOntologyVocabulary.TYPE_PRP_URI, null, null);
		ontologyTestUtility.assertDataTypeProperty(
				CommonOntologyVocabulary.CREATED_AT_PRP_URI, null, null);
		ontologyTestUtility.assertDataTypeProperty(
				CommonOntologyVocabulary.STATUS_URI, null, null);
		ontologyTestUtility.assertDataTypeProperty(
				CommonOntologyVocabulary.ADDRESS_PRP_URI, null, null);

		// assert objecttype properties
		ontologyTestUtility.assertObjectTypeProperty(
				CommonOntologyVocabulary.HOMETOWN_URI, null, null);
		ontologyTestUtility.assertObjectTypeProperty(
				CommonOntologyVocabulary.OWNER_URI, null, null);
		ontologyTestUtility.assertObjectTypeProperty(
				CommonOntologyVocabulary.HAS_PHOTO_URI, null,
				CommonOntologyVocabulary.PHOTO_URI);

	}

	@Override
	protected String getOntURI() {
		return AltEntryManager.getSosepOntURI();
	}

	@Override
	protected OntologyCreator getOntologyCreator() {
		return new OntologyCreator();
	}

}

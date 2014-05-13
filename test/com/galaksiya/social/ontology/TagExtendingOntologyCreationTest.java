package com.galaksiya.social.ontology;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;

public class TagExtendingOntologyCreationTest extends OntologyCreationTest {

	@Test
	public void assertExtendingTAGOntology() throws Exception {

		// checking whether the schema is null.
		assertNotNull(getSchemaOntModel());

		// checking object type property
		ontologyTestUtility.assertObjectTypeProperty(
				CommonOntologyVocabulary.HAS_TAG_PRP_URI, null,
				CommonOntologyVocabulary.TAG_RSC_URI);

	}

	@Override
	protected String getOntURI() {
		return AltEntryManager.getTAGExtendingOntURI();
	}

	@Override
	protected OntologyCreator getOntologyCreator() {
		return new TAGExtendingOntologyCreator();
	}
}

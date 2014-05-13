package com.galaksiya.social.ontology;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;

public class LGDOExtendedOntologyCreationTest extends OntologyCreationTest {

	@Test
	public void assertExtendingLGDOOntology() throws Exception {

		assertNotNull(getSchemaOntModel());
		// assert object type property
		ontologyTestUtility.assertObjectTypeProperty(
				CommonOntologyVocabulary.LOCATION_URI, null,
				CommonOntologyVocabulary.GEODDATA_PLACE_URI);

	}

	@Override
	protected String getOntURI() {
		return AltEntryManager.getLGDOExtendingOntURI();
	}

	@Override
	protected OntologyCreator getOntologyCreator() {
		return new LGDOExtendingOntologyCreator();
	}
}

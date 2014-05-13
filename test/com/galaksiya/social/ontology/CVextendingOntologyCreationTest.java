package com.galaksiya.social.ontology;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;

public class CVextendingOntologyCreationTest extends OntologyCreationTest {

	@Test
	public void assertExtendingCVOntology() throws Exception {

		// assert ontmodel is not null
		assertNotNull(getSchemaOntModel());

		// assert that ontology contains CVExtendingOntology schema classes...
		ontologyTestUtility.assertObjectTypeProperty(
				CommonOntologyVocabulary.EMPLOYER_PRP_URI, null, null);
		ontologyTestUtility.assertDataTypeProperty(
				CommonOntologyVocabulary.WORK_POSITION_PRP_URI, null, null);

	}

	@Override
	protected String getOntURI() {
		return AltEntryManager.getCVExtendingOntURI();
	}

	@Override
	protected OntologyCreator getOntologyCreator() {
		return new CVExtendingOntologyCreator();
	}

}

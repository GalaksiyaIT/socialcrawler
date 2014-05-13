package com.galaksiya.social.ontology;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class CVExtendingOntologyCreator extends OntologyCreator {

	@Override
	protected OntModel createOntology() {
		OntModel extendedCVSchema = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		// initialize super schema
		setCreatedSchema(extendedCVSchema);

		AltEntryManager altEntryManager = new AltEntryManager(
				getCreatedSchema());
		altEntryManager.importWithAltEntry(
				CommonOntologyVocabulary.CV_EXTENDING_ONTOLOGY_URI,
				CommonOntologyVocabulary.CV_RDFS_URI,
				AltEntryManager.getCvOntURI());

		extendedCVSchema
				.createObjectProperty(CommonOntologyVocabulary.EMPLOYER_PRP_URI);

		extendedCVSchema
				.createDatatypeProperty(CommonOntologyVocabulary.WORK_POSITION_PRP_URI);

		extendedCVSchema
				.createOntology(CommonOntologyVocabulary.CV_EXTENDING_ONTOLOGY_URI);

		return extendedCVSchema;
	}
}

package com.galaksiya.social.ontology;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class TAGExtendingOntologyCreator extends OntologyCreator {
	@Override
	protected OntModel createOntology() {

		OntModel extendedTAGSchema = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);

		// initialize super schema
		setCreatedSchema(extendedTAGSchema);

		new AltEntryManager(getCreatedSchema()).importWithAltEntry(
				CommonOntologyVocabulary.TAG_EXTENDING_ONTOLOGY_URI,
				CommonOntologyVocabulary.TAG_BASE_URI,
				AltEntryManager.getCvOntURI());

		OntClass tagCls = extendedTAGSchema
				.createClass(CommonOntologyVocabulary.TAG_RSC_URI);

		ObjectProperty hasTagPrp = extendedTAGSchema
				.createObjectProperty(CommonOntologyVocabulary.HAS_TAG_PRP_URI);
		hasTagPrp.addRange(tagCls);

		extendedTAGSchema
				.createOntology(CommonOntologyVocabulary.TAG_EXTENDING_ONTOLOGY_URI);

		return extendedTAGSchema;
	}
}

package com.galaksiya.social.ontology;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class LGDOExtendingOntologyCreator extends OntologyCreator {
	@Override
	protected OntModel createOntology() {

		OntModel extendedLGDOSchema = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);

		// initialize super schema
		setCreatedSchema(extendedLGDOSchema);

		new AltEntryManager(getCreatedSchema()).importWithAltEntry(
				CommonOntologyVocabulary.LGDO_EXTENDING_ONTOLOGY_URI,
				CommonOntologyVocabulary.GEODATA_BASE_URI,
				AltEntryManager.getCvOntURI());

		OntClass placeCls = extendedLGDOSchema
				.createClass(CommonOntologyVocabulary.GEODDATA_PLACE_URI);

		ObjectProperty locationPrp = extendedLGDOSchema
				.createObjectProperty(CommonOntologyVocabulary.LOCATION_URI);

		locationPrp.addRange(placeCls);

		extendedLGDOSchema
				.createOntology(CommonOntologyVocabulary.LGDO_EXTENDING_ONTOLOGY_URI);

		return extendedLGDOSchema;
	}
}

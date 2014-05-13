package com.galaksiya.social.ontology;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Ontology schema is creating in this class
 * 
 * @author Burak YÖNYÜL
 * 
 */
public class OntologyCreator {
	private OntModel createdSchema;

	public OntologyCreator() {
		super();
		setCreatedSchema(createOntology());
	}

	protected void setCreatedSchema(OntModel ontModel) {
		this.createdSchema = ontModel;
	}

	public OntModel getCreatedSchema() {
		return createdSchema;
	}

	/**
	 * creates an ontology which includes the concepts both from Facebook,
	 * Foursquare and LinkedIn
	 * 
	 * @return {@link OntModel}
	 */
	protected OntModel createOntology() {
		createdSchema = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

		OntClass photoCls = createdSchema
				.createClass(CommonOntologyVocabulary.PHOTO_URI);

		ObjectProperty hasPhotoPrp = createdSchema
				.createObjectProperty(CommonOntologyVocabulary.HAS_PHOTO_URI);
		hasPhotoPrp.addRange(photoCls);

		createdSchema
				.createObjectProperty(CommonOntologyVocabulary.HOMETOWN_URI);

		createdSchema
				.createDatatypeProperty(CommonOntologyVocabulary.DESCRIPTION_URI);

		createdSchema
				.createDatatypeProperty(CommonOntologyVocabulary.TYPE_PRP_URI);

		createdSchema
				.createDatatypeProperty(CommonOntologyVocabulary.CREATED_AT_PRP_URI);

		createdSchema
				.createDatatypeProperty(CommonOntologyVocabulary.STATUS_URI);

		createdSchema.createObjectProperty(CommonOntologyVocabulary.OWNER_URI);

		createdSchema
				.createDatatypeProperty(CommonOntologyVocabulary.ADDRESS_PRP_URI);

		createdSchema.createOntology(CommonOntologyVocabulary.COSOSE_BASE_URI);

		return createdSchema;
	}

	/**
	 * creates dataType property with a given URI,domain and range
	 * 
	 * @param propURI
	 * @param domain
	 * @param range
	 */
	protected DatatypeProperty createDataTypeProperty(String propURI,
			OntClass domain, Resource range) {
		DatatypeProperty dataProperty = createdSchema
				.createDatatypeProperty(propURI);
		dataProperty.addDomain(domain);
		dataProperty.addRange(range);
		return dataProperty;
	}

	/**
	 * 
	 * creates dataType property with a given URI,domain and range
	 * 
	 * @param propURI
	 * @param domain
	 * @param range
	 * @return
	 */
	protected ObjectProperty createObjectTypeProperty(String propURI,
			OntClass domain, OntClass range) {
		ObjectProperty objectProperty = createdSchema
				.createObjectProperty(propURI);
		objectProperty.addDomain(domain);
		objectProperty.addRange(range);
		return objectProperty;
	}

	protected ObjectProperty createObjectTypeProperty(String propURI,
			OntClass domain, Resource range) {
		ObjectProperty objectProperty = createdSchema
				.createObjectProperty(propURI);
		objectProperty.addRange(range);
		objectProperty.addDomain(domain);
		return objectProperty;
	}

	protected DatatypeProperty createDataTypeProperty(String propURI,
			Resource domain, Resource range) {
		DatatypeProperty dataProperty = createdSchema
				.createDatatypeProperty(propURI);
		dataProperty.addRange(range);
		dataProperty.addDomain(domain);
		return dataProperty;
	}

}

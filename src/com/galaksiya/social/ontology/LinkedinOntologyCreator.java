package com.galaksiya.social.ontology;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.LinkedinOntologyVocabulary;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

public class LinkedinOntologyCreator extends OntologyCreator {

	@Override
	protected OntModel createOntology() {
		OntModel linkedinSchema = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);

		/**
		 * Importing FOAF,and SIOC ontologies
		 **/

		new AltEntryManager(linkedinSchema)
				.importWithAltEntries(LinkedinOntologyVocabulary.LINKEDIN_SCHEMA_BASE_URI);

		// initialize super schema
		setCreatedSchema(linkedinSchema);

		/**
		 * Creating core concept classes (for LinkedIn)
		 */
		OntClass personCls = linkedinSchema
				.createClass(CommonOntologyVocabulary.FOAF_PERSON_RSC_URI);
		OntClass groupCls = linkedinSchema
				.createClass(CommonOntologyVocabulary.GROUP_URI);
		OntClass organizationCls = linkedinSchema
				.createClass(CommonOntologyVocabulary.ORGANIZATION_URI);
		OntClass skillCls = linkedinSchema
				.createClass(CommonOntologyVocabulary.SKILL_URI);
		OntClass imageCls = linkedinSchema
				.createClass(CommonOntologyVocabulary.IMAGE_URI);
		OntClass placeCls = linkedinSchema
				.createClass(CommonOntologyVocabulary.GEODDATA_PLACE_URI);

		OntClass languageSkillCls = linkedinSchema
				.createClass(CommonOntologyVocabulary.LANGUAGE_SKILL_URI);
		OntClass workHistoryCls = linkedinSchema
				.createClass(CommonOntologyVocabulary.WORK_HISTORY_URI);
		OntClass companyCls = linkedinSchema
				.createClass(CommonOntologyVocabulary.COMPANY_URI);
		OntClass eduOrgCls = linkedinSchema
				.createClass(CommonOntologyVocabulary.EDUCATIONAL_ORG);

		/**
		 * creating properties of Person
		 */
		// CREATING DATA TYPE PROPERTIES (for LinkedIn)
		createDataTypeProperty(LinkedinOntologyVocabulary.HEADLINE_URI,
				personCls, XSD.xstring);
		Property emailPrp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.SIOC_EMAIL_URI);
		Property idPrp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.SIOC_ID_URI);
		emailPrp.addProperty(RDFS.domain, personCls);
		emailPrp.addProperty(RDFS.range, XSD.xstring);
		idPrp.addProperty(RDFS.domain, personCls);
		Property namePrp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.FOAF_NAME_URI);

		// CREATING OBJECT TYPE PROPERTIES (for LinkedIn)
		ObjectProperty locationPrp = createObjectTypeProperty(
				CommonOntologyVocabulary.LOCATION_URI, personCls, placeCls);
		Property memberOfprp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.SIOC_MEMBER_OF_URI);
		memberOfprp.addProperty(RDFS.domain, personCls);
		memberOfprp.addProperty(RDFS.range, groupCls);
		memberOfprp.addProperty(RDFS.range, organizationCls);
		Property imgPrp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.FOAF_HAS_IMAGE_URI);
		Property industryPrp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.CV_INDUSTRY_PRP_URI);
		industryPrp.addProperty(RDFS.domain, personCls);
		Property hasSkillPrp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.CV_HAS_SKILL_PRP_URI);
		hasSkillPrp.addProperty(RDFS.domain, personCls);
		Property hasWorkHistoryPrp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.CV_HAS_WORK_HISTORY_PRP_URI);
		hasWorkHistoryPrp.addProperty(RDFS.domain, personCls);

		/**
		 * educational organization class
		 */
		idPrp.addProperty(RDFS.domain, eduOrgCls);
		namePrp.addProperty(RDFS.domain, eduOrgCls);

		/**
		 * properties of Group
		 */

		// data type properties
		namePrp.addProperty(RDFS.domain, groupCls);
		emailPrp.addProperty(RDFS.domain, groupCls);
		createDataTypeProperty(CommonOntologyVocabulary.DESCRIPTION_URI,
				groupCls, XSD.xstring);
		idPrp.addProperty(RDFS.domain, groupCls);
		Property urlPrp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.FOAF_URL);
		urlPrp.addProperty(RDFS.domain, groupCls);
		imgPrp.addProperty(RDFS.domain, groupCls);

		/**
		 * properties of Image
		 */
		urlPrp.addProperty(RDFS.domain, imageCls);

		/**
		 * properties of Language
		 */
		namePrp.addProperty(RDFS.domain, languageSkillCls);

		/**
		 * properties of Skill
		 */
		namePrp.addProperty(RDFS.domain, skillCls);

		/**
		 * properties of WorkHistory
		 */
		createObjectTypeProperty(CommonOntologyVocabulary.EMPLOYER_PRP_URI,
				workHistoryCls, personCls);
		createDataTypeProperty(CommonOntologyVocabulary.WORK_POSITION_PRP_URI,
				workHistoryCls, XSD.xstring);
		idPrp.addProperty(RDFS.domain, workHistoryCls);
		locationPrp.addDomain(workHistoryCls);
		createDataTypeProperty(
				LinkedinOntologyVocabulary.SKILLS_EXPERIENCE_URI,
				workHistoryCls, XSD.xstring);
		createDataTypeProperty(LinkedinOntologyVocabulary.JOB_FUNCTION_URI,
				workHistoryCls, XSD.xstring);
		linkedinSchema.createProperty(CommonOntologyVocabulary.CV_START_DATE);
		linkedinSchema.createProperty(CommonOntologyVocabulary.CV_END_DATE);

		/**
		 * properties of Company
		 */

		namePrp.addProperty(RDFS.domain, companyCls);

		Property companySizePrp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.CV_COMPANY_SIZE);
		companySizePrp.addProperty(RDFS.domain, companyCls);
		urlPrp.addProperty(RDFS.domain, companyCls);
		createDataTypeProperty(LinkedinOntologyVocabulary.WEBSITE_URI,
				companyCls, XSD.xstring);
		locationPrp.addDomain(companyCls);
		createDataTypeProperty(CommonOntologyVocabulary.TYPE_PRP_URI,
				companyCls, XSD.xstring);
		createDataTypeProperty(LinkedinOntologyVocabulary.SPECIALITY_URI,
				companyCls, XSD.xstring);
		imgPrp.addProperty(RDFS.domain, companyCls);
		createDataTypeProperty(CommonOntologyVocabulary.STATUS_URI, companyCls,
				XSD.xstring);
		createDataTypeProperty(LinkedinOntologyVocabulary.STOCK_EXCHANGE_URI,
				companyCls, XSD.xstring);
		industryPrp.addProperty(RDFS.domain, companyCls);
		createDataTypeProperty(LinkedinOntologyVocabulary.NUM_FOLLOWERS_URI,
				companyCls, XSD.xstring);
		createDataTypeProperty(
				LinkedinOntologyVocabulary.EMPLOYEE_COUNT_RANGE_URI,
				companyCls, XSD.xstring);

		/**
		 * properties of Place
		 */
		namePrp.addProperty(RDFS.domain, placeCls);
		Property addressPrp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.ADDRESS_PRP_URI);
		addressPrp.addProperty(RDFS.domain, placeCls);
		ObjectProperty cityPrp = linkedinSchema
				.createObjectProperty(CommonOntologyVocabulary.CITY_PRP_URI);
		cityPrp.addProperty(RDFS.domain, placeCls);
		ObjectProperty countryPrp = linkedinSchema
				.createObjectProperty(CommonOntologyVocabulary.COUNTRY_PRP_URI);
		countryPrp.addProperty(RDFS.domain, placeCls);
		Property postalCodePrp = linkedinSchema
				.createProperty(CommonOntologyVocabulary.POSTALCODE_PRP_URI);
		postalCodePrp.addProperty(RDFS.domain, placeCls);
		createObjectTypeProperty(
				CommonOntologyVocabulary.RESOURCE_OF_CITY_PRP_URI, placeCls,
				RDFS.Resource);

		linkedinSchema
				.createOntology(LinkedinOntologyVocabulary.LINKEDIN_SCHEMA_BASE_URI);

		return linkedinSchema;
	}

}

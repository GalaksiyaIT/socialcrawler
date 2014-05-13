package com.galaksiya.social.ontology;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.OWL2;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

public class FacebookOntologyCreator extends OntologyCreator {

	@Override
	protected OntModel createOntology() {

		OntModel facebookSchema = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);

		// initialize super schema
		setCreatedSchema(facebookSchema);

		/**
		 * Importing FOAF,and SIOC ontologies
		 **/

		new AltEntryManager(facebookSchema)
				.importWithAltEntries(FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI);

		/**
		 * creating foaf Person class for Facebook
		 */
		OntClass personCls = facebookSchema
				.createClass(CommonOntologyVocabulary.FOAF_PERSON_RSC_URI);

		/**
		 * Educational organization concept for Facebook
		 */
		OntClass eduOrgCls = facebookSchema
				.createClass(CommonOntologyVocabulary.EDUCATIONAL_ORG);

		/**
		 * Place concept for Facebook
		 */
		OntClass placeCls = facebookSchema
				.createClass(CommonOntologyVocabulary.GEODDATA_PLACE_URI);

		/**
		 * Creating work history property for Facebook
		 */
		OntClass workHistoryCls = facebookSchema
				.createClass(CommonOntologyVocabulary.WORK_HISTORY_URI);

		/**
		 * location property for Facebook
		 */
		ObjectProperty locationPrp = createObjectTypeProperty(
				CommonOntologyVocabulary.LOCATION_URI, personCls, placeCls);

		/**
		 * Tag class for Facebook
		 */
		OntClass tagCls = facebookSchema
				.createClass(CommonOntologyVocabulary.TAG_RSC_URI);

		/**
		 * Creating core concept classes (for Facebook)
		 */
		Resource movieRsc = facebookSchema
				.createResource(CommonOntologyVocabulary.MOVIE_URI);
		Resource musicRsc = facebookSchema
				.createResource(CommonOntologyVocabulary.MUSIC_URI);
		Resource bookRsc = facebookSchema
				.createResource(CommonOntologyVocabulary.BOOK_URI);

		OntClass homeCls = facebookSchema
				.createClass(FacebookOntologyVocabulary.NEWS_FEED_URI);
		OntClass feedCls = facebookSchema
				.createClass(FacebookOntologyVocabulary.PROFILE_FEED_RSC_URI);
		OntClass photoAlbumCls = facebookSchema
				.createClass(FacebookOntologyVocabulary.PHOTO_ALBUM_RSC_URI);
		OntClass eventCls = facebookSchema
				.createClass(FacebookOntologyVocabulary.EVENT_RSC_URI);
		OntClass photoCls = facebookSchema
				.createClass(CommonOntologyVocabulary.PHOTO_URI);// ---
		OntClass videoCls = facebookSchema
				.createClass(FacebookOntologyVocabulary.VIDEO_RSC_URI);
		OntClass teamCls = facebookSchema
				.createClass(FacebookOntologyVocabulary.TEAM_RSC_URI);
		/**
		 * Facebook post class
		 */
		OntClass postCls = facebookSchema
				.createClass(FacebookOntologyVocabulary.POST_RSC_URI);

		// CREATING OBJECT TYPE PROPERTIES (for Facebook)
		ObjectProperty likesPrp = createObjectTypeProperty(
				FacebookOntologyVocabulary.LIKES, personCls, movieRsc);
		likesPrp.addRange(musicRsc);
		likesPrp.addRange(bookRsc);
		createObjectTypeProperty(
				FacebookOntologyVocabulary.HAS_PHOTO_ALBUM_URI, personCls,
				photoAlbumCls);
		createObjectTypeProperty(FacebookOntologyVocabulary.JOINS_PRP_URI,
				personCls, eventCls);
		createObjectTypeProperty(CommonOntologyVocabulary.HAS_PHOTO_URI,
				personCls, photoCls);
		createObjectTypeProperty(FacebookOntologyVocabulary.HAS_VIDEO_URI,
				personCls, videoCls);
		createObjectTypeProperty(
				CommonOntologyVocabulary.CV_HAS_WORK_HISTORY_PRP_URI,
				personCls, workHistoryCls);

		Property eduPrp = facebookSchema
				.createProperty(CommonOntologyVocabulary.CV_EDU_PRP_URI);
		eduPrp.addProperty(RDFS.domain, personCls);

		createObjectTypeProperty(FacebookOntologyVocabulary.HAS_POST_URI,
				personCls, postCls);
		createObjectTypeProperty(
				FacebookOntologyVocabulary.FAVOURITE_TEAM_PRP_URI, personCls,
				teamCls);

		/**
		 * ADDING SUBCLASS_OF RESTRICTIONS TO CONCEPTS: Subclass of concepts
		 * between created classes and imported ontology classes (for Facebook)
		 **/
		postCls.addSubClass(homeCls);
		postCls.addSubClass(feedCls);

		/**
		 * creating properties for Facebook Person
		 */
		// CREATING DATA TYPE PROPERTIES (for Facebook)
		Property emailPrp = facebookSchema
				.createProperty(CommonOntologyVocabulary.SIOC_EMAIL_URI);
		Property idPrp = facebookSchema
				.createProperty(CommonOntologyVocabulary.SIOC_ID_URI);
		emailPrp.addProperty(RDFS.domain, personCls);
		emailPrp.addProperty(RDFS.range, XSD.xstring);
		idPrp.addProperty(RDFS.domain, personCls);
		Property namePrp = facebookSchema
				.createProperty(CommonOntologyVocabulary.FOAF_NAME_URI);
		createObjectTypeProperty(CommonOntologyVocabulary.HOMETOWN_URI,
				personCls, placeCls);
		createDataTypeProperty(FacebookOntologyVocabulary.RELIGION_URI,
				personCls, XSD.xstring);

		/**
		 * educational organization class
		 */
		idPrp.addProperty(RDFS.domain, eduOrgCls);
		namePrp.addProperty(RDFS.domain, eduOrgCls);

		/**
		 * movie class properties
		 */
		idPrp.addProperty(RDFS.domain, movieRsc);
		// DatatypeProperty categoryPrp = createDataTypeProperty(
		// Vocabulary.CATEGORY_PRP_URI, movieRsc, XSD.xstring);
		namePrp.addProperty(RDFS.domain, movieRsc);
		DatatypeProperty createdAtPrp = createDataTypeProperty(
				CommonOntologyVocabulary.CREATED_AT_PRP_URI, movieRsc,
				XSD.xdouble);

		/**
		 * music class properties
		 */
		idPrp.addProperty(RDFS.domain, musicRsc);
		// categoryPrp.addDomain(musicRsc);
		namePrp.addProperty(RDFS.domain, musicRsc);
		createdAtPrp.addDomain(musicRsc);

		/**
		 * book class properties
		 */
		idPrp.addProperty(RDFS.domain, bookRsc);
		// categoryPrp.addDomain(bookRsc);
		namePrp.addProperty(RDFS.domain, bookRsc);
		createdAtPrp.addDomain(bookRsc);

		/**
		 * properties of WorkHistory
		 */
		createObjectTypeProperty(CommonOntologyVocabulary.EMPLOYER_PRP_URI,
				workHistoryCls, personCls);
		createDataTypeProperty(CommonOntologyVocabulary.WORK_POSITION_PRP_URI,
				workHistoryCls, XSD.xstring);
		createObjectTypeProperty(CommonOntologyVocabulary.WITH_PRP_URI,
				workHistoryCls, personCls);
		idPrp.addProperty(RDFS.domain, workHistoryCls);
		locationPrp.addDomain(workHistoryCls);

		/**
		 * properties of Event
		 */

		Property startDatePrp = facebookSchema
				.createProperty(CommonOntologyVocabulary.CV_START_DATE);
		Property endDatePrp = facebookSchema
				.createProperty(CommonOntologyVocabulary.CV_END_DATE);
		createDataTypeProperty(CommonOntologyVocabulary.DESCRIPTION_URI,
				eventCls, XSD.xstring);
		locationPrp.addDomain(eventCls);
		namePrp.addProperty(RDFS.domain, eventCls);
		ObjectProperty ownerPrp = createObjectTypeProperty(
				CommonOntologyVocabulary.OWNER_URI, eventCls, personCls);
		startDatePrp.addProperty(RDFS.domain, eventCls);
		endDatePrp.addProperty(RDFS.domain, eventCls);
		createDataTypeProperty(FacebookOntologyVocabulary.ORGANIZED_IN_PRP_URI,
				eventCls, XSD.xstring);

		/**
		 * properties of Place
		 */
		namePrp.addProperty(RDFS.domain, placeCls);
		ObjectProperty cityPrp = facebookSchema
				.createObjectProperty(CommonOntologyVocabulary.CITY_PRP_URI);
		cityPrp.addProperty(RDFS.domain, placeCls);
		ObjectProperty countryPrp = facebookSchema
				.createObjectProperty(CommonOntologyVocabulary.COUNTRY_PRP_URI);
		countryPrp.addProperty(RDFS.domain, placeCls);

		// TODO: will be removed
		createObjectTypeProperty(
				CommonOntologyVocabulary.RESOURCE_OF_CITY_PRP_URI, placeCls,
				RDFS.Resource);

		/**
		 * Properties of Tag
		 */

		createDataTypeProperty(FacebookOntologyVocabulary.APSIS_PRP_URI,
				tagCls, XSD.xstring);
		createDataTypeProperty(FacebookOntologyVocabulary.ORDINATE_PRP_URI,
				tagCls, XSD.xstring);
		createdAtPrp.addDomain(tagCls);

		Property urlPrp = facebookSchema
				.createProperty(CommonOntologyVocabulary.FOAF_URL);

		/**
		 * properties of Photo
		 */
		urlPrp.addProperty(RDFS.domain, photoCls);
		createdAtPrp.addDomain(photoCls);
		createObjectTypeProperty(CommonOntologyVocabulary.HAS_TAG_PRP_URI,
				photoCls, tagCls);
		ownerPrp.addDomain(photoCls);

		facebookSchema
				.createOntology(FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI);

		// createFamilyBondProperties(ontModel, personCls);

		return facebookSchema;
	}

	@SuppressWarnings("unused")
	private void createFamilyBondProperties(OntModel ontModel,
			OntClass personCls) {

		/**
		 * Family relationship properties
		 */

		ObjectProperty husbandPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.HUSBAND_PRP_URI);
		husbandPrp.addProperty(RDFS.domain, personCls);
		husbandPrp.addProperty(RDFS.range, personCls);
		husbandPrp.addRDFType(OWL2.AsymmetricProperty);
		husbandPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty brotherPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.BROTHER_PRP_URI);
		brotherPrp.addProperty(RDFS.domain, personCls);
		brotherPrp.addProperty(RDFS.range, personCls);
		brotherPrp.addRDFType(OWL2.TransitiveProperty);
		brotherPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty fatherPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.FATHER_PRP_URI);
		fatherPrp.addProperty(RDFS.domain, personCls);
		fatherPrp.addProperty(RDFS.range, personCls);
		fatherPrp.addRDFType(OWL2.AsymmetricProperty);
		fatherPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty sonPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.SON_PRP_URI);
		sonPrp.addProperty(RDFS.domain, personCls);
		sonPrp.addProperty(RDFS.range, personCls);
		sonPrp.addRDFType(OWL2.AsymmetricProperty);
		sonPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty unclePrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.UNCLE_PRP_URI);
		unclePrp.addProperty(RDFS.domain, personCls);
		unclePrp.addProperty(RDFS.range, personCls);
		unclePrp.addRDFType(OWL2.AsymmetricProperty);
		unclePrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty nephewPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.NEPHEW_PRP_URI);
		nephewPrp.addProperty(RDFS.domain, personCls);
		nephewPrp.addProperty(RDFS.range, personCls);
		nephewPrp.addRDFType(OWL2.AsymmetricProperty);
		nephewPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty grandFatherPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.GRANDFATHER_PRP_URI);
		grandFatherPrp.addProperty(RDFS.domain, personCls);
		grandFatherPrp.addProperty(RDFS.range, personCls);
		grandFatherPrp.addRDFType(OWL2.AsymmetricProperty);
		grandFatherPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty wifePrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.WIFE_PRP_URI);
		wifePrp.addProperty(RDFS.domain, personCls);
		wifePrp.addProperty(RDFS.range, personCls);
		wifePrp.addRDFType(OWL2.AsymmetricProperty);
		wifePrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty sisterPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.SISTER_PRP_URI);
		sisterPrp.addProperty(RDFS.domain, personCls);
		sisterPrp.addProperty(RDFS.range, personCls);
		sisterPrp.addRDFType(OWL2.IrreflexiveProperty);
		sisterPrp.addRDFType(OWL2.TransitiveProperty);

		ObjectProperty motherPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.MOTHER_PRP_URI);
		motherPrp.addProperty(RDFS.domain, personCls);
		motherPrp.addProperty(RDFS.range, personCls);
		motherPrp.addRDFType(OWL2.AsymmetricProperty);
		motherPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty daughterPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.DAUGHTER_PRP_URI);
		daughterPrp.addProperty(RDFS.domain, personCls);
		daughterPrp.addProperty(RDFS.range, personCls);
		daughterPrp.addRDFType(OWL2.AsymmetricProperty);
		daughterPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty auntPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.AUNT_PRP_URI);
		auntPrp.addProperty(RDFS.domain, personCls);
		auntPrp.addProperty(RDFS.range, personCls);
		auntPrp.addRDFType(OWL2.AsymmetricProperty);
		auntPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty niecePrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.NIECE_PRP_URI);
		niecePrp.addProperty(RDFS.domain, personCls);
		niecePrp.addProperty(RDFS.range, personCls);
		niecePrp.addRDFType(OWL2.AsymmetricProperty);
		niecePrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty grandMotherPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.GRANDMOTHER_PRP_URI);
		grandMotherPrp.addProperty(RDFS.domain, personCls);
		grandMotherPrp.addProperty(RDFS.range, personCls);
		grandMotherPrp.addRDFType(OWL2.AsymmetricProperty);
		grandMotherPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty partnerPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.PARTNER_PRP_URI);
		partnerPrp.addProperty(RDFS.domain, personCls);
		partnerPrp.addProperty(RDFS.range, personCls);
		partnerPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty cousinPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.COUSIN_PRP_URI);
		cousinPrp.addProperty(RDFS.domain, personCls);
		cousinPrp.addProperty(RDFS.range, personCls);
		cousinPrp.addRDFType(OWL2.TransitiveProperty);
		cousinPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty parentPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.PARENT_PRP_URI);
		parentPrp.addProperty(RDFS.domain, personCls);
		parentPrp.addProperty(RDFS.range, personCls);
		parentPrp.addRDFType(OWL2.AsymmetricProperty);
		parentPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty grandparentPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.GRANDPARENT_PRP_URI);
		grandparentPrp.addProperty(RDFS.domain, personCls);
		grandparentPrp.addProperty(RDFS.range, personCls);
		grandparentPrp.addRDFType(OWL2.AsymmetricProperty);
		grandparentPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty childPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.CHILD_PRP_URI);
		childPrp.addProperty(RDFS.domain, personCls);
		childPrp.addProperty(RDFS.range, personCls);
		childPrp.addRDFType(OWL2.AsymmetricProperty);
		childPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty grandChildPrp = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.GRANDCHILD_PRP_URI);
		grandChildPrp.addProperty(RDFS.domain, personCls);
		grandChildPrp.addProperty(RDFS.range, personCls);
		grandChildPrp.addRDFType(OWL2.AsymmetricProperty);
		grandChildPrp.addRDFType(OWL2.IrreflexiveProperty);

		ObjectProperty isDirectSiblingOf = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.IS_DIRECT_SIBLING_OF_PRP_URI);
		isDirectSiblingOf.addDomain(personCls);
		isDirectSiblingOf.addRange(personCls);
		isDirectSiblingOf.addRDFType(OWL2.TransitiveProperty);

		ObjectProperty isParentOf = ontModel
				.getObjectProperty(FacebookOntologyVocabulary.IS_PARENT_OF_PRP_URI);
		isParentOf.addDomain(personCls);
		isParentOf.addRange(personCls);

		parentPrp.addSubProperty(fatherPrp);

		parentPrp.addSubProperty(motherPrp);

		childPrp.addSubProperty(sonPrp);

		childPrp.addSubProperty(daughterPrp);

		grandparentPrp.addProperty(OWL2.inverseOf, grandChildPrp);

		parentPrp.addProperty(OWL2.inverseOf, childPrp);

		husbandPrp.addProperty(OWL2.inverseOf, wifePrp);
	}

}

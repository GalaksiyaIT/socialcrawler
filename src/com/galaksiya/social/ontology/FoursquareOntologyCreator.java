package com.galaksiya.social.ontology;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

public class FoursquareOntologyCreator extends OntologyCreator {

	@Override
	protected OntModel createOntology() {

		OntModel foursquareSchema = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);

		// initialize super schema
		setCreatedSchema(foursquareSchema);

		/**
		 * Importing FOAF,and SIOC ontologies
		 **/

		new AltEntryManager(foursquareSchema)
				.importWithAltEntries(FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI);

		/**
		 * foaf:Person class for Foursquare
		 */
		OntClass personCls = foursquareSchema
				.createClass(CommonOntologyVocabulary.FOAF_PERSON_RSC_URI);

		/**
		 * lgdo:Place class for Foursquare
		 */
		OntClass placeCls = foursquareSchema
				.createClass(CommonOntologyVocabulary.GEODDATA_PLACE_URI);

		OntClass photoCls = foursquareSchema
				.createClass(CommonOntologyVocabulary.PHOTO_URI);

		OntClass tagCls = foursquareSchema
				.createClass(CommonOntologyVocabulary.TAG_RSC_URI);

		/**
		 * Creating core concepts (for Foursquare)
		 */

		OntClass checkinCls = foursquareSchema
				.createClass(FoursquareOntologyVocabulary.CHECKIN_URI);
		OntClass venueCls = foursquareSchema
				.createClass(FoursquareOntologyVocabulary.VENUE_URI);
		OntClass contactCls = foursquareSchema
				.createClass(FoursquareOntologyVocabulary.CONTACT_URI);
		OntClass scoresCls = foursquareSchema
				.createClass(FoursquareOntologyVocabulary.SCORES_URI);
		OntClass tipCls = foursquareSchema
				.createClass(FoursquareOntologyVocabulary.TIP_URI);
		OntClass todoCls = foursquareSchema
				.createClass(FoursquareOntologyVocabulary.TODO_URI);
		OntClass commentCls = foursquareSchema
				.createClass(FoursquareOntologyVocabulary.COMMENT_URI);
		OntClass categoryCls = foursquareSchema
				.createClass(FoursquareOntologyVocabulary.CATEGORY_URI);
		OntClass sourceCls = foursquareSchema
				.createClass(FoursquareOntologyVocabulary.SOURCE_URI);
		OntClass specialCls = foursquareSchema
				.createClass(FoursquareOntologyVocabulary.SPECIAL_URI);
		OntClass statisticsCls = foursquareSchema
				.createClass(FoursquareOntologyVocabulary.STATISTICS_URI);

		/**
		 * Basic properties of Person
		 */
		Property emailPrp = foursquareSchema
				.createProperty(CommonOntologyVocabulary.SIOC_EMAIL_URI);
		emailPrp.addProperty(RDFS.range, XSD.xstring);
		Property idPrp = foursquareSchema
				.createProperty(CommonOntologyVocabulary.SIOC_ID_URI);
		emailPrp.addProperty(RDFS.domain, personCls);
		idPrp.addProperty(RDFS.domain, personCls);
		Property namePrp = foursquareSchema
				.createProperty(CommonOntologyVocabulary.FOAF_NAME_URI);
		createObjectTypeProperty(CommonOntologyVocabulary.HOMETOWN_URI,
				personCls, placeCls);

		/**
		 * properties of Photo
		 */
		DatatypeProperty createdAtPrp = createDataTypeProperty(
				CommonOntologyVocabulary.CREATED_AT_PRP_URI, photoCls,
				XSD.xlong);
		Property urlPrp = foursquareSchema
				.createProperty(CommonOntologyVocabulary.FOAF_URL);
		urlPrp.addProperty(RDFS.domain, photoCls);
		createObjectTypeProperty(
				FoursquareOntologyVocabulary.RELATED_CHECKIN_PRP_URI, photoCls,
				checkinCls);
		ObjectProperty hasSourcePrp = createObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_SOURCE_PRP_URI, photoCls,
				sourceCls);
		createObjectTypeProperty(CommonOntologyVocabulary.HAS_TAG_PRP_URI,
				photoCls, tagCls);
		ObjectProperty hasVenuePrp = createObjectTypeProperty(
				FoursquareOntologyVocabulary.VENUE_PRP_URI, photoCls, venueCls);
		ObjectProperty ownerPrp = createObjectTypeProperty(
				CommonOntologyVocabulary.OWNER_URI, photoCls, personCls);

		ObjectProperty tipPrp = createObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_TIP, photoCls, tipCls);

		DatatypeProperty typePrp = createDataTypeProperty(
				CommonOntologyVocabulary.TYPE_PRP_URI, checkinCls, XSD.xstring);

		ObjectProperty hasPhotoPrp = createObjectTypeProperty(
				CommonOntologyVocabulary.HAS_PHOTO_URI, personCls, photoCls);

		/**
		 * properties of Place
		 */
		namePrp.addProperty(RDFS.domain, placeCls);
		Property addressPrp = foursquareSchema
				.createProperty(CommonOntologyVocabulary.ADDRESS_PRP_URI);
		addressPrp.addProperty(RDFS.domain, placeCls);
		ObjectProperty cityPrp = foursquareSchema
				.createObjectProperty(CommonOntologyVocabulary.CITY_PRP_URI);
		cityPrp.addProperty(RDFS.domain, placeCls);
		ObjectProperty countryPrp = foursquareSchema
				.createObjectProperty(CommonOntologyVocabulary.COUNTRY_PRP_URI);
		countryPrp.addProperty(RDFS.domain, placeCls);
		createObjectTypeProperty(
				CommonOntologyVocabulary.RESOURCE_OF_CITY_PRP_URI, placeCls,
				RDFS.Resource);
		Property streetPrp = foursquareSchema
				.createProperty(CommonOntologyVocabulary.CROSS_STREET_PRP_URI);
		streetPrp.addProperty(RDFS.domain, placeCls);
		Property distancePrp = foursquareSchema
				.createProperty(FoursquareOntologyVocabulary.DISTANCE_PRP_URI);
		distancePrp.addProperty(RDFS.domain, placeCls);
		distancePrp.addProperty(RDFS.range, XSD.xdouble);
		Property latitudePrp = foursquareSchema
				.createProperty(CommonOntologyVocabulary.LAT_PRP_URI);
		latitudePrp.addProperty(RDFS.domain, placeCls);
		latitudePrp.addProperty(RDFS.range, XSD.xdouble);
		Property longitudePrp = foursquareSchema
				.createProperty(CommonOntologyVocabulary.LNG_PRP_URI);
		longitudePrp.addProperty(RDFS.domain, placeCls);
		longitudePrp.addProperty(RDFS.range, XSD.xdouble);
		Property postalCodePrp = foursquareSchema
				.createProperty(CommonOntologyVocabulary.POSTALCODE_PRP_URI);
		postalCodePrp.addProperty(RDFS.domain, placeCls);
		Property statePrp = foursquareSchema
				.createProperty(CommonOntologyVocabulary.STATE_PRP_URI);
		statePrp.addProperty(RDFS.domain, placeCls);

		/**
		 * properties of Source
		 */
		urlPrp.addProperty(RDFS.domain, sourceCls);
		namePrp.addProperty(RDFS.domain, sourceCls);

		/**
		 * creating properties of Checkin
		 */
		createDataTypeProperty(FoursquareOntologyVocabulary.SHOUT_PRP_URI,
				checkinCls, XSD.xstring);
		createDataTypeProperty(FoursquareOntologyVocabulary.TIME_ZONE_PRP_URI,
				checkinCls, XSD.xstring);
		typePrp.addDomain(checkinCls);
		createdAtPrp.addDomain(checkinCls);
		createObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_COMMENT_PRP_URI, checkinCls,
				commentCls);
		createDataTypeProperty(FoursquareOntologyVocabulary.IS_MAYOR_PRP_URI,
				checkinCls, XSD.xboolean);

		ObjectProperty locationPrp = createObjectTypeProperty(
				CommonOntologyVocabulary.LOCATION_URI, checkinCls, placeCls);
		createObjectTypeProperty(FoursquareOntologyVocabulary.OVERLAPS_URI,
				checkinCls, checkinCls);
		hasPhotoPrp.addDomain(checkinCls);
		hasSourcePrp.addDomain(checkinCls);
		ownerPrp.addDomain(checkinCls);
		hasVenuePrp.addDomain(checkinCls);
		createDataTypeProperty(FoursquareOntologyVocabulary.IS_PRIVATE,
				checkinCls, XSD.xboolean);

		/**
		 * creating properties of Comment
		 */
		idPrp.addProperty(RDFS.domain, commentCls);
		DatatypeProperty textPrp = createDataTypeProperty(
				FoursquareOntologyVocabulary.TEXT_PRP_URI, commentCls,
				XSD.xstring);
		createdAtPrp.addDomain(commentCls);
		ownerPrp.addDomain(commentCls);

		/**
		 * properties of Venue
		 */
		namePrp.addProperty(RDFS.domain, venueCls);
		urlPrp.addProperty(RDFS.domain, venueCls);
		ObjectProperty categoryPrp = createObjectTypeProperty(
				FoursquareOntologyVocabulary.CATEGORY_PRP_URI, venueCls,
				categoryCls);
		ObjectProperty contactPrp = createObjectTypeProperty(
				FoursquareOntologyVocabulary.CONTACT_PRP_URI, venueCls,
				contactCls);
		locationPrp.addDomain(venueCls);
		createObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_SPECIAL_PRP_URI, venueCls,
				specialCls);
		createObjectTypeProperty(FoursquareOntologyVocabulary.HAS_STAT_URI,
				venueCls, statisticsCls);
		tipPrp.addDomain(venueCls);
		ObjectProperty todoPrp = createObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_TODO, venueCls, todoCls);
		createDataTypeProperty(
				FoursquareOntologyVocabulary.IS_VERIFIED_PRP_URI, venueCls,
				XSD.xboolean);

		/**
		 * properties of Category
		 */
		namePrp.addProperty(RDFS.domain, categoryCls);
		DatatypeProperty iconPrp = createDataTypeProperty(
				FoursquareOntologyVocabulary.ICON_PRP_URI, categoryCls,
				XSD.xstring);
		DatatypeProperty pluralNamePrp = createDataTypeProperty(
				FoursquareOntologyVocabulary.PLURAL_NAME_PRP_URI, categoryCls,
				XSD.xstring);
		pluralNamePrp.addSuperProperty(namePrp);
		categoryPrp.addDomain(categoryCls);
		createDataTypeProperty(FoursquareOntologyVocabulary.HAS_PARENT_PRP_URI,
				categoryCls, XSD.xstring);
		createDataTypeProperty(FoursquareOntologyVocabulary.PRIMARY_PRP_URI,
				categoryCls, XSD.xboolean);

		/**
		 * properties of Contact
		 */
		emailPrp.addProperty(RDFS.domain, contactCls);
		createDataTypeProperty(FoursquareOntologyVocabulary.FACEBOOK,
				contactCls, XSD.xstring);
		createDataTypeProperty(FoursquareOntologyVocabulary.TWITTER,
				contactCls, XSD.xstring);
		createDataTypeProperty(FoursquareOntologyVocabulary.PHONE_PRP_URI,
				contactCls, XSD.xstring);

		/**
		 * properties of Statistics
		 */
		DatatypeProperty checkinCountPrp = createDataTypeProperty(
				FoursquareOntologyVocabulary.CHECKIN_COUNT_PRP_URI,
				statisticsCls, XSD.xint);
		createDataTypeProperty(FoursquareOntologyVocabulary.USER_COUNT_PRP_URI,
				statisticsCls, XSD.xint);

		/**
		 * properties of Todo
		 */
		idPrp.addProperty(RDFS.domain, todoCls);
		createdAtPrp.addDomain(todoCls);
		tipPrp.addDomain(todoCls);

		/**
		 * properties of Special
		 */
		createDataTypeProperty(CommonOntologyVocabulary.DESCRIPTION_URI,
				specialCls, XSD.xstring);
		createDataTypeProperty(FoursquareOntologyVocabulary.FINE_PRINT_PRP_URI,
				specialCls, XSD.xstring);
		iconPrp.addDomain(specialCls);
		idPrp.addProperty(RDFS.domain, specialCls);
		createDataTypeProperty(FoursquareOntologyVocabulary.MESSAGE_PRP_URI,
				specialCls, XSD.xstring);
		createDataTypeProperty(FoursquareOntologyVocabulary.PROVIDER_PRP_URI,
				specialCls, XSD.xstring);
		typePrp.addProperty(RDFS.domain, specialCls);
		createDataTypeProperty(FoursquareOntologyVocabulary.UNLOCKED_PRP_URI,
				specialCls, XSD.xboolean);
		hasVenuePrp.addDomain(specialCls);

		/**
		 * properties of Tip
		 */
		idPrp.addProperty(RDFS.domain, tipCls);
		createDataTypeProperty(CommonOntologyVocabulary.STATUS_URI, tipCls,
				XSD.xstring);
		textPrp.addDomain(tipCls);
		urlPrp.addProperty(RDFS.domain, tipCls);
		createdAtPrp.addDomain(tipCls);
		hasPhotoPrp.addDomain(tipCls);
		ownerPrp.addDomain(tipCls);
		hasVenuePrp.addDomain(tipCls);

		/**
		 * properties of Scores
		 */
		checkinCountPrp.addDomain(scoresCls);
		createDataTypeProperty(FoursquareOntologyVocabulary.GOAL_PRP_URI,
				scoresCls, XSD.xlong);
		createDataTypeProperty(FoursquareOntologyVocabulary.MAX_PRP_URI,
				scoresCls, XSD.xlong);
		createDataTypeProperty(FoursquareOntologyVocabulary.RECENT_PRP_URI,
				scoresCls, XSD.xlong);

		/**
		 * Foursquare properties of Person
		 */
		locationPrp.addDomain(personCls);
		createObjectTypeProperty(FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				personCls, checkinCls);
		createDataTypeProperty(
				FoursquareOntologyVocabulary.BADGE_COUNT_PRP_URI, personCls,
				XSD.xint);
		contactPrp.addDomain(personCls);
		createDataTypeProperty(
				FoursquareOntologyVocabulary.FOLLOWER_COUNT_PRP_URI, personCls,
				XSD.xint);
		createDataTypeProperty(
				FoursquareOntologyVocabulary.FOLLOWING_COUNT_PRP_URI,
				personCls, XSD.xint);
		createObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_MAYORSHIP_PRP_URI, personCls,
				venueCls);
		createDataTypeProperty(FoursquareOntologyVocabulary.PINGS_PRP_URI,
				personCls, XSD.xboolean);
		createDataTypeProperty(
				FoursquareOntologyVocabulary.RELATIONSHIP_PRP_URI, personCls,
				XSD.xstring);
		createDataTypeProperty(
				FoursquareOntologyVocabulary.REQUEST_COUNT_PRP_URI, personCls,
				XSD.xint);
		createObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_SCORE_PRP_URI, personCls,
				scoresCls);
		tipPrp.addDomain(personCls);
		todoPrp.addDomain(personCls);

		foursquareSchema
				.createOntology(FoursquareOntologyVocabulary.FOURSQUARE_SCHEMA_BASE_URI);

		return foursquareSchema;
	}

}

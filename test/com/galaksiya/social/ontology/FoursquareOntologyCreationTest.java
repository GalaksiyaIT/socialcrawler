package com.galaksiya.social.ontology;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.hp.hpl.jena.vocabulary.XSD;

/**
 * OntologyCreationTest class tests creating ontology model and writes it to
 * file
 * 
 * 
 */
public class FoursquareOntologyCreationTest extends OntologyCreationTest {

	@Test
	public void createFoursquareOntology() throws Exception {

		// assert schema ontModel is not null
		assertNotNull(getSchemaOntModel());

		// assert that ontology contains Forusquare schema classes...
		ontologyTestUtility
				.assertOntClass(FoursquareOntologyVocabulary.CHECKIN_URI);
		ontologyTestUtility
				.assertOntClass(FoursquareOntologyVocabulary.VENUE_URI);
		ontologyTestUtility
				.assertOntClass(FoursquareOntologyVocabulary.CONTACT_URI);
		ontologyTestUtility
				.assertOntClass(FoursquareOntologyVocabulary.SCORES_URI);
		ontologyTestUtility
				.assertOntClass(FoursquareOntologyVocabulary.TIP_URI);
		ontologyTestUtility
				.assertOntClass(FoursquareOntologyVocabulary.TODO_URI);
		ontologyTestUtility
				.assertOntClass(FoursquareOntologyVocabulary.COMMENT_URI);
		ontologyTestUtility
				.assertOntClass(FoursquareOntologyVocabulary.CATEGORY_URI);
		ontologyTestUtility
				.assertOntClass(FoursquareOntologyVocabulary.SOURCE_URI);
		ontologyTestUtility
				.assertOntClass(FoursquareOntologyVocabulary.SPECIAL_URI);
		ontologyTestUtility
				.assertOntClass(FoursquareOntologyVocabulary.STATISTICS_URI);

		// assert that ontology contains Foursquare schema object properties...
		ontologyTestUtility.assertObjectTypeProperty(
				CommonOntologyVocabulary.HOMETOWN_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI,
				CommonOntologyVocabulary.GEODDATA_PLACE_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FoursquareOntologyVocabulary.RELATED_CHECKIN_PRP_URI,
				CommonOntologyVocabulary.PHOTO_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				CommonOntologyVocabulary.HAS_TAG_PRP_URI,
				CommonOntologyVocabulary.PHOTO_URI,
				CommonOntologyVocabulary.TAG_RSC_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_COMMENT_PRP_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI,
				FoursquareOntologyVocabulary.COMMENT_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FoursquareOntologyVocabulary.OVERLAPS_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_SPECIAL_PRP_URI,
				FoursquareOntologyVocabulary.VENUE_URI,
				FoursquareOntologyVocabulary.SPECIAL_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_STAT_URI,
				FoursquareOntologyVocabulary.VENUE_URI,
				FoursquareOntologyVocabulary.STATISTICS_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_MAYORSHIP_PRP_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI,
				FoursquareOntologyVocabulary.VENUE_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FoursquareOntologyVocabulary.HAS_SCORE_PRP_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI,
				FoursquareOntologyVocabulary.SCORES_URI);

		// assert that ontology contains Foursquare schema data properties...
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.SHOUT_PRP_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.TIME_ZONE_PRP_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.IS_MAYOR_PRP_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI, XSD.xboolean);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.IS_PRIVATE,
				FoursquareOntologyVocabulary.CHECKIN_URI, XSD.xboolean);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.IS_VERIFIED_PRP_URI,
				FoursquareOntologyVocabulary.VENUE_URI, XSD.xboolean);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.HAS_PARENT_PRP_URI,
				FoursquareOntologyVocabulary.CATEGORY_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.PRIMARY_PRP_URI,
				FoursquareOntologyVocabulary.CATEGORY_URI, XSD.xboolean);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.FACEBOOK,
				FoursquareOntologyVocabulary.CONTACT_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.TWITTER,
				FoursquareOntologyVocabulary.CONTACT_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.PHONE_PRP_URI,
				FoursquareOntologyVocabulary.CONTACT_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.PHONE_PRP_URI,
				FoursquareOntologyVocabulary.CONTACT_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.PHONE_PRP_URI,
				FoursquareOntologyVocabulary.CONTACT_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				CommonOntologyVocabulary.DESCRIPTION_URI,
				FoursquareOntologyVocabulary.SPECIAL_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.FINE_PRINT_PRP_URI,
				FoursquareOntologyVocabulary.SPECIAL_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.MESSAGE_PRP_URI,
				FoursquareOntologyVocabulary.SPECIAL_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.PROVIDER_PRP_URI,
				FoursquareOntologyVocabulary.SPECIAL_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.UNLOCKED_PRP_URI,
				FoursquareOntologyVocabulary.SPECIAL_URI, XSD.xboolean);
		ontologyTestUtility.assertDataTypeProperty(
				CommonOntologyVocabulary.STATUS_URI,
				FoursquareOntologyVocabulary.TIP_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.GOAL_PRP_URI,
				FoursquareOntologyVocabulary.SCORES_URI, XSD.xlong);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.MAX_PRP_URI,
				FoursquareOntologyVocabulary.SCORES_URI, XSD.xlong);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.RECENT_PRP_URI,
				FoursquareOntologyVocabulary.SCORES_URI, XSD.xlong);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.BADGE_COUNT_PRP_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI, XSD.xint);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.FOLLOWER_COUNT_PRP_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI, XSD.xint);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.FOLLOWING_COUNT_PRP_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI, XSD.xint);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.PINGS_PRP_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI, XSD.xboolean);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.RELATIONSHIP_PRP_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.REQUEST_COUNT_PRP_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI, XSD.xint);
		ontologyTestUtility.assertDataTypeProperty(
				FoursquareOntologyVocabulary.PLURAL_NAME_PRP_URI,
				FoursquareOntologyVocabulary.CATEGORY_URI, XSD.xstring);

		// checking created_at property...
		ontologyTestUtility.assertDatatypePropertyDomains(
				CommonOntologyVocabulary.CREATED_AT_PRP_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI,
				FoursquareOntologyVocabulary.COMMENT_URI,
				CommonOntologyVocabulary.PHOTO_URI);
		ontologyTestUtility
				.assertDatatypePropertyRanges(
						CommonOntologyVocabulary.CREATED_AT_PRP_URI,
						XSD.xlong.getURI());

		// checking has_source property...
		ontologyTestUtility.assertObjectPropertyDomains(
				FoursquareOntologyVocabulary.HAS_SOURCE_PRP_URI,
				CommonOntologyVocabulary.PHOTO_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI);
		ontologyTestUtility.assertObjectPropertyRanges(
				FoursquareOntologyVocabulary.HAS_SOURCE_PRP_URI,
				FoursquareOntologyVocabulary.SOURCE_URI);

		// checking venue property...
		ontologyTestUtility.assertObjectPropertyDomains(
				FoursquareOntologyVocabulary.VENUE_PRP_URI,
				CommonOntologyVocabulary.PHOTO_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI,
				FoursquareOntologyVocabulary.SPECIAL_URI,
				FoursquareOntologyVocabulary.TIP_URI);
		ontologyTestUtility.assertObjectPropertyRanges(
				FoursquareOntologyVocabulary.VENUE_PRP_URI,
				FoursquareOntologyVocabulary.VENUE_URI);

		// checking owner property...
		ontologyTestUtility.assertObjectPropertyDomains(
				CommonOntologyVocabulary.OWNER_URI,
				CommonOntologyVocabulary.PHOTO_URI,
				FoursquareOntologyVocabulary.CHECKIN_URI,
				FoursquareOntologyVocabulary.COMMENT_URI);
		ontologyTestUtility.assertObjectPropertyRanges(
				CommonOntologyVocabulary.OWNER_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI);

		// checking has_tip property...
		ontologyTestUtility.assertObjectPropertyDomains(
				FoursquareOntologyVocabulary.HAS_TIP,
				CommonOntologyVocabulary.PHOTO_URI,
				FoursquareOntologyVocabulary.VENUE_URI,
				FoursquareOntologyVocabulary.TODO_URI);
		ontologyTestUtility.assertObjectPropertyRanges(
				FoursquareOntologyVocabulary.HAS_TIP,
				FoursquareOntologyVocabulary.TIP_URI);

		// checking has_todo property...
		ontologyTestUtility.assertObjectPropertyDomains(
				FoursquareOntologyVocabulary.HAS_TODO,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI,
				FoursquareOntologyVocabulary.VENUE_URI);
		ontologyTestUtility.assertObjectPropertyRanges(
				FoursquareOntologyVocabulary.HAS_TODO,
				FoursquareOntologyVocabulary.TODO_URI);

		// checking has_todo property...
		ontologyTestUtility.assertDatatypePropertyDomains(
				FoursquareOntologyVocabulary.ICON_PRP_URI,
				FoursquareOntologyVocabulary.SPECIAL_URI,
				FoursquareOntologyVocabulary.CATEGORY_URI);
		ontologyTestUtility
				.assertDatatypePropertyRanges(
						FoursquareOntologyVocabulary.ICON_PRP_URI,
						XSD.xstring.getURI());

	}

	@Override
	protected String getOntURI() {
		return AltEntryManager.getFoursquareOntURI();
	}

	@Override
	protected OntologyCreator getOntologyCreator() {
		return new FoursquareOntologyCreator();
	}

}

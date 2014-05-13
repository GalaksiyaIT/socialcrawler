package com.galaksiya.social.ontology;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.vocabulary.XSD;

/**
 * OntologyCreationTest class tests creating ontology model and writes it to
 * file
 * 
 * @author Burak YÖNYÜL
 * 
 */
public class FacebookOntologyCreationTest extends OntologyCreationTest {

	@Test
	public void createFacebookOntology() throws Exception {

		// asserting ontmodel is not null
		assertNotNull(getSchemaOntModel());

		// assert that ontology contains Facebook schema classes...
		ontologyTestUtility
				.assertOntClass(FacebookOntologyVocabulary.EVENT_RSC_URI);
		ontologyTestUtility
				.assertOntClass(FacebookOntologyVocabulary.POST_RSC_URI);
		ontologyTestUtility
				.assertOntClass(FacebookOntologyVocabulary.NEWS_FEED_URI);
		ontologyTestUtility
				.assertOntClass(FacebookOntologyVocabulary.PHOTO_ALBUM_RSC_URI);
		ontologyTestUtility
				.assertOntClass(FacebookOntologyVocabulary.PROFILE_FEED_RSC_URI);
		ontologyTestUtility
				.assertOntClass(FacebookOntologyVocabulary.TEAM_RSC_URI);
		ontologyTestUtility
				.assertOntClass(FacebookOntologyVocabulary.VIDEO_RSC_URI);

		// assert that ontology contains Facebook schema properties...
		ontologyTestUtility.assertDataTypeProperty(
				FacebookOntologyVocabulary.APSIS_PRP_URI,
				CommonOntologyVocabulary.TAG_RSC_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FacebookOntologyVocabulary.ORDINATE_PRP_URI,
				CommonOntologyVocabulary.TAG_RSC_URI, XSD.xstring);
		ontologyTestUtility.assertObjectTypeProperty(
				FacebookOntologyVocabulary.FAVOURITE_TEAM_PRP_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI,
				FacebookOntologyVocabulary.TEAM_RSC_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FacebookOntologyVocabulary.HAS_PHOTO_ALBUM_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI,
				FacebookOntologyVocabulary.PHOTO_ALBUM_RSC_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FacebookOntologyVocabulary.HAS_POST_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI,
				FacebookOntologyVocabulary.POST_RSC_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FacebookOntologyVocabulary.HAS_VIDEO_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI,
				FacebookOntologyVocabulary.VIDEO_RSC_URI);
		ontologyTestUtility.assertObjectTypeProperty(
				FacebookOntologyVocabulary.JOINS_PRP_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI,
				FacebookOntologyVocabulary.EVENT_RSC_URI);
		ontologyTestUtility.assertDataTypeProperty(
				FacebookOntologyVocabulary.ORGANIZED_IN_PRP_URI,
				FacebookOntologyVocabulary.EVENT_RSC_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				FacebookOntologyVocabulary.RELIGION_URI,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI, XSD.xstring);
		ontologyTestUtility.assertDataTypeProperty(
				CommonOntologyVocabulary.DESCRIPTION_URI,
				FacebookOntologyVocabulary.EVENT_RSC_URI, XSD.xstring);

		// check likes property...
		ontologyTestUtility.assertObjectPropertyDomains(
				FacebookOntologyVocabulary.LIKES,
				CommonOntologyVocabulary.FOAF_PERSON_RSC_URI);
		ontologyTestUtility.assertObjectPropertyRanges(
				FacebookOntologyVocabulary.LIKES,
				CommonOntologyVocabulary.MOVIE_URI,
				CommonOntologyVocabulary.MUSIC_URI,
				CommonOntologyVocabulary.BOOK_URI);

		// check created_at property
		ontologyTestUtility.assertDatatypePropertyDomains(
				CommonOntologyVocabulary.CREATED_AT_PRP_URI,
				CommonOntologyVocabulary.MOVIE_URI,
				CommonOntologyVocabulary.MUSIC_URI,
				CommonOntologyVocabulary.BOOK_URI,
				CommonOntologyVocabulary.TAG_RSC_URI,
				CommonOntologyVocabulary.PHOTO_URI);
		ontologyTestUtility.assertDatatypePropertyRanges(
				CommonOntologyVocabulary.CREATED_AT_PRP_URI,
				XSD.xdouble.getURI());

	}

	/**
	 * Returns the ontology model of the schema creator.
	 * 
	 * @return
	 */
	public OntModel getSchemaOntModel() {
		return creator.getCreatedSchema();
	}

	@Override
	protected String getOntURI() {
		return AltEntryManager.getFacebookOntURI();
	}

	@Override
	protected OntologyCreator getOntologyCreator() {
		return new FacebookOntologyCreator();
	}

}

package com.galaksiya.social.data;

import com.galaksiya.social.fetcher.FoursquareIndividualCreator;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.galaksiya.social.vocabulary.FoursquareTestVocabulary;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;

public class FoursquareDataCreator {

	private FoursquareIndividualCreator indvCreator;

	public FoursquareDataCreator() {
		getOntModel().createResource(
				FoursquareTestVocabulary.FOURSQUARE_ONTOLOGY_URI, OWL.Ontology);
		createIndividuals();
	}

	private void createIndividuals() {
		// creating individual of Burak Yönyül, Erdem Ekinci, Tayfun Halaç, and
		// Ziya Akar
		createBurakYonyul();
		createErdemEkinci();
		createTayfunHalac();
		createZiyaAkar();
	}

	private void createProperty(Resource subjectIndv, String propURI,
			Object objectValue) {
		getIndividualCreator()
				.createProperty(subjectIndv, propURI, objectValue);
	}

	private void createProperty(Resource subjectIndv, String uri,
			Resource objectIndv) {
		getIndividualCreator().createProperty(subjectIndv, uri, objectIndv);
	}

	public Model getOntModel() {
		return getIndividualCreator().getModel();
	}

	private FoursquareIndividualCreator getIndividualCreator() {
		if (indvCreator == null) {
			indvCreator = new FoursquareIndividualCreator(
					CommonTestVocabulary.BASE_URI);
		}
		return indvCreator;
	}

	/**
	 * Creating test data for Burak Yönyül
	 * 
	 * @return
	 */
	private Resource createBurakYonyul() {

		// creating basic profile properties
		Resource burakYonyulIndv = getOntModel().createResource(
				CommonTestVocabulary.URI_BURAK_YONYUL, FOAF.Person);
		createProperty(
				burakYonyulIndv,
				FOAF.name.getURI(),
				ResourceFactory
						.createPlainLiteral(FoursquareTestVocabulary.NAME_OF_BURAK_YONYUL));
		createProperty(burakYonyulIndv,
				CommonOntologyVocabulary.SIOC_EMAIL_URI,
				FoursquareTestVocabulary.EMAIL_OF_BURAK_YONYUL);
		createProperty(burakYonyulIndv, FOAF.page.getURI(),
				FoursquareTestVocabulary.PAGE_OF_BURAK_YONYUL);
		createProperty(burakYonyulIndv, FOAF.gender.getURI(),
				CommonTestVocabulary.MALE_GENDER);
		createProperty(burakYonyulIndv, FOAF.depiction.getURI(),
				FoursquareTestVocabulary.DEPICTION_OF_BURAK_YONYUL);
		createProperty(burakYonyulIndv, RDFS.label.getURI(),
				FoursquareTestVocabulary.LABEL_OF_BURAK_YONYUL);
		createProperty(burakYonyulIndv,
				FoursquareOntologyVocabulary.RELATIONSHIP_PRP_URI,
				FoursquareTestVocabulary.SELF_RELATIONSHIP);
		createProperty(burakYonyulIndv,
				FoursquareOntologyVocabulary.PINGS_PRP_URI, false);
		createProperty(burakYonyulIndv,
				FoursquareOntologyVocabulary.REQUEST_COUNT_PRP_URI, 2);
		createProperty(burakYonyulIndv,
				FoursquareOntologyVocabulary.BADGE_COUNT_PRP_URI, 1);
		createProperty(burakYonyulIndv,
				FoursquareOntologyVocabulary.FOLLOWING_COUNT_PRP_URI, 0);

		// creating account property
		Resource accountOfBurakYonyul = createAccountIndividual(FoursquareTestVocabulary.ACCOUNT_URI_OF_BURAK_YONYUL);
		createProperty(accountOfBurakYonyul,
				FOAF.accountServiceHomepage.getURI(),
				FoursquareOntologyVocabulary.FOURSQUARE_HOMEPAGE_RSC);
		createProperty(accountOfBurakYonyul, FOAF.accountName.getURI(),
				FoursquareTestVocabulary.ID_OF_BURAK_YONYUL);

		createProperty(burakYonyulIndv,
				CommonOntologyVocabulary.ACCOUNT_PRP_URI, accountOfBurakYonyul);

		// creating hometown property
		Resource izmirRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_IZMIR_URI);
		createProperty(burakYonyulIndv, CommonOntologyVocabulary.HOMETOWN_URI,
				izmirRsc);

		// creating personal contact property
		Resource contactIndv = createContactIndividual(
				FoursquareTestVocabulary.CONTACT_URI_OF_BURAK_YONYUL, null,
				FoursquareTestVocabulary.PHONE_OF_BURAK_YONYUL, null, null);
		createProperty(burakYonyulIndv,
				FoursquareOntologyVocabulary.CONTACT_PRP_URI, contactIndv);

		// creating checkin properties
		Resource cafeCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.CAFE_CATEGORY_URI,
				FoursquareTestVocabulary.CAFE_CATEGORY_NAME,
				FoursquareTestVocabulary.CAFE_CATEGORY_PLURAL_NAME, true,
				FoursquareTestVocabulary.CAFE_CATEGORY_ICON);

		Resource collegeLabCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_URI,
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_NAME,
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_PLURAL_NAME,
				true, FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_ICON);

		Resource gardenCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.GARDEN_CATEGORY_URI,
				FoursquareTestVocabulary.GARDEN_CATEGORY_NAME,
				FoursquareTestVocabulary.GARDEN_CATEGORY_PLURAL_NAME, true,
				FoursquareTestVocabulary.GARDEN_CATEGORY_ICON);

		Resource busLineCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.BUS_LINE_CATEGORY_URI,
				FoursquareTestVocabulary.BUS_LINE_CATEGORY_NAME,
				FoursquareTestVocabulary.BUS_LINE_CATEGORY_PLURAL_NAME, true,
				FoursquareTestVocabulary.BUS_LINE_CATEGORY_ICON);

		Resource turkeyRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_TURKEY_URI);

		Resource locationOfSeagentIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_SEAGENT,
				FoursquareTestVocabulary.LAT_OF_SEAGENT,
				FoursquareTestVocabulary.LNG_OF_SEAGENT, null, turkeyRsc, null,
				null);

		Resource locationOfBilmuhCimlerIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_BILMUH_CIMLER,
				FoursquareTestVocabulary.LAT_OF_BILMUH_CIMLER,
				FoursquareTestVocabulary.LNG_OF_BILMUH_CIMLER, null, turkeyRsc,
				null, null);

		Resource locationOf525Indv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_525,
				FoursquareTestVocabulary.LAT_OF_525,
				FoursquareTestVocabulary.LNG_OF_525, null, turkeyRsc,
				FoursquareTestVocabulary.ADDRESS_OF_525, null);

		Resource locationOfOzdilekCafeIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_OZDILEK_CAFE,
				FoursquareTestVocabulary.LAT_OF_OZDILEK_CAFE,
				FoursquareTestVocabulary.LNG_OF_OZDILEK_CAFE, izmirRsc,
				turkeyRsc, FoursquareTestVocabulary.ADDRESS_OF_OZDILEK_CAFE,
				FoursquareTestVocabulary.STREET_OF_OZDILEK_CAFE);

		Resource venueOfSeagentIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_SEAGENT,
				FoursquareTestVocabulary.NAME_OF_SEAGENT,
				locationOfSeagentIndv, null, collegeLabCategoryIndv, false);

		Resource venueOfBilmuhCimlerIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_BILMUH_CIMLER,
				FoursquareTestVocabulary.NAME_OF_BILMUH_CIMLER,
				locationOfBilmuhCimlerIndv, null, gardenCategoryIndv, false);

		Resource venueOf525Indv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_525,
				FoursquareTestVocabulary.NAME_OF_525, locationOf525Indv, null,
				busLineCategoryIndv, false);

		Resource venueOfOzdilekCafeIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_OZDILEK_CAFE,
				FoursquareTestVocabulary.NAME_OF_OZDILEK_CAFE,
				locationOfOzdilekCafeIndv, null, cafeCategoryIndv, false);

		Resource checkin1ofBurakYonyul = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_1_OF_BURAK_YONYUL,
				venueOfSeagentIndv,
				FoursquareTestVocabulary.TIME_ZONE_ISTANBUL, null);

		Resource checkin2ofBurakYonyul = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_2_OF_BURAK_YONYUL,
				venueOfBilmuhCimlerIndv,
				FoursquareTestVocabulary.TIME_ZONE_ISTANBUL, null);

		Resource checkin3ofBurakYonyul = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_3_OF_BURAK_YONYUL,
				venueOf525Indv, FoursquareTestVocabulary.TIME_ZONE_ISTANBUL,
				null);

		Resource checkin4ofBurakYonyul = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_4_OF_BURAK_YONYUL,
				venueOfOzdilekCafeIndv,
				FoursquareTestVocabulary.TIME_ZONE_ISTANBUL, null);

		createProperty(burakYonyulIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin1ofBurakYonyul);
		createProperty(burakYonyulIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin2ofBurakYonyul);
		createProperty(burakYonyulIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin3ofBurakYonyul);
		createProperty(burakYonyulIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin4ofBurakYonyul);

		return burakYonyulIndv;
	}

	/**
	 * Creating test data for Erdem Ekinci
	 * 
	 * @return
	 */
	private Resource createErdemEkinci() {

		// creating basic profile properties
		Resource erdemEkinciIndv = getOntModel()
				.createResource(
						CommonTestVocabulary.URI_ERDEM_ESER_EKINCI,
						FOAF.Person);
		createProperty(
				erdemEkinciIndv,
				FOAF.name.getURI(),
				ResourceFactory
						.createPlainLiteral(FoursquareTestVocabulary.NAME_OF_ERDEM_EKINCI));
		createProperty(erdemEkinciIndv,
				CommonOntologyVocabulary.SIOC_EMAIL_URI,
				FoursquareTestVocabulary.EMAIL_OF_ERDEM_EKINCI);
		createProperty(erdemEkinciIndv, FOAF.page.getURI(),
				FoursquareTestVocabulary.PAGE_OF_ERDEM_EKINCI);
		createProperty(erdemEkinciIndv, FOAF.gender.getURI(),
				CommonTestVocabulary.MALE_GENDER);
		createProperty(erdemEkinciIndv, FOAF.depiction.getURI(),
				FoursquareTestVocabulary.DEPICTION_OF_ERDEM_EKINCI);
		createProperty(erdemEkinciIndv, RDFS.label.getURI(),
				FoursquareTestVocabulary.LABEL_OF_ERDEM_EKINCI);
		createProperty(erdemEkinciIndv,
				FoursquareOntologyVocabulary.RELATIONSHIP_PRP_URI,
				FoursquareTestVocabulary.SELF_RELATIONSHIP);
		createProperty(erdemEkinciIndv,
				FoursquareOntologyVocabulary.PINGS_PRP_URI, false);
		createProperty(erdemEkinciIndv,
				FoursquareOntologyVocabulary.REQUEST_COUNT_PRP_URI, 2);
		createProperty(erdemEkinciIndv,
				FoursquareOntologyVocabulary.BADGE_COUNT_PRP_URI, 9);
		createProperty(erdemEkinciIndv,
				FoursquareOntologyVocabulary.FOLLOWING_COUNT_PRP_URI, 0);

		// creating account property
		Resource accountOfErdemEkinci = createAccountIndividual(FoursquareTestVocabulary.ACCOUNT_URI_OF_ERDEM_EKINCI);
		createProperty(accountOfErdemEkinci,
				FOAF.accountServiceHomepage.getURI(),
				FoursquareOntologyVocabulary.FOURSQUARE_HOMEPAGE_RSC);
		createProperty(accountOfErdemEkinci, FOAF.accountName.getURI(),
				FoursquareTestVocabulary.ID_OF_ERDEM_ESER_EKINCI);

		createProperty(erdemEkinciIndv,
				CommonOntologyVocabulary.ACCOUNT_PRP_URI, accountOfErdemEkinci);

		// creating hometown property
		Resource kayseriRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_KAYSERI_URI);
		createProperty(erdemEkinciIndv, CommonOntologyVocabulary.HOMETOWN_URI,
				kayseriRsc);

		// creating personal contact property
		Resource contactOfErdemEserEkinci = createContactIndividual(
				FoursquareTestVocabulary.CONTACT_URI_OF_ERDEM_EKINCI, null,
				FoursquareTestVocabulary.PHONE_OF_ERDEM_EKINCI,
				FoursquareTestVocabulary.FACEBOOK_OF_ERDEM_EKINCI, null);
		createProperty(erdemEkinciIndv,
				FoursquareOntologyVocabulary.CONTACT_PRP_URI,
				contactOfErdemEserEkinci);

		// creating checkin properties
		Resource barCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.BAR_CATEGORY_URI,
				FoursquareTestVocabulary.BAR_CATEGORY_NAME,
				FoursquareTestVocabulary.BAR_CATEGORY_PLURAL_NAME, true,
				FoursquareTestVocabulary.BAR_CATEGORY_ICON);

		Resource collegeLabCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_URI,
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_NAME,
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_PLURAL_NAME,
				true, FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_ICON);

		Resource officeCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.OFFICE_CATEGORY_URI,
				FoursquareTestVocabulary.OFFICE_CATEGORY_NAME,
				FoursquareTestVocabulary.OFFICE_CATEGORY_PLURAL_NAME, true,
				FoursquareTestVocabulary.OFFICE_CATEGORY_ICON);

		Resource contactOfPolinasIndv = createContactIndividual(
				FoursquareTestVocabulary.CONTACT_URI_OF_POLINAS, null,
				FoursquareTestVocabulary.PHONE_OF_POLINAS, null, null);

		Resource turkeyRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_TURKEY_URI);

		Resource locationOfSeagentIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_SEAGENT,
				FoursquareTestVocabulary.LAT_OF_SEAGENT,
				FoursquareTestVocabulary.LNG_OF_SEAGENT, null, turkeyRsc, null,
				null);

		Resource manisaRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_MANISA_URI);

		Resource locationOfPolinasIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_POLINAS,
				FoursquareTestVocabulary.LAT_OF_POLINAS,
				FoursquareTestVocabulary.LNG_OF_POLINAS, manisaRsc, turkeyRsc,
				null, null);

		Resource locationOfDurockIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_DUROCK,
				FoursquareTestVocabulary.LAT_OF_DUROCK,
				FoursquareTestVocabulary.LNG_OF_DUROCK, null, turkeyRsc, null,
				null);

		Resource venueOfSeagentIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_SEAGENT,
				FoursquareTestVocabulary.NAME_OF_SEAGENT,
				locationOfSeagentIndv, null, collegeLabCategoryIndv, false);

		Resource venueOfPolinasIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_POLINAS,
				FoursquareTestVocabulary.NAME_OF_POLINAS,
				locationOfPolinasIndv, contactOfPolinasIndv,
				officeCategoryIndv, false);

		Resource venueOfDurockIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_DUROCK,
				FoursquareTestVocabulary.NAME_OF_DUROCK, locationOfDurockIndv,
				null, barCategoryIndv, false);

		Resource checkin1ofErdemEkinci = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_1_OF_ERDEM_EKINCI,
				venueOfSeagentIndv,
				FoursquareTestVocabulary.TIME_ZONE_ISTANBUL, null);

		Resource checkin2ofErdemEkinci = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_2_OF_ERDEM_EKINCI,
				venueOfPolinasIndv,
				FoursquareTestVocabulary.TIME_ZONE_ISTANBUL, null);

		Resource checkin3ofErdemEkinci = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_3_OF_ERDEM_EKINCI,
				venueOfDurockIndv, FoursquareTestVocabulary.TIME_ZONE_ISTANBUL,
				null);

		createProperty(erdemEkinciIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin1ofErdemEkinci);
		createProperty(erdemEkinciIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin2ofErdemEkinci);
		createProperty(erdemEkinciIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin3ofErdemEkinci);

		return erdemEkinciIndv;
	}

	/**
	 * creating test data for Tayfun Gökmen Halaç
	 * 
	 * @return
	 */
	private Resource createTayfunHalac() {

		// creating basic profile properties
		Resource tayfunHalacIndv = getOntModel().createResource(
				CommonTestVocabulary.URI_TAYFUN_GOKMEN_HALAC,
				FOAF.Person);

		createProperty(
				tayfunHalacIndv,
				FOAF.name.getURI(),
				ResourceFactory
						.createPlainLiteral(FoursquareTestVocabulary.NAME_OF_TAYFUN_HALAC));
		createProperty(tayfunHalacIndv,
				CommonOntologyVocabulary.SIOC_EMAIL_URI,
				FoursquareTestVocabulary.EMAIL_OF_TAYFUN_HALAC);
		createProperty(tayfunHalacIndv, FOAF.page.getURI(),
				FoursquareTestVocabulary.PAGE_OF_TAYFUN_HALAC);
		createProperty(tayfunHalacIndv, FOAF.gender.getURI(),
				CommonTestVocabulary.MALE_GENDER);
		createProperty(tayfunHalacIndv, FOAF.depiction.getURI(),
				FoursquareTestVocabulary.DEPICTION_OF_TAYFUN_HALAC);
		createProperty(tayfunHalacIndv, RDFS.label.getURI(),
				FoursquareTestVocabulary.LABEL_OF_TAYFUN_HALAC);
		createProperty(tayfunHalacIndv,
				FoursquareOntologyVocabulary.RELATIONSHIP_PRP_URI,
				FoursquareTestVocabulary.SELF_RELATIONSHIP);
		createProperty(tayfunHalacIndv,
				FoursquareOntologyVocabulary.PINGS_PRP_URI, false);
		createProperty(tayfunHalacIndv,
				FoursquareOntologyVocabulary.REQUEST_COUNT_PRP_URI, 0);
		createProperty(tayfunHalacIndv,
				FoursquareOntologyVocabulary.BADGE_COUNT_PRP_URI, 7);
		createProperty(tayfunHalacIndv,
				FoursquareOntologyVocabulary.FOLLOWING_COUNT_PRP_URI, 0);

		// creating account property
		Resource accountOfTayfunHalac = createAccountIndividual(FoursquareTestVocabulary.ACCOUNT_URI_OF_TAYFUN_HALAC);
		createProperty(accountOfTayfunHalac,
				FOAF.accountServiceHomepage.getURI(),
				FoursquareOntologyVocabulary.FOURSQUARE_HOMEPAGE_RSC);
		createProperty(accountOfTayfunHalac, FOAF.accountName.getURI(),
				FoursquareTestVocabulary.ID_OF_TAYFUN_GOKMEN_HALAC);

		createProperty(tayfunHalacIndv,
				CommonOntologyVocabulary.ACCOUNT_PRP_URI, accountOfTayfunHalac);

		// creating hometown property
		Resource izmirRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_IZMIR_URI);
		createProperty(tayfunHalacIndv, CommonOntologyVocabulary.HOMETOWN_URI,
				izmirRsc);

		// creating personal contact property
		Resource contactOfTayfunHalac = createContactIndividual(
				FoursquareTestVocabulary.CONTACT_URI_OF_TAYFUN_HALAC, null,
				FoursquareTestVocabulary.PHONE_OF_TAYFUN_HALAC,
				FoursquareTestVocabulary.FACEBOOK_OF_TAYFUN_HALAC, null);
		createProperty(tayfunHalacIndv,
				FoursquareOntologyVocabulary.CONTACT_PRP_URI,
				contactOfTayfunHalac);

		// creating checkin properties
		Resource historicSideCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.HISTORIC_SIDE_CATEGORY_URI,
				FoursquareTestVocabulary.HISTORIC_SIDE_CATEGORY_NAME,
				FoursquareTestVocabulary.HISTORIC_SIDE_CATEGORY_PLURAL_NAME,
				true, FoursquareTestVocabulary.HISTORIC_SIDE_CATEGORY_ICON);

		Resource collegeLabCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_URI,
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_NAME,
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_PLURAL_NAME,
				true, FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_ICON);

		Resource plazaCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.PLAZA_CATEGORY_URI,
				FoursquareTestVocabulary.PLAZA_CATEGORY_NAME,
				FoursquareTestVocabulary.PLAZA_CATEGORY_PLURAL_NAME, true,
				FoursquareTestVocabulary.PLAZA_CATEGORY_ICON);

		Resource turkeyRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_TURKEY_URI);

		Resource locationOfSeagentIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_SEAGENT,
				FoursquareTestVocabulary.LAT_OF_SEAGENT,
				FoursquareTestVocabulary.LNG_OF_SEAGENT, null, turkeyRsc, null,
				null);

		Resource lyonRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_MANISA_URI);
		Resource franceRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_FRANCE_URI);

		Resource locationOfVieuxLyonIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_VIEUX_LYON,
				FoursquareTestVocabulary.LAT_OF_VIEUX_LYON,
				FoursquareTestVocabulary.LNG_OF_VIEUX_LYON, lyonRsc, franceRsc,
				FoursquareTestVocabulary.ADDRESS_OF_VIEUX_LYON, null);

		Resource locationOfPlaceBellecourIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_PLACE_BELLECOUR,
				FoursquareTestVocabulary.LAT_OF_PLACE_BELLECOUR,
				FoursquareTestVocabulary.LNG_OF_PLACE_BELLECOUR, lyonRsc,
				franceRsc, FoursquareTestVocabulary.ADDRESS_OF_PLACE_BELLECOUR,
				null);

		Resource venueOfSeagentIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_SEAGENT,
				FoursquareTestVocabulary.NAME_OF_SEAGENT,
				locationOfSeagentIndv, null, collegeLabCategoryIndv, false);

		Resource venueOfVieuxLyonIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_VIEUX_LYON,
				FoursquareTestVocabulary.NAME_OF_VIEUX_LYON,
				locationOfVieuxLyonIndv, null, historicSideCategoryIndv, false);

		Resource venueOfPlaceBellecourIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_PLACE_BELLECOUR,
				FoursquareTestVocabulary.NAME_OF_PLACE_BELLECOUR,
				locationOfPlaceBellecourIndv, null, plazaCategoryIndv, false);

		Resource checkin1ofTayfunHalac = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_1_OF_TAYFUN_HALAC,
				venueOfSeagentIndv,
				FoursquareTestVocabulary.TIME_ZONE_ISTANBUL, null);

		Resource checkin2ofTayfunHalac = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_2_OF_TAYFUN_HALAC,
				venueOfVieuxLyonIndv, FoursquareTestVocabulary.TIME_ZONE_PARIS,
				null);

		Resource checkin3ofTayfunHalac = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_3_OF_TAYFUN_HALAC,
				venueOfPlaceBellecourIndv,
				FoursquareTestVocabulary.TIME_ZONE_PARIS, null);

		createProperty(tayfunHalacIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin1ofTayfunHalac);
		createProperty(tayfunHalacIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin2ofTayfunHalac);
		createProperty(tayfunHalacIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin3ofTayfunHalac);

		return tayfunHalacIndv;
	}

	/**
	 * creating test data for Ziya Akar
	 * 
	 * @return
	 */
	private Resource createZiyaAkar() {

		// creating basic profile properties
		Resource ziyaAkarIndv = getOntModel().createResource(
				CommonTestVocabulary.URI_ZIYA_AKAR, FOAF.Person);

		createProperty(
				ziyaAkarIndv,
				FOAF.name.getURI(),
				ResourceFactory
						.createPlainLiteral(FoursquareTestVocabulary.NAME_OF_ZIYA_AKAR));
		createProperty(ziyaAkarIndv, CommonOntologyVocabulary.SIOC_EMAIL_URI,
				FoursquareTestVocabulary.EMAIL_OF_ZIYA_AKAR);
		createProperty(ziyaAkarIndv, FOAF.page.getURI(),
				FoursquareTestVocabulary.PAGE_OF_ZIYA_AKAR);
		createProperty(ziyaAkarIndv, FOAF.gender.getURI(),
				CommonTestVocabulary.MALE_GENDER);
		createProperty(ziyaAkarIndv, FOAF.depiction.getURI(),
				FoursquareTestVocabulary.DEPICTION_OF_ZIYA_AKAR);
		createProperty(ziyaAkarIndv, RDFS.label.getURI(),
				FoursquareTestVocabulary.LABEL_OF_ZIYA_AKAR);
		createProperty(ziyaAkarIndv,
				FoursquareOntologyVocabulary.RELATIONSHIP_PRP_URI,
				FoursquareTestVocabulary.SELF_RELATIONSHIP);
		createProperty(ziyaAkarIndv,
				FoursquareOntologyVocabulary.PINGS_PRP_URI, false);
		createProperty(ziyaAkarIndv,
				FoursquareOntologyVocabulary.REQUEST_COUNT_PRP_URI, 0);
		createProperty(ziyaAkarIndv,
				FoursquareOntologyVocabulary.BADGE_COUNT_PRP_URI, 1);
		createProperty(ziyaAkarIndv,
				FoursquareOntologyVocabulary.FOLLOWING_COUNT_PRP_URI, 3);

		// creating account property
		Resource accountOfZiyaAkar = createAccountIndividual(FoursquareTestVocabulary.ACCOUNT_URI_OF_ZIYA_AKAR);
		createProperty(accountOfZiyaAkar, FOAF.accountServiceHomepage.getURI(),
				FoursquareOntologyVocabulary.FOURSQUARE_HOMEPAGE_RSC);
		createProperty(accountOfZiyaAkar, FOAF.accountName.getURI(),
				FoursquareTestVocabulary.ID_OF_ZIYA_AKAR);

		createProperty(ziyaAkarIndv, CommonOntologyVocabulary.ACCOUNT_PRP_URI,
				accountOfZiyaAkar);

		// creating hometown property
		Resource bursaRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_BURSA_URI);
		createProperty(ziyaAkarIndv, CommonOntologyVocabulary.HOMETOWN_URI,
				bursaRsc);

		// creating checkin properties
		Resource coffeeShopCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.COFFEE_SHOP_CATEGORY_URI,
				FoursquareTestVocabulary.COFFEE_SHOP_CATEGORY_NAME,
				FoursquareTestVocabulary.COFFEE_SHOP_CATEGORY_PLURAL_NAME,
				true, FoursquareTestVocabulary.COFFEE_SHOP_CATEGORY_ICON);

		Resource collegeLabCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_URI,
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_NAME,
				FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_PLURAL_NAME,
				true, FoursquareTestVocabulary.COLLEGE_LAB_CATEGORY_ICON);

		Resource mediRestaurantCategoryIndv = createCategoryIndividual(
				FoursquareTestVocabulary.MEDI_RESTAURANT_CATEGORY_URI,
				FoursquareTestVocabulary.MEDI_RESTAURANT_NAME,
				FoursquareTestVocabulary.MEDI_RESTAURANT_PLURAL_NAME, true,
				FoursquareTestVocabulary.MEDI_RESTAURANT_ICON);

		Resource turkeyRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_TURKEY_URI);

		Resource locationOfSeagentIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_SEAGENT,
				FoursquareTestVocabulary.LAT_OF_SEAGENT,
				FoursquareTestVocabulary.LNG_OF_SEAGENT, null, turkeyRsc, null,
				null);

		Resource izmirRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_IZMIR_URI);

		Resource locationOfAlinsIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_ALINS,
				FoursquareTestVocabulary.LAT_OF_ALINS,
				FoursquareTestVocabulary.LNG_OF_ALINS, null, turkeyRsc,
				FoursquareTestVocabulary.ADDRESS_OF_ALINS,
				FoursquareTestVocabulary.STREET_OF_ALINS);

		Resource locationOfKahveDiyariIndv = createLocationIndividual(
				FoursquareTestVocabulary.LOCATION_URI_OF_KAHVE_DIYARI,
				FoursquareTestVocabulary.LAT_OF_KAHVE_DIYARI,
				FoursquareTestVocabulary.LNG_OF_KAHVE_DIYARI, izmirRsc,
				turkeyRsc, FoursquareTestVocabulary.ADDRESS_OF_KAHVE_DIYARI,
				null);

		Resource venueOfSeagentIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_SEAGENT,
				FoursquareTestVocabulary.NAME_OF_SEAGENT,
				locationOfSeagentIndv, null, collegeLabCategoryIndv, false);

		Resource venueOfAlinsIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_ALINS,
				FoursquareTestVocabulary.NAME_OF_ALINS, locationOfAlinsIndv,
				null, mediRestaurantCategoryIndv, false);

		Resource venueOfKahveDiyariIndv = createVenueIndividual(
				FoursquareTestVocabulary.VENUE_URI_OF_KAHVE_DIYARI,
				FoursquareTestVocabulary.NAME_OF_KAHVE_DIYARI,
				locationOfKahveDiyariIndv, null, coffeeShopCategoryIndv, false);

		Resource checkin1ofZiyaAkar = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_1_OF_ZIYA_AKAR,
				venueOfSeagentIndv,
				FoursquareTestVocabulary.TIME_ZONE_ISTANBUL, null);

		Resource checkin2ofZiyaAkar = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_2_OF_ZIYA_AKAR,
				venueOfAlinsIndv, FoursquareTestVocabulary.TIME_ZONE_ISTANBUL,
				null);

		Resource checkin3ofZiyaAkar = createCheckinIndividual(
				FoursquareTestVocabulary.CHECKIN_URI_3_OF_ZIYA_AKAR,
				venueOfKahveDiyariIndv,
				FoursquareTestVocabulary.TIME_ZONE_ISTANBUL, null);

		createProperty(ziyaAkarIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin1ofZiyaAkar);
		createProperty(ziyaAkarIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin2ofZiyaAkar);
		createProperty(ziyaAkarIndv,
				FoursquareOntologyVocabulary.CHECKIN_PRP_URI,
				checkin3ofZiyaAkar);

		return ziyaAkarIndv;
	}

	/**
	 * This method creates a checkin {@link Resource} type ,individual with
	 * given URI and parameters.
	 * 
	 * @param checkinURI
	 *            URI of checkin {@link Individual} instance
	 * @param venueIndv
	 *            venue {@link Individual} instance for venue property of
	 *            checkin {@link Individual} instance
	 * @param timeZone
	 *            time zone property value of checkin {@link Individual}
	 *            instance
	 * @param shout
	 *            shout (personal checkin message) property value of checkin
	 *            {@link Individual} instance
	 * @return
	 */
	private Resource createCheckinIndividual(String checkinURI,
			Resource venueIndv, String timeZone, String shout) {
		Resource checkinIndv = getOntModel().createResource(checkinURI,
				FoursquareOntologyVocabulary.CHECKIN_RSC);
		createProperty(checkinIndv, FoursquareOntologyVocabulary.VENUE_PRP_URI,
				venueIndv);
		createProperty(checkinIndv,
				FoursquareOntologyVocabulary.TIME_ZONE_PRP_URI, timeZone);
		createProperty(checkinIndv, FoursquareOntologyVocabulary.SHOUT_PRP_URI,
				shout);
		return checkinIndv;
	}

	/**
	 * This method creates Venue {@link Resource} type individual with given URI
	 * and parameters.
	 * 
	 * @param venueURI
	 *            URI of Venue {@link Individual} instance
	 * @param venueName
	 *            name property value of {@link Individual} instance
	 * @param locationIndv
	 *            location {@link Individual} instance for location property of
	 *            Venue {@link Individual} instance
	 * @param contactIndv
	 *            contact {@link Individual} instance for location property of
	 *            {@link Individual} instance
	 * @param categoryIndv
	 *            category {@link Individual} instance for location
	 *            {@link Individual} instance
	 * @param isVerified
	 *            is verified property value of location {@link Individual}
	 *            instance
	 * @return
	 */
	private Resource createVenueIndividual(String venueURI, String venueName,
			Resource locationIndv, Resource contactIndv, Resource categoryIndv,
			boolean isVerified) {
		Resource venueIndv = getOntModel().createResource(venueURI,
				FoursquareOntologyVocabulary.VENUE_RSC);
		createProperty(venueIndv, FOAF.name.getURI(), venueName);
		createProperty(venueIndv, CommonOntologyVocabulary.LOCATION_URI,
				locationIndv);
		createProperty(venueIndv, FoursquareOntologyVocabulary.CONTACT_PRP_URI,
				contactIndv);
		createProperty(venueIndv,
				FoursquareOntologyVocabulary.CATEGORY_PRP_URI, categoryIndv);
		createProperty(venueIndv,
				FoursquareOntologyVocabulary.IS_VERIFIED_PRP_URI, isVerified);

		return venueIndv;

	}

	/**
	 * This method creates Category {@link Resource} type individual with given
	 * URI and parameters.
	 * 
	 * @param categoryURI
	 *            URI of category {@link Individual} instance
	 * @param name
	 *            name value of category {@link Individual} instance
	 * @param pluralName
	 *            plural name value of category {@link Individual} instance
	 * @param primary
	 *            primary value of category {@link Individual} instance
	 * @param icon
	 *            icon property value of category {@link Individual} instance
	 * @return
	 */
	private Resource createCategoryIndividual(String categoryURI, String name,
			String pluralName, boolean primary, String icon) {
		Resource categoryIndv = getOntModel().createResource(categoryURI,
				FoursquareOntologyVocabulary.CATEGORY_RSC);
		createProperty(categoryIndv, FOAF.name.getURI(), name);
		createProperty(categoryIndv,
				FoursquareOntologyVocabulary.PLURAL_NAME_PRP_URI, pluralName);
		createProperty(categoryIndv,
				FoursquareOntologyVocabulary.PRIMARY_PRP_URI, primary);
		createProperty(categoryIndv, FoursquareOntologyVocabulary.ICON_PRP_URI,
				icon);
		return categoryIndv;
	}

	/**
	 * This method creates an Place type {@link Resource} individual with given
	 * URI and parameters.
	 * 
	 * @param locationURI
	 *            URI of place {@link Individual} instance
	 * @param latitude
	 *            latitude property value of place {@link Individual} instance
	 * @param longitude
	 *            longitude property value of place {@link Individual} instance
	 * @param cityRsc
	 *            city property resource of place {@link Individual} instance
	 * @param countryRsc
	 *            country property resource of place {@link Individual} instance
	 * @param address
	 *            address property value of place {@link Individual} instance
	 * @param street
	 *            street property value of place {@link Individual} instance
	 * @return
	 */
	private Resource createLocationIndividual(String locationURI,
			double latitude, double longitude, Resource cityRsc,
			Resource countryRsc, String address, String street) {
		Resource locationIndv = getOntModel().createResource(locationURI,
				CommonOntologyVocabulary.PLACE_RSC);
		createProperty(locationIndv, CommonOntologyVocabulary.LAT_PRP_URI,
				latitude);
		createProperty(locationIndv, CommonOntologyVocabulary.LNG_PRP_URI,
				longitude);
		createProperty(locationIndv, CommonOntologyVocabulary.CITY_PRP_URI,
				cityRsc);
		createProperty(locationIndv, CommonOntologyVocabulary.COUNTRY_PRP_URI,
				countryRsc);
		createProperty(locationIndv, CommonOntologyVocabulary.ADDRESS_PRP_URI,
				address);
		createProperty(locationIndv,
				CommonOntologyVocabulary.CROSS_STREET_PRP_URI, street);
		return locationIndv;
	}

	/**
	 * This method creates an Contact {@link Resource} type individual with
	 * given URI and property values.
	 * 
	 * @param contactURI
	 *            URI of contact {@link Individual} instance
	 * @param phone
	 *            phone contact value
	 * @param facebookAdd
	 *            facebook address contact value
	 * @param twitterAdd
	 *            twitter address contact value
	 * @return contact {@link Individual} instance
	 */
	private Resource createContactIndividual(String contactURI, String email,
			String phone, String facebookAdd, String twitterAdd) {
		Resource contactIndv = getOntModel().createResource(contactURI,
				FoursquareOntologyVocabulary.CONTACT_RSC);
		// creating email, phone, facebook and twitter proeprties of contact
		// individual
		createProperty(contactIndv, CommonOntologyVocabulary.SIOC_EMAIL_URI,
				email);
		createProperty(contactIndv, FoursquareOntologyVocabulary.PHONE_PRP_URI,
				phone);
		createProperty(contactIndv, FoursquareOntologyVocabulary.FACEBOOK,
				facebookAdd);
		createProperty(contactIndv, FoursquareOntologyVocabulary.TWITTER,
				twitterAdd);
		return contactIndv;
	}

	/**
	 * It creates an Account {@link Resource} type individual with given URI
	 * 
	 * @param accountURI
	 *            URI of account individual
	 * @return
	 */
	private Resource createAccountIndividual(String accountURI) {
		return getOntModel().createResource(accountURI, FOAF.OnlineAccount);
	}

}
package com.galaksiya.social.data;

import com.galaksiya.social.fetcher.FacebookIndividualCreator;
import com.galaksiya.social.fetcher.IndividualCreator;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.galaksiya.social.vocabulary.FacebookTestVocabulary;
import com.galaksiya.social.vocabulary.FoursquareTestVocabulary;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;

public class FacebookDataCreator {

	private FacebookIndividualCreator indvCreator;

	/**
	 * This method creates foaf:knows properties with given individual list
	 * 
	 * @param facebookIndividuals
	 *            list of Facebook individuals that knows each other
	 */
	private void createFoafKnowsProperties(Resource... facebookIndividuals) {
		for (int i = 0; i < facebookIndividuals.length; i++) {
			for (int j = i + 1; j < facebookIndividuals.length; j++) {
				// creating two both side foaf:knows properties
				createObjectProperty(facebookIndividuals[i],
						FOAF.knows.getURI(), facebookIndividuals[j]);
				createObjectProperty(facebookIndividuals[j],
						FOAF.knows.getURI(), facebookIndividuals[i]);
			}

		}
	}

	/**
	 * creating Facebook test data for Burak Yönyül
	 * 
	 * @return
	 */
	private Resource createBurakYonyul() {

		// creating basic profile properties
		Resource burakYonyulIndv = getOntModel().createResource(
				CommonTestVocabulary.URI_BURAK_YONYUL, FOAF.Person);

		createDatatypeProperty(
				burakYonyulIndv,
				FOAF.name.getURI(),
				ResourceFactory
						.createPlainLiteral(FacebookTestVocabulary.NAME_OF_BURAK_YONYUL));
		createDatatypeProperty(burakYonyulIndv,
				CommonOntologyVocabulary.SIOC_EMAIL_URI,
				FacebookTestVocabulary.EMAIL_OF_BURAK_YONYUL);
		createDatatypeProperty(burakYonyulIndv, FOAF.page.getURI(),
				FacebookTestVocabulary.PAGE_OF_BURAK_YONYUL);
		createDatatypeProperty(burakYonyulIndv, FOAF.gender.getURI(),
				CommonTestVocabulary.MALE_GENDER);
		createDatatypeProperty(burakYonyulIndv, FOAF.depiction.getURI(),
				FacebookTestVocabulary.DEPICTION_OF_BURAK_YONYUL);
		createDatatypeProperty(burakYonyulIndv, RDFS.label.getURI(),
				FacebookTestVocabulary.LABEL_OF_BURAK_YONYUL);

		// creating favourite team properties
		Resource galatasarayIndv = createTeamIndv(
				FacebookTestVocabulary.GALATASARAY_URI,
				FacebookTestVocabulary.NAME_OF_GALATASARAY);
		createObjectProperty(burakYonyulIndv,
				FacebookOntologyVocabulary.FAVOURITE_TEAM_PRP_URI,
				galatasarayIndv);

		// creating like properties
		Resource yahsiCazibeIndv = createLikeIndividual(
				FacebookTestVocabulary.YAHSI_CAZIBE_URI,
				FacebookTestVocabulary.NAME_OF_YAHSI_CAZIBE,
				CommonOntologyVocabulary.MOVIE_RSC);
		Resource lordOfTheRingsIndv = createLikeIndividual(
				FacebookTestVocabulary.YUZUKLERIN_EFENDISI_URI,
				FacebookTestVocabulary.NAME_OF_YUZUKLERIN_EFENDISI,
				CommonOntologyVocabulary.MOVIE_RSC);
		Resource jurassicParkIndv = createLikeIndividual(
				FacebookTestVocabulary.JURASSIC_PARK_LOCAL_URI,
				FacebookTestVocabulary.NAME_OF_JURASSIC_PARC,
				CommonOntologyVocabulary.MOVIE_RSC);
		Resource linkinParkIndv = createLikeIndividual(
				FacebookTestVocabulary.LINKIN_PARK_URI,
				FacebookTestVocabulary.NAME_OF_LINKIN_PARK,
				CommonOntologyVocabulary.MUSIC_RSC);
		Resource queenIndv = createLikeIndividual(
				FacebookTestVocabulary.QUEEN_URI,
				FacebookTestVocabulary.NAME_OF_QUEEN,
				CommonOntologyVocabulary.MUSIC_RSC);
		createObjectProperty(burakYonyulIndv, FacebookOntologyVocabulary.LIKES,
				yahsiCazibeIndv);
		createObjectProperty(burakYonyulIndv, FacebookOntologyVocabulary.LIKES,
				lordOfTheRingsIndv);
		createObjectProperty(burakYonyulIndv, FacebookOntologyVocabulary.LIKES,
				jurassicParkIndv);
		createObjectProperty(burakYonyulIndv, FacebookOntologyVocabulary.LIKES,
				linkinParkIndv);
		createObjectProperty(burakYonyulIndv, FacebookOntologyVocabulary.LIKES,
				queenIndv);

		// creating director like property
		createObjectProperty(burakYonyulIndv, FacebookOntologyVocabulary.LIKES,
				FacebookTestVocabulary.STEVEN_SPIELBERG_RSC);

		// creating account property
		Resource accountIndv = createAccountIndividual(FacebookTestVocabulary.ACCOUNT_URI_OF_BURAK_YONYUL);
		createObjectProperty(accountIndv, FOAF.accountServiceHomepage.getURI(),
				FacebookOntologyVocabulary.FACEBOOK_HOMEPAGE_RSC);
		createDatatypeProperty(accountIndv, FOAF.accountName.getURI(),
				FacebookTestVocabulary.FACEBOOK_ID_OF_BURAK_YONYUL);

		createObjectProperty(burakYonyulIndv,
				CommonOntologyVocabulary.ACCOUNT_PRP_URI, accountIndv);
		// creating location property
		Resource izmirRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_IZMIR_URI);
		createObjectProperty(burakYonyulIndv,
				CommonOntologyVocabulary.LOCATION_URI, izmirRsc);
		// creating hometown property
		createObjectProperty(burakYonyulIndv,
				CommonOntologyVocabulary.HOMETOWN_URI, izmirRsc);

		// creating education properties
		Resource eduOrgEgeUniIndv = createEducationalOrgIndv(
				FacebookTestVocabulary.URI_OF_EGE_UNIVERSITY,
				FacebookTestVocabulary.NAME_OF_EGE_UNIVERSITY);

		String[] eduMajors1 = { FacebookTestVocabulary.COMPUTER_ENGINEERING };

		Resource eduIndv1 = createEducationIndividual(
				FacebookTestVocabulary.EDUCATION_URI_1_OF_BURAK_YONYUL,
				eduMajors1,
				FacebookTestVocabulary.EDU_GRAD_DATE_1_OF_BURAK_YONYUL,
				eduOrgEgeUniIndv);

		String[] eduMajors2 = { FacebookTestVocabulary.COMPUTER_ENGINEERING };

		Resource eduIndv2 = createEducationIndividual(
				FacebookTestVocabulary.EDUCATION_URI_2_OF_BURAK_YONYUL,
				eduMajors2,
				FacebookTestVocabulary.EDU_GRAD_DATE_2_OF_BURAK_YONYUL,
				eduOrgEgeUniIndv);
		createObjectProperty(burakYonyulIndv,
				CommonOntologyVocabulary.CV_EDU_PRP_URI, eduIndv1);
		createObjectProperty(burakYonyulIndv,
				CommonOntologyVocabulary.CV_EDU_PRP_URI, eduIndv2);

		Resource eventIndv1 = createEventIndividual(
				FacebookTestVocabulary.EVENT_URI_OF_KAC_BABA_KAC,
				FacebookTestVocabulary.NAME_OF_KAC_BABA_KAC);
		Resource eventIndv2 = createEventIndividual(
				FacebookTestVocabulary.EVENT_URI_OF_DEVELOPING_SOSEP,
				FacebookTestVocabulary.NAME_OF_DEVELOPING_SOSEP);

		createObjectProperty(burakYonyulIndv,
				FacebookOntologyVocabulary.JOINS_PRP_URI, eventIndv1);
		createObjectProperty(burakYonyulIndv,
				FacebookOntologyVocabulary.JOINS_PRP_URI, eventIndv2);

		return burakYonyulIndv;
	}

	/**
	 * creating Facebook test data for Burak Yönyül
	 * 
	 * @return
	 */
	private Resource createZiyaAkar() {
		// creating basic profile properties
		Resource ziyaAkarIndv = getOntModel().createResource(
				CommonTestVocabulary.URI_ZIYA_AKAR, FOAF.Person);

		createDatatypeProperty(
				ziyaAkarIndv,
				FOAF.name.getURI(),
				ResourceFactory
						.createPlainLiteral(FacebookTestVocabulary.NAME_OF_ZIYA_AKAR));
		createDatatypeProperty(ziyaAkarIndv,
				CommonOntologyVocabulary.SIOC_EMAIL_URI,
				FacebookTestVocabulary.EMAIL_OF_ZIYA_AKAR);
		createDatatypeProperty(ziyaAkarIndv, FOAF.page.getURI(),
				FacebookTestVocabulary.PAGE_OF_ZIYA_AKAR);
		createDatatypeProperty(ziyaAkarIndv, FOAF.gender.getURI(),
				CommonTestVocabulary.MALE_GENDER);
		createDatatypeProperty(ziyaAkarIndv, FOAF.depiction.getURI(),
				FacebookTestVocabulary.DEPICTION_OF_ZIYA_AKAR);
		createDatatypeProperty(ziyaAkarIndv, RDFS.label.getURI(),
				FacebookTestVocabulary.LABEL_OF_ZIYA_AKAR);

		// creating like properties
		Resource leonIndv = createLikeIndividual(
				FacebookTestVocabulary.LEON_URI,
				FacebookTestVocabulary.NAME_OF_LEON,
				CommonOntologyVocabulary.MOVIE_RSC);
		Resource snatchIndv = createLikeIndividual(
				FacebookTestVocabulary.SNATCH_URI,
				FacebookTestVocabulary.NAME_OF_SNATCH,
				CommonOntologyVocabulary.MOVIE_RSC);
		Resource jeuxIndv = createLikeIndividual(
				FacebookTestVocabulary.JEUX_URI,
				FacebookTestVocabulary.NAME_OF_JEUX,
				CommonOntologyVocabulary.MOVIE_RSC);
		Resource clintEastwoodIndv = createLikeIndividual(
				FacebookTestVocabulary.CLINT_EASTWOOD_URI,
				FacebookTestVocabulary.NAME_OF_CLINT_EASTWOOD,
				CommonOntologyVocabulary.MOVIE_RSC);
		Resource benHurIndv = createLikeIndividual(
				FacebookTestVocabulary.BEN_HUR_URI,
				FacebookTestVocabulary.NAME_OF_BEN_HUR,
				CommonOntologyVocabulary.MOVIE_RSC);
		Resource truvaIndv = createLikeIndividual(
				FacebookTestVocabulary.TRUVA_URI,
				FacebookTestVocabulary.NAME_OF_TRUVA,
				CommonOntologyVocabulary.MOVIE_RSC);
		Resource bruceLeeIndv = createLikeIndividual(
				FacebookTestVocabulary.BRUCE_LEE_URI,
				FacebookTestVocabulary.NAME_OF_BRUCE_LEE,
				CommonOntologyVocabulary.MOVIE_RSC);

		Resource bonJoviIndv = createLikeIndividual(
				FacebookTestVocabulary.BON_JOVI_URI,
				FacebookTestVocabulary.NAME_OF_BON_JOVI,
				CommonOntologyVocabulary.MUSIC_RSC);
		Resource dokkenIndv = createLikeIndividual(
				FacebookTestVocabulary.DOKKEN_URI,
				FacebookTestVocabulary.NAME_OF_DOKKEN,
				CommonOntologyVocabulary.MUSIC_RSC);
		Resource headPinsIndv = createLikeIndividual(
				FacebookTestVocabulary.HEAD_PINS_URI,
				FacebookTestVocabulary.NAME_OF_HEAD_PINS,
				CommonOntologyVocabulary.MUSIC_RSC);
		Resource ennioMorriconeIndv = createLikeIndividual(
				FacebookTestVocabulary.ENNIO_MORRICONE_URI,
				FacebookTestVocabulary.NAME_OF_ENNIO_MORRICONE,
				CommonOntologyVocabulary.MUSIC_RSC);
		Resource skidRowIndv = createLikeIndividual(
				FacebookTestVocabulary.SKID_ROW_URI,
				FacebookTestVocabulary.NAME_OF_SKID_ROW,
				CommonOntologyVocabulary.MUSIC_RSC);
		Resource barisMancoIndv = createLikeIndividual(
				FacebookTestVocabulary.BARIS_MANCO_URI,
				FacebookTestVocabulary.NAME_OF_BARIS_MANCO,
				CommonOntologyVocabulary.MUSIC_RSC);
		Resource defLeppardIndv = createLikeIndividual(
				FacebookTestVocabulary.DEF_LEPPARD_URI,
				FacebookTestVocabulary.NAME_OF_DEF_LEPPARD,
				CommonOntologyVocabulary.MUSIC_RSC);
		Resource blackSabbathIndv = createLikeIndividual(
				FacebookTestVocabulary.BLACK_SABBATH_URI,
				FacebookTestVocabulary.NAME_OF_BLACK_SABBATH,
				CommonOntologyVocabulary.MUSIC_RSC);
		Resource manowarIndv = createLikeIndividual(
				FacebookTestVocabulary.MANOWAR_URI,
				FacebookTestVocabulary.NAME_OF_MANOWAR,
				CommonOntologyVocabulary.MUSIC_RSC);

		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				leonIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				snatchIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				jeuxIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				clintEastwoodIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				benHurIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				truvaIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				bruceLeeIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				headPinsIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				ennioMorriconeIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				skidRowIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				barisMancoIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				defLeppardIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				blackSabbathIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				manowarIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				bonJoviIndv);
		createObjectProperty(ziyaAkarIndv, FacebookOntologyVocabulary.LIKES,
				dokkenIndv);

		// creating account property
		Resource accountIndv = createAccountIndividual(FacebookTestVocabulary.ACCOUNT_URI_OF_ZIYA_AKAR);
		createObjectProperty(ziyaAkarIndv,
				CommonOntologyVocabulary.ACCOUNT_PRP_URI, accountIndv);
		createObjectProperty(accountIndv, FOAF.accountServiceHomepage.getURI(),
				FacebookOntologyVocabulary.FACEBOOK_HOMEPAGE_RSC);
		createDatatypeProperty(accountIndv, FOAF.accountName.getURI(),
				FacebookTestVocabulary.FACEBOOK_ID_OF_ZIYA_AKAR);

		// creating location property
		Resource izmirRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_IZMIR_URI);
		createObjectProperty(ziyaAkarIndv,
				CommonOntologyVocabulary.LOCATION_URI, izmirRsc);
		Resource bursaRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_BURSA_URI);
		// creating hometown property
		createObjectProperty(ziyaAkarIndv,
				CommonOntologyVocabulary.HOMETOWN_URI, bursaRsc);

		// creating education properties
		Resource eduOrgEgeUniIndv = createEducationalOrgIndv(
				FacebookTestVocabulary.URI_OF_EGE_UNIVERSITY,
				FacebookTestVocabulary.NAME_OF_EGE_UNIVERSITY);

		Resource eduOrgCelMehIndv = createEducationalOrgIndv(
				FacebookTestVocabulary.URI_OF_CELEBI_MEHMET_LISESI,
				FacebookTestVocabulary.NAME_OF_CELEBI_MEHMET_LISESI);

		Resource eduIndv1 = createEducationIndividual(
				FacebookTestVocabulary.EDUCATION_URI_1_OF_ZIYA_AKAR, null,
				null, eduOrgCelMehIndv);

		String[] eduMajors2 = { FacebookTestVocabulary.COMPUTER_ENGINEERING };

		Resource eduIndv2 = createEducationIndividual(
				FacebookTestVocabulary.EDUCATION_URI_2_OF_ZIYA_AKAR,
				eduMajors2,
				FacebookTestVocabulary.EDU_GRAD_DATE_2_OF_ZIYA_AKAR,
				eduOrgEgeUniIndv);

		Resource eduIndv3 = createEducationIndividual(
				FacebookTestVocabulary.EDUCATION_URI_3_OF_ZIYA_AKAR, null,
				null, eduOrgEgeUniIndv);

		createObjectProperty(ziyaAkarIndv,
				CommonOntologyVocabulary.CV_EDU_PRP_URI, eduIndv1);
		createObjectProperty(ziyaAkarIndv,
				CommonOntologyVocabulary.CV_EDU_PRP_URI, eduIndv2);
		createObjectProperty(ziyaAkarIndv,
				CommonOntologyVocabulary.CV_EDU_PRP_URI, eduIndv3);

		return ziyaAkarIndv;
	}

	/**
	 * Creating Facebook test data for Erdem Eser Ekinci
	 * 
	 * @return
	 */
	private Resource createErdemEserEkinci() {
		// creating basic profile properties
		Resource erdemEserEkinciIndv = getOntModel().createResource(
				CommonTestVocabulary.URI_ERDEM_ESER_EKINCI, FOAF.Person);

		createDatatypeProperty(
				erdemEserEkinciIndv,
				FOAF.name.getURI(),
				ResourceFactory
						.createPlainLiteral(FacebookTestVocabulary.NAME_OF_ERDEM_ESER_EKINCI));
		createDatatypeProperty(erdemEserEkinciIndv,
				CommonOntologyVocabulary.SIOC_EMAIL_URI,
				FacebookTestVocabulary.EMAIL_OF_ERDEM_ESER_EKINCI);
		createDatatypeProperty(erdemEserEkinciIndv, FOAF.page.getURI(),
				FacebookTestVocabulary.PAGE_OF_ERDEM_ESER_EKINCI);
		createDatatypeProperty(erdemEserEkinciIndv, FOAF.gender.getURI(),
				CommonTestVocabulary.MALE_GENDER);
		createDatatypeProperty(erdemEserEkinciIndv, FOAF.depiction.getURI(),
				FacebookTestVocabulary.DEPICTION_OF_ERDEM_ESER_EKINCI);
		createDatatypeProperty(erdemEserEkinciIndv, RDFS.label.getURI(),
				FacebookTestVocabulary.LABEL_OF_ERDEM_ESER_EKINCI);

		// creating favourite team properties
		Resource galatasarayIndv = createTeamIndv(
				FacebookTestVocabulary.GALATASARAY_URI,
				FacebookTestVocabulary.NAME_OF_GALATASARAY);
		createObjectProperty(erdemEserEkinciIndv,
				FacebookOntologyVocabulary.FAVOURITE_TEAM_PRP_URI,
				galatasarayIndv);

		// creating like properties
		Resource cedricIndv = createLikeIndividual(
				FacebookTestVocabulary.CEDRIC_URI,
				FacebookTestVocabulary.NAME_OF_CEDRIC,
				CommonOntologyVocabulary.MOVIE_RSC);
		createObjectProperty(erdemEserEkinciIndv,
				FacebookOntologyVocabulary.LIKES, cedricIndv);

		// creating account property
		Resource accountIndv = createAccountIndividual(FacebookTestVocabulary.ACCOUNT_URI_OF_ERDEM_ESER_EKINCI);
		createObjectProperty(accountIndv, FOAF.accountServiceHomepage.getURI(),
				FacebookOntologyVocabulary.FACEBOOK_HOMEPAGE_RSC);
		createDatatypeProperty(accountIndv, FOAF.accountName.getURI(),
				FacebookTestVocabulary.FACEBOOK_ID_OF_ERDEM_ESER_EKINCI);

		createObjectProperty(erdemEserEkinciIndv,
				CommonOntologyVocabulary.ACCOUNT_PRP_URI, accountIndv);
		// creating location property
		Resource izmirRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_IZMIR_URI);
		createObjectProperty(erdemEserEkinciIndv,
				CommonOntologyVocabulary.LOCATION_URI, izmirRsc);
		// creating hometown property
		Resource kayseriRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_KAYSERI_URI);
		createObjectProperty(erdemEserEkinciIndv,
				CommonOntologyVocabulary.HOMETOWN_URI, kayseriRsc);

		// creating education properties
		Resource eduOrgEgeUniIndv = createEducationalOrgIndv(
				FacebookTestVocabulary.URI_OF_EGE_UNIVERSITY,
				FacebookTestVocabulary.NAME_OF_EGE_UNIVERSITY);

		Resource eduOrgKayseriLiseIndv = createEducationalOrgIndv(
				FacebookTestVocabulary.URI_OF_KAYSERI_LISESI,
				FacebookTestVocabulary.NAME_OF_KAYSERI_LISESI);

		String[] eduMajors1 = { FacebookTestVocabulary.COMPUTER_ENGINEERING };

		Resource eduIndv1 = createEducationIndividual(
				FacebookTestVocabulary.EDUCATION_URI_1_OF_ERDEM_ESER_EKINCI,
				eduMajors1,
				FacebookTestVocabulary.EDU_GRAD_DATE_1_OF_ERDEM_ESER_EKINCI,
				eduOrgEgeUniIndv);

		Resource eduIndv2 = createEducationIndividual(
				FacebookTestVocabulary.EDUCATION_URI_2_OF_ERDEM_ESER_EKINCI,
				null,
				FacebookTestVocabulary.EDU_GRAD_DATE_2_OF_ERDEM_ESER_EKINCI,
				eduOrgKayseriLiseIndv);

		String[] eduMajors2 = { FacebookTestVocabulary.COMPUTER_ENGINEERING,
				FacebookTestVocabulary.PHD_COMPUTER_ENGINEERING };
		Resource eduIndv3 = createEducationIndividual(
				FacebookTestVocabulary.EDUCATION_URI_3_OF_ERDEM_ESER_EKINCI,
				eduMajors2,
				FacebookTestVocabulary.EDU_GRAD_DATE_3_OF_ERDEM_ESER_EKINCI,
				eduOrgEgeUniIndv);

		createObjectProperty(erdemEserEkinciIndv,
				CommonOntologyVocabulary.CV_EDU_PRP_URI, eduIndv1);
		createObjectProperty(erdemEserEkinciIndv,
				CommonOntologyVocabulary.CV_EDU_PRP_URI, eduIndv2);
		createObjectProperty(erdemEserEkinciIndv,
				CommonOntologyVocabulary.CV_EDU_PRP_URI, eduIndv3);

		return erdemEserEkinciIndv;
	}

	/**
	 * Creating Facebook test data for Erdem Eser Ekinci
	 * 
	 * @return
	 */
	private Resource createTayfunGokmenHalac() {
		// creating basic profile properties
		Resource tayfunGokmenHalacIndv = getOntModel().createResource(
				CommonTestVocabulary.URI_TAYFUN_GOKMEN_HALAC, FOAF.Person);

		createDatatypeProperty(
				tayfunGokmenHalacIndv,
				FOAF.name.getURI(),
				ResourceFactory
						.createPlainLiteral(FacebookTestVocabulary.NAME_OF_TAYFUN_GOKMEN_HALAC));
		createDatatypeProperty(tayfunGokmenHalacIndv,
				CommonOntologyVocabulary.SIOC_EMAIL_URI,
				FacebookTestVocabulary.EMAIL_OF_TAYFUN_GOKMEN_HALAC);
		createDatatypeProperty(tayfunGokmenHalacIndv, FOAF.page.getURI(),
				FacebookTestVocabulary.PAGE_OF_TAYFUN_GOKMEN_HALAC);
		createDatatypeProperty(tayfunGokmenHalacIndv, FOAF.gender.getURI(),
				CommonTestVocabulary.MALE_GENDER);
		createDatatypeProperty(tayfunGokmenHalacIndv, FOAF.depiction.getURI(),
				FacebookTestVocabulary.DEPICTION_OF_TAYFUN_GOKMEN_HALAC);
		createDatatypeProperty(tayfunGokmenHalacIndv, RDFS.label.getURI(),
				FacebookTestVocabulary.LABEL_OF_TAYFUN_GOKMEN_HALAC);

		// creating favourite team properties
		Resource altayIndv = createTeamIndv(FacebookTestVocabulary.ALTAY_URI,
				FacebookTestVocabulary.NAME_OF_ALTAY);
		createObjectProperty(tayfunGokmenHalacIndv,
				FacebookOntologyVocabulary.FAVOURITE_TEAM_PRP_URI, altayIndv);

		// creating account property
		Resource accountIndv = createAccountIndividual(FacebookTestVocabulary.ACCOUNT_URI_OF_TAYFUN_GOKMEN_HALAC);
		createObjectProperty(accountIndv, FOAF.accountServiceHomepage.getURI(),
				FacebookOntologyVocabulary.FACEBOOK_HOMEPAGE_RSC);
		createDatatypeProperty(accountIndv, FOAF.accountName.getURI(),
				FacebookTestVocabulary.FACEBOOK_ID_OF_TAYFUN_GOKMEN_HALAC);

		createObjectProperty(tayfunGokmenHalacIndv,
				CommonOntologyVocabulary.ACCOUNT_PRP_URI, accountIndv);
		// creating location property
		Resource izmirRsc = ResourceFactory
				.createResource(FoursquareTestVocabulary.DBPEDIA_IZMIR_URI);
		createObjectProperty(tayfunGokmenHalacIndv,
				CommonOntologyVocabulary.LOCATION_URI, izmirRsc);
		// creating hometown property
		createObjectProperty(tayfunGokmenHalacIndv,
				CommonOntologyVocabulary.HOMETOWN_URI, izmirRsc);

		// creating education properties
		Resource eduOrgEgeUniIndv = createEducationalOrgIndv(
				FacebookTestVocabulary.URI_OF_EGE_UNIVERSITY,
				FacebookTestVocabulary.NAME_OF_EGE_UNIVERSITY);

		String[] eduMajors1 = { FacebookTestVocabulary.COMPUTER_ENGINEERING };

		Resource eduIndv1 = createEducationIndividual(
				FacebookTestVocabulary.EDUCATION_URI_1_OF_TAYFUN_GOKMEN_HALAC,
				eduMajors1,
				FacebookTestVocabulary.EDU_GRAD_DATE_1_OF_TAYFUN_GOKMEN_HALAC,
				eduOrgEgeUniIndv);

		String[] eduMajors2 = { FacebookTestVocabulary.COMPUTER_ENGINEERING };
		Resource eduIndv2 = createEducationIndividual(
				FacebookTestVocabulary.EDUCATION_URI_2_OF_TAYFUN_GOKMEN_HALAC,
				eduMajors2,
				FacebookTestVocabulary.EDU_GRAD_DATE_2_OF_TAYFUN_GOKMEN_HALAC,
				eduOrgEgeUniIndv);

		String[] eduMajors3 = { FacebookTestVocabulary.COMPUTER_ENGINEERING,
				FacebookTestVocabulary.PHD_COMPUTER_ENGINEERING };
		Resource eduIndv3 = createEducationIndividual(
				FacebookTestVocabulary.EDUCATION_URI_3_OF_TAYFUN_GOKMEN_HALAC,
				eduMajors3,
				FacebookTestVocabulary.EDU_GRAD_DATE_3_OF_TAYFUN_GOKMEN_HALAC,
				eduOrgEgeUniIndv);

		createObjectProperty(tayfunGokmenHalacIndv,
				CommonOntologyVocabulary.CV_EDU_PRP_URI, eduIndv1);
		createObjectProperty(tayfunGokmenHalacIndv,
				CommonOntologyVocabulary.CV_EDU_PRP_URI, eduIndv2);
		createObjectProperty(tayfunGokmenHalacIndv,
				CommonOntologyVocabulary.CV_EDU_PRP_URI, eduIndv3);

		Resource eventIndv1 = createEventIndividual(
				FacebookTestVocabulary.EVENT_URI_OF_DEVELOPING_SOSEP,
				FacebookTestVocabulary.NAME_OF_DEVELOPING_SOSEP);
		createObjectProperty(tayfunGokmenHalacIndv,
				FacebookOntologyVocabulary.JOINS_PRP_URI, eventIndv1);

		return tayfunGokmenHalacIndv;
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

	/**
	 * This method creates an Team {@link Resource} type individual with given
	 * URI and name
	 * 
	 * @param teamURI
	 *            URI of team individual
	 * @param teamName
	 *            name property value of team individual
	 * @return
	 */
	private Resource createTeamIndv(String teamURI, String teamName) {
		Resource teamIndv = getOntModel().createResource(teamURI,
				FacebookOntologyVocabulary.TEAM_RSC);
		createDatatypeProperty(teamIndv, FOAF.name.getURI(), teamName);
		return teamIndv;
	}

	/**
	 * This method creates an Event {@link Resource} type individual wi,th given
	 * URI and name
	 * 
	 * @param eventURI
	 *            URI of event {@link Individual} instance
	 * @param eventName
	 *            name property value of event {@link Individual} instance
	 * @return
	 */
	private Resource createEventIndividual(String eventURI, String eventName) {
		Resource eventIndv = getOntModel().createResource(eventURI,
				FacebookOntologyVocabulary.EVENT_RSC);
		createDatatypeProperty(eventIndv, FOAF.name.getURI(), eventName);
		return eventIndv;
	}

	/**
	 * This method creates an like individual which can be Movie, Music or Book
	 * type, with given URI name and {@link Resource} instance
	 * 
	 * @param likeUri
	 *            URI of like individual
	 * @param likeName
	 *            name property value of like individual
	 * @param likeTypeRsc
	 *            Movie, Music or Book {@link Resource} type
	 * @return
	 */
	private Resource createLikeIndividual(String likeUri, String likeName,
			Resource likeTypeRsc) {
		Resource likeIndv = getOntModel().createResource(likeUri, likeTypeRsc);
		createDatatypeProperty(likeIndv, FOAF.name.getURI(), likeName);
		return likeIndv;
	}

	/**
	 * This method creates an individual with EducationalOrganization
	 * {@link Resource} type, using given URI and name
	 * 
	 * @param eduOrgUri
	 *            URI of educational organization individual
	 * @param eduOrgName
	 *            name property of educational organization individual
	 * @return
	 */
	private Resource createEducationalOrgIndv(String eduOrgUri,
			String eduOrgName) {
		Resource eduOrgIndv = getOntModel().createResource(eduOrgUri,
				CommonOntologyVocabulary.EDUCATIONAL_ORG_RSC);
		createDatatypeProperty(eduOrgIndv, FOAF.name.getURI(), eduOrgName);
		return eduOrgIndv;
	}

	/**
	 * This method creates and Education individual with Education
	 * {@link Resource} type using given uri, education majors, graduation date
	 * and educational organization individual.
	 * 
	 * @param eduUri
	 *            URI of Education
	 * @param eduMajors
	 *            education major values (education type or name)
	 * @param gradDate
	 *            graduation date
	 * @param eduOrgIndv
	 *            educational organization {@link Individual} value
	 * @return
	 */
	private Resource createEducationIndividual(String eduUri,
			String[] eduMajors, String gradDate, Resource eduOrgIndv) {
		Resource eduIndv = getOntModel().createResource(eduUri,
				CommonOntologyVocabulary.EDUCATION_RSC);
		// creating education major properties
		if (eduMajors != null) {
			for (String eduMajor : eduMajors) {
				createDatatypeProperty(eduIndv,
						CommonOntologyVocabulary.EDU_MAJOR_PRP_URI, eduMajor);
			}
		}
		// creating graduation date property
		createDatatypeProperty(eduIndv,
				CommonOntologyVocabulary.GRAD_DATE_PRP_URI, gradDate);
		// creating studied school property
		createObjectProperty(eduIndv,
				CommonOntologyVocabulary.CV_STUDIED_IN_PRP_URI, eduOrgIndv);
		return eduIndv;
	}

	/**
	 * Create an ontology model containing facebook individuals.
	 */
	public FacebookDataCreator() {
		getOntModel().createResource(
				FacebookTestVocabulary.FACEBOOK_ONTOLOGY_URI, OWL.Ontology);
		createIndividuals();
	}

	/**
	 * Creates individual of Burak Yönyül, Erdem Eser Ekinci, Tayfun Gökmen
	 * Halaç, and Ziya Akar
	 */
	private void createIndividuals() {
		Resource burakYonyul = createBurakYonyul();
		Resource erdemEserEkinci = createErdemEserEkinci();
		Resource tayfunGokmenHalac = createTayfunGokmenHalac();
		Resource ziyaAkar = createZiyaAkar();

		createFoafKnowsProperties(burakYonyul, erdemEserEkinci,
				tayfunGokmenHalac, ziyaAkar);
	}

	public Model getOntModel() {
		return getIndividualCreator().getModel();
	}

	private IndividualCreator getIndividualCreator() {
		if (indvCreator == null) {
			indvCreator = new FacebookIndividualCreator(
					CommonTestVocabulary.BASE_URI);
		}
		return indvCreator;
	}

	private void createDatatypeProperty(Resource subjectIndv, String propURI,
			Object objectValue) {
		getIndividualCreator()
				.createProperty(subjectIndv, propURI, objectValue);
	}

	private void createObjectProperty(Resource subjectIndv, String uri,
			Resource objectIndv) {
		getIndividualCreator().createProperty(subjectIndv, uri, objectIndv);
	}
}
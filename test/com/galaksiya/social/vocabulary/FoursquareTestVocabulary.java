package com.galaksiya.social.vocabulary;

import com.galaksiya.social.fetcher.FoursquareIndividualCreator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class FoursquareTestVocabulary {

	public static final String FACEBOOK_ID_OF_TAYFUN = "718598389";
	public static final String FOURSQUARE_TRIPLE_DB = "FoursquareTripleDB";
	public static final String FOURSQUARE_GRAPH_URI = "http://foursquareindividualmodel.org";
	public static final String ONTOLOGY_URI = "http://www.trafficontology.org#";
	public static final String CAR = ONTOLOGY_URI + "Car";
	public static final String BUS = ONTOLOGY_URI + "Bus";
	public static final String TRUCK = ONTOLOGY_URI + "Truck";
	public static final String BICYCLE = ONTOLOGY_URI + "Bicycle";
	public static final String VAN = ONTOLOGY_URI + "Van";
	public static final String WHEEL_COUNT = ONTOLOGY_URI + "wheelCount";
	public static final String VEHICLE_MODEL = ONTOLOGY_URI + "model";
	public static final String VEHICLE_WEIGHT = ONTOLOGY_URI + "weight";
	public static final String SEAT_COUNT = ONTOLOGY_URI + "seatCount";
	public static final String FACEBOOK_TRIPLE_DB = "FacebookTripleDB";
	public static final String USER_NAME_TEXT = "USER_NAME";
	public static final String PASSWORD_TEXT = "PASSWORD";
	public static final String[] MAP_FIELDS = { USER_NAME_TEXT, PASSWORD_TEXT };
	public static final String ACCESS_TOKEN = "25ZYINAT3EWKM2Y3JPAJYCCIXBM0HY5LE5ZAREPZQOSC3DA1";
	public static final String ACCESS_TOKEN_COPIED = "JQ2UA1N5XQCFM2TNYQGHPSMD4R51E1YXPTAT04XWDOSJ1UYE";
	public static final String PERSON_NAME = "Amelia Verboom";
	public static final String EMAIL_VALUE = "ameliaverboom@gmail.com";
	public static final String DEPICTION_URI = "https://irs2.4sqi.net/img/user/512x512/-1T31K0SUEZZO2X5K.jpg";
	public static final String GENDER_VALUE = "female";
	public static final int BADGE_COUNT_VALUE = 1;
	public static final int FOLLOWING_COUNT_VALUE = 0;
	public static final boolean PINGS_VALUE = false;
	public static final String PHONE_VALUE = "";
	public static final String FACEBOOK_VALUE = "100008264590021";
	public static final String TWITTER_VALUE = null;

	public static final String[] VENUE_NAMES = { "Central Park", "Maialino",
			"Birch Coffee", "California Academy of Sciences" };

	public static final String[] VENUE_CATEGORIES = { "Italian Restaurant",
			"Coffee Shop", "Science Museum" };
	public static final String EXPECTED_REDIRECT_URL = "https://foursquare.com/oauth2/authenticate?client_id=SCD1CYZMLEIPCIOFHSXKDLAUTKCRDM1BHBB5JMEUVUJAGBU2&response_type=code&redirect_uri=http://23.23.193.63/project/request/foursquareauth&v=20140321";
	public static final String FOURSQUARE_TEST_DB = "FoursquareTestDB";
	public static final String NAME_OF_BURAK_YONYUL = "Burak Yönyül";
	public static final String EMAIL_OF_BURAK_YONYUL = "burakyonyul@hotmail.com";
	public static final String PAGE_OF_BURAK_YONYUL = "https://foursquare.com/user/19209229";
	public static final String DEPICTION_OF_BURAK_YONYUL = "https://is0.4sqi.net/userpix_thumbs/WPJHYQ3ZBFI35C4P.jpg";
	public static final String LABEL_OF_BURAK_YONYUL = NAME_OF_BURAK_YONYUL;
	public static final String SELF_RELATIONSHIP = "self";
	private static final String FOURSQUARE_ACCOUNT_CONSTANT = "foursquareAccountOf";
	public static final String ACCOUNT_URI_OF_BURAK_YONYUL = CommonTestVocabulary.BASE_URI
			+ FOURSQUARE_ACCOUNT_CONSTANT + "BurakYonyul";
	public static final String CONTACT_URI_OF_BURAK_YONYUL = CommonTestVocabulary.BASE_URI
			+ "contactOfBurakyonyul";
	public static final String PHONE_OF_BURAK_YONYUL = "05438852004";
	private static final String CATEGORY_PATTERN = "Category";
	public static final String CAFE_CATEGORY_URI = CommonTestVocabulary.BASE_URI
			+ CATEGORY_PATTERN + FoursquareIndividualCreator.UNDERLINE + "Café";
	public static final String CAFE_CATEGORY_NAME = "Café";
	public static final String CAFE_CATEGORY_PLURAL_NAME = "Cafés";
	public static final String CAFE_CATEGORY_ICON = "https://foursquare.com/img/categories/food/cafe.png";
	public static final String COLLEGE_LAB_CATEGORY_URI = CommonTestVocabulary.BASE_URI
			+ CATEGORY_PATTERN
			+ FoursquareIndividualCreator.UNDERLINE
			+ "College_Lab";
	public static final String COLLEGE_LAB_CATEGORY_NAME = "College Lab";
	public static final String COLLEGE_LAB_CATEGORY_PLURAL_NAME = "College Labs";
	public static final String COLLEGE_LAB_CATEGORY_ICON = "https://foursquare.com/img/categories/education/default.png";
	public static final String GARDEN_CATEGORY_URI = CommonTestVocabulary.BASE_URI
			+ CATEGORY_PATTERN
			+ FoursquareIndividualCreator.UNDERLINE
			+ "Garden";
	public static final String GARDEN_CATEGORY_NAME = "Garden";
	public static final String GARDEN_CATEGORY_PLURAL_NAME = "Gardens";
	public static final String GARDEN_CATEGORY_ICON = "https://foursquare.com/img/categories/parks_outdoors/garden.png";
	public static final String BUS_LINE_CATEGORY_URI = CommonTestVocabulary.BASE_URI
			+ CATEGORY_PATTERN
			+ FoursquareIndividualCreator.UNDERLINE
			+ "Bus_Line";
	public static final String BUS_LINE_CATEGORY_NAME = "Bus Line";
	public static final String BUS_LINE_CATEGORY_PLURAL_NAME = "Bus Lines";
	public static final String BUS_LINE_CATEGORY_ICON = "https://foursquare.com/img/categories/travel/busstation.png";
	public static final String LOCATION_URI_OF_SEAGENT = CommonTestVocabulary.BASE_URI
			+ "locationOfSeagent";
	public static final double LAT_OF_SEAGENT = 38.457850548288924;
	public static final double LNG_OF_SEAGENT = 27.213344643627305;
	public static final String VENUE_URI_OF_SEAGENT = CommonTestVocabulary.BASE_URI
			+ "venue4e7866e862e1b76aa3a1136c";
	public static final String NAME_OF_SEAGENT = "SEAGENT Multi-Agent Systems Laboratory";
	public static final String CHECKIN_URI_1_OF_BURAK_YONYUL = CommonTestVocabulary.BASE_URI
			+ "checkin1ofBurakYonyul";
	public static final String TIME_ZONE_ISTANBUL = "Europe/Istanbul";
	public static final String LOCATION_URI_OF_BILMUH_CIMLER = CommonTestVocabulary.BASE_URI
			+ "locationOfBilmuhCimler";
	public static final double LAT_OF_BILMUH_CIMLER = 38.458476768604726;
	public static final double LNG_OF_BILMUH_CIMLER = 27.213091978096223;
	public static final String VENUE_URI_OF_BILMUH_CIMLER = CommonTestVocabulary.BASE_URI
			+ "venue4df086aad22d43368717cb93";
	public static final String NAME_OF_BILMUH_CIMLER = "BilMüh Çimler";
	public static final String LOCATION_URI_OF_525 = CommonTestVocabulary.BASE_URI
			+ "locationOf525";
	public static final double LAT_OF_525 = 38.4584742378648;
	public static final double LNG_OF_525 = 27.21366584300995;
	public static final String VENUE_URI_OF_525 = CommonTestVocabulary.BASE_URI
			+ "venue4df086aad22d43368717cb93";
	public static final String NAME_OF_525 = "525";
	public static final String ADDRESS_OF_525 = "Bornova Metro - E.Ü. Kampüs";
	public static final String LOCATION_URI_OF_OZDILEK_CAFE = CommonTestVocabulary.BASE_URI
			+ "locationOfOzdilekCafe";
	public static final double LAT_OF_OZDILEK_CAFE = 38.41024335951834;
	public static final double LNG_OF_OZDILEK_CAFE = 27.031373548393262;
	public static final String ADDRESS_OF_OZDILEK_CAFE = "İnciraltı Cad. No: 67 İnciraltı Balçova";
	public static final String VENUE_URI_OF_OZDILEK_CAFE = CommonTestVocabulary.BASE_URI
			+ "venue4edf69e34690cf9d22a88480";
	public static final String NAME_OF_OZDILEK_CAFE = "Özdilek Cafe & Restaurant";
	public static final String STREET_OF_OZDILEK_CAFE = "İnciralti";
	public static final String CHECKIN_URI_2_OF_BURAK_YONYUL = CommonTestVocabulary.BASE_URI
			+ "checkin2ofBurakYonyul";
	public static final String CHECKIN_URI_3_OF_BURAK_YONYUL = CommonTestVocabulary.BASE_URI
			+ "checkin3ofBurakYonyul";
	public static final String CHECKIN_URI_4_OF_BURAK_YONYUL = CommonTestVocabulary.BASE_URI
			+ "checkin4ofBurakYonyul";
	public static final String NAME_OF_ERDEM_EKINCI = "Erdem Ekinci";
	public static final String EMAIL_OF_ERDEM_EKINCI = "erdemeserekinci@gmail.com";
	public static final String PAGE_OF_ERDEM_EKINCI = "https://foursquare.com/user/12561251";
	public static final String DEPICTION_OF_ERDEM_EKINCI = "https://is1.4sqi.net/userpix_thumbs/Y15WFDITMOAPXPSZ.jpg";
	public static final String LABEL_OF_ERDEM_EKINCI = NAME_OF_ERDEM_EKINCI;
	public static final String CONTACT_URI_OF_ERDEM_EKINCI = CommonTestVocabulary.BASE_URI
			+ "contactOfErdemEkinci";
	public static final String PHONE_OF_ERDEM_EKINCI = "05326460868";
	public static final String FACEBOOK_OF_ERDEM_EKINCI = "544008025";
	public static final String CHECKIN_URI_1_OF_ERDEM_EKINCI = CommonTestVocabulary.BASE_URI
			+ "checkin1ofErdemEkinci";
	public static final String CHECKIN_URI_2_OF_ERDEM_EKINCI = CommonTestVocabulary.BASE_URI
			+ "checkin2ofErdemEkinci";
	public static final String CHECKIN_URI_3_OF_ERDEM_EKINCI = CommonTestVocabulary.BASE_URI
			+ "checkin3ofErdemEkinci";
	public static final String CHECKIN_URI_4_OF_ERDEM_EKINCI = CommonTestVocabulary.BASE_URI
			+ "checkin4ofErdemEkinci";
	public static final String ACCOUNT_URI_OF_ERDEM_EKINCI = CommonTestVocabulary.BASE_URI
			+ FOURSQUARE_ACCOUNT_CONSTANT + "ErdemEkinci";
	public static final String BAR_CATEGORY_URI = CommonTestVocabulary.BASE_URI
			+ CATEGORY_PATTERN + FoursquareIndividualCreator.UNDERLINE + "Bar";
	public static final String BAR_CATEGORY_NAME = "Bar";
	public static final String BAR_CATEGORY_PLURAL_NAME = "Bars";
	public static final String BAR_CATEGORY_ICON = "https://foursquare.com/img/categories/nightlife/bar.png";
	public static final String OFFICE_CATEGORY_URI = CommonTestVocabulary.BASE_URI
			+ CATEGORY_PATTERN
			+ FoursquareIndividualCreator.UNDERLINE
			+ "Office";
	public static final String OFFICE_CATEGORY_NAME = "Office";
	public static final String OFFICE_CATEGORY_PLURAL_NAME = "Offices";
	public static final String OFFICE_CATEGORY_ICON = "https://foursquare.com/img/categories/building/default.png";
	public static final String CONTACT_URI_OF_POLINAS = CommonTestVocabulary.BASE_URI
			+ "contactOfPolinas";
	public static final String PHONE_OF_POLINAS = "03122405650";
	public static final double LAT_OF_POLINAS = 38.619841979545484;
	public static final double LNG_OF_POLINAS = 27.36787622553547;
	public static final String LOCATION_URI_OF_POLINAS = CommonTestVocabulary.BASE_URI
			+ "locationOfPolinas";
	public static final String LOCATION_URI_OF_DUROCK = CommonTestVocabulary.BASE_URI
			+ "locationOfDurock";
	public static final double LAT_OF_DUROCK = 45.76156430132894;
	public static final double LNG_OF_DUROCK = 27.115413306633837;
	public static final String VENUE_URI_OF_POLINAS = CommonTestVocabulary.BASE_URI
			+ "venue4dac0d04fa8cc7649759a9ed";
	public static final String NAME_OF_POLINAS = "Polinas Plastik Sanayi Ve Tic. A.Ş";
	public static final String VENUE_URI_OF_DUROCK = CommonTestVocabulary.BASE_URI
			+ "venue4c05631291d776b07b58f7f9";
	public static final String NAME_OF_DUROCK = "DuRock";
	public static final String NAME_OF_TAYFUN_HALAC = "Tayfun Halaç";
	public static final String EMAIL_OF_TAYFUN_HALAC = "tayfunhalac@yahoo.com";
	public static final String PAGE_OF_TAYFUN_HALAC = "https://foursquare.com/user/22855197";
	public static final String DEPICTION_OF_TAYFUN_HALAC = "https://is1.4sqi.net/userpix_thumbs/TOW25TJCDBTK5CMM.jpg";
	public static final String LABEL_OF_TAYFUN_HALAC = NAME_OF_TAYFUN_HALAC;
	public static final String CONTACT_URI_OF_TAYFUN_HALAC = CommonTestVocabulary.BASE_URI
			+ "contactOfTayfunHalac";
	public static final String PHONE_OF_TAYFUN_HALAC = "05326460868";
	public static final String FACEBOOK_OF_TAYFUN_HALAC = "718598389";
	public static final String CHECKIN_URI_1_OF_TAYFUN_HALAC = CommonTestVocabulary.BASE_URI
			+ "checkin1ofTayfunHalac";
	public static final String CHECKIN_URI_2_OF_TAYFUN_HALAC = CommonTestVocabulary.BASE_URI
			+ "checkin2ofTayfunHalac";
	public static final String CHECKIN_URI_3_OF_TAYFUN_HALAC = CommonTestVocabulary.BASE_URI
			+ "checkin3ofTayfunHalac";
	public static final String ACCOUNT_URI_OF_TAYFUN_HALAC = CommonTestVocabulary.BASE_URI
			+ FOURSQUARE_ACCOUNT_CONSTANT + "TayfunHalac";
	public static final String HISTORIC_SIDE_CATEGORY_URI = CommonTestVocabulary.BASE_URI
			+ CATEGORY_PATTERN
			+ FoursquareIndividualCreator.UNDERLINE
			+ "Historic_Site";
	public static final String HISTORIC_SIDE_CATEGORY_NAME = "Historic Site";
	public static final String HISTORIC_SIDE_CATEGORY_PLURAL_NAME = "Historic Sites";
	public static final String HISTORIC_SIDE_CATEGORY_ICON = "https://foursquare.com/img/categories/arts_entertainment/museum.png";
	public static final String PLAZA_CATEGORY_URI = CommonTestVocabulary.BASE_URI
			+ CATEGORY_PATTERN
			+ FoursquareIndividualCreator.UNDERLINE
			+ "Plaza";
	public static final String PLAZA_CATEGORY_NAME = "Plaza";
	public static final String PLAZA_CATEGORY_PLURAL_NAME = "Plazas";
	public static final String PLAZA_CATEGORY_ICON = "https://foursquare.com/img/categories/parks_outdoors/default.png";
	public static final String LOCATION_URI_OF_VIEUX_LYON = CommonTestVocabulary.BASE_URI
			+ "locationOfVieuxLyon";
	public static final double LAT_OF_VIEUX_LYON = 45.76156430132894;
	public static final double LNG_OF_VIEUX_LYON = 4.828000853583218;
	public static final String ADDRESS_OF_VIEUX_LYON = "Rue Saint-Jean";
	public static final String VENUE_URI_OF_VIEUX_LYON = CommonTestVocabulary.BASE_URI
			+ "venue4b24e320f964a520546a24e3";
	public static final String NAME_OF_VIEUX_LYON = "Vieux Lyon";
	public static final String VENUE_URI_OF_PLACE_BELLECOUR = CommonTestVocabulary.BASE_URI
			+ "venue4b5e0a3df964a520817a29e3";
	public static final String NAME_OF_PLACE_BELLECOUR = "Place Bellecour";
	public static final String ADDRESS_OF_PLACE_BELLECOUR = "Place Bellecour";
	public static final String LOCATION_URI_OF_PLACE_BELLECOUR = CommonTestVocabulary.BASE_URI
			+ "locationOfPlaceBellecour";
	public static final double LAT_OF_PLACE_BELLECOUR = 45.75783672318841;
	public static final double LNG_OF_PLACE_BELLECOUR = 4.8321832716465;
	public static final String TIME_ZONE_PARIS = "Europe/Paris";
	public static final String NAME_OF_ZIYA_AKAR = "Ziya Akar";
	public static final String EMAIL_OF_ZIYA_AKAR = "gel_pcpc-z-@hotmail.com";
	public static final String PAGE_OF_ZIYA_AKAR = "https://foursquare.com/user/28976829";
	public static final String DEPICTION_OF_ZIYA_AKAR = "https://is1.4sqi.net/userpix_thumbs/DHB4QEK4L3YQR0LY.jpg";
	public static final String LABEL_OF_ZIYA_AKAR = NAME_OF_ZIYA_AKAR;
	public static final String CONTACT_URI_OF_ZIYA_AKAR = CommonTestVocabulary.BASE_URI
			+ "contactOfZiyaAkar";
	public static final String CHECKIN_URI_1_OF_ZIYA_AKAR = CommonTestVocabulary.BASE_URI
			+ "checkin1ofZiyaAkar";
	public static final String CHECKIN_URI_2_OF_ZIYA_AKAR = CommonTestVocabulary.BASE_URI
			+ "checkin2ofZiyaAkar";
	public static final String CHECKIN_URI_3_OF_ZIYA_AKAR = CommonTestVocabulary.BASE_URI
			+ "checkin3ofZiyaAkar";
	public static final String ACCOUNT_URI_OF_ZIYA_AKAR = CommonTestVocabulary.BASE_URI
			+ FOURSQUARE_ACCOUNT_CONSTANT + "ZiyaAkar";
	public static final String COFFEE_SHOP_CATEGORY_URI = CommonTestVocabulary.BASE_URI
			+ CATEGORY_PATTERN
			+ FoursquareIndividualCreator.UNDERLINE
			+ "Coffee_Shop";
	public static final String COFFEE_SHOP_CATEGORY_NAME = "Coffee Shop";
	public static final String COFFEE_SHOP_CATEGORY_PLURAL_NAME = "Coffee Shops";
	public static final String COFFEE_SHOP_CATEGORY_ICON = "https://foursquare.com/img/categories/food/coffeeshop.png";
	public static final String MEDI_RESTAURANT_CATEGORY_URI = CommonTestVocabulary.BASE_URI
			+ CATEGORY_PATTERN
			+ FoursquareIndividualCreator.UNDERLINE
			+ "Mediterranean_Restaurant";
	public static final String MEDI_RESTAURANT_NAME = "Mediterranean Restaurant";
	public static final String MEDI_RESTAURANT_PLURAL_NAME = "Mediterranean Restaurant";
	public static final String MEDI_RESTAURANT_ICON = "https://foursquare.com/img/categories/food/default.png";
	public static final String LOCATION_URI_OF_ALINS = CommonTestVocabulary.BASE_URI
			+ "locationOfAlins";
	public static final double LAT_OF_ALINS = 38.438984760512405;
	public static final double LNG_OF_ALINS = 27.141852378845215;
	public static final String ADDRESS_OF_ALINS = "Atatürk Cd. Alsancak";
	public static final String STREET_OF_ALINS = "1.Kordon";
	public static final String VENUE_URI_OF_ALINS = CommonTestVocabulary.BASE_URI
			+ "venue4e3670d0b0fb59390ea8f0ca";
	public static final String NAME_OF_ALINS = "Alin's";
	public static final String LOCATION_URI_OF_KAHVE_DIYARI = CommonTestVocabulary.BASE_URI
			+ "locationOfKahveDiyari";
	public static final double LAT_OF_KAHVE_DIYARI = 38.46219008273411;
	public static final double LNG_OF_KAHVE_DIYARI = 27.21117974921267;
	public static final String ADDRESS_OF_KAHVE_DIYARI = "Kazımdirik mah. Zafer cad. Bornova";
	public static final String VENUE_URI_OF_KAHVE_DIYARI = CommonTestVocabulary.BASE_URI
			+ "venue4e3670d0b0fb59390ea8f0ca";
	public static final String NAME_OF_KAHVE_DIYARI = "Kahve Diyarı";
	public static final String FOURSQUARE_ONTOLOGY_URI = "http://155.223.25.235:8180/sosep/foursquareOntology";
	public static final String SAMPLE_FOURSQUARE_ID = "84883265";
	public static final String PAGE_URI = "https://foursquare.com/user/"
			+ SAMPLE_FOURSQUARE_ID;
	public static final String ID_OF_BURAK_YONYUL = "19209229";
	public static final String ID_OF_ERDEM_ESER_EKINCI = "12561251";
	public static final String ID_OF_TAYFUN_GOKMEN_HALAC = "718598389";
	public static final String ID_OF_ZIYA_AKAR = "697988140";
	public static final String DBPEDIA_TURKEY_URI = "http://dbpedia.org/resource/Turkey";
	public static final String DBPEDIA_IZMIR_URI = "http://dbpedia.org/resource/%C4%B0zmir";
	public static final String DBPEDIA_BURSA_URI = "http://dbpedia.org/resource/Bursa";
	public static final String DBPEDIA_MANISA_URI = "http://dbpedia.org/resource/Manisa";
	public static final String DBPEDIA_FRANCE_URI = "http://dbpedia.org/resource/France";
	public static final String DBPEDIA_KAYSERI_URI = "http://dbpedia.org/resource/Kayseri";
	public static final String LACK_PERMISSONS = "tips,todos,mayorships";
	public static final Resource FOURSQUARE_USER_RSC = ResourceFactory
			.createResource(CommonTestVocabulary.BASE_URI + "84883265");
	public static final String ACCESS_TOKEN_NOT_VALID = "JQ2UA1N5XQCFM2TNYQGHPSME4R51E1YXPTAT04XWDOSJ1UYE";
	public static final String ACCESS_TOKEN_DEFECTED = "ABC123321CBA";
}

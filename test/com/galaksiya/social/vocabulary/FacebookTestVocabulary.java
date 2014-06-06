package com.galaksiya.social.vocabulary;

import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class FacebookTestVocabulary {
	public static final String AMSTERDAM = "Amsterdam, Netherlands";
	public static final String CALIFORNIA = "California City, California";
	public static final String ACCESS_TOKEN_CONTAINS_ALL_INFORMATION = "CAAD5pz1EKfYBACixahB16y6VJyDZCgLxbZADaVBZBboSaByjNCNfC1QiDi0OsALUDlpSQFZBraSRTs2MN9ZADMggWEKqHlrdPQT56ez14xeJrV4NQBeX5ZCYeHp0J5d6yrVxTBxpLy8gkdGFhxB3XDocMWbLhqHtuN0EZBYxBjs8Qmvv1v9IwamZBT4kGoSDaT8ZD";
	/**
	 * access token that lacks email and like properties
	 */
	public static final String ACCESS_TOKEN_MISSING_MAIL_AND_LIKES = "CAAD5pz1EKfYBAOXh0ZBsM2nZBVkGZCq02QvBVZCE4DaAyvOH5o8ZCd87E9gzpuKQpqLIul88EeiyscI3TVNZAJSskFs1bNPfTDU6A1ZCZBwZAJxBnYPCo27dxsd3d1LZBuj47jI6odb8VCE0Fv0WufDwhZBliwBGZCjRkqCjZBFZBKtu53BJUYRY2MLQsZBWEpUtNwONK8ZD";
	/**
	 * defected access token
	 */
	public static final String ACCESS_TOKEN_DEFECTED = "ACB123321CBA";
	/**
	 * not valid access token
	 */
	public static final String ACCESS_TOKEN_NOT_VALID = "CAACEdEose0cBAOZCLxYra2sGOgIYyQFqZBiVQbh4aDo3hKmdAdZAHJ7O60RTtcO80jNfeNSTFPRDZBBs7ujydePehpRnDZAjqZBLHhV79qv1gEcwSfVbyZC77B1NG932xO6ZBHp86SrSLeu78mrZA9ki0Qx0oHEmRAIGSuSfZC0FyLoyuwAthk65D418HKqpxbtzlpQuZCOhwQxKQZDZD";
	/**
	 * expired access token
	 */
	public static final String ACCESS_TOKEN_EXPIRED = "CAACEdEose0cBAOZCLxYra2sGOgIYyQFqZBiVQbh4aDo3hKmdAdZAHJ7O60RTtcO80jNfeNSTFPRDZBBs8ujydePehpRnDZAjqZBLHhV79qv1gEcwSfVbyZC77B1NG932xO6ZBHp86SrSLeu78mrZA9ki0Qx0oHEmRAIGSuSfZC0FyLoyuwAthk65D418HKqpxbtzlpQuZCOhwQxKQZDZD";

	/**
	 * resource uri whose properties will be checked
	 */
	public static final String FACEBOOK_COMPLETE_DATA_USER_URI = CommonTestVocabulary.BASE_URI
			+ "100008264590021";
	/**
	 * resource uri whose properties will be checked
	 */
	public static final String FACEBOOK_LESS_DATA_USER_URI = CommonTestVocabulary.BASE_URI
			+ "100008243257703";
	/**
	 * facebook user resource
	 */
	public static final Resource FACEBOOK_LESS_DATA_USER_RSC = ResourceFactory
			.createResource(FACEBOOK_LESS_DATA_USER_URI);
	/**
	 * facebook user resource
	 */
	public static final Resource FACEBOOK_COMPLETE_DATA_USER_RSC = ResourceFactory
			.createResource(FACEBOOK_COMPLETE_DATA_USER_URI);

	public static final String COMPLETE_PERMISSONS = "user_likes,user_events,user_groups,user_photos,user_videos,email,user_birthday,user_education_history,user_work_history";
	public static final String LACK_PERMISSONS = "user_events,user_groups,user_photos,user_videos,email,user_birthday";
	public static final String FACEBOOK_GRAPH_URI = "http://facebookindividualmodel.org";
	public static final String ACCESS_TOKEN = "CAAGXUUCYZAvUBAC9tZCAPJfoG58upyq86b0QpkZAmJKl7aVQr9DZBmR1GXsFwYCfJ51wkTNJ2WH4oHZCU4STJrxZAMXDjgAEQvAX2DaMZCvmI1MXwXAAVlhjG50tbZBn50y8hNbsYw0dElNB1gVX9lP6gmYNw5G5hAy2xd9H8Xp0ZCVbacRYbmgRuXB3HI1aWIjkZD";
	public static final String ACCESS_TOKEN_COPY = "CAAGXUUCYZAvUBAE33Bf8FX2abXinIJlWJ2j8ZCJgaJBgiWrZCKZAZBsncD8cYr2IzGvrLBZAcaC6c0YhkTuwnMyesFMmIuRG7ZC0jSK7wp7rwnKgT4qe4aZBeJBRImvZAnafeVD0WfqGzSit3NQPDcbghN5WgS8gWS3Cfu94phMSiJ69EcenpueHVT4x72WvKaXIZD";
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
	public static final String MOVIE_LIKE_4 = "Titanic";
	public static final String MOVIE_LIKE_3 = "The Hunger Games";
	public static final String MOVIE_LIKE_2 = "The Hobbit";
	public static final String MOVIE_LIKE_1 = "The Lord of the Rings Trilogy";
	public static final String MUSIC_LIKE_1 = "Linkin Park";
	public static final String AJAX = "AFC Ajax";
	public static final String EVENT_1 = "Sample Event";
	public static final String FEMALE_GENDER = "female";
	public static final String EMAIL_VALUE = "ameliaverboom@gmail.com";
	public static final String PAGE_URI = "https://www.facebook.com/profile.php?id=100008264590021";
	public static final String LABEL = "Amelia Verboom";
	public static final String DEPICTION_1 = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/t1.0-1/s100x100/10336617_1390608204557986_3333158814594344401_s.jpg";
	public static final String DEPICTION_2 = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/t1.0-1/s100x100/10336617_1390608204557986_3333158814594344401_s.jpg";
	public static final String PERSON_NAME = LABEL;
	public static final String EXPECTED_REDIRECT_URL = "https://www.facebook.com/dialog/oauth?client_id=169311759819999&redirect_uri=https://23.23.193.63/project/request/facebookauth&scope=user_likes,user_events,user_groups,user_photos,user_videos,email,user_birthday,user_education_history,user_work_history";

	public static final String FACEBOOK_RSC_PATTERN = "/facebook/resource/";
	public static final String NAME_OF_BURAK_YONYUL = "Burak Yönyül";
	public static final String LABEL_OF_BURAK_YONYUL = NAME_OF_BURAK_YONYUL;
	public static final String EMAIL_OF_BURAK_YONYUL = "burakyonyul@hotmail.com";
	public static final String PAGE_OF_BURAK_YONYUL = "http://www.facebook.com/burak.yonyul";
	public static final String DEPICTION_OF_BURAK_YONYUL = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc4/275481_713737927_6652372_s.jpg";
	private static final String FAVOURITE_TEAM_PATTERN = "favTm";
	public static final String GALATASARAY_URI = CommonTestVocabulary.BASE_URI
			+ FAVOURITE_TEAM_PATTERN + "GS";
	public static final String NAME_OF_GALATASARAY = "Galatasaray";
	public static final String NAME_OF_QUEEN = "Queen";
	public static final String NAME_OF_LINKIN_PARK = "Linkin Park";
	public static final String NAME_OF_JURASSIC_PARC = "Jurassic Park";
	public static final String NAME_OF_YUZUKLERIN_EFENDISI = "The Lord of the Rings Trilogy (Official Page)";
	public static final String NAME_OF_YAHSI_CAZIBE = "Yahşi Cazibe";
	private static final String MOVIE_PATTERN = "movie";
	private static final String MUSIC_PATTERN = "music";
	public static final String YAHSI_CAZIBE_URI = CommonTestVocabulary.BASE_URI
			+ MOVIE_PATTERN + "169180056457457";
	public static final String YUZUKLERIN_EFENDISI_URI = CommonTestVocabulary.BASE_URI
			+ MOVIE_PATTERN + "212216417436";
	public static final String JURASSIC_PARK_LOCAL_URI = CommonTestVocabulary.BASE_URI
			+ MOVIE_PATTERN + "170889472955944";
	public static final String LINKIN_PARK_URI = CommonTestVocabulary.BASE_URI
			+ MUSIC_PATTERN + "8210451787";
	public static final String QUEEN_URI = CommonTestVocabulary.BASE_URI
			+ MUSIC_PATTERN + "17337462361";
	private static final String FACEBOOK_ACCOUNT_CONSTANT = "facebookAccountOf";
	public static final String ACCOUNT_URI_OF_BURAK_YONYUL = CommonTestVocabulary.BASE_URI
			+ FACEBOOK_ACCOUNT_CONSTANT + "BurakYonyul";
	private static final String SCHOOL_PATTERN = "school";
	public static final String URI_OF_EGE_UNIVERSITY = CommonTestVocabulary.BASE_URI
			+ SCHOOL_PATTERN + "113366952010088";
	public static final String NAME_OF_EGE_UNIVERSITY = "Ege University";
	public static final String EDUCATION_URI_1_OF_BURAK_YONYUL = CommonTestVocabulary.BASE_URI
			+ "edu1ofBurakYonyul";
	public static final String COMPUTER_ENGINEERING = "Computer Engineering";
	public static final String EDU_GRAD_DATE_1_OF_BURAK_YONYUL = "2011";
	public static final String EDUCATION_URI_2_OF_BURAK_YONYUL = CommonTestVocabulary.BASE_URI
			+ "edu2ofBurakYonyul";
	public static final String EDU_GRAD_DATE_2_OF_BURAK_YONYUL = "2013";
	public static final String NAME_OF_ERDEM_ESER_EKINCI = "Erdem Eser Ekinci";
	public static final String EMAIL_OF_ERDEM_ESER_EKINCI = "erdemeserekinci@gmail.com";
	public static final String PAGE_OF_ERDEM_ESER_EKINCI = "http://www.facebook.com/erdemeserekinci";
	public static final String DEPICTION_OF_ERDEM_ESER_EKINCI = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc4/186088_544008025_1237543_s.jpg";
	public static final String LABEL_OF_ERDEM_ESER_EKINCI = NAME_OF_ERDEM_ESER_EKINCI;
	public static final String CEDRIC_URI = CommonTestVocabulary.BASE_URI
			+ MOVIE_PATTERN + "12746305883";
	public static final String NAME_OF_CEDRIC = "Cedric";
	public static final String ACCOUNT_URI_OF_ERDEM_ESER_EKINCI = CommonTestVocabulary.BASE_URI
			+ FACEBOOK_ACCOUNT_CONSTANT + "ErdemEserEkinci";
	public static final String URI_OF_KAYSERI_LISESI = CommonTestVocabulary.BASE_URI
			+ SCHOOL_PATTERN + "121151354583623";
	public static final String NAME_OF_KAYSERI_LISESI = "Kayseri Sümer Lisesi";
	public static final String EDUCATION_URI_1_OF_ERDEM_ESER_EKINCI = CommonTestVocabulary.BASE_URI
			+ "edu1ofErdemEserEkinci";
	public static final String EDU_GRAD_DATE_1_OF_ERDEM_ESER_EKINCI = "2011";
	public static final String EDUCATION_URI_2_OF_ERDEM_ESER_EKINCI = CommonTestVocabulary.BASE_URI
			+ "edu2ofErdemEserEkinci";
	public static final String EDU_GRAD_DATE_2_OF_ERDEM_ESER_EKINCI = "1998";
	public static final String PHD_COMPUTER_ENGINEERING = "";
	public static final String EDUCATION_URI_3_OF_ERDEM_ESER_EKINCI = CommonTestVocabulary.BASE_URI
			+ "edu3ofErdemEserEkinci";
	public static final String EDU_GRAD_DATE_3_OF_ERDEM_ESER_EKINCI = "2013";
	public static final String NAME_OF_TAYFUN_GOKMEN_HALAC = "Tayfun Gökmen Halaç";
	public static final String EMAIL_OF_TAYFUN_GOKMEN_HALAC = "tayfunhalac@yahoo.com";
	public static final String PAGE_OF_TAYFUN_GOKMEN_HALAC = "http://www.facebook.com/tayfun.halac";
	public static final String DEPICTION_OF_TAYFUN_GOKMEN_HALAC = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc4/371663_718598389_1247093102_s.jpg";
	public static final String LABEL_OF_TAYFUN_GOKMEN_HALAC = NAME_OF_TAYFUN_GOKMEN_HALAC;
	public static final String EDUCATION_URI_1_OF_TAYFUN_GOKMEN_HALAC = CommonTestVocabulary.BASE_URI
			+ "edu1ofTayfunGokmenHalac";
	public static final String EDU_GRAD_DATE_1_OF_TAYFUN_GOKMEN_HALAC = "2009";
	public static final String EDUCATION_URI_2_OF_TAYFUN_GOKMEN_HALAC = CommonTestVocabulary.BASE_URI
			+ "edu2ofTayfunGokmenHalac";
	public static final String EDU_GRAD_DATE_2_OF_TAYFUN_GOKMEN_HALAC = "2011";
	public static final String EDUCATION_URI_3_OF_TAYFUN_GOKMEN_HALAC = CommonTestVocabulary.BASE_URI
			+ "edu3ofTayfunGokmenHalac";
	public static final String EDU_GRAD_DATE_3_OF_TAYFUN_GOKMEN_HALAC = "2015";
	public static final String ALTAY_URI = CommonTestVocabulary.BASE_URI
			+ FAVOURITE_TEAM_PATTERN + "Altay";
	public static final String NAME_OF_ALTAY = "Altay";
	public static final String ACCOUNT_URI_OF_TAYFUN_GOKMEN_HALAC = CommonTestVocabulary.BASE_URI
			+ FACEBOOK_ACCOUNT_CONSTANT + "TayfunGokmenHalac";
	public static final String EVENT_URI_OF_DEVELOPING_SOSEP = CommonTestVocabulary.BASE_URI
			+ "event384396154937516";
	public static final String NAME_OF_DEVELOPING_SOSEP = "Developing SoSEp";
	public static final String EVENT_URI_OF_KAC_BABA_KAC = CommonTestVocabulary.BASE_URI
			+ "event186366694819545";
	public static final String NAME_OF_KAC_BABA_KAC = "\"Kaç Baba Kaç\" - 19 Mayıs Cumartesi 20:00";
	public static final String NAME_OF_ZIYA_AKAR = "Ziya Akar";
	public static final String EMAIL_OF_ZIYA_AKAR = "gel_pcpc-z-@hotmail.com";
	public static final String PAGE_OF_ZIYA_AKAR = "http://www.facebook.com/profile.php?id=697988140";
	public static final String DEPICTION_OF_ZIYA_AKAR = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc4/573940_697988140_174490032_s.jpg";
	public static final String LABEL_OF_ZIYA_AKAR = NAME_OF_ZIYA_AKAR;
	public static final String ACCOUNT_URI_OF_ZIYA_AKAR = CommonTestVocabulary.BASE_URI
			+ FACEBOOK_ACCOUNT_CONSTANT + "ZiyaAkar";
	public static final String LEON_URI = CommonTestVocabulary.BASE_URI
			+ MOVIE_PATTERN + "108533319168572";
	public static final String NAME_OF_LEON = "Leon";
	public static final String SNATCH_URI = CommonTestVocabulary.BASE_URI
			+ MOVIE_PATTERN + "162629997121827";
	public static final String NAME_OF_SNATCH = "Snatch";
	public static final String JEUX_URI = CommonTestVocabulary.BASE_URI
			+ MOVIE_PATTERN + "8845218015";
	public static final String NAME_OF_JEUX = "jeux d'enfants";
	public static final String CLINT_EASTWOOD_URI = CommonTestVocabulary.BASE_URI
			+ MOVIE_PATTERN + "22271024344";
	public static final String NAME_OF_CLINT_EASTWOOD = "Clint Eastwood";
	public static final String BEN_HUR_URI = CommonTestVocabulary.BASE_URI
			+ MOVIE_PATTERN + "154177751317986";
	public static final String NAME_OF_BEN_HUR = "Ben-Hur";
	public static final String TRUVA_URI = CommonTestVocabulary.BASE_URI
			+ MOVIE_PATTERN + "103126876394839";
	public static final String NAME_OF_TRUVA = "Truva";
	public static final String BRUCE_LEE_URI = CommonTestVocabulary.BASE_URI
			+ MOVIE_PATTERN + "107389392623820";
	public static final String NAME_OF_BRUCE_LEE = "Bruce Lee";
	public static final String HEAD_PINS_URI = CommonTestVocabulary.BASE_URI
			+ MUSIC_PATTERN + "112294418787400";
	public static final String NAME_OF_HEAD_PINS = "Headpins";
	public static final String ENNIO_MORRICONE_URI = CommonTestVocabulary.BASE_URI
			+ MUSIC_PATTERN + "57691724305";
	public static final String NAME_OF_ENNIO_MORRICONE = "Ennio Morricone";
	public static final String SKID_ROW_URI = CommonTestVocabulary.BASE_URI
			+ MUSIC_PATTERN + "107994532555463";
	public static final String NAME_OF_SKID_ROW = "Skid Row";
	public static final String BARIS_MANCO_URI = CommonTestVocabulary.BASE_URI
			+ MUSIC_PATTERN + "7659404294";
	public static final String NAME_OF_BARIS_MANCO = "Barış Manço";
	public static final String DEF_LEPPARD_URI = CommonTestVocabulary.BASE_URI
			+ MUSIC_PATTERN + "42988975659";
	public static final String NAME_OF_DEF_LEPPARD = "Def Leppard";
	public static final String BLACK_SABBATH_URI = CommonTestVocabulary.BASE_URI
			+ MUSIC_PATTERN + "56848544614";
	public static final String NAME_OF_BLACK_SABBATH = "Black Sabbath";
	public static final String MANOWAR_URI = CommonTestVocabulary.BASE_URI
			+ MUSIC_PATTERN + "120910203516";
	public static final String NAME_OF_MANOWAR = "Manowar";
	public static final String BON_JOVI_URI = CommonTestVocabulary.BASE_URI
			+ MUSIC_PATTERN + "7220821999";
	public static final String NAME_OF_BON_JOVI = "Bon Jovi";
	public static final String DOKKEN_URI = CommonTestVocabulary.BASE_URI
			+ MUSIC_PATTERN + "107518742604141";
	public static final String NAME_OF_DOKKEN = "Dokken";

	public static final String EDUCATION_URI_3_OF_ZIYA_AKAR = CommonTestVocabulary.BASE_URI
			+ "edu3ofZiyaAkar";
	public static final String EDUCATION_URI_2_OF_ZIYA_AKAR = CommonTestVocabulary.BASE_URI
			+ "edu2ofZiyaAkar";
	public static final String EDU_GRAD_DATE_2_OF_ZIYA_AKAR = "2009";
	public static final String EDUCATION_URI_1_OF_ZIYA_AKAR = CommonTestVocabulary.BASE_URI
			+ "edu1ofZiyaAkar";
	public static final String URI_OF_CELEBI_MEHMET_LISESI = CommonTestVocabulary.BASE_URI
			+ SCHOOL_PATTERN + "110678028959563";
	public static final String NAME_OF_CELEBI_MEHMET_LISESI = "Çelebi Mehmet Lisesi";
	public static final String FACEBOOK_TEST_DB = "FacebookTestDB";
	public static final String FACEBOOK_ONTOLOGY_URI = "http://155.223.25.235:8180/sosep/facebookOntology";
	private static final String STEVEN_SPIELBERG_URI = "http://dbpedia.org/resource/Steven_Spielberg";
	public static final Resource STEVEN_SPIELBERG_RSC = ResourceFactory
			.createResource(STEVEN_SPIELBERG_URI);
	public static final String FACEBOOK_VOCABULARY = FacebookOntologyVocabulary.FACEBOOK_SCHEMA_BASE_URI;
	public static final Literal FACEBOOK_URI_SPACE_LITERAL = ResourceFactory
			.createPlainLiteral(CommonTestVocabulary.BASE_URI);
	public static final String BURAK_FACEBOOK_ID = "burakyonyul123";
	public static final String AMELIA_FACEBOOK_ID = "burakyonyul123";
	public static final String FACEBOOK_ID_OF_BURAK_YONYUL = "713737927";
	public static final String FACEBOOK_ID_OF_ZIYA_AKAR = "697988140 ";
	public static final String FACEBOOK_ID_OF_ERDEM_ESER_EKINCI = "544008025";
	public static final String FACEBOOK_ID_OF_TAYFUN_GOKMEN_HALAC = "718598389";
	private static final String LORD_OF_THE_RINGS_MOVIE_URI = "http://data.linkedmdb.org/resource/film/839";
	private static final String GREEN_MILE_MOVIE_URI = "http://data.linkedmdb.org/resource/film/1641";
	private static final String JURASSIC_PARK_MOVIE_URI = "http://data.linkedmdb.org/resource/film/138";
	public static final Resource LORD_OF_THE_RINGS_MOVIE_RSC = ResourceFactory
			.createResource(LORD_OF_THE_RINGS_MOVIE_URI);
	public static final Resource GREEN_MILE_MOVIE_RSC = ResourceFactory
			.createResource(GREEN_MILE_MOVIE_URI);
	public static final Resource JURASSIC_PARK_MOVIE_RSC = ResourceFactory
			.createResource(JURASSIC_PARK_MOVIE_URI);
	public static final String COMMUNITY_LIKE_1 = "Trust Me, I'm an \"Engineer\"";
	public static final String COMMUNITY_LIKE_2 = "Scala Programming Language";
	public static final String AUTHOR_LIKE_1 = "Stephen King";
	public static final String TV_SHOW_LIKE_1 = "Game of Thrones";
	public static final String TV_SHOW_LIKE_2 = "How I Met Your Mother";
	public static final String TEAM_LIKE_1 = "AFC Ajax";
	public static final String BOOK_LIKE_1 = "The Dark Tower Series";
	public static final String PRODUCER_LIKE_1 = "Peter Jackson";
	public static final String ACTOR_DIRECTOR_LIKE_1 = "Quentin Tarantino";
	public static final String UNCATEGORİZED_LIKE_1 = "Eclipse";
	public static final String UNCATEGORİZED_LIKE_2 = "Java";
}

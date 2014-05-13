package com.galaksiya.social.ontology.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;
import com.restfb.types.Page.Cover;

public class CommonOntologyVocabulary {

	public static final String DC_TERMS_PREFIX = "http://purl.org/dc/terms/";

	private static final String SOSEP_COMMON_SCHEMA_FILE_PATTERN = "coSoSe.owl";

	public static final String DIEZ = "#";

	public static final String CUSTOM_ONTOLOGY_FOLDER_PATH = "http://sociallinker.galaksiya.com/ontology/";

	public static final String OWL_URI = OWL.getURI();

	public static final String COSOSE_BASE_URI = CUSTOM_ONTOLOGY_FOLDER_PATH
			+ SOSEP_COMMON_SCHEMA_FILE_PATTERN + DIEZ;

	public static final String RDF_URI = RDF.getURI();

	public static final String RDFS_URI = RDFS.getURI();

	/**
	 * URI of SIOC ontology
	 */
	public static final String SIOC_BASE_URI = "http://rdfs.org/sioc/ns#";

	/**
	 * URI of FOAF ontology
	 */
	public static final String FOAF_BASE_URI = "http://xmlns.com/foaf/0.1/";

	/**
	 * URI of TAG ontology
	 */
	public static final String TAG_BASE_URI = "http://www.holygoat.co.uk/owl/redwood/0.1/tags/";

	/**
	 * URI of CV ontology
	 */
	public static final String CV_RDFS_URI = "http://kaste.lv/~captsolo/semweb/resume/cv.rdfs#";

	/**
	 * URI of DBPEDIA ontology
	 */
	public static final String DBPEDIA_BASE_URI = "http://dbpedia.org/ontology/";

	/**
	 * URI of GEODATA ontology
	 */
	public static final String GEODATA_BASE_URI = "http://linkedgeodata.org/ontology/";

	/**
	 * URI of GEODATA ontology
	 */
	public static final String SKOS_BASE_URI = "http://www.w3.org/2004/02/skos/core#";

	/**
	 * URI of FAMILY BOND ontology
	 */
	public static final String FAMILY_BOND_URI = CUSTOM_ONTOLOGY_FOLDER_PATH
			+ "familyBond.owl#";

	public static final String CV_EXTENDING_ONTOLOGY_URI = CUSTOM_ONTOLOGY_FOLDER_PATH
			+ "extendingCV.owl/";
	public static final String LGDO_EXTENDING_ONTOLOGY_URI = CUSTOM_ONTOLOGY_FOLDER_PATH
			+ "extendingLGDO.owl/";

	public static final String TAG_EXTENDING_ONTOLOGY_URI = CUSTOM_ONTOLOGY_FOLDER_PATH
			+ "extendingTAG.owl/";

	/**
	 * concepts and properties with dbpedia ontology schema prefix
	 */
	public static final String MOVIE_URI = DBPEDIA_BASE_URI + "Film";
	public static final String MUSIC_URI = DBPEDIA_BASE_URI + "Music";
	public static final String BOOK_URI = DBPEDIA_BASE_URI + "Book";
	public static final String BAND_URI = DBPEDIA_BASE_URI + "Band";
	public static final String CITY_PRP_URI = DBPEDIA_BASE_URI + "city";
	public static final String COUNTRY_PRP_URI = DBPEDIA_BASE_URI + "country";

	/**
	 * Tag concept with Tag ontology schema prefix
	 */
	public static final String TAG_RSC_URI = TAG_BASE_URI + "Tag";

	public static final String RDF_TYPE_PRP_URI = RDF_URI + "type";

	/**
	 * concepts and properties with foaf ontology schema prefix
	 */
	public static final String GROUP_URI = FOAF_BASE_URI + "Group";
	public static final String IMAGE_URI = FOAF_BASE_URI + "Image";
	public static final String ORGANIZATION_URI = FOAF_BASE_URI
			+ "Organization";
	public static final String FOAF_BIRTHDAY_URI = FOAF_BASE_URI + "birthday";
	public static final String FOAF_GENDER_URI = FOAF_BASE_URI + "gender";
	public static final String FOAF_KNOWS_URI = FOAF_BASE_URI + "knows";
	public static final String FOAF_NAME_URI = FOAF_BASE_URI + "name";
	public static final String FOAF_HAS_IMAGE_URI = FOAF_BASE_URI + "img";
	public static final String WEBLOG_URI = FOAF_BASE_URI + "weblog";
	public static final String FOAF_URL = FOAF_BASE_URI + "url";
	public static final String FOAF_PERSON_RSC_URI = FOAF_BASE_URI + "Person";
	public static final String FOAF_PAGE_PRP_URI = FOAF_BASE_URI + "page";
	public static final String FOAF_PICTURE_PRP_URI = FOAF_BASE_URI
			+ "depiction";
	public static final String FOAF_IMAGE_RSC_URI = FOAF_BASE_URI + "Image";
	public static final String FOAF_DOCUMENT_RSC_URI = FOAF_BASE_URI
			+ "Document";
	public static final String ACCOUNT_PRP_URI = FOAF_BASE_URI + "account";
	public static final Property ACCOUNT_PRP = ResourceFactory
			.createProperty(ACCOUNT_PRP_URI);
	public static final String ACCOUNT_RSC_URI = FOAF_BASE_URI
			+ "OnlineAccount";
	public static final Resource ACCOUNT_RSC = ResourceFactory
			.createResource(ACCOUNT_RSC_URI);
	/**
	 * concepts and properties with sioc ontology schema prefix
	 */
	public static final String SIOC_MEMBER_OF_URI = SIOC_BASE_URI + "member_of";
	public static final String SIOC_EMAIL_URI = SIOC_BASE_URI + "email";
	public static final String SIOC_ID_URI = SIOC_BASE_URI + "id";

	/**
	 * concepts and properties with cv ontology schema prefix
	 */
	public static final String LANGUAGE_SKILL_URI = CV_RDFS_URI
			+ "LanguageSkill";
	public static final String SKILL_URI = CV_RDFS_URI + "Skill";
	public static final String WORK_HISTORY_URI = CV_RDFS_URI + "WorkHistory";
	public static final String COMPANY_URI = CV_RDFS_URI + "Company";
	public static final String CV_INDUSTRY_PRP_URI = CV_RDFS_URI + "Industry";
	public static final String EDUCATIONAL_ORG = CV_RDFS_URI + "EducationalOrg";
	public static final String EDUCATION_URI = CV_RDFS_URI + "Education";
	public static final String CV_HAS_SKILL_PRP_URI = CV_RDFS_URI + "hasSkill";
	public static final String EMPLOYED_IN_PRP_URI = CV_RDFS_URI + "employedIn";
	public static final String CV_COMPANY_SIZE = CV_RDFS_URI
			+ "targetCompanySize";
	public static final String CV_HAS_WORK_HISTORY_PRP_URI = CV_RDFS_URI
			+ "hasWorkHistory";
	public static final String CV_EDU_PRP_URI = CV_RDFS_URI + "hasEducation";
	public static final String CV_TITLE_URI = CV_RDFS_URI + "jobTitle";
	public static final String CV_START_DATE = CV_RDFS_URI + "startDate";
	public static final String CV_END_DATE = CV_RDFS_URI + "endDate";
	public static final String CV_JOB_DESCRIPTION_PRP_URI = CV_RDFS_URI
			+ "jobDescription";
	public static final String CV_TRG_COM_DESCRIPTION = CV_RDFS_URI
			+ "targetCompanyDescription";
	public static final String CV_STUDIED_IN_PRP_URI = CV_RDFS_URI
			+ "studiedIn";
	public static final String GRAD_DATE_PRP_URI = CV_RDFS_URI + "eduGradDate";
	public static final String EDU_MAJOR_PRP_URI = CV_RDFS_URI + "eduMajor";
	public static final Property EDU_MAJOR_PRP = ResourceFactory
			.createProperty(EDU_MAJOR_PRP_URI);

	/**
	 * concepts and properties with geodata ontology schema prefix
	 */
	public static final String GEODDATA_PLACE_URI = GEODATA_BASE_URI + "Place";
	public static final String POSTALCODE_PRP_URI = GEODATA_BASE_URI
			+ "hasPostalCode";

	/**
	 * OWL sameAs property
	 */
	public static final String OWL_SAME_AS = OWL_URI + "sameAs";

	/**
	 * RDFS label propery
	 */
	public static final String RDFS_LABEL_PRP_URI = RDFS_URI + "label";

	/**
	 * concepts and properties with socsem ontology schema prefix
	 */
	public static final String HONOURS_URI = COSOSE_BASE_URI + "honours";
	public static final String WITH_PRP_URI = COSOSE_BASE_URI + "with";
	public static final String HOMETOWN_URI = COSOSE_BASE_URI + "hometown";
	public static final String BOOKMARK_ORDER_URI = COSOSE_BASE_URI
			+ "bookmark_order";
	public static final String VERSION_URI = COSOSE_BASE_URI + "version";
	public static final String CREATED_AT_PRP_URI = COSOSE_BASE_URI
			+ "created_at";
	public static final String DESCRIPTION_URI = COSOSE_BASE_URI
			+ "description";
	public static final String SUMMARY_URI = COSOSE_BASE_URI + "summary";
	public static final String OWNER_URI = COSOSE_BASE_URI + "owner";
	public static final String EMPLOYER_PRP_URI = CV_EXTENDING_ONTOLOGY_URI
			+ "employer";
	public static final String LOCATION_URI = LGDO_EXTENDING_ONTOLOGY_URI
			+ "location";
	public static final String HAS_TAG_PRP_URI = TAG_EXTENDING_ONTOLOGY_URI
			+ "has_tag";
	public static final String TYPE_PRP_URI = COSOSE_BASE_URI + "type";
	public static final String ADDRESS_PRP_URI = COSOSE_BASE_URI
			+ "has_address";
	public static final String RESOURCE_OF_CITY_PRP_URI = COSOSE_BASE_URI
			+ "has_resource_of_city";

	public static final String HAS_PHOTO_URI = COSOSE_BASE_URI + "has_photo";

	public static final String STATUS_URI = COSOSE_BASE_URI + "status";

	public static final String WORK_POSITION_PRP_URI = CV_EXTENDING_ONTOLOGY_URI
			+ "position";

	public static final String PHOTO_URI = COSOSE_BASE_URI + "Photo";

	/**
	 * family bond Person concept
	 */
	public static final String FAMILY_PERSON = FAMILY_BOND_URI + "Person";

	public static final Resource MUSIC_RSC = ResourceFactory
			.createResource(MUSIC_URI);

	public static final Resource MOVIE_RSC = ResourceFactory
			.createResource(MOVIE_URI);

	public static final Resource PLACE_RSC = ResourceFactory
			.createResource(GEODDATA_PLACE_URI);

	public static final Resource EDUCATIONAL_ORG_RSC = ResourceFactory
			.createResource(EDUCATIONAL_ORG);

	public static final Resource EDUCATION_RSC = ResourceFactory
			.createResource(EDUCATION_URI);

	public static final String CROSS_STREET_PRP_URI = GEODATA_BASE_URI
			+ "hasStreet";

	public static final String LAT_PRP_URI = GEODATA_BASE_URI + "latitude";

	public static final String LNG_PRP_URI = GEODATA_BASE_URI + "longitude";

	public static final String STATE_PRP_URI = GEODATA_BASE_URI + "hasState";

	public static final Property HOMETOWN_PRP = ResourceFactory
			.createProperty(HOMETOWN_URI);

	public static final Property LOCATION_PRP = ResourceFactory
			.createProperty(LOCATION_URI);

	public static final Property CITY_PRP = ResourceFactory
			.createProperty(CITY_PRP_URI);

	public static final Property COUNTRY_PRP = ResourceFactory
			.createProperty(COUNTRY_PRP_URI);

	public static final String DBPEDIA_COUNTRY_URI = DBPEDIA_BASE_URI
			+ "Country";

	public static final Resource DBPEDIA_COUNTRY_RSC = ResourceFactory
			.createResource(DBPEDIA_COUNTRY_URI);

	public static final String DBPEDIA_CITY_URI = DBPEDIA_BASE_URI + "City";

	public static final Resource DBPEDIA_CITY_RSC = ResourceFactory
			.createResource(DBPEDIA_CITY_URI);

	public static final String SKOS_ALT_LABEL_PRP_URI = SKOS_BASE_URI
			+ "altLabel";

	public static final Property SKOS_ALT_LABEL_PRP = ResourceFactory
			.createProperty(SKOS_ALT_LABEL_PRP_URI);

	public static final String CUSTOMER_ID_PRP_URI = COSOSE_BASE_URI
			+ "customerID";

	public static final Resource GEODATA_PLACE_RSC = ResourceFactory
			.createResource(GEODDATA_PLACE_URI);

	// foaf person resource
	public static final Resource FOAF_PERSON_RSC = ResourceFactory
			.createResource(FOAF_PERSON_RSC_URI);
	public static final Property SIOC_EMAIL_PRP = ResourceFactory
			.createProperty(SIOC_EMAIL_URI);
	public static final Property WORK_HISTORY_PRP = ResourceFactory
			.createProperty(CV_HAS_WORK_HISTORY_PRP_URI);
	public static final Property EDUCATION_PRP = ResourceFactory
			.createProperty(CV_EDU_PRP_URI);
	public static final Property CV_STUDIED_IN_PRP = ResourceFactory
			.createProperty(CV_STUDIED_IN_PRP_URI);

	public static final Property CV_INDUSTRY_PRP = ResourceFactory
			.createProperty(CV_INDUSTRY_PRP_URI);

	public static final Property FOAF_ACCOUNT_PRP = ResourceFactory
			.createProperty(FOAF.getURI() + "account");

	public static final Property CREATED_AT_PRP = ResourceFactory
			.createProperty(CREATED_AT_PRP_URI);

	public static final String SIOC_COMMUNITY = SIOC_BASE_URI + "Community";

	public static final String DATE_TIME_URI = XSD.getURI() + "dateTime";

	public static final String DC_TERMS_DATE_PRP_URI = DC_TERMS_PREFIX + "date";

	public static final Property DC_TERMS_DATE_PRP = ResourceFactory
			.createProperty(DC_TERMS_DATE_PRP_URI);

}

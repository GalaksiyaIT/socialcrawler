package com.galaksiya.social.vocabulary;

import com.galaksiya.social.crawler.LinkedinUserCrawler;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class LinkedinTestVocabulary {

	public static final String LINKEDIN_TRIPLE_DB = "LinkedinTripleDB";
	public static final String LINKEDIN_GRAPH_URI = "http://linkedinindividualmodel.org";
	public static final String MYSQL_SERVER_URL = "jdbc:mysql://localhost:3306/";
	public static final String USER_NAME = "root";
	public static final String PASSWORD = "toor";
	public static final String TOKEN_VALUE_FOR_ZIYA = "c64f93e2-442f-452b-aacc-00285082e369";
	public static final String TOKEN_SECRET_FOR_ZIYA = "2d1a4b9f-a07f-4c4e-b570-c8747acc6514";
	public static final String TOKEN_VALUE_FOR_BURAK = "0d3149f7-9011-45e3-b869-6672b197f36e";
	public static final String TOKEN_SECRET_FOR_BURAK = "cdb44855-2b8b-40d3-b63e-3a714fd758d8";
	public static final String TOKEN_VALUE_FOR_GEORGE = "a50b87d8-b341-49dc-922f-cbd31b08c29e";
	public static final String TOKEN_SECRET_FOR_GEORGE = "3e7a64f2-01a0-413b-93d5-6df98e8cf354";

	public static final String NOT_VALID_TOKEN_VALUE = "a50b87d8-b351-49dc-922f-cbd31b08c29e";
	public static final String NOT_VALID_TOKEN_SECRET = "3e7a64f2-01a0-413b-93e5-6df98e8cf354";
	
	public static final String LINKEDIN_NOT_VALID_API_KEY = "77t7wsd18hl6pz";
	public static final String LINKEDIN_NOT_VALID_SECRET_KEY = "ab0tu96RBPBoB4z4";
	
	public static final String LINKEDIN_DEFECTED_API_KEY = "2348had34sdf234fss234";
	public static final String LINKEDIN_DEFECTED_SECRET_KEY = "as23423424wdf3wer5dfsv3";

	public static final String DEFECTED_TOKEN_VALUE = "a51b08529e";
	public static final String DEFECTED_TOKEN_SECRET = "3ecfs473g4";

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
	public static final String LABEL_VALUE = "George Franco";
	public static final String PERSON_NAME = LABEL_VALUE;
	public static final String DEPICTION_VALUE = "http://m.c.lnkd.licdn.com/mpr/mprx/0_in1UQZKjimt6AVsZ_creQViAfa3clxsZTlFXQR1hrIz6mRS47KiBHUba38Td-ZUNGvA5Wy3x6VAZ";
	public static final String HEADLINE_VALUE = "Web Developer at Google";
	public static final String INDUSTRY_VALUE = "Computer Software";
	public static final String[] COMPANIES = { "Apple", "Google" };
	public static final String[] TITLES = { "Software Developer",
			"Web Developer" };
	public static final String EXPECTED_PURE_URL = "https://www.linkedin.com/uas/oauth/authorize";
	public static final String SAMPLE_LINKEDIN_ID = "burakyonyul123";
	public static final Resource LINKEDIN_USER_RESOURCE = ResourceFactory
			.createResource(CommonTestVocabulary.BASE_URI + "yx1Q9Jo9ng");

	/**
	 * this method generates a sample permission list with lacking industry and
	 * location permissions
	 * 
	 * @return
	 */
	public static String generateLackPermissionText() {
		String lackOfPermissionText = "";
		// iterate on each permission
		for (int i = 0; i < LinkedinUserCrawler.linkedinProfileFields.length; i++) {
			// get profile field
			ProfileField profileField = LinkedinUserCrawler.linkedinProfileFields[i];
			// check and add field name to the permissions string
			if (!profileField.fieldName().equals("industry")
					&& !profileField.fieldName().equals("location")
					&& !profileField.fieldName().equals("positions")) {
				lackOfPermissionText += profileField.fieldName();
				// add comma to separate permissions
				if (i < LinkedinUserCrawler.linkedinProfileFields.length - 1) {
					lackOfPermissionText += ",";
				}
			}
		}
		return lackOfPermissionText;
	}

	// public static final String LACK_PERMISSONS =
	// "first-name,last-name,id,languages,industry,interests,location,main-address,positions,skills,headline,specialties,summary,current-status-timestamp,distance,num-connections,num-recommenders";
}

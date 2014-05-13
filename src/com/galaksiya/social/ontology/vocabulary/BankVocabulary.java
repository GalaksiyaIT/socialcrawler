package com.galaksiya.social.ontology.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class BankVocabulary {

	public static final String BANK_ENDPOINT = "http://galaksiya.com:8090/sparql";
	public static final String FINANCE_ONTOLOGY_URI = "http://linked-open-finance.com/ontology#";
	public static final String BANK_RSC_URI = "http://galaksiya.com:8090/resource/";
	public static final String BURAK_BANK_RSC_URI = BANK_RSC_URI + "Person1";
	public static final String TAYFUN_BANK_RSC_URI = BANK_RSC_URI + "Person2";
	public static final String ENES_BANK_RSC_URI = BANK_RSC_URI + "Person3";
	public static final String BAHTIYAR_BANK_RSC_URI = BANK_RSC_URI + "Person4";
	public static final String EMRAH_BANK_RSC_URI = BANK_RSC_URI + "Person5";
	public static final String GUL_BANK_RSC_URI = BANK_RSC_URI + "Person6";
	public static final String PINAR_BANK_RSC_URI = BANK_RSC_URI + "Person7";
	public static final String ERDEM_BANK_RSC_URI = BANK_RSC_URI + "Person8";
	public static final String HAS_MONEY_PRP_URI = FINANCE_ONTOLOGY_URI
			+ "hasMoney";
	public static final Property HAS_MONEY_PRP = ResourceFactory
			.createProperty(HAS_MONEY_PRP_URI);
	public static final String FINANCE_NAME_PRP_URI = FINANCE_ONTOLOGY_URI
			+ "name";

}

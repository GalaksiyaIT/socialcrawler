package com.galaksiya.social.fetcher;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;

public abstract class IndividualCreator {

	private String baseURI;

	private Logger logger = Logger.getLogger(this.getClass());

	private Model model;

	public IndividualCreator(String baseURI) {
		this.baseURI = baseURI;
	}

	/**
	 * This method creates an account indidivual and sets it as the property of
	 * the given individual.
	 * 
	 * @param userIndv
	 *            user individual to add her an account property.
	 * @param userId
	 *            Identifier of the social user account.
	 * @param socialHomepageRsc
	 *            Homepage address resource of social network which the user has
	 *            the account.
	 */
	public void createAccountProperty(Resource userIndv, String userId,
			Resource socialHomepageRsc) {
		// create account individual
		Resource accountIndv = createAccountIndv(userId, socialHomepageRsc);
		// create account property
		createProperty(userIndv, CommonOntologyVocabulary.ACCOUNT_PRP_URI,
				accountIndv);
	}

	public void createHometownPrp(Resource fbUserIndv, String hometownName) {
		// create hometown resource
		Resource hometownRsc = createHometownResource(hometownName);
		// create hometown property of facebook user individual
		createProperty(fbUserIndv, CommonOntologyVocabulary.HOMETOWN_URI,
				hometownRsc);
	}

	public void createLocationProperty(Resource userIndv, String locationName) {
		// create location resource
		Resource locationRsc = createLocationResource(locationName);
		// create location property of facebook user individual
		createProperty(userIndv, CommonOntologyVocabulary.LOCATION_URI,
				locationRsc);
	}

	/**
	 * create location resource using given location object.
	 * 
	 * @param locationName
	 * 
	 * @return
	 */
	public Resource createLocationResource(String locationName) {
		Resource locationRsc = null;
		// create location resource
		if (isTextNotEmpty(locationName)) {
			locationRsc = getModel().createResource(
					getBaseURI() + "LOC" + UUID.randomUUID(),
					CommonOntologyVocabulary.PLACE_RSC);
			// create name property of hometown
			createProperty(locationRsc, CommonOntologyVocabulary.FOAF_NAME_URI,
					locationName);
		}
		return locationRsc;
	}

	/**
	 * Creates a property for the given propURI and generates a statement for
	 * this property with subject indv and literal value.
	 * 
	 * @param subjectIndv
	 *            Subject of the statement
	 * @param propURI
	 *            Predicate of the statement
	 * @param literalValue
	 *            literal value of the statement.
	 */
	public void createProperty(Resource subjectIndv, String propURI,
			Literal literalValue) {
		if (subjectIndv != null && literalValue != null) {
			Property retrievedPrp = getModel().getProperty(propURI);
			subjectIndv.addLiteral(retrievedPrp, literalValue);
		}
	}

	/**
	 * Creates a property for the given propURI and generates a statement for
	 * this property with subject indv and object value.
	 * 
	 * @param subjectIndv
	 *            Subject of the statement
	 * @param propURI
	 *            Predicate of the statement
	 * @param objectValue
	 *            Object literal of the statement.
	 */
	public void createProperty(Resource subjectIndv, String propURI,
			Object objectValue) {
		if (subjectIndv != null && objectValue != null) {
			Property retrievedPrp = getModel().getProperty(propURI);
			subjectIndv.addProperty(retrievedPrp, "" + objectValue);
		}
	}

	/**
	 * Creates a property for the given propURI and generates a statement for
	 * this property with subject indv and object indv.
	 * 
	 * @param subjectIndv
	 * @param uri
	 * @param objectIndv
	 */
	public void createProperty(Resource subjectIndv, String uri,
			Resource objectIndv) {
		if (subjectIndv != null && objectIndv != null) {
			Property property = getModel().getProperty(uri);
			subjectIndv.addProperty(property, objectIndv);
		}
	}

	public Logger getLogger() {
		return logger;
	}

	public Model getModel() {
		if (model == null) {
			model = ModelFactory.createDefaultModel();
		}
		return model;
	}

	protected Resource createCityResource(String city) {
		Resource cityRsc = null;
		if (isTextNotEmpty(city)) {
			cityRsc = getModel().createResource(
					getBaseURI() + "City" + UUID.randomUUID(),
					CommonOntologyVocabulary.DBPEDIA_CITY_RSC);
			createProperty(cityRsc, FOAF.name.getURI(), city);
		}
		return cityRsc;
	}

	protected Resource createCountryResource(String country) {
		Resource countryRsc = null;
		if (isTextNotEmpty(country)) {
			countryRsc = getModel().createResource(
					getBaseURI() + "Country" + UUID.randomUUID(),
					CommonOntologyVocabulary.DBPEDIA_COUNTRY_RSC);
			createProperty(countryRsc, FOAF.name.getURI(), country);
		}
		return countryRsc;
	}

	/**
	 * creates Document individual using userProfileURL
	 * 
	 * @param userProfileURL
	 * @return
	 */
	protected Resource createDocumentIndv(String userProfileURL) {
		Resource documentIndv = null;
		if (userProfileURL != null && !userProfileURL.trim().equals("null")) {
			documentIndv = getModel().createResource(
					userProfileURL,
					getModel().createResource(
							CommonOntologyVocabulary.FOAF_DOCUMENT_RSC_URI));
		}
		return documentIndv;
	}

	/**
	 * creates Image individual using userProfilePictureURL
	 * 
	 * @param userProfilePictureURL
	 * @return
	 */
	protected Resource createImageIndv(String userProfilePictureURL) {
		Resource imageIndv = null;
		if (userProfilePictureURL != null) {
			imageIndv = getModel().createResource(
					userProfilePictureURL,
					getModel().createResource(
							CommonOntologyVocabulary.FOAF_IMAGE_RSC_URI));
		}
		return imageIndv;
	}

	protected String createIndividualURI(String userID) {
		return getBaseURI() + userID;
	}

	protected String getBaseURI() {
		return baseURI;
	}

	/**
	 * creates an account individual
	 * 
	 * @param userId
	 * @param socialHomepageRsc
	 * 
	 * @return account individual
	 */
	private Resource createAccountIndv(String userId, Resource socialHomepageRsc) {
		// create account individual
		String userID = "account" + UUID.randomUUID();
		Resource accountIndv = getModel()
				.createResource(
						createIndividualURI(userID),
						ResourceFactory
								.createResource(CommonOntologyVocabulary.ACCOUNT_RSC_URI));
		createProperty(accountIndv, FOAF.accountServiceHomepage.getURI(),
				socialHomepageRsc);
		createProperty(accountIndv, FOAF.accountName.getURI(), userId);
		return accountIndv;
	}

	private Resource createHometownResource(String hometownName) {
		// create hometown resource

		Resource hometownRsc = null;
		if (isTextNotEmpty(hometownName)) {
			hometownRsc = getModel().createResource(
					getBaseURI() + "HT" + UUID.randomUUID(),
					CommonOntologyVocabulary.PLACE_RSC);
			// create name property of hometown
			createProperty(hometownRsc, FOAF.name.getURI(), hometownName);
		}
		return hometownRsc;
	}

	/**
	 * Checks that the given string is not null and not an empty string or not
	 * only space text.
	 * 
	 * @param text
	 *            text to check.
	 * @return <code>true</code> if there is at least one character in the given
	 *         text.
	 */
	private boolean isTextNotEmpty(String text) {
		return text != null && !text.trim().equals("");
	}
}
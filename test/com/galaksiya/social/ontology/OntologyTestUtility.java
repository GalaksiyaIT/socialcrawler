package com.galaksiya.social.ontology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDFS;

public class OntologyTestUtility {

	private OntModel ontModel;

	public OntologyTestUtility(OntModel ontModel) {
		this.ontModel = ontModel;
	}

	/**
	 * Asserts that the given property has the given ranges.
	 * 
	 * @param propertyURI
	 *            URI of the property to test.
	 * @param rangeURIs
	 *            URIs of the range classes.
	 */
	public void assertObjectPropertyRanges(String propertyURI,
			String... rangeURIs) {
		assertPropertyRanges(ontModel.getObjectProperty(propertyURI), rangeURIs);
	}

	/**
	 * Asserts that the given property has the given ranges.
	 * 
	 * @param propertyURI
	 *            URI of the property to test.
	 * @param rangeURIs
	 *            URIs of the range classes.
	 */
	public void assertDatatypePropertyRanges(String propertyURI,
			String... rangeURIs) {
		assertPropertyRanges(ontModel.getDatatypeProperty(propertyURI),
				rangeURIs);
	}

	/**
	 * Checks the property ranges.
	 * 
	 * @param property
	 *            property from model.
	 * @param rangeURIs
	 *            URI list of ranges.
	 */
	public void assertPropertyRanges(Property property, String... rangeURIs) {
		assertNotNull(property);
		// assert that there is a range statement for each range URI...
		assertStatements(property, RDFS.range, rangeURIs);
	}

	/**
	 * This method checks that there is a statement between the given property
	 * and each of the URIs with the given {@link RDFS} property.
	 * 
	 * @param property
	 *            property URI to test.
	 * @param rdfsProperty
	 *            {@link RDFS} property to check the statements.
	 * @param objectURIs
	 *            range class URIs.
	 */
	public void assertStatements(Property property, Property rdfsProperty,
			String... objectURIs) {
		// check that there is a statement for each object URI...
		for (String value : objectURIs) {
			Resource valueRsc = ontModel.getResource(value);
			String failMessage = MessageFormat.format(
					"Does not exists: [{0}, {1}, {2}]", property, rdfsProperty,
					value);
			assertFalse(failMessage,
					ontModel.listStatements(property, rdfsProperty, valueRsc)
							.toList().isEmpty());
		}
	}

	/**
	 * Checks the given property's domains.
	 * 
	 * @param property
	 *            property to test.
	 * @param domains
	 *            domain URIs.
	 */
	public void assertPropertyDomains(Property property, String... domains) {
		assertNotNull(MessageFormat.format(
				"Property could not be found in the model.", property),
				property);
		// assert that property has domain assertions...
		assertStatements(property, RDFS.domain, domains);
	}

	/**
	 * Asserts that the given property has the given domains.
	 * 
	 * @param propertyURI
	 *            URI of the datatype property to test.
	 * @param domains
	 *            URIs of the domain classes.
	 */
	public void assertDatatypePropertyDomains(String propertyURI,
			String... domains) {
		assertPropertyDomains(ontModel.getDatatypeProperty(propertyURI),
				domains);
	}

	/**
	 * Asserts that the given property has the given domains.
	 * 
	 * @param propertyURI
	 *            URI of the property to test.
	 * @param domains
	 *            URIs of the domain classes.
	 */
	public void assertObjectPropertyDomains(String propertyURI,
			String... domains) {
		assertPropertyDomains(ontModel.getObjectProperty(propertyURI), domains);
	}

	/**
	 * Asserts that there is an {@link OntClass} resource for the given URI.
	 * 
	 * @param URI
	 *            class URI.
	 */
	public void assertOntClass(String URI) {
		assertNotNull(ontModel.getOntClass(URI));
	}

	/**
	 * FIXME javadoc.
	 * 
	 * @param propertyURI
	 * @param domainRscURI
	 * @param range
	 */
	public void assertDataTypeProperty(String propertyURI, String domainRscURI,
			Object range) {
		// get asserted property
		DatatypeProperty property = ontModel.getDatatypeProperty(propertyURI);

		// create domain resource
		Resource domain = ResourceFactory.createResource(domainRscURI);
		assertNotNull(property);

		if (domainRscURI != null) {
			// Domain test
			assertEquals(domain, property.getDomain());
		}
		if (range != null) {
			// Range test
			assertEquals(range, property.getRange());
		}
	}

	/**
	 * FIXME Javadoc.
	 * 
	 * @param propertyURI
	 * @param domainRscURI
	 * @param rangeRscURI
	 */
	public void assertObjectTypeProperty(String propertyURI,
			String domainRscURI, String rangeRscURI) {
		ObjectProperty property = ontModel.getObjectProperty(propertyURI);

		// create domain resource
		Resource domain = ResourceFactory.createResource(domainRscURI);

		// create range resource
		Resource range = ResourceFactory.createResource(rangeRscURI);
		assertNotNull(property);

		if (domainRscURI != null) {
			// Domain test
			assertEquals(domain, property.getDomain());
		}

		if (rangeRscURI != null) {
			// Range test
			assertEquals(range, property.getRange());
		}
	}

	/**
	 * Creates a file object for the given local ontology file URI.
	 * 
	 * @param ontURI
	 *            URI of the local file.
	 * @return file object for the ontology.
	 * @throws URISyntaxException
	 */
	public static File getFile(String ontURI) throws URISyntaxException {
		return new File(new URI(ontURI));
	}
}

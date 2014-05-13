package com.galaksiya.social.linker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;

public class LocationLinkerTest {

	private static final String CORRECT_BUCA_IZMIR_PATTERN = "Buca, İzmir";

	private static final String DEFECTED_BUCA_IZMIR_PATTERN = "Boca, İzmir";

	@Test
	public void linkLocationWithCorrectName() throws Exception {
		// check whether location linker can link location property.
		Property linkProperty = CommonOntologyVocabulary.LOCATION_PRP;
		// create sample resource and its properties to link with linker
		Resource subjectRscToBeLinked = createSampleResourceToLink(
				linkProperty,
				LinkerTestContants.SAMPLE_LOCATION_OBJECT_RSC_URI,
				CORRECT_BUCA_IZMIR_PATTERN);

		// try to link appropriate statements of user
		linkResource(subjectRscToBeLinked);

		// check whether subject resource has location property with linked
		// value.
		assertTrue(subjectRscToBeLinked.hasProperty(linkProperty,
				LinkerTestContants.DBPEDIA_BUCA_RSC));

		// check whether subject resource has location property with old value.
		assertFalse(subjectRscToBeLinked.hasProperty(linkProperty,
				LinkerTestContants.SAMPLE_LOCATION_OBJECT_RSC));
	}

	private void linkResource(Resource subjectRscToBeLinked) throws Exception {
		LinkerTestUtility.executeLinker(subjectRscToBeLinked,
				new LocationLinker());
	}

	@Test
	public void linkHometownWithCorrectName() throws Exception {
		// check whether location linker can link hometown property.
		Property linkProperty = CommonOntologyVocabulary.HOMETOWN_PRP;
		// create sample resource and its properties to link with linker
		Resource subjectRscToBeLinked = createSampleResourceToLink(
				linkProperty,
				LinkerTestContants.SAMPLE_LOCATION_OBJECT_RSC_URI,
				CORRECT_BUCA_IZMIR_PATTERN);

		// try to link appropriate statements of user
		linkResource(subjectRscToBeLinked);

		// check whether subject resource has location property with linked
		// value.
		assertTrue(subjectRscToBeLinked.hasProperty(linkProperty,
				LinkerTestContants.DBPEDIA_BUCA_RSC));

		// check whether subject resource has location property with old value.
		assertFalse(subjectRscToBeLinked.hasProperty(linkProperty,
				LinkerTestContants.SAMPLE_LOCATION_OBJECT_RSC));
	}

	@Test
	public void linkLocationWithDefectedName() throws Exception {
		// check whether location linker can link hometown property.
		Property linkProperty = CommonOntologyVocabulary.HOMETOWN_PRP;
		// create sample resource and its properties to link with linker
		Resource subjectRscToBeLinked = createSampleResourceToLink(
				linkProperty,
				LinkerTestContants.SAMPLE_LOCATION_OBJECT_RSC_URI,
				DEFECTED_BUCA_IZMIR_PATTERN);

		// try to link appropriate statements of user
		linkResource(subjectRscToBeLinked);

		// check whether subject resource has location property with linked
		// value.
		assertTrue(subjectRscToBeLinked.hasProperty(linkProperty,
				LinkerTestContants.DBPEDIA_BUCA_RSC));

		// check whether subject resource has location property with old value.
		assertFalse(subjectRscToBeLinked.hasProperty(linkProperty,
				LinkerTestContants.SAMPLE_LOCATION_OBJECT_RSC));
	}

	@Test
	public void linkHometownWithDefectedName() throws Exception {
		// check whether location linker can link hometown property.
		Property linkProperty = CommonOntologyVocabulary.HOMETOWN_PRP;
		// create sample resource and its properties to link with linker
		Resource subjectRscToBeLinked = createSampleResourceToLink(
				linkProperty,
				LinkerTestContants.SAMPLE_LOCATION_OBJECT_RSC_URI,
				DEFECTED_BUCA_IZMIR_PATTERN);

		// try to link appropriate statements of user
		linkResource(subjectRscToBeLinked);

		// check whether subject resource has location property with linked
		// value.
		assertTrue(subjectRscToBeLinked.hasProperty(linkProperty,
				LinkerTestContants.DBPEDIA_BUCA_RSC));

		// check whether subject resource has location property with old value.
		assertFalse(subjectRscToBeLinked.hasProperty(linkProperty,
				LinkerTestContants.SAMPLE_LOCATION_OBJECT_RSC));
	}

	/**
	 * This method create sample subject resource to link.
	 * 
	 * @param linkProperty
	 * @param tempLocationURI
	 * @param locationName
	 * @return
	 */
	private Resource createSampleResourceToLink(Property linkProperty,
			String tempLocationURI, String locationName) {

		// create sample model
		Model model = ModelFactory.createDefaultModel();

		// create object resource
		Resource locationRsc = model.createResource(tempLocationURI,
				CommonOntologyVocabulary.PLACE_RSC);
		locationRsc.addProperty(FOAF.name, locationName);

		// create subject resource
		Resource subjectRsc = model
				.createResource(LinkerTestContants.SAMPLE_SUBJECT_RSC_URI);
		subjectRsc.addProperty(linkProperty, locationRsc);

		return subjectRsc;
	}

}

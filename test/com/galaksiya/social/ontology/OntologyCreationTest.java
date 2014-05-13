package com.galaksiya.social.ontology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URISyntaxException;

import org.junit.Before;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.util.FileUtils;

public abstract class OntologyCreationTest {

	/**
	 * ontology creator which will create ontology that will be checked
	 */
	protected OntologyCreator creator;

	/**
	 * ontology test utility to check properties created in ontology
	 */
	protected OntologyTestUtility ontologyTestUtility;

	/**
	 * ontology creator that is specific for each ontology type
	 * 
	 * @return {@link OntologyCreator} instance
	 */
	protected abstract OntologyCreator getOntologyCreator();

	/**
	 * ontology uri that is specific for each ontology type
	 * 
	 * @return ontology uri
	 */
	protected abstract String getOntURI();

	@Before
	public void before() throws Exception {
		// initialize ontology creator
		creator = getOntologyCreator();
		// overwrite ontology into same file
		overWriteToExistingFile();
		// initialize the ontology test utility
		ontologyTestUtility = new OntologyTestUtility(getSchemaOntModel());
	}

	/**
	 * Returns the ontology model of the schema creator.
	 * 
	 * @return created {@link OntModel} instance
	 */
	public OntModel getSchemaOntModel() {
		return creator.getCreatedSchema();
	}

	/**
	 * this method overwrites newly created ontology file to existing ontology
	 * file specific in given folder if the folder exists
	 * 
	 * @throws FileNotFoundException
	 * @throws URISyntaxException
	 */
	protected void overWriteToExistingFile() throws FileNotFoundException,
			URISyntaxException {
		// get ont file
		File ontFile = OntologyTestUtility.getFile(getOntURI());
		// check if parent folder of this file exists
		if (ontFile.getParentFile().exists()) {
			// overwrite new file into old file
			creator.getCreatedSchema().write(new FileOutputStream(ontFile),
					FileUtils.langXMLAbbrev);
		}
	}

}

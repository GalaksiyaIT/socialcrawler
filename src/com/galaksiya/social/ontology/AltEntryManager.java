package com.galaksiya.social.ontology;

import java.io.File;

import org.apache.log4j.Logger;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.Ontology;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * This class adds alt entries for ontologies which are imported in the social
 * semantic ontology. This class assumes that the given ontology model includes
 * social semantic schema.
 */
public class AltEntryManager {

	private final OntModel ontModel;

	public AltEntryManager(OntModel ontModel) {
		this.ontModel = ontModel;
	}

	private Logger log = Logger.getLogger(AltEntryManager.class);

	/**
	 * This method operates adding imports and alt entries in order
	 * 
	 * @param ontologyURI
	 */
	public void importWithAltEntries(String ontologyURI) {
		// add alt entries...
		addAltEntriesOnTheWeb();
		// import ontologies...
		addImports(ontologyURI);
	}

	/**
	 * This method operates importerOntology imports related importedOntology
	 * adding localFilePath for it.
	 * 
	 * @param importerOntologyURI
	 * @param importedOntologyURI
	 * @param localFileURI
	 */
	public void importWithAltEntry(String importerOntologyURI,
			String importedOntologyURI, String localFileURI) {
		// add alt enrty...
		addAltEntrySpecifically(importedOntologyURI, localFileURI);
		// import related ontology
		addImport(importerOntologyURI, importedOntologyURI);
	}

	/**
	 * this method operates importing related ontology with {@link OntModel}
	 * field
	 * 
	 * @param importerOntologyURI
	 * @param importedOntologyURI
	 */
	private void addImport(String importerOntologyURI,
			String importedOntologyURI) {
		// enabling dynamic imports property
		ontModel.setDynamicImports(true);
		// creating resource to be imported
		Resource importedRsc = ontModel.createResource(importedOntologyURI);
		// creating importer ontology which will be import another
		Ontology importerOntology = ontModel
				.createOntology(importerOntologyURI);
		// importing ontology resource to be imported
		importerOntology.addImport(importedRsc);
	}

	/**
	 * this method adds {@link OntModel} field to be imported ontology with
	 * local file path
	 * 
	 * @param importedOntologyURI
	 * @param localFileURI
	 */
	private void addAltEntrySpecifically(String importedOntologyURI,
			String localFileURI) {
		// getting ontDocumentManager to add alt entry
		OntDocumentManager documentManager = ontModel.getDocumentManager();
		// adding alt entry
		documentManager.addAltEntry(importedOntologyURI, localFileURI);
	}

	/**
	 * This method uses {@link OntDocumentManager} instance of {@link OntModel}
	 * field instance and adds alt entry URIs using this
	 * {@link OntDocumentManager} instance
	 */
	public void addAltEntriesOnTheWeb() {
		// add schema altentry
		OntDocumentManager documentManager = ontModel.getDocumentManager();

		documentManager.addAltEntry(CommonOntologyVocabulary.COSOSE_BASE_URI,
				getSosepOntURI());

		documentManager.addAltEntry(CommonOntologyVocabulary.GEODATA_BASE_URI,
				getGeoOntURI());

		documentManager.addAltEntry(CommonOntologyVocabulary.FAMILY_BOND_URI,
				getFamilyOntURI());

		documentManager.addAltEntry(CommonOntologyVocabulary.CV_RDFS_URI,
				getCvOntURI());

		documentManager.addAltEntry(CommonOntologyVocabulary.FOAF_BASE_URI,
				getFoafOntURI());

		documentManager.addAltEntry(CommonOntologyVocabulary.SIOC_BASE_URI,
				getSiocOntURI());

		documentManager.addAltEntry(CommonOntologyVocabulary.TAG_BASE_URI,
				getTagOntURI());

		documentManager.addAltEntry(CommonOntologyVocabulary.DBPEDIA_BASE_URI,
				getDbpediaOntURI());

		documentManager.addAltEntry(
				CommonOntologyVocabulary.CV_EXTENDING_ONTOLOGY_URI,
				getCVExtendingOntURI());

		documentManager.addAltEntry(
				CommonOntologyVocabulary.LGDO_EXTENDING_ONTOLOGY_URI,
				getLGDOExtendingOntURI());

		documentManager.addAltEntry(
				CommonOntologyVocabulary.TAG_EXTENDING_ONTOLOGY_URI,
				getTAGExtendingOntURI());

		log.info("Alt entries are added to the ontology model.");
	}

	/**
	 * Name of the folder which contains schema files.
	 */
	public static final String ONTOLOGY_FOLDER = "ontology/";

	public static String getTAGExtendingOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "extendingTAG.owl");
	}

	public static String getLGDOExtendingOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "extendingLGDO.owl");
	}

	public static String getCVExtendingOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "extendingCV.owl");
	}

	public static String getSosepOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "coSoSe.owl");
	}

	/**
	 * Returns the ontology file URI of FamilyBond
	 * 
	 * @return
	 */
	public static String getFamilyOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "familyBond.owl");
	}

	/**
	 * Returns the ontology file URI of CV
	 * 
	 * @return
	 */
	public static String getCvOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "cv.owl");
	}

	/**
	 * Returns the ontolgy file URI of Dbpedia
	 * 
	 * @return
	 */
	public String getDbpediaOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "dbpedia.owl");
	}

	/**
	 * Returns the ontology file URI of TAG
	 * 
	 * @return
	 */
	public static String getTagOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "tag.rdf");
	}

	/**
	 * Returns the ontology file URI of SIOC
	 * 
	 * @return
	 */
	public static String getSiocOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "sioc.rdf");
	}

	/**
	 * Returns the ontology file URI of FOAF.
	 * 
	 * @return
	 */
	public static String getFoafOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "foaf.rdf");
	}

	/**
	 * Returns the ontology file URI of Facebook schema.
	 * 
	 * @return
	 */
	public static String getFacebookOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "semanticFB.owl");
	}

	/**
	 * Returns the ontology file URI of Foursquare schema
	 * 
	 * @return
	 */
	public static String getFoursquareOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "semanticFS.owl");
	}

	/**
	 * returns the ontology file URI of Linkedin schema.
	 * 
	 * @return
	 */
	public static String getLinkedinOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "semanticLI.owl");
	}

	/**
	 * Returns the ontology file URI for the GeoData.
	 * 
	 * @return
	 */
	public static String getGeoOntURI() {
		return AltEntryManager.getOntURI(ONTOLOGY_FOLDER + "geo.owl");
	}

	public void addImports(String ontologyURI) {
		ontModel.setDynamicImports(true);

		// firstly creating resources that will be imported
		Resource FOAFresource = ontModel
				.createResource(CommonOntologyVocabulary.FOAF_BASE_URI);
		Resource SIOCresource = ontModel
				.createResource(CommonOntologyVocabulary.SIOC_BASE_URI);
		Resource TAGresource = ontModel
				.createResource(CommonOntologyVocabulary.TAG_BASE_URI);
		Resource DBPEDIAresource = ontModel
				.createResource(CommonOntologyVocabulary.DBPEDIA_BASE_URI);
		Resource CVresource = ontModel
				.createResource(CommonOntologyVocabulary.CV_RDFS_URI);

		Resource GEODATAresource = ontModel
				.createResource(CommonOntologyVocabulary.GEODATA_BASE_URI);

		Resource FAMILYTREEresource = ontModel
				.createResource(CommonOntologyVocabulary.FAMILY_BOND_URI);

		Resource ExtendedCVresource = ontModel
				.createResource(CommonOntologyVocabulary.CV_EXTENDING_ONTOLOGY_URI);

		Resource ExtendedTAGresource = ontModel
				.createResource(CommonOntologyVocabulary.TAG_EXTENDING_ONTOLOGY_URI);

		Resource ExtendedLGDOresource = ontModel
				.createResource(CommonOntologyVocabulary.LGDO_EXTENDING_ONTOLOGY_URI);

		// creating an ontology with given URI
		Ontology ont = ontModel.createOntology(ontologyURI);

		// adding resources and opertaing imports
		ont.addImport(FOAFresource);
		ont.addImport(SIOCresource);
		ont.addImport(TAGresource);
		ont.addImport(DBPEDIAresource);
		ont.addImport(GEODATAresource);
		ont.addImport(CVresource);
		ont.addImport(FAMILYTREEresource);
		ont.addImport(ExtendedCVresource);
		ont.addImport(ExtendedTAGresource);
		ont.addImport(ExtendedLGDOresource);
	}

	/**
	 * Constructs the ontology URI.
	 * 
	 * @param fileRelativePath
	 *            relative file path of the ontology in the application.
	 * @return constructed ontology URI.
	 */
	private static String getOntURI(String fileRelativePath) {
		return new File("/home/etmen/workspace/sociallinker/WebContent/"
				+ fileRelativePath).toURI().toString();
	}
}

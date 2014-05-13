package com.galaksiya.social.linker;

import java.text.MessageFormat;
import java.util.List;

import net.ricecode.similarity.JaroWinklerStrategy;

import org.apache.log4j.Logger;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * Abstract class for the linker classes. A linker class should extend
 * {@link #find(String)} method.
 * 
 * Linker inheritance must fit the following rules:
 * <p>
 * <li>A linker must be annotated with {@link LinkageMap} annotation correctly.
 * 
 * @author etmen
 * 
 */
public abstract class Linker implements ResourceFinder {

	private Logger logger;

	public void link(Statement statement) throws Exception {
		// get object value of statement
		RDFNode object = statement.getObject();

		// this linker works needs resource objects and in model subjects...
		if (object.isResource() && statement.getSubject().getModel() != null) {
			// cast it to resource
			Resource objectRsc = object.asResource();

			String namePrpValue = null;
			// try to get name property
			Statement nameStmt = objectRsc.getProperty(FOAF.name);
			if (nameStmt != null) {
				namePrpValue = nameStmt.getObject().asLiteral().getString();
			} else {
				getLogger()
						.warn(MessageFormat
								.format("Name property of {0} resource cannot be retrieved. This resource won't be linked.",
										objectRsc));
			}

			// get subject value of statement
			Resource subject = statement.getSubject();
			// look for resource for given name property value
			Resource foundResource = lookForResource(subject, namePrpValue);

			if (foundResource != null && !foundResource.equals(objectRsc)) {
				subject.addProperty(statement.getPredicate(), foundResource);
				// remove property with old value.
				removeResource(subject.getModel(), objectRsc);

				getLogger()
						.info(MessageFormat
								.format("Linking operation with found resource {0} successful.",
										foundResource));
			} else {
				getLogger()
						.warn(MessageFormat
								.format("Linking operation for \"{0}\" failed to find resource.",
										statement.getPredicate()));
			}
		}
	}

	/**
	 * This method looks for resource that will be matched with name value
	 * 
	 * @param subject
	 *            subject that contains model
	 * @param nameValue
	 *            name value that is key for search
	 * @return found resource
	 */
	private Resource lookForResource(Resource subject, String nameValue) {
		Resource foundResource = null;

		if (nameValue != null) {
			if (!isLiteralContainedInModel(subject.getModel(), nameValue)) {
				// add found resource with given property to subject.
				foundResource = find(nameValue);
				if (foundResource != null) {
					subject.getModel().add(foundResource,
							CommonOntologyVocabulary.SKOS_ALT_LABEL_PRP,
							nameValue);
				}
			} else {
				foundResource = getSubjectResource(subject.getModel(),
						nameValue);
			}
		}
		return foundResource;
	}

	/**
	 * Looks for given literal value in the model and returns the subject of
	 * first found one.
	 * 
	 * @param model
	 * @param literalValue
	 * @return subject of first found statement
	 */
	private Resource getSubjectResource(Model model, String literalValue) {
		List<Statement> prpStmtList = model.listStatements(null,
				CommonOntologyVocabulary.SKOS_ALT_LABEL_PRP, literalValue)
				.toList();
		return prpStmtList.get(0).getSubject();
	}

	/**
	 * This method looks for literal value in the model.
	 * 
	 * @param model
	 * @param literalValue
	 * @return true if literal found in the model, false otherwise
	 */
	private boolean isLiteralContainedInModel(Model model, String literalValue) {
		return model.contains(null,
				CommonOntologyVocabulary.SKOS_ALT_LABEL_PRP, literalValue);
	}

	/**
	 * Removes the statements about the given place resource.
	 * 
	 * @param model
	 *            model which the resource will be removed from.
	 * 
	 * @param rscToRemove
	 *            place resource to be removed.
	 */
	private void removeResource(Model model, Resource rscToRemove) {
		getLogger().info(
				MessageFormat.format("Resource that will be removed: {0}",
						rscToRemove));
		model.removeAll(null, null, rscToRemove);
		model.removeAll(rscToRemove, null, (RDFNode) null);
	}

	/**
	 * This method searches given label on given solution list, looks for
	 * similarity of label and returns the most similar resource if finds any.
	 * 
	 * @param labelToSearch
	 * @param querySolutionPattern
	 * @param model
	 * @return found {@link Resource} instance of location in dbpedia
	 */
	public Resource searchSimilarityByLabel(String labelToSearch,
			List<QuerySolution> querySolutionList, String querySolutionPattern) {
		// initial similarity is set to 0.
		double maxValue = 0;
		// initial similar resource is set to null.
		Resource mostSimilarRsc = null;
		JaroWinklerStrategy similarityFinder = new JaroWinklerStrategy();
		// iterate on these statements.
		for (QuerySolution querySolution : querySolutionList) {
			// get label value.
			String actualLabelValue = querySolution.get("label").asLiteral()
					.getString();
			// find similarity of label value with given label.
			double similarityValue = similarityFinder.score(labelToSearch,
					actualLabelValue);
			// control whether similarity is greater than thresold and maximum
			// value
			if (similarityValue > 0.85 && similarityValue > maxValue) {
				// change maximum similarity value and most similar resource
				maxValue = similarityValue;
				mostSimilarRsc = querySolution
						.getResource(querySolutionPattern);
			}
		}
		return mostSimilarRsc;
	}

	/**
	 * This method searches given label on given Model, looks for similarity of
	 * label and returns the most similar resource if finds any.
	 * 
	 * @param label
	 * @param model
	 * @return found {@link Resource} instance of location in dbpedia
	 */
	public Resource searchSimilarityByLabelInModel(String label, Model model) {
		// initial similarity is set to 0.
		double maxValue = 0;
		// initial similar resource is set to null.
		Resource mostSimilarRsc = null;
		JaroWinklerStrategy similarityFinder = new JaroWinklerStrategy();
		// list rdfs:label statements in given model.
		List<Statement> labelStmtList = model.listStatements((Resource) null,
				RDFS.label, (RDFNode) null).toList();
		// iterate on these statements.
		for (Statement labelStmt : labelStmtList) {
			// get label value.
			String labelValue = labelStmt.getObject().asLiteral().getString();
			// find similarity of label value with given label.
			double similarityValue = similarityFinder.score(label, labelValue);
			// control whether similarity is greater than thresold and maximum
			// value
			if (similarityValue > 0.85 && similarityValue > maxValue) {
				// change maximum similarity value and most similar resource
				maxValue = similarityValue;
				mostSimilarRsc = labelStmt.getSubject();
			}
		}
		return mostSimilarRsc;
	}

	/**
	 * Creates an instance of Logger for thw current {@link Linker} instance and
	 * returns it.
	 * 
	 * @return logger.
	 */
	protected Logger getLogger() {
		if (logger == null) {
			logger = Logger.getLogger(this.getClass());
		}
		return logger;
	}
}

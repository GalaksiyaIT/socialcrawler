package com.galaksiya.social.linker;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.galaksiya.social.ontology.vocabulary.BankVocabulary;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.OWL;

public class BankPersonLinker {
	private Logger logger = Logger.getLogger(BankPersonLinker.class);

	/**
	 * It searches for a person in Bank dataset according to FOAF:name property
	 * of social person resource, then if it finds such as person in bank
	 * dataset then links social person resource to bank dataset.
	 * 
	 * @param socialPerson
	 *            the person {@link Resource} instance which will be linked to
	 *            the bank dataset.
	 */
	public void linkPersonToBankDataset(Resource socialPerson) {
		logger.info(MessageFormat.format(
				"User \"{0}\" will try to be linked with bank dataset",
				socialPerson));
		// construct bank query
		Statement foafNameStmt = socialPerson.getProperty(FOAF.name);
		if (foafNameStmt != null) {
			if (!isDatasetAvailable()) {
				logger.info(MessageFormat.format(
						"No bank person is linked to {0}.", socialPerson));
				return;
			}
			String query = prepareLinkQuery(foafNameStmt);
			// execute query and get first result if there is such any.
			ResultSet resultSet = QueryExecutionFactory.sparqlService(
					BankVocabulary.BANK_ENDPOINT, query).execSelect();
			if (resultSet.hasNext()) {
				RDFNode person = resultSet.next().get("person");
				if (person.isResource()) {
					// add sameAs property as linking
					socialPerson.addProperty(OWL.sameAs, person.asResource());
					logger.info(MessageFormat.format(
							"User \"{0}\" has been successfully linked.",
							socialPerson));
				} else {
					logger.warn(MessageFormat
							.format("User \"{0}\" has not been linked because result returning from Bank dataset is not a resource.",
									socialPerson));
				}
			} else {
				logger.warn(MessageFormat
						.format("User \"{0}\" has not been linked because no result was returned from Bank dataset.",
								socialPerson));
			}
		} else {
			logger.warn(MessageFormat
					.format("User \"{0}\" has no FOAF:name property, and she has not been linked.",
							socialPerson));
		}
	}

	/**
	 * This method constructs query that checks whether the person with the
	 * given name is contained.
	 * 
	 * @param foafNameStmt
	 * @return
	 */
	private String prepareLinkQuery(Statement foafNameStmt) {
		String query = "SELECT ?person WHERE { ?person <"
				+ BankVocabulary.FINANCE_NAME_PRP_URI + "> \""
				+ foafNameStmt.getObject().asLiteral().getString() + "\"}";
		return query;
	}

	/**
	 * Checks whether endpoint of dataset is available or not.
	 * 
	 * @return
	 */
	private boolean isDatasetAvailable() {
		try {
			String askQuery = "ASK {?s ?p ?o}";
			boolean anyTripleContained = QueryExecutionFactory.sparqlService(
					BankVocabulary.BANK_ENDPOINT, askQuery).execAsk();
			if (anyTripleContained) {
				return true;
			} else {
				logger.warn("There is no triple contained in the bank dataset.");
			}
		} catch (Exception e) {
			logger.warn(MessageFormat.format(
					"Bank dataset is unavailable. {0}", e.getMessage()));
		}
		return false;
	}

}

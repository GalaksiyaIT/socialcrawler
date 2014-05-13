package com.galaksiya.social.ontology.samplequery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.galaksiya.social.TestUtil;
import com.galaksiya.social.fetcher.LinkedinIndividualCreator;
import com.galaksiya.social.fetcher.SocialAPIException;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.query.SampleQueries;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.galaksiya.social.vocabulary.LinkedinTestVocabulary;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * TODO Linkedin'de bilgisi değişmeyen test kullanıcısı açılmalı!
 * <p>
 * This class tests Linkedin sample queries.
 */
public class LinkedinSampleQueryTest {

	/**
	 * Individual creator to create test data.
	 */
	private static LinkedinIndividualCreator creator;
	private static Resource linkedinIndividual;

	/**
	 * Creates individual creator before test.
	 * 
	 * @throws SocialAPIException
	 */
	@BeforeClass
	public static void before() throws SocialAPIException {
		creator = new LinkedinIndividualCreator(CommonTestVocabulary.BASE_URI);
		linkedinIndividual = creator.createPersonIndividual(TestUtil
				.createLinkedinPersonGeorge());
	}

	/**
	 * this method executes positions select query for a linkedin person
	 * individual and controls query solutions
	 * 
	 * @throws Exception
	 */
	@Test
	public void selectQueryPositions() throws Exception {
		assertNotNull(linkedinIndividual);

		QueryExecution qexec = QueryExecutionFactory.create(
				QueryFactory.create(SampleQueries.POSITIONS_SELECT_QUERY),
				creator.getModel());
		ResultSet nameResultSetActual = qexec.execSelect();
		assertNotNull(nameResultSetActual);

		List<String> companies = Arrays
				.asList(LinkedinTestVocabulary.COMPANIES);
		List<String> titles = Arrays.asList(LinkedinTestVocabulary.TITLES);

		while (nameResultSetActual.hasNext()) {
			QuerySolution solution = (QuerySolution) nameResultSetActual.next();

			// assert linkedin individual rdf:type
			RDFNode personNode = solution.get("person");
			assertNotNull(personNode);
			Statement typeStmt = personNode.asResource().getProperty(RDF.type);
			RDFNode typeNode = typeStmt.getObject();
			assertNotNull(typeNode);
			assertEquals(FOAF.Person, typeNode.asResource());

			// assert personName values
			RDFNode personNameNode = solution.get("personName");
			assertNotNull(personNameNode);
			String personNameValue = personNameNode.asLiteral().getString();
			assertEquals(LinkedinTestVocabulary.PERSON_NAME, personNameValue);

			// assert companyName values
			RDFNode companyNameNode = solution.get("companyName");
			assertNotNull(companyNameNode);
			String companyNameValue = companyNameNode.asLiteral().getString();
			assertTrue(companies.contains(companyNameValue));

			// assert companyName values
			RDFNode titleNode = solution.get("title");
			assertNotNull(titleNode);
			String titleValue = titleNode.asLiteral().getString();
			assertTrue(titles.contains(titleValue));

			// assert startdate and enddate vales have been got
			RDFNode startDateNode = solution.get("startDate");
			assertNotNull(startDateNode);

			RDFNode endDateNode = solution.get("endDate");
			assertNotNull(endDateNode);
		}

	}

	/**
	 * this method executes positions construct query for a linkedin person
	 * individual and controls query solutions
	 * 
	 * @throws Exception
	 */
	@Test
	public void constructQueryPositions() throws Exception {

		Query query = QueryFactory
				.create(SampleQueries.POSITIONS_CONSTRUCT_QUERY);
		QueryExecution qexec = QueryExecutionFactory.create(query,
				creator.getModel());
		OntModel queriedModel = ModelFactory.createOntologyModel();
		queriedModel.add(qexec.execConstruct());

		StmtIterator nameStmtIter = queriedModel.listStatements(
				linkedinIndividual, FOAF.name,
				LinkedinTestVocabulary.PERSON_NAME);
		assertNotNull(nameStmtIter);
		List<Statement> nameStmtList = nameStmtIter.toList();
		assertEquals(1, nameStmtList.size());

		StmtIterator whStmtIter = queriedModel
				.listStatements(
						linkedinIndividual,
						ResourceFactory
								.createProperty(CommonOntologyVocabulary.CV_HAS_WORK_HISTORY_PRP_URI),
						(RDFNode) null);
		assertNotNull(whStmtIter);
		List<Statement> whStmtList = whStmtIter.toList();
		assertEquals(2, whStmtList.size());

		// creating titles which are expected values
		List<String> titles = Arrays.asList(LinkedinTestVocabulary.TITLES);
		// creating company names which are expected values
		List<String> companyNames = Arrays
				.asList(LinkedinTestVocabulary.COMPANIES);

		for (Statement whStmt : whStmtList) {

			// checking job titles
			RDFNode whNode = whStmt.getObject();
			assertNotNull(whNode);
			Statement titleStmt = whNode
					.asResource()
					.getProperty(
							ResourceFactory
									.createProperty(CommonOntologyVocabulary.CV_TITLE_URI));
			assertNotNull(titleStmt);
			RDFNode titleNode = titleStmt.getObject();
			assertNotNull(titleNode);
			String titleValue = titleNode.asLiteral().getString();
			assertTrue(titles.contains(titleValue));

			// checking company names
			Statement companyStmt = whNode
					.asResource()
					.getProperty(
							ResourceFactory
									.createProperty(CommonOntologyVocabulary.EMPLOYED_IN_PRP_URI));
			assertNotNull(companyStmt);
			RDFNode companyNode = companyStmt.getObject();
			assertNotNull(companyNode);
			Statement companyNameStmt = companyNode.asResource().getProperty(
					FOAF.name);
			assertNotNull(companyNameStmt);
			RDFNode companyNameNode = companyNameStmt.getObject();
			String companyNameValue = companyNameNode.asLiteral().getString();
			assertTrue(companyNames.contains(companyNameValue));
		}
	}
}

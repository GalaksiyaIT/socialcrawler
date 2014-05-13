package com.galaksiya.social.ontology.samplequery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.galaksiya.social.TestUtil;
import com.galaksiya.social.fetcher.FoursquareIndividualCreator;
import com.galaksiya.social.ontology.vocabulary.FoursquareOntologyVocabulary;
import com.galaksiya.social.query.SampleQueries;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.galaksiya.social.vocabulary.FoursquareTestVocabulary;
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
 * TODO Bu test için bir Foursquare kullanıcısı açılmalı!
 * 
 * @author etmen
 * 
 */
public class FoursquareSampleQueryTest {

	private static FoursquareIndividualCreator foursquareIndividualCreator;

	private static Resource foursquareIndividual;

	/**
	 * Initialize test utils
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void beforeClass() throws Exception {
		foursquareIndividualCreator = new FoursquareIndividualCreator(
				CommonTestVocabulary.BASE_URI);
		foursquareIndividual = foursquareIndividualCreator
				.createPersonIndividual(TestUtil.createFoursquareUser());
	}

	/**
	 * Asserts venue select query results for sample model of sample foursquare
	 * individual
	 * 
	 * @throws Exception
	 */
	@Test
	public void selectQueryCheckedInVenues() throws Exception {

		assertNotNull(foursquareIndividual);
		QueryExecution qexec = QueryExecutionFactory.create(
				QueryFactory.create(SampleQueries.SIMPLE_CHECKIN_SELECT_QUERY),
				foursquareIndividualCreator.getModel());
		ResultSet checkinQueryResultset = qexec.execSelect();
		assertNotNull(checkinQueryResultset);
		assertTrue(checkinQueryResultset.hasNext());
		List<String> venueNameList = Arrays
				.asList(FoursquareTestVocabulary.VENUE_NAMES);
		List<String> categoryNameList = Arrays
				.asList(FoursquareTestVocabulary.VENUE_CATEGORIES);
		while (checkinQueryResultset.hasNext()) {
			QuerySolution solution = (QuerySolution) checkinQueryResultset
					.next();
			// asserting person solution
			RDFNode personNode = solution.get("person");
			assertNotNull(personNode);
			assertEquals(foursquareIndividual.getURI(), personNode.asResource()
					.getURI());
			Statement rdfPersonTypeStmt = personNode.asResource().getProperty(
					RDF.type);
			assertNotNull(rdfPersonTypeStmt);
			RDFNode rdfPersonTypeNode = rdfPersonTypeStmt.getObject();
			assertNotNull(rdfPersonTypeNode);
			assertEquals(FOAF.Person, rdfPersonTypeNode.asResource());

			// asserting personName solution
			RDFNode personNameNode = solution.get("personName");
			assertNotNull(personNameNode);
			String personNameValue = personNameNode.asLiteral().getString();
			assertEquals(FoursquareTestVocabulary.PERSON_NAME, personNameValue);

			// asserting venueName solution
			RDFNode venueNameNode = solution.get("venueName");
			assertNotNull(venueNameNode);
			String venueNameValue = venueNameNode.asLiteral().getString();
			TestUtil.assertContains("In the venue list", venueNameList,
					venueNameValue);

			// asserting categoryName solution
			RDFNode categoryNameNode = solution.get("categoryName");
			assertNotNull(categoryNameNode);
			String categoryNameValue = categoryNameNode.asLiteral().getString();
			TestUtil.assertContains("In the category list", categoryNameList,
					categoryNameValue);

		}
	}

	/**
	 * Asserts venue construct query results for sample model of sample
	 * foursquare individual
	 * TODO: Central Park checkininde eksik bilgiler olduğundan bu checkin sorguya cevap olarak gelmiyor.
	 * @throws Exception
	 */
	@Test
	public void constructQueryCheckedInVenues() throws Exception {

		assertNotNull(foursquareIndividual);

		// executing construct query
		Query query = QueryFactory
				.create(SampleQueries.SIMPLE_CHECKIN_CONSTRUCT_QUERY);
		QueryExecution qexec = QueryExecutionFactory.create(query,
				foursquareIndividualCreator.getModel());
		OntModel queriedModel = ModelFactory.createOntologyModel();
		queriedModel.add(qexec.execConstruct());
		assertTrue(!queriedModel.isEmpty());

		// assert there is only 1 individual who has this name
		StmtIterator personNameStmtIter = queriedModel.listStatements(
				foursquareIndividual, FOAF.name,
				FoursquareTestVocabulary.PERSON_NAME);
		assertNotNull(personNameStmtIter);
		List<Statement> personNameStmtList = personNameStmtIter.toList();
		assertEquals(1, personNameStmtList.size());

		// assert checkin count and checkin venueNames
		StmtIterator checkinStmtIter = queriedModel
				.listStatements(
						foursquareIndividual,
						ResourceFactory
								.createProperty(FoursquareOntologyVocabulary.CHECKIN_PRP_URI),
						(RDFNode) null);
		assertNotNull(checkinStmtIter);
		List<Statement> checkinStmtList = checkinStmtIter.toList();
		assertEquals(3, checkinStmtList.size());

		// generating expected venue name list
		List<String> venueNameList = Arrays
				.asList(FoursquareTestVocabulary.VENUE_NAMES);

		// generating expected venue category
		List<String> categoryNameList = Arrays
				.asList(FoursquareTestVocabulary.VENUE_CATEGORIES);

		// asserting checkins
		for (Statement checkinStmt : checkinStmtList) {
			RDFNode checkinNode = checkinStmt.getObject();
			assertNotNull(checkinNode);
			Statement venueStmt = checkinNode
					.asResource()
					.getProperty(
							ResourceFactory
									.createProperty(FoursquareOntologyVocabulary.VENUE_PRP_URI));
			assertNotNull(venueStmt);
			RDFNode venueNode = venueStmt.getObject();
			assertNotNull(venueNode);

			// checking venue names
			Statement venueNameStmt = venueNode.asResource().getProperty(
					FOAF.name);
			assertNotNull(venueNameStmt);
			RDFNode venueNameNode = venueNameStmt.getObject();
			assertNotNull(venueNameNode);
			String venueNameValue = venueNameNode.asLiteral().getString();

			TestUtil.assertContains("In the venue list", venueNameList,
					venueNameValue);

			// getting categories
			Statement categoryStmt = venueNode
					.asResource()
					.getProperty(
							ResourceFactory
									.createProperty(FoursquareOntologyVocabulary.CATEGORY_PRP_URI));
			assertNotNull(categoryStmt);
			RDFNode categoryNode = categoryStmt.getObject();
			assertNotNull(categoryNode);
			// checking category names
			Statement categoryNameStmt = categoryNode.asResource().getProperty(
					FOAF.name);
			assertNotNull(categoryNameStmt);
			RDFNode categoryNameNode = categoryNameStmt.getObject();
			assertNotNull(categoryNameNode);
			String categoryNameValue = categoryNameNode.asLiteral().getString();
			TestUtil.assertContains("In the category list", categoryNameList,
					categoryNameValue);

		}

	}
}

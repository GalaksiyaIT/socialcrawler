package com.galaksiya.social.ontology.samplequery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.galaksiya.social.TestUtil;
import com.galaksiya.social.fetcher.FacebookIndividualCreator;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.galaksiya.social.query.SampleQueries;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.galaksiya.social.vocabulary.FacebookTestVocabulary;
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

public class FacebookSampleQueryTest {

	private static FacebookIndividualCreator creator;

	private static Resource facebookIndividual;

	@BeforeClass
	public static void beforeClass() throws Exception {
		creator = new FacebookIndividualCreator(CommonTestVocabulary.BASE_URI);
		facebookIndividual = creator
				.createPersonIndividual(TestUtil
						.createFacebookUser(FacebookTestVocabulary.ACCESS_TOKEN_CONTAINS_ALL_INFORMATION));
	}

	/**
	 * This method executes movie like select query and asserts results of it
	 * 
	 * @throws Exception
	 */
	@Test
	public void selectQueryLikedMovies() throws Exception {
		assertNotNull(facebookIndividual);
		// executing select query
		QueryExecution qexec = QueryExecutionFactory.create(
				QueryFactory.create(SampleQueries.MOVIE_LIKE_QUERY_SELECT),
				creator.getModel());
		ResultSet movieQueryResultset = qexec.execSelect();
		assertNotNull(movieQueryResultset);
		assertTrue(movieQueryResultset.hasNext());

		// generating expected control values
		ArrayList<String> movieNames = new ArrayList<String>();
		movieNames.add(FacebookTestVocabulary.MOVIE_LIKE_1);
		movieNames.add(FacebookTestVocabulary.MOVIE_LIKE_2);
		movieNames.add(FacebookTestVocabulary.MOVIE_LIKE_3);
		movieNames.add(FacebookTestVocabulary.MOVIE_LIKE_4);

		// asserting each result value
		while (movieQueryResultset.hasNext()) {
			QuerySolution movieLikeSolution = (QuerySolution) movieQueryResultset
					.next();

			// asserting "person" value
			RDFNode personNode = movieLikeSolution.get("person");
			assertNotNull(personNode);
			assertEquals(facebookIndividual.getURI(), personNode.asResource()
					.getURI());

			// asserting "personName" value
			RDFNode personNameNode = movieLikeSolution.get("personName");
			assertNotNull(personNameNode);
			String personName = personNameNode.asLiteral().getString();
			assertEquals(FacebookTestVocabulary.PERSON_NAME, personName);

			// asserting "movieName" value
			RDFNode movieNode = movieLikeSolution.get("movieName");
			assertNotNull(movieNode);
			String movie = movieNode.asLiteral().getString();
			TestUtil.assertContains("In the given movie list: ", movieNames,
					movie);
		}

	}

	/**
	 * This method execute movie like construct query and asserts results of it
	 * 
	 * @throws Exception
	 */
	@Test
	public void constructQueryLikedMovies() throws Exception {

		// generating expected control values
		ArrayList<String> movieNames = new ArrayList<String>();
		movieNames.add(FacebookTestVocabulary.MOVIE_LIKE_1);
		movieNames.add(FacebookTestVocabulary.MOVIE_LIKE_2);
		movieNames.add(FacebookTestVocabulary.MOVIE_LIKE_3);
		movieNames.add(FacebookTestVocabulary.MOVIE_LIKE_4);

		assertNotNull(facebookIndividual);

		// executing construct query
		Query query = QueryFactory
				.create(SampleQueries.MOVIE_LIKE_QUERY_CONSTRUCT);
		QueryExecution qexec = QueryExecutionFactory.create(query,
				creator.getModel());
		OntModel queriedModel = ModelFactory.createOntologyModel();
		queriedModel.add(qexec.execConstruct());
		assertTrue(!queriedModel.isEmpty());

		// asserting "?person foaf:name ?personName"
		StmtIterator namePrpStmtIterOfFacebookIndv = queriedModel
				.listStatements(facebookIndividual, FOAF.name,
						FacebookTestVocabulary.PERSON_NAME);
		assertTrue(namePrpStmtIterOfFacebookIndv.hasNext());
		assertEquals(1, namePrpStmtIterOfFacebookIndv.toList().size());

		// asserting "?person facebook:likes ?movie" statements
		StmtIterator likePrpStmtIterOfFacebookIndv = queriedModel
				.listStatements(facebookIndividual, ResourceFactory
						.createProperty(FacebookOntologyVocabulary.LIKES),
						(RDFNode) null);
		assertTrue(likePrpStmtIterOfFacebookIndv.hasNext());
		List<Statement> likePrpStmts = likePrpStmtIterOfFacebookIndv.toList();
		assertNotNull(likePrpStmts);
		assertEquals(4, likePrpStmts.size());

		// asserting "?movie foaf:name ?movieName"
		for (Statement likePrpStmt : likePrpStmts) {
			RDFNode likeNode = likePrpStmt.getObject();
			Statement namePrpStmtOfLike = likeNode.asResource().getProperty(
					FOAF.name);
			assertNotNull(namePrpStmtOfLike);
			RDFNode likeNameNode = namePrpStmtOfLike.getObject();
			String likeString = likeNameNode.asLiteral().getString();
			TestUtil.assertContains("In the given like list: ", movieNames,
					likeString);
		}

	}
}

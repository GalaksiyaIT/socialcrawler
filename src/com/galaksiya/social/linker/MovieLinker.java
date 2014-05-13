package com.galaksiya.social.linker;

import java.util.ArrayList;
import java.util.List;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Resource;

@LinkageMap(properties = { FacebookOntologyVocabulary.LIKES }, classes = { CommonOntologyVocabulary.MOVIE_URI })
public class MovieLinker extends Linker {

	private static final String SPARQL_ENDPOINT_OF_LMDB = "http://data.linkedmdb.org/sparql";

	/**
	 * This method returns all movies contained in LinkedMDB.
	 * 
	 * @return {@link List} of {@link QuerySolution}s that contains movie
	 *         resources and their labels.
	 */
	private List<QuerySolution> getAllMovieQuerySolutions() {
		String allMovieQuery = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "SELECT ?movie ?label WHERE {"
				+ "?movie rdf:type <http://data.linkedmdb.org/resource/movie/film>."
				+ "?movie rdfs:label ?label.}";
		List<QuerySolution> allMovieSolutions = new ArrayList<QuerySolution>();
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(
				SPARQL_ENDPOINT_OF_LMDB, allMovieQuery);
		ResultSet resultSet = queryExecution.execSelect();
		while (resultSet.hasNext()) {
			QuerySolution querySolution = (QuerySolution) resultSet.next();
			allMovieSolutions.add(querySolution);
		}
		return allMovieSolutions;
	}

	/**
	 * This method first looks for movie resource on LinkedMDB directly, if it
	 * cannot found directly then it looks for similar ones in all movies in
	 * LinkedMDB.
	 * 
	 * @param movieName
	 *            movie name to search
	 * @return
	 */
	private Resource lookForMovieResource(String movieName) {
		// initialize foundPlace by null.
		Resource foundPlace = null;
		foundPlace = searchMovieOnLinkedMDB(movieName);
		if (foundPlace == null) {
			foundPlace = searchSimilarityByLabel(movieName,
					getAllMovieQuerySolutions(), "movie");
		}
		return foundPlace;
	}

	/**
	 * This method searches given movie name on LinkedMDB and returns its
	 * resource if finds it.
	 * 
	 * @param movieName
	 * @return found {@link Resource} instance of movie in LinkedMDB
	 */
	public Resource searchMovieOnLinkedMDB(String movieName) {
		String query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "SELECT ?movie WHERE {?movie rdfs:label \""
				+ movieName
				+ "\". ?movie rdf:type <http://data.linkedmdb.org/resource/movie/film>}";
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				SPARQL_ENDPOINT_OF_LMDB, query);
		ResultSet set = exec.execSelect();
		if (!set.hasNext()) {
			return null;
		} else {
			return set.next().getResource("movie");
		}
	}

	@Override
	public Resource find(String resourceName) {
		return lookForMovieResource(resourceName);
	}

}

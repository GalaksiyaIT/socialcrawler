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
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

@LinkageMap(properties = { FacebookOntologyVocabulary.LIKES }, classes = { CommonOntologyVocabulary.MUSIC_URI })
public class MusicLinker 
//extends Linker 
{

	public static final String SPARQL_ENDPOINT_OF_MUSICBRAINZ = "http://dbtune.org/musicbrainz/sparql";

//	@Override
	public Resource find(String musicName) {
		return lookForResource(musicName);
	}

	/**
	 * This method returns all musics with given type contained in MusicBrainz.
	 * 
	 * @param type
	 * 
	 * @return {@link List} of {@link QuerySolution}s that contains movie
	 *         resources and their labels.
	 */
	protected List<QuerySolution> getAllMusicQuerySolutions(String type) {
		String allMusicQuery = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "SELECT ?music ?label WHERE {"
				+ "?music rdf:type <"
				+ type
				+ ">." + "?music rdfs:label ?label.}";
		List<QuerySolution> allMovieSolutions = new ArrayList<QuerySolution>();
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(
				SPARQL_ENDPOINT_OF_MUSICBRAINZ, allMusicQuery);
		ResultSet resultSet = queryExecution.execSelect();
		while (resultSet.hasNext()) {
			QuerySolution querySolution = (QuerySolution) resultSet.next();
			allMovieSolutions.add(querySolution);
		}
		return allMovieSolutions;
	}

	/**
	 * This method looks for place in directly given place name, if it doesn't
	 * found same name, it looks for similar one by order of in districts,
	 * towns, cities, and countries.
	 * 
	 * @param musicName
	 * 
	 * @return found {@link Resource} instance of location in dbpedia
	 */
	private Resource lookForResource(String musicName) {
		// initialize searchLevel by 0 and foundMusic by null.
		Resource foundMusic = null;
//		foundMusic = searchMovieOnLinkedMDB(musicName);
//		if (foundMusic == null) {
//			foundMusic = searchSimilarityByLabel(musicName,
//					getAllMovieQuerySolutions(), "movie");
//		}
		return foundMusic;
	}

	/**
	 * This method searches given music name on MusicBrainz and returns its
	 * resource if finds it.
	 * 
	 * @param musicName
	 * @return found {@link Resource} instance of movie in MusicBrainz
	 */
	protected Resource searchMusicOnMusicBrainz(String musicName, String typeURI) {
		String musicQuery = "PREFIX rdfs:<" + RDFS.getURI() + "> "
				+ "PREFIX rdf:<" + RDF.getURI() + "> "
				+ "SELECT * WHERE { ?music rdfs:label \"" + musicName
				+ "\". ?s rdf:type <" + typeURI + ">}";
		QueryExecution execution = QueryExecutionFactory.sparqlService(
				SPARQL_ENDPOINT_OF_MUSICBRAINZ, musicQuery);
		ResultSet resultSet = execution.execSelect();
		if (resultSet.hasNext()) {
			return resultSet.next().getResource("music");
		} else {
			return null;
		}
	}

}

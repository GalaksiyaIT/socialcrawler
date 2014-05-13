package com.galaksiya.social.linker;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

@LinkageMap(properties = { CommonOntologyVocabulary.HOMETOWN_URI,
		CommonOntologyVocabulary.LOCATION_URI,
		CommonOntologyVocabulary.CITY_PRP_URI,
		CommonOntologyVocabulary.COUNTRY_PRP_URI }, classes = {
		CommonOntologyVocabulary.GEODDATA_PLACE_URI,
		CommonOntologyVocabulary.DBPEDIA_CITY_URI,
		CommonOntologyVocabulary.DBPEDIA_COUNTRY_URI })
public class LocationLinker extends Linker {

	public static final String SPARQL_ENDPOINT_OF_DBPEDIA = "http://dbpedia.org/sparql";

	/**
	 * This method explores string type location on dbpedia and returns related
	 * location resource from dbpedia.
	 * 
	 * @param locationText
	 *            location text contains location pattern or patterns to be
	 *            explored.
	 * @return found {@link Resource} instance of location in dbpedia
	 */
	private Resource explore(String locationText) {
		if (locationText == null) {
			return null;
		}
		List<String> locPatternList = splitText(locationText, ",");
		// control whether list contains only one element. If so search it
		// directly DBpedia.
		Resource relatedPlace = null;
		switch (locPatternList.size()) {
		case 1:
			getLogger().info("Single pattern explore is active");
			relatedPlace = lookForResourceBySinglePattern(locPatternList.get(0));
			break;
		case 2:
			try {
				getLogger().info("Double pattern explore is active");
				relatedPlace = lookForResourceByDoublePattern(locPatternList);
			} catch (Exception e) {
				getLogger().error(e);
			}
			break;
		default:
			getLogger().warn(
					MessageFormat.format("\"{0}\" could not be interpreted.",
							locationText));
			break;
		}

		if (relatedPlace != null) {
			getLogger()
					.info(MessageFormat
							.format("Resource <{1}> for the location pattern <{0}> has been found.",
									locationText, relatedPlace.getURI()));
		} else {
			getLogger()
					.error(MessageFormat
							.format("No resource has been found for the location pattern: <{0}>",
									locationText));
		}

		return relatedPlace;
	}

	/**
	 * This method uses first and second patterns of locationPatternList and
	 * looks for appropriate location resource in dbpedia with them.
	 * 
	 * @param locPatternList
	 *            list contains location patterns.
	 * @return found {@link Resource} instance of location in dbpedia
	 * @throws Exception
	 */
	private Resource lookForResourceByDoublePattern(List<String> locPatternList)
			throws Exception {

		Resource certainFoundPlace = null;

		if (locPatternList.size() != 2) {
			throw new Exception(
					"List size must be size of '2' to use this method");
		}

		// first look for places directly on dbpedia.
		getLogger().info(
				MessageFormat.format(
						"Searching first resource {0} directly on dbpedia",
						locPatternList.get(0)));
		Resource foundPlaceForFirstPattern = searchPopulatedPlaceOnDBpedia(locPatternList
				.get(0));

		getLogger().info(
				MessageFormat.format(
						"Searching second resource {0} directly on dbpedia",
						locPatternList.get(1)));
		Resource foundPlaceForSecondPattern = searchPopulatedPlaceOnDBpedia(locPatternList
				.get(1));

		// if first one is found certainFoundPlace is the found one.
		if (foundPlaceForFirstPattern != null) {
			certainFoundPlace = foundPlaceForFirstPattern;
		}// if first one is not found dircetly but second one is found
		else if (foundPlaceForFirstPattern == null
				&& foundPlaceForSecondPattern != null) {
			// search first location pattern by using found place for second
			// pattern.
			certainFoundPlace = searchSimilarPlaceWithDoublePattern(
					locPatternList.get(0), foundPlaceForSecondPattern);
		}
		// if certainFoundPlace is still null search first in all districts,
		// towns, cities, and countries. If first one is not found, then it
		// searches second one as first.
		if (certainFoundPlace == null) {
			certainFoundPlace = lookForFirstThenSecondOneByOne(locPatternList);
		}

		return certainFoundPlace;
	}

	/**
	 * This method looks for place firstly in first pattern, if it doesn't find
	 * in first one then it looks for in second one.
	 * 
	 * @param locPatternList
	 *            pattern list to be look for
	 * @return found place.
	 */
	private Resource lookForFirstThenSecondOneByOne(List<String> locPatternList) {
		Resource foundPlace = null;
		getLogger()
				.info(MessageFormat
						.format("First location : {0}, then Second location : {1} will be searched",
								locPatternList.get(0), locPatternList.get(1)));
		// First look foundPlace in first pattern
		foundPlace = lookForResourceBySinglePattern(locPatternList.get(0));
		// If it cannot be found in first then look for place in second pattern.
		if (foundPlace == null) {
			foundPlace = lookForResourceBySinglePattern(locPatternList.get(1));
		}
		return foundPlace;
	}

	/**
	 * This method looks for first location pattern in links of resource of
	 * second found place.
	 * 
	 * @param firstLocationPattern
	 *            location pattern to be looked for.
	 * @param foundPlaceForSecondPattern
	 *            resource for the first location pattern to be looked for by
	 *            using its links.
	 * @return found {@link Resource} instance of location in dbpedia
	 */
	private Resource searchSimilarPlaceWithDoublePattern(
			String firstLocationPattern, Resource foundPlaceForSecondPattern) {
		// first initialize searchLevel by 0 and foundPlace by null.
		int searchLevel = 0;
		Resource foundPlace = null;
		getLogger()
				.info(MessageFormat
						.format("Searching first location : {0} using second resource  with URI : {1}",
								firstLocationPattern,
								foundPlaceForSecondPattern.getURI()));
		while ((searchLevel < 3) && (foundPlace == null)) {
			switch (searchLevel) {
			// search if first pattern is part of city, and second pattern
			// is city
			case 0:
				getLogger()
						.info("Searching in 'partOfCity <--> city' matching");
				foundPlace = searchSimilarityByLabelInModel(
						firstLocationPattern,
						getPartsOfCityGiven(foundPlaceForSecondPattern));
				break;
			// search if first pattern is parto of city, and second
			// pattern is country
			case 1:
				getLogger().info(
						"Searching in 'partOfCountry <--> country' matching");
				foundPlace = searchSimilarityByLabelInModel(
						firstLocationPattern,
						getPartsOfCountryGiven(foundPlaceForSecondPattern));
				break;
			// search if first pattern is city, and second pattern is
			// country
			case 2:
				getLogger().info("Searching in 'city <--> country' matching");
				foundPlace = searchSimilarityByLabelInModel(
						firstLocationPattern,
						getCitiesOfCountryGiven(foundPlaceForSecondPattern));
				break;
			}
			searchLevel++;
		}
		return foundPlace;
	}

	/**
	 * This method looks for place in directly given place name, if it doesn't
	 * found same name, it looks for similar one by order of in districts,
	 * towns, cities, and countries.
	 * 
	 * @param placeName
	 *            TODO
	 * 
	 * @return found {@link Resource} instance of location in dbpedia
	 */
	private Resource lookForResourceBySinglePattern(String placeName) {
		// initialize searchLevel by 0 and foundPlace by null.
		Resource foundPlace = null;
		int searchLevel = 0;
		// search while searchLevel is lower than 4 and foundPlace is not null.
		while ((searchLevel < 5) && (foundPlace == null)) {
			switch (searchLevel) {
			case 0:
				getLogger()
						.info(MessageFormat
								.format("Searching single resource {0} directly on dbpedia",
										placeName));
				foundPlace = searchPopulatedPlaceOnDBpedia(placeName);
				break;
			case 1:
				getLogger()
						.info(MessageFormat
								.format("Searching single resource {0} on dbpedia districts",
										placeName));
				foundPlace = searchSimilarityByLabelInModel(placeName,
						getAllDistrictsConstructed());
				break;
			case 2:
				getLogger()
						.info(MessageFormat
								.format("Searching single resource {0} on dbpedia towns",
										placeName));
				foundPlace = searchSimilarityByLabelInModel(placeName,
						getAllTownsConstructed());
				break;
			case 3:
				getLogger()
						.info(MessageFormat
								.format("Searching single resource {0} on dbpedia cities",
										placeName));
				foundPlace = searchSimilarityByLabelInModel(placeName,
						getAllCitiesConstructed());
				break;
			case 4:
				getLogger()
						.info(MessageFormat
								.format("Searching single resource {0} on dbpedia countries",
										placeName));
				foundPlace = searchSimilarityByLabelInModel(placeName,
						getAllCountriesConstructed());
				break;
			}
			searchLevel++;
		}
		return foundPlace;
	}

	/**
	 * This method splits given text by given splitting token and returns a
	 * pattern list after splitting operation.
	 * 
	 * @param textToBeSplitted
	 *            given text to be splitted.
	 * @param splitToken
	 *            splitting parameter
	 * @return token {@link List} instance
	 */
	private List<String> splitText(String textToBeSplitted, String splitToken) {
		// split text by given splitToken.
		StringTokenizer tokenizer = new StringTokenizer(textToBeSplitted,
				splitToken);
		List<String> patternList = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			// trim spaces in text patterns
			String locationPattern = tokenizer.nextToken().trim();
			// add pattern to list
			patternList.add(editFirstCharIfTurkish(locationPattern));
		}
		return patternList;
	}

	/**
	 * This method replaces Turkish first character of given text with non
	 * Turkish one.
	 * 
	 * @param text
	 * @return
	 */
	private String editFirstCharIfTurkish(String text) {
		switch (text.charAt(0)) {
		case 'Ç':
			text = text.replaceFirst("Ç", "C");
			break;
		case 'Ü':
			text = text.replaceFirst("Ü", "U");
			break;
		case 'Ö':
			text = text.replaceFirst("Ö", "O");
			break;
		case 'İ':
			text = text.replaceFirst("İ", "I");
			break;
		case 'Ğ':
			text = text.replaceFirst("Ğ", "G");
			break;
		default:
			break;
		}
		return text;
	}

	/**
	 * This method returns model of cities in dbpedia.
	 * 
	 * @return Model instance contains triples coming from construct query.
	 */
	public Model getAllCitiesConstructed() {
		String query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix dbowl:<http://dbpedia.org/ontology/>"
				+ "CONSTRUCT {?city rdfs:label ?cityLabel} WHERE {"
				+ "?city rdf:type dbowl:PopulatedPlace. "
				+ "?city rdf:type dbowl:City. "
				+ "?city rdf:type dbowl:Settlement. "
				+ "?city rdfs:label ?cityLabel.}";
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				LocationLinker.SPARQL_ENDPOINT_OF_DBPEDIA, query);
		Model model = exec.execConstruct();
		return model;
	}

	/**
	 * This method returns model of countries in dbpedia.
	 * 
	 * @return Model instance contains triples coming from construct query.
	 */
	public Model getAllCountriesConstructed() {
		String query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix dbowl:<http://dbpedia.org/ontology/>"
				+ "CONSTRUCT {?country rdfs:label ?countryLabel} WHERE {"
				+ "?country rdf:type dbowl:PopulatedPlace. "
				+ "?country rdf:type dbowl:Country. "
				+ "?country rdfs:label ?countryLabel.}";
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				LocationLinker.SPARQL_ENDPOINT_OF_DBPEDIA, query);
		Model model = exec.execConstruct();
		return model;
	}

	/**
	 * This method returns model of districts in dbpedia. settlementType
	 * 
	 * @return Model instance contains triples coming from construct query.
	 */
	public Model getAllDistrictsConstructed() {
		String query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix dbowl:<http://dbpedia.org/ontology/>"
				+ "prefix dbprop:<http://dbpedia.org/property/>"
				+ "CONSTRUCT {?district rdfs:label ?districtLabel} WHERE {"
				+ "?district dbprop:settlementType \"District\"@en."
				+ "?district rdf:type dbowl:PopulatedPlace. "
				+ "?district rdfs:label ?districtLabel. " + "}";
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				LocationLinker.SPARQL_ENDPOINT_OF_DBPEDIA, query);
		Model model = exec.execConstruct();
		return model;
	}

	/**
	 * This method returns model of districts in dbpedia. settlementType
	 * 
	 * @return Model instance contains triples coming from construct query.
	 */
	public Model getAllTownsConstructed() {
		String query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix dbowl:<http://dbpedia.org/ontology/>"
				+ "prefix dbprop:<http://dbpedia.org/property/>"
				+ "CONSTRUCT {?town rdfs:label ?townLabel} WHERE {"
				+ "?town dbprop:settlementType \"Town\"@en."
				+ "?town rdf:type dbowl:PopulatedPlace. "
				+ "?town rdfs:label ?townLabel. " + "}";
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				LocationLinker.SPARQL_ENDPOINT_OF_DBPEDIA, query);
		Model model = exec.execConstruct();
		return model;
	}

	/**
	 * This method returns model of cities of given country in dbpedia
	 * resources.
	 * 
	 * @param country
	 * @return Model instance contains triples coming from construct query.
	 */
	public Model getCitiesOfCountryGiven(Resource country) {
		String query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix dbowl:<http://dbpedia.org/ontology/>"
				+ "CONSTRUCT {"
				+ "?city rdfs:label ?cityLabel"
				+ "} WHERE {"
				+ "?city dbowl:country <"
				+ country.getURI()
				+ ">."
				+ "?city rdfs:label ?cityLabel" + "}";
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				LocationLinker.SPARQL_ENDPOINT_OF_DBPEDIA, query);
		Model model = exec.execConstruct();
		return model;
	}

	/**
	 * This method returns model of cities of given country in dbpedia
	 * resources.
	 * 
	 * @param city
	 * @return Model instance contains triples coming from construct query.
	 */
	public Model getPartsOfCityGiven(Resource city) {
		String query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix dbowl:<http://dbpedia.org/ontology/>"
				+ "CONSTRUCT {"
				+ "?part rdfs:label ?partLabel"
				+ "} WHERE {"
				+ "?part dbowl:isPartOf <"
				+ city.getURI()
				+ ">."
				+ "?part rdfs:label ?partLabel" + "}";
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				LocationLinker.SPARQL_ENDPOINT_OF_DBPEDIA, query);
		Model model = exec.execConstruct();
		return model;
	}

	/**
	 * This method returns model of cities of given country in dbpedia
	 * resources.
	 * 
	 * @param country
	 * @return Model instance contains triples coming from construct query.
	 */
	public Model getPartsOfCountryGiven(Resource country) {
		String query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix dbowl:<http://dbpedia.org/ontology/>"
				+ "CONSTRUCT {"
				+ "?part rdfs:label ?partLabel"
				+ "} WHERE {"
				+ "?city dbowl:country <"
				+ country.getURI()
				+ ">."
				+ "?part dbowl:isPartOf ?city."
				+ "?part rdfs:label ?partLabel"
				+ "}";
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				LocationLinker.SPARQL_ENDPOINT_OF_DBPEDIA, query);
		Model model = exec.execConstruct();
		return model;
	}

	/**
	 * This method searches given country name on DBpedia and returns its
	 * resource if finds it.
	 * 
	 * @param countryName
	 * @return found {@link Resource} instance of location in dbpedia
	 */
	public Resource searchCountryOnDBpedia(String countryName) {
		String query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix dbowl:<http://dbpedia.org/ontology/>"
				+ "SELECT ?country WHERE {?country rdfs:label \""
				+ countryName
				+ "\"@en. ?country rdf:type dbowl:PopulatedPlace. "
				+ "?country rdf:type dbowl:Country.}";
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				LocationLinker.SPARQL_ENDPOINT_OF_DBPEDIA, query);
		ResultSet set = exec.execSelect();
		if (!set.hasNext()) {
			return null;
		} else {
			return set.next().getResource("country");
		}
	}

	/**
	 * This method searches given populated place name on DBPedia and returns
	 * its resource if finds it.
	 * 
	 * @param placeName
	 * @return found {@link Resource} instance of location in dbpedia
	 */
	public Resource searchPopulatedPlaceOnDBpedia(String placeName) {
		String query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix dbowl:<http://dbpedia.org/ontology/>"
				+ "SELECT ?place WHERE {?place rdfs:label \""
				+ placeName
				+ "\"@en. ?place rdf:type dbowl:PopulatedPlace}";
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				LocationLinker.SPARQL_ENDPOINT_OF_DBPEDIA, query);
		ResultSet set = exec.execSelect();
		if (!set.hasNext()) {
			return null;
		} else {
			return set.next().getResource("place");
		}
	}

	/**
	 * This method searches populated place on DBpedia, with given place name
	 * and its country name. So it returns resource of populated place if finds
	 * it.
	 * 
	 * @param placeName
	 * @return found {@link Resource} instance of location in dbpedia
	 */
	public Resource searchPopulatedPlaceWithCountryOnDBpedia(String placeName,
			String countryName) {
		String query = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix dbowl:<http://dbpedia.org/ontology/>"
				+ "SELECT ?place ?country WHERE {?place rdfs:label \""
				+ placeName
				+ "\"@en. "
				+ "?place rdf:type dbowl:PopulatedPlace. "
				+ "?place dbowl:country ?country. "
				+ "?country rdfs:label \""
				+ countryName
				+ "\"@en. "
				+ "?country rdf:type dbowl:PopulatedPlace. "
				+ "?country rdf:type dbowl:Country.}";
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				LocationLinker.SPARQL_ENDPOINT_OF_DBPEDIA, query);
		ResultSet set = exec.execSelect();
		if (!set.hasNext()) {
			return null;
		} else {
			return set.next().getResource("place");
		}
	}

	@Override
	public Resource find(String resourceName) {
		return explore(resourceName);
	}
}

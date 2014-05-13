package com.galaksiya.social.linker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;

public class MovieLinkerTest {

	private static final String DEFECTED_JURASSIC_PARK_NAME = "Jorasic Parc";
	private static final String CORRECT_JURASSIC_PARK_NAME = "Jurassic Park";

	@Test
	public void linkMovieLikeWithCorrectName() throws Exception {
		// create sample resource and its properties to link with linker
		Resource resource = createPersonWhoLikesMovie(
				CommonTestVocabulary.URI_BURAK_YONYUL,
				LinkerTestContants.JURASSIC_PARK_LOCAL_MOVIE_URI,
				CORRECT_JURASSIC_PARK_NAME);
		// try to link appropriate statements of resource
		linkResource(resource);

		// check whether subject resource has likes property with linked
		// value.
		assertTrue(resource.hasProperty(FacebookOntologyVocabulary.LIKES_PRP,
				LinkerTestContants.LMDB_JURASSIC_PARK_RSC));

		// check whether subject resource has not likes property with old value.
		assertFalse(resource.hasProperty(FacebookOntologyVocabulary.LIKES_PRP,
				LinkerTestContants.JURASSIC_PARK_LOCAL_MOVIE_RSC));

		// check model contains skos:altLabel property and value correctly
		assertTrue(resource.getModel().contains(
				LinkerTestContants.LMDB_JURASSIC_PARK_RSC,
				CommonOntologyVocabulary.SKOS_ALT_LABEL_PRP,
				CORRECT_JURASSIC_PARK_NAME));

	}

	@Test
	public void controlModelContainsLiteralValue() throws Exception {
		// create sample resource and its properties to link with linker
		Resource resource = createPersonWhoLikesMovie(
				CommonTestVocabulary.URI_BURAK_YONYUL,
				LinkerTestContants.JURASSIC_PARK_LOCAL_MOVIE_URI,
				CORRECT_JURASSIC_PARK_NAME);

		// add skos:altLabel property to local Jurassic Park resource
		resource.getModel().add(
				LinkerTestContants.JURASSIC_PARK_LOCAL_MOVIE_RSC,
				CommonOntologyVocabulary.SKOS_ALT_LABEL_PRP,
				CORRECT_JURASSIC_PARK_NAME);

		// try to link appropriate statements of resource
		linkResource(resource);

		// check whether subject resource has likes property with local movie
		// value.
		assertTrue(resource.hasProperty(FacebookOntologyVocabulary.LIKES_PRP,
				LinkerTestContants.JURASSIC_PARK_LOCAL_MOVIE_RSC));

		// check whether subject resource has not likes property with LMDB
		// value.
		assertFalse(resource.hasProperty(FacebookOntologyVocabulary.LIKES_PRP,
				LinkerTestContants.LMDB_JURASSIC_PARK_RSC));

	}

	@Test
	public void linkMovieLikeWithDefectedName() throws Exception {
		// create sample resource and its properties to link with linker
		Resource resource = createPersonWhoLikesMovie(
				CommonTestVocabulary.URI_BURAK_YONYUL,
				LinkerTestContants.JURASSIC_PARK_LOCAL_MOVIE_URI,
				DEFECTED_JURASSIC_PARK_NAME);
		// try to link appropriate statements of resource
		linkResource(resource);

		// check whether subject resource has likes property with linked
		// value.
		assertTrue(resource.hasProperty(FacebookOntologyVocabulary.LIKES_PRP,
				LinkerTestContants.LMDB_JURASSIC_PARK_RSC));

		// check whether subject resource has not likes property with old value.
		assertFalse(resource.hasProperty(FacebookOntologyVocabulary.LIKES_PRP,
				LinkerTestContants.JURASSIC_PARK_LOCAL_MOVIE_RSC));

		// check model contains skos:altLabel property and value correctly
		assertTrue(resource.getModel().contains(
				LinkerTestContants.LMDB_JURASSIC_PARK_RSC,
				CommonOntologyVocabulary.SKOS_ALT_LABEL_PRP,
				DEFECTED_JURASSIC_PARK_NAME));
	}

	private void linkResource(Resource resource) throws Exception {
		LinkerTestUtility.executeLinker(resource, new MovieLinker());
	}

	/**
	 * This method creates person with given resource URI and adds it a like
	 * property with given temporary like URI and movie name
	 * 
	 * @param resourceURI
	 *            URI of Resource will be created.
	 * @param tempLikeURI
	 *            URI of movie like will be created.
	 * @param likedMovieName
	 *            name of movie
	 * @return created person resource
	 */
	private Resource createPersonWhoLikesMovie(String resourceURI,
			String tempLikeURI, String likedMovieName) {
		// create a default model to create resource in it.
		Model model = ModelFactory.createDefaultModel();
		// create sample person individual
		Resource burakRsc = model.createResource(
				CommonTestVocabulary.URI_BURAK_YONYUL, FOAF.Person);

		// create sample movie resources to be liked
		Resource jurassicParkMovie = model.createResource(tempLikeURI,
				CommonOntologyVocabulary.MOVIE_RSC);
		jurassicParkMovie.addProperty(FOAF.name, likedMovieName);

		burakRsc.addProperty(FacebookOntologyVocabulary.LIKES_PRP,
				jurassicParkMovie);
		return burakRsc;
	}

}

package com.galaksiya.social.linker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.FacebookOntologyVocabulary;
import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;

/**
 * FIXME: music linker düzeltilmeli
 * 
 * @author etmen
 * 
 */
@Ignore
public class MusicLinkerTest {

	private static final String DEFECTED_NUMB_NAME = "Nomb";
	private static final String CORRECT_NUMB_NAME = "Numb";

	@Test
	public void linkMusicLikeWithCorrectName() throws Exception {
		// create sample resource and its properties to link with linker
		Resource resource = createPersonWhoLikesMusic(
				CommonTestVocabulary.URI_BURAK_YONYUL,
				LinkerTestContants.NUMB_LOCAL_MOVIE_URI, CORRECT_NUMB_NAME);

		// try to link appropriate statements of resource
		linkResource(resource);

		// check whether subject resource has likes property with linked
		// value.
		assertTrue(resource.hasProperty(FacebookOntologyVocabulary.LIKES_PRP,
				LinkerTestContants.MUSICBRAINZ_NUMB_RSC));

		// check whether subject resource has not likes property with old value.
		assertFalse(resource.hasProperty(FacebookOntologyVocabulary.LIKES_PRP,
				LinkerTestContants.NUMB_LOCAL_MUSIC_RSC));

		// check model contains skos:altLabel property and value correctly
		assertTrue(resource.getModel().contains(
				LinkerTestContants.MUSICBRAINZ_NUMB_RSC,
				CommonOntologyVocabulary.SKOS_ALT_LABEL_PRP, CORRECT_NUMB_NAME));

	}

	private void linkResource(Resource resource) throws Exception {
//		LinkerTestUtility.executeLinker(resource, new MusicLinker());
	}

	@Test
	public void linkMusicLikeWithDefectedName() throws Exception {
		// create sample resource and its properties to link with linker
		Resource resource = createPersonWhoLikesMusic(
				CommonTestVocabulary.URI_BURAK_YONYUL,
				LinkerTestContants.NUMB_LOCAL_MOVIE_URI, DEFECTED_NUMB_NAME);

		// try to link appropriate statements of resource
		linkResource(resource);

		// check whether subject resource has likes property with linked
		// value.
		assertTrue(resource.hasProperty(FacebookOntologyVocabulary.LIKES_PRP,
				LinkerTestContants.MUSICBRAINZ_NUMB_RSC));

		// check whether subject resource has not likes property with old value.
		assertFalse(resource.hasProperty(FacebookOntologyVocabulary.LIKES_PRP,
				LinkerTestContants.NUMB_LOCAL_MUSIC_RSC));

		// check model contains skos:altLabel property and value correctly
		assertTrue(resource.getModel().contains(
				LinkerTestContants.MUSICBRAINZ_NUMB_RSC,
				CommonOntologyVocabulary.SKOS_ALT_LABEL_PRP, CORRECT_NUMB_NAME));

	}

	/**
	 * This method creates person with given resource URI and adds it a like
	 * property with given temporary like URI and music name
	 * 
	 * @param resourceURI
	 *            URI of Resource will be created.
	 * @param tempLikeURI
	 *            URI of music like will be created.
	 * @param likedMusicName
	 *            name of music
	 * @return created person resource
	 */
	private Resource createPersonWhoLikesMusic(String resourceURI,
			String tempLikeURI, String likedMusicName) {
		// create a default model to create resource in it.
		Model model = ModelFactory.createDefaultModel();
		// create sample person individual
		Resource burakRsc = model.createResource(
				CommonTestVocabulary.URI_BURAK_YONYUL, FOAF.Person);

		// create sample music resources to be liked
		Resource jurassicParkMovie = model.createResource(tempLikeURI,
				CommonOntologyVocabulary.MUSIC_RSC);
		jurassicParkMovie.addProperty(FOAF.name, likedMusicName);

		burakRsc.addProperty(FacebookOntologyVocabulary.LIKES_PRP,
				jurassicParkMovie);
		return burakRsc;
	}

}

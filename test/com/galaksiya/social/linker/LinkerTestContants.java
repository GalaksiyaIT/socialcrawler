package com.galaksiya.social.linker;

import com.galaksiya.social.vocabulary.CommonTestVocabulary;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class LinkerTestContants {

	/**
	 * sample location resource uri used before linking phase.
	 */
	public static final String SAMPLE_LOCATION_OBJECT_RSC_URI = "http://linkerobject.com/resource/buca";

	/**
	 * sample location resource which will be removed after linking operation.
	 */
	public static final Resource SAMPLE_LOCATION_OBJECT_RSC = ResourceFactory
			.createResource(SAMPLE_LOCATION_OBJECT_RSC_URI);

	/**
	 * DBpedia Buca resource URI used after linking phase.
	 */
	public static final String DBPEDIA_BUCA_URI = "http://dbpedia.org/resource/Buca";

	/**
	 * DBpedia Buca resource
	 */
	public static final Resource DBPEDIA_BUCA_RSC = ResourceFactory
			.createResource(DBPEDIA_BUCA_URI);

	/**
	 * sample subject resource URI whose properties will be linked.
	 */
	public static final String SAMPLE_SUBJECT_RSC_URI = "http://linkersubject.com/resource/burakyonyul";

	public static final String JURASSIC_PARK_LOCAL_MOVIE_URI = CommonTestVocabulary.BASE_URI
			+ "/JurassicPark";

	public static final Resource JURASSIC_PARK_LOCAL_MOVIE_RSC = ResourceFactory
			.createResource(JURASSIC_PARK_LOCAL_MOVIE_URI);

	private static final String LMDB_JURASSIC_PARK_MOVIE_URI = "http://data.linkedmdb.org/resource/film/138";

	public static final Resource LMDB_JURASSIC_PARK_RSC = ResourceFactory
			.createResource(LMDB_JURASSIC_PARK_MOVIE_URI);

	public static final String NUMB_LOCAL_MOVIE_URI = CommonTestVocabulary.BASE_URI
			+ "/Numb";

	private static final String MUSICBRAINZ_NUMB_URI = "http://dbtune.org/musicbrainz/resource/track/b7e9aca0-3d4a-42d7-aadd-5e8df41cd035";

	public static final Resource MUSICBRAINZ_NUMB_RSC = ResourceFactory
			.createResource(MUSICBRAINZ_NUMB_URI);

	public static final Resource NUMB_LOCAL_MUSIC_RSC = ResourceFactory
			.createResource(NUMB_LOCAL_MOVIE_URI);

}

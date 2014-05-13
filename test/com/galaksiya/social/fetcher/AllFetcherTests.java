package com.galaksiya.social.fetcher;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ FacebookDataFetcherTest.class, FoursquareDataFetcherTest.class,
		FacebookIndividualCreationTest.class,
		FoursquareIndividualCreationTest.class,
		LinkedinIndividualCreationTest.class, DateTransformerTest.class,
		FacebookDataFetcherTest.class })
public class AllFetcherTests {
	
}

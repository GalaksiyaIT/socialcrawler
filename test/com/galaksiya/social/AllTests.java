package com.galaksiya.social;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.galaksiya.social.crawler.AllCrawlerTests;
import com.galaksiya.social.fetcher.AllFetcherTests;
import com.galaksiya.social.linker.AllLinkerTests;
import com.galaksiya.social.ontology.AllOntologyTests;

@RunWith(Suite.class)
@SuiteClasses({ AllFetcherTests.class, AllCrawlerTests.class,
		AllOntologyTests.class, AllLinkerTests.class, HashGeneratorTest.class })
public class AllTests {

}

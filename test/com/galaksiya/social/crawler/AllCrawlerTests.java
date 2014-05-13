package com.galaksiya.social.crawler;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SocialCrawlerFacebookClientTest.class,
		FacebookUserCrawlerTest.class, FoursquareUserCrawlerTest.class,
		LinkedinUserCrawlerTest.class })
public class AllCrawlerTests {

}

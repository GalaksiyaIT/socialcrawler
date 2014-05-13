package com.galaksiya.social.linker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LocationLinkerTest.class, MovieLinkerTest.class,
	BankInfoCreationTest.class, BankPersonLinkerTest.class })
public class AllLinkerTests {

}

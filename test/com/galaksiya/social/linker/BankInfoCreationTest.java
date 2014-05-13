package com.galaksiya.social.linker;

import org.junit.Ignore;
import org.junit.Test;
@Ignore
public class BankInfoCreationTest {
	@Test
	public void createBankInfo() throws Exception {
		BankInfoCreator bankInfoCreator = new BankInfoCreator();
		bankInfoCreator.createAllIndividuals();
		bankInfoCreator.writeBankModel("/home/etmen/Desktop/bank.rdf");
	}
}

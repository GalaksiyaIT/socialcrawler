package com.galaksiya.social.linker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.galaksiya.social.ontology.vocabulary.BankVocabulary;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;

public class BankInfoCreator {
	private Model bankModel;

	public BankInfoCreator() {
		super();
		bankModel = ModelFactory.createDefaultModel();
	}

	public Model getBankModel() {
		return bankModel;
	}

	public void createBurak() {
		Resource burakRsc = bankModel
				.createResource(BankVocabulary.BURAK_BANK_RSC_URI);
		burakRsc.addProperty(FOAF.name, "Burak Yönyül");
		burakRsc.addProperty(BankVocabulary.HAS_MONEY_PRP, ResourceFactory
				.createTypedLiteral("3000", XSDDatatype.XSDdouble));
	}

	public void createTayfun() {
		Resource burakRsc = bankModel
				.createResource(BankVocabulary.TAYFUN_BANK_RSC_URI);
		burakRsc.addProperty(FOAF.name, "Tayfun Gökmen Halaç");
		burakRsc.addProperty(BankVocabulary.HAS_MONEY_PRP, ResourceFactory
				.createTypedLiteral("5500", XSDDatatype.XSDdouble));
	}

	public void createEnes() {
		Resource burakRsc = bankModel
				.createResource(BankVocabulary.ENES_BANK_RSC_URI);
		burakRsc.addProperty(FOAF.name, "Enes Bulut");
		burakRsc.addProperty(BankVocabulary.HAS_MONEY_PRP, ResourceFactory
				.createTypedLiteral("4000", XSDDatatype.XSDdouble));
	}

	public void createBahtiyar() {
		Resource burakRsc = bankModel
				.createResource(BankVocabulary.BAHTIYAR_BANK_RSC_URI);
		burakRsc.addProperty(FOAF.name, "Bahtiyar Erden");
		burakRsc.addProperty(BankVocabulary.HAS_MONEY_PRP, ResourceFactory
				.createTypedLiteral("4100", XSDDatatype.XSDdouble));
	}

	public void createEmrah() {
		Resource burakRsc = bankModel
				.createResource(BankVocabulary.EMRAH_BANK_RSC_URI);
		burakRsc.addProperty(FOAF.name, "Emrah İnan");
		burakRsc.addProperty(BankVocabulary.HAS_MONEY_PRP, ResourceFactory
				.createTypedLiteral("2500", XSDDatatype.XSDdouble));
	}

	public void createGul() {
		Resource burakRsc = bankModel
				.createResource(BankVocabulary.GUL_BANK_RSC_URI);
		burakRsc.addProperty(FOAF.name, "Zehra Gül Çabuk");
		burakRsc.addProperty(BankVocabulary.HAS_MONEY_PRP, ResourceFactory
				.createTypedLiteral("3000", XSDDatatype.XSDdouble));
	}

	public void createPinar() {
		Resource burakRsc = bankModel
				.createResource(BankVocabulary.PINAR_BANK_RSC_URI);
		burakRsc.addProperty(FOAF.name, "Pınar Göçebe");
		burakRsc.addProperty(BankVocabulary.HAS_MONEY_PRP, ResourceFactory
				.createTypedLiteral("2000", XSDDatatype.XSDdouble));
	}

	public void createErdem() {
		Resource burakRsc = bankModel
				.createResource(BankVocabulary.ERDEM_BANK_RSC_URI);
		burakRsc.addProperty(FOAF.name, "Erdem Eser Ekinci");
		burakRsc.addProperty(BankVocabulary.HAS_MONEY_PRP, ResourceFactory
				.createTypedLiteral("4700", XSDDatatype.XSDdouble));
	}

	public void createAllIndividuals() {
		createBurak();
		createTayfun();
		createEnes();
		createBahtiyar();
		createEmrah();
		createGul();
		createPinar();
		createErdem();
	}

	public void writeBankModel(String filePath) throws IOException {
		bankModel.write(new FileWriter(new File(filePath)));
	}

}

package com.galaksiya.social.fetcher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.galaksiya.social.entity.LinkedInPerson;
import com.galaksiya.social.linker.BankPersonLinker;
import com.galaksiya.social.ontology.vocabulary.CommonOntologyVocabulary;
import com.galaksiya.social.ontology.vocabulary.LinkedinOntologyVocabulary;
import com.google.code.linkedinapi.schema.Company;
import com.google.code.linkedinapi.schema.DateOfBirth;
import com.google.code.linkedinapi.schema.EndDate;
import com.google.code.linkedinapi.schema.JobFunction;
import com.google.code.linkedinapi.schema.JobFunctions;
import com.google.code.linkedinapi.schema.Location;
import com.google.code.linkedinapi.schema.Person;
import com.google.code.linkedinapi.schema.Position;
import com.google.code.linkedinapi.schema.Positions;
import com.google.code.linkedinapi.schema.StartDate;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;

/**
 * There is creating individuals using the ontology schema in this class
 * 
 */
public class LinkedinIndividualCreator extends IndividualCreator {

	public LinkedinIndividualCreator(String baseURI) {
		super(baseURI);
	}

	/**
	 * creates an {@link Resource} instance of {@link LinkedInPerson} person,
	 * creates properties for this individual and add this individual to the
	 * {@link Model} individual ontology
	 * 
	 * @param inPerson
	 * @return
	 * @throws SocialAPIException
	 */
	public Resource createPersonIndividual(LinkedInPerson inPerson)
			throws SocialAPIException {
		// check for whether person id is null
		if (inPerson.getId() == null) {
			throw new SocialAPIException(
					"User id of the LinkedIn client is null");
		}

		// creating ontology person individual
		Resource linkedinIndividual = getModel().createResource(
				createUserURI(inPerson),
				getModel().getResource(
						CommonOntologyVocabulary.FOAF_PERSON_RSC_URI));

		// Create singular properties...

		createProperty(linkedinIndividual,
				CommonOntologyVocabulary.FOAF_NAME_URI,
				getPersonName(inPerson.getPerson()));
		getLogger().info("name property has been added");

		Location location = inPerson.getPerson().getLocation();
		if (location != null) {
			Resource locationRsc = createLocationResource(UUID.randomUUID()
					.toString(), inPerson.getPerson().getLocation().getName());
			createProperty(linkedinIndividual,
					CommonOntologyVocabulary.LOCATION_URI, locationRsc);
			getLogger().info("location property has been added");
		}

		createProperty(linkedinIndividual,
				LinkedinOntologyVocabulary.HEADLINE_URI, inPerson.getPerson()
						.getHeadline());
		getLogger().info("headline property has been added");

		createProperty(linkedinIndividual,
				CommonOntologyVocabulary.CV_INDUSTRY_PRP_URI, inPerson
						.getPerson().getIndustry());

		getLogger().info("industry property has been added");
		createProperty(linkedinIndividual, FOAF.birthday.getURI(),
				getLiteralBirthday(inPerson));
		getLogger().info("birthday property has been added");
		getLogger().info("position properties are beginning to create");
		generateWorkHistoryProperties(inPerson, linkedinIndividual);

		// creating profile, picture URL properties and label property of
		// linkedin individual
		createLinkedinLabels(inPerson, linkedinIndividual);

		// creating account property for linkedin person
		createAccountProperty(linkedinIndividual, inPerson.getPerson().getId(),
				LinkedinOntologyVocabulary.LINKEDIN_HOMEPAGE_RSC);

		getLogger().info("position properties have been created");

		new BankPersonLinker().linkPersonToBankDataset(linkedinIndividual);

		return linkedinIndividual;
	}

	private String controlAndGenerateText(Object object, String text) {
		if (object != null) {
			return text += "/" + object;
		}
		return text += "?";
	}

	/**
	 * creating RDFS label, profile URL, and profile pricute URL properties of
	 * linkedin individual
	 * 
	 * @param linkedInPerson
	 * @param linkedinIndividual
	 */
	private void createLinkedinLabels(LinkedInPerson linkedInPerson,
			Resource linkedinIndividual) {

		// getting profile URL
		String publicProfileUrl = linkedInPerson.getPerson()
				.getPublicProfileUrl();
		// creating foaf:page property of linkedinIndividual
		createProperty(linkedinIndividual,
				CommonOntologyVocabulary.FOAF_PAGE_PRP_URI,
				createDocumentIndv(publicProfileUrl));

		// getting picture URL
		String pictureUrl = linkedInPerson.getPerson().getPictureUrl();
		// creating foaf:depiction property of linkedinIndividual
		createProperty(linkedinIndividual,
				CommonOntologyVocabulary.FOAF_PICTURE_PRP_URI,
				createImageIndv(pictureUrl));

		// generating fullName
		String fullName = linkedInPerson.getPerson().getFirstName() + " "
				+ linkedInPerson.getPerson().getLastName();
		// creating rdfs:label property of linkedinIndividual
		createProperty(linkedinIndividual,
				CommonOntologyVocabulary.RDFS_LABEL_PRP_URI, fullName);

	}

	private Resource createLocationIndividual(Location location) {
		if (location != null) {
			Resource locationIndv = getModel()
					.createResource(
							getBaseURI() + "location" + UUID.randomUUID(),
							ResourceFactory
									.createResource(CommonOntologyVocabulary.GEODDATA_PLACE_URI));
			createProperty(locationIndv,
					CommonOntologyVocabulary.FOAF_NAME_URI, location.getName());

		}
		return null;
	}

	private String createUserURI(LinkedInPerson inPerson) {
		return getBaseURI() + inPerson.getId();
	}

	private Resource createWorkHistoryIndividual(Position position) {
		String workHistoryUniqueness = position.getId();
		if (workHistoryUniqueness == null) {
			workHistoryUniqueness = "" + UUID.randomUUID();
		}
		// creating position individual
		Resource workHistoryIndv = getModel().createResource(
				getBaseURI() + "wh" + workHistoryUniqueness,
				getModel().getResource(
						CommonOntologyVocabulary.WORK_HISTORY_URI));

		// getting property values
		getLogger().info("Position property values are beginning to get");
		String description = position.getDescription();
		String title = position.getTitle();
		String summary = position.getSummary();
		Resource companyIndividual = null;
		try {
			companyIndividual = generateCompanyIndividual(position);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String skillsAndExperience = position.getSkillsAndExperience();
		String startDateTxt = generateStartDateText(position);
		String endDateTxt = generateEndDateText(position);
		getLogger().info("Position property values have been got");

		// creating properties
		getLogger().info("Position properties are beginning to create");
		createProperty(workHistoryIndv,
				CommonOntologyVocabulary.CV_JOB_DESCRIPTION_PRP_URI,
				description);
		createProperty(workHistoryIndv, CommonOntologyVocabulary.CV_TITLE_URI,
				title);
		createProperty(workHistoryIndv, CommonOntologyVocabulary.SUMMARY_URI,
				summary);

		Resource locationIndv = createLocationIndividual(position.getLocation());

		createProperty(workHistoryIndv, CommonOntologyVocabulary.LOCATION_URI,
				locationIndv);

		createProperty(workHistoryIndv,
				CommonOntologyVocabulary.EMPLOYED_IN_PRP_URI, companyIndividual);

		// createProperty(workHistoryIndv, Vocabulary.JOB_TYPE_URI,
		// ontModel.getResource(Vocabulary.CV_EMPLOYEE));
		createProperty(workHistoryIndv,
				LinkedinOntologyVocabulary.SKILLS_EXPERIENCE_URI,
				skillsAndExperience);

		// createProperty(workHistoryIndv, Vocabulary.CAREER_LVL_URI,
		// ontModel.getResource(Vocabulary.CV_ENTRY_LVL));
		createProperty(workHistoryIndv,

		CommonOntologyVocabulary.CV_START_DATE, startDateTxt);
		createProperty(workHistoryIndv, CommonOntologyVocabulary.CV_END_DATE,
				endDateTxt);
		generateJobFunctionsProperties(position, workHistoryIndv);
		getLogger().info("Position properties have been created");
		return workHistoryIndv;
	}

	private Resource generateCompanyIndividual(Position position)
			throws UnsupportedEncodingException {
		Company company = position.getCompany();
		if (company != null) {
			String id = company.getId();
			String name = company.getName();
			String description = company.getDescription();
			String industry = company.getIndustry();
			if (id == null && name != null) {
				id = URLEncoder.encode(name, "UTF-8");
			}
			Resource compIndv = getModel().createResource(
					getBaseURI() + "com" + id,
					getModel()
							.getResource(CommonOntologyVocabulary.COMPANY_URI));

			createProperty(compIndv, CommonOntologyVocabulary.FOAF_NAME_URI,
					name);

			createProperty(compIndv,
					CommonOntologyVocabulary.CV_TRG_COM_DESCRIPTION,
					description);

			createProperty(compIndv,
					CommonOntologyVocabulary.CV_INDUSTRY_PRP_URI, industry);

			return compIndv;

		}
		return null;
	}

	private String generateEndDateText(Position position) {
		EndDate endDate = position.getEndDate();
		String dateTxt = "";
		if (position.isIsCurrent()) {
			dateTxt += "current";
		} else {
			if (endDate != null) {
				Long day = endDate.getDay();
				Long month = endDate.getMonth();
				Long year = endDate.getYear();
				dateTxt = controlAndGenerateText(day, dateTxt);
				dateTxt = controlAndGenerateText(month, dateTxt);
				dateTxt = controlAndGenerateText(year, dateTxt);
				return dateTxt;
			}
		}
		return dateTxt;
	}

	private void generateJobFunctionsProperties(Position position,
			Resource positionIndv) {
		String jobFunctionTxt;
		JobFunctions jobFunctions = position.getJobFunctions();
		if (jobFunctions != null) {
			List<JobFunction> jobFunctionList = jobFunctions
					.getJobFunctionList();
			Iterator<JobFunction> iterator = jobFunctionList.iterator();
			while (iterator.hasNext()) {
				JobFunction jobFunction = (JobFunction) iterator.next();
				if (jobFunction != null) {
					jobFunctionTxt = jobFunction.getName();
					createProperty(positionIndv,
							LinkedinOntologyVocabulary.JOB_FUNCTION_URI,
							jobFunctionTxt);
				}
			}
		}
	}

	private String generateStartDateText(Position position) {
		String dateTxt = "";
		StartDate startDate = position.getStartDate();
		if (startDate != null) {
			Long day = startDate.getDay();
			Long month = startDate.getMonth();
			Long year = startDate.getYear();
			dateTxt = controlAndGenerateText(day, dateTxt);
			dateTxt = controlAndGenerateText(month, dateTxt);
			dateTxt = controlAndGenerateText(year, dateTxt);
			return dateTxt;
		}
		return dateTxt;
	}

	private void generateWorkHistoryProperties(LinkedInPerson inPerson,
			Resource linkedinIndividual) {
		Positions positions = inPerson.getPerson().getPositions();
		if (positions != null) {
			List<Position> positionList = positions.getPositionList();
			Iterator<Position> iterator = positionList.iterator();
			while (iterator.hasNext()) {

				Position position = (Position) iterator.next();
				Resource positionIndv = createWorkHistoryIndividual(position);
				createProperty(linkedinIndividual,
						CommonOntologyVocabulary.CV_HAS_WORK_HISTORY_PRP_URI,
						positionIndv);
			}
		}

	}

	/**
	 * This method returns Literal birthday value of {@link LinkedInPerson}
	 * instance
	 * 
	 * @param inPerson
	 * @return
	 */
	private Literal getLiteralBirthday(LinkedInPerson inPerson) {
		// get date of birth object
		DateOfBirth dateOfBirth = inPerson.getPerson().getDateOfBirth();
		if (dateOfBirth != null) {
			Calendar calendarBirthday = DateTransformer
					.transformPartialDateToCalendarFormat(dateOfBirth.getDay(),
							dateOfBirth.getMonth(), dateOfBirth.getYear());
			// get literal value of Calendar type birthday value
			Literal literalBirthday = ResourceFactory
					.createTypedLiteral(calendarBirthday);
			return literalBirthday;
		}
		return null;
	}

	private String getPersonName(Person person) {
		return person.getFirstName() + " " + person.getLastName();
	}
}

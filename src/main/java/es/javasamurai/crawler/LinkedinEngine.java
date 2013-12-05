package es.javasamurai.crawler;

import com.google.common.collect.Lists;
import es.javasamurai.crawler.csv.CsvParser;
import es.javasamurai.crawler.data.LinkedinProfile;
import es.javasamurai.crawler.data.ResultSearchData;
import es.javasamurai.crawler.data.SurnameData;
import es.javasamurai.crawler.mongodb.MongoDBConnector;
import es.javasamurai.crawler.util.HtmlParserUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkedinEngine {
	private static final String SEARCH_URL_BASE = "http://es.linkedin.com/pub/dir/?first=&";
	private String fileInput;
	private boolean trace;
	private static Logger log = LoggerFactory.getLogger(LinkedinEngine.class);

	public LinkedinEngine(String fileInput, boolean trace) {
		this.fileInput = fileInput;
		this.trace = trace;
	}

	public LinkedinEngine(String fileInput) {
		this(fileInput, false);
	}

	public void start() throws IOException {
		CsvParser parser = new CsvParser(this.fileInput, this.trace);
		MongoDBConnector mongo = new MongoDBConnector();

		List<SurnameData> surnameDataList = parser.loader();
		
		for (SurnameData surname : surnameDataList) {
			ResultSearchData result = startSearch(surname);
			mongo.insert(result);
		}
		
		mongo.close();
	}

	private ResultSearchData startSearch(SurnameData surnameData) throws IOException {
		ResultSearchData results = new ResultSearchData();
		results.setSurnameData(surnameData);
		
		results.setSearchUrl(generateSearchUrl(surnameData.getSurname()));
		
		if (this.trace) {
			log.info("Searching about surname: {} - ", results.getSearchUrl());
		}
		
		try {
			
			Document page = Jsoup.connect(results.getSearchUrl()).get();

			results.setnProfiles(HtmlParserUtil.getNumberResults(page));
			if (this.trace) {
				log.info("nº matches profiles: {}", Integer.valueOf(results.getnProfiles()));
			}
			
			if (results.getnProfiles() > 0) {
				results.setLinkedinProfiles(createLinkedinProfileList(page));
			}
			
		}
		catch (HttpStatusException e) {
			
			if (this.trace) {
				log.error("no hay resultados para {}", surnameData.getSurname());
			}
			results.setnProfiles(0);
			
		}
		
		return results;
		
	}

	private String generateSearchUrl(String surname) {
		String s = surname.replace(" ", "+").toLowerCase().trim();

		StringBuffer url = new StringBuffer(SEARCH_URL_BASE);
		url.append("last=").append(s);

		return url.toString();
	}

	private List<LinkedinProfile> createLinkedinProfileList(Document page) {
		ArrayList<LinkedinProfile> profilesList = Lists.newArrayList();

		Elements vcard = page.select("li.vcard");
		for (int i = 0; i < vcard.size(); i++) {
			
			LinkedinProfile profile = new LinkedinProfile();
			
			profile.setName(HtmlParserUtil.getTitle(vcard.get(i)));
			if (this.trace) {
				log.info("Procesando perfil {} de : {}", Integer.valueOf(i + 1), profile.getName());
			}
			
			String publicUrl = HtmlParserUtil.getPublicUrl(vcard.get(i));
			if ((publicUrl != null) && (publicUrl.length() > 0)) {
				profile.setPublicUrl(publicUrl);
			}
			
			String location = HtmlParserUtil.getLocation(vcard.get(i));
			if ((location != null) && (location.length() > 0)) {
				profile.setLocation(location);
			}
			
			String industry = HtmlParserUtil.getIndustry(vcard.get(i));
			if ((industry != null) && (industry.length() > 0)) {
				profile.setIndustry(industry);
			}
			
			String activeJob = HtmlParserUtil.getActiveJob(vcard.get(i));
			if ((activeJob != null) && (activeJob.length() > 0)) {
				profile.setActiveJob(activeJob);
			}
			
			String education = HtmlParserUtil.getEducation(vcard.get(i));
			if ((education != null) && (education.length() > 0)) {
				profile.setFormation(education);
			}
			
			profilesList.add(profile);
			
		}
		
		return profilesList;
		
	}
	
}
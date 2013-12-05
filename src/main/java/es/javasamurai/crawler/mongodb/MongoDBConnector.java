package es.javasamurai.crawler.mongodb;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import es.javasamurai.crawler.data.LinkedinProfile;
import es.javasamurai.crawler.data.ResultSearchData;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDBConnector {
	private static final String MONGO_SERVER = "java-samurai.es";
	private static final int PORT = 27017;
	private static final String DB_CRAWLER_LINKEDIN = "crawler-linkedin";
	private static final String PROFILES = "profiles";
	
	private static Logger log = LoggerFactory.getLogger(MongoDBConnector.class);
	
	private MongoClient client;
	private DB db;
	private DBCollection profilesCollection;

	public MongoDBConnector() throws UnknownHostException {
		this.client = new MongoClient(MONGO_SERVER, PORT);

		this.db = this.client.getDB(DB_CRAWLER_LINKEDIN);
		this.profilesCollection = this.db.getCollection(PROFILES);
	}

	public void insert(ResultSearchData result) {
		
		BasicDBObjectBuilder search = new BasicDBObjectBuilder();
		
		search.add("surname_id", Integer.valueOf(result.getSurnameData().getId()));
		search.add("surname", result.getSurnameData().getSurname());
		search.add("frequency", Long.valueOf(result.getSurnameData().getFrequency()));
		search.add("search_url", result.getSearchUrl());
		search.add("matches", Integer.valueOf(result.getnProfiles()));

		ArrayList<DBObject> profiles = Lists.newArrayList();
		
		for (LinkedinProfile p : result.getLinkedinProfiles()) {
			
			BasicDBObjectBuilder profile = new BasicDBObjectBuilder();
			profile.add("name", p.getName());
			profile.add("public_url", p.getPublicUrl());
			if (p.getLocation() != null) {
				profile.add("location", p.getLocation());
			}
			if (p.getIndustry() != null) {
				profile.add("industry", p.getIndustry());
			}
			if (p.getActiveJob() != null) {
				profile.add("active-job", p.getActiveJob());
			}
			if (p.getFormation() != null) {
				profile.add("education", p.getFormation());
			}
			profiles.add(profile.get());
			
		}
		
		search.add("profiles", profiles);

		WriteResult message = this.profilesCollection.insert(new DBObject[] { search.get() });
		if (message.getError() != null) {
			log.error("Error en inserccion: {}", message.getError());
		}
		else {
			log.info("Insertados resultados para surname: {}", result.getSurnameData().getSurname());
		}
		
	}

	public void close() {
		this.client.close();
	}

}
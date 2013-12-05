package es.javasamurai.crawler.data;

public class LinkedinProfile {
	private String name;
	private String publicUrl;
	private String activeJob;
	private String location;
	private String formation;
	private String industry;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublicUrl() {
		return this.publicUrl;
	}

	public void setPublicUrl(String publicUrl) {
		this.publicUrl = publicUrl;
	}

	public String getActiveJob() {
		return this.activeJob;
	}

	public void setActiveJob(String activeJob) {
		this.activeJob = activeJob;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFormation() {
		return this.formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

}
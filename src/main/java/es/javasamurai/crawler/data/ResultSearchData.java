package es.javasamurai.crawler.data;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

public class ResultSearchData {
	private SurnameData surnameData;
	private String searchUrl;
	private List<LinkedinProfile> profiles;
	private int nProfiles;

	public ResultSearchData() {
		this.profiles = Lists.newArrayList();
	}

	public SurnameData getSurnameData() {
		return this.surnameData;
	}

	public void setSurnameData(SurnameData surnameData) {
		this.surnameData = surnameData;
	}

	public String getSearchUrl() {
		return this.searchUrl;
	}

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public void setLinkedinProfile(LinkedinProfile profile) {
		this.profiles.add(profile);
	}

	public List<LinkedinProfile> getLinkedinProfiles() {
		return this.profiles;
	}

	public void setLinkedinProfiles(List<LinkedinProfile> profiles) {
		this.profiles = profiles;
	}

	public List<LinkedinProfile> getProfiles() {
		return this.profiles;
	}

	public void setProfiles(ArrayList<LinkedinProfile> profiles) {
		this.profiles = profiles;
	}

	public int getnProfiles() {
		return this.nProfiles;
	}

	public void setnProfiles(int nProfiles) {
		this.nProfiles = nProfiles;
	}

}

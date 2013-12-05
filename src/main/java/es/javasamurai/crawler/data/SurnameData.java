package es.javasamurai.crawler.data;

public class SurnameData {
	private int id;
	private String surname;
	private long freqFirstSurname;
	private long freqSecondSurname;
	private long frequency;

	public SurnameData() {
	}

	public SurnameData(int id, String surname, long freqFirstSurname, long freqSecondSurname, 
			long frequency) {
		this.id = id;
		this.surname = surname;
		this.freqFirstSurname = freqFirstSurname;
		this.freqSecondSurname = freqSecondSurname;
		this.frequency = frequency;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public long getFreqFirstSurname() {
		return this.freqFirstSurname;
	}

	public void setFreqFirstSurname(long freqFirstSurname) {
		this.freqFirstSurname = freqFirstSurname;
	}

	public long getFreqSecondSurname() {
		return this.freqSecondSurname;
	}

	public void setFreqSecondSurname(long freqSecondSurname) {
		this.freqSecondSurname = freqSecondSurname;
	}

	public long getFrequency() {
		return this.frequency;
	}

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}
	
}
package se.berkar.web.model;

public class Option {
	private Integer itsId;
	private String itsText;

	public Option() {
	}

	public Option(Integer theId, String theText) {
		itsId = theId;
		itsText = theText;
	}

	public Integer getId() {
		return itsId;
	}

	public void setId(Integer theId) {
		itsId = theId;
	}

	public String getText() {
		return itsText;
	}

	public void setText(String theText) {
		itsText = theText;
	}
}

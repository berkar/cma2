package se.berkar;

public class ApplicationProperty {
	private String itsKey;
	private String itsDefaultValue;

	public ApplicationProperty(String theKey) {
		itsKey = theKey;
		itsDefaultValue = null;
	}

	public ApplicationProperty(String theKey, String theDefaultValue) {
		itsKey = theKey;
		itsDefaultValue = theDefaultValue;
	}

	public String key() {
		return itsKey;
	}

	public String defaultValue() {
		return itsDefaultValue;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ApplicationProperty)) return false;

		ApplicationProperty that = (ApplicationProperty) o;

		if (!itsKey.equals(that.itsKey)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return itsKey.hashCode();
	}

	@Override
	public String toString() {
		return itsKey + "(" + (itsDefaultValue != null ? itsDefaultValue : "null") + ")";
	}
}
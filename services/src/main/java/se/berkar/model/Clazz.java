package se.berkar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "class")
public class Clazz {

	private String itsKey;
	private String itsNamn;
	private Integer itsStartnumber;
	private String itsStarttime;

	@Id
	@Column(name = "key")
	public String getKey() {
		return itsKey;
	}

	public void setKey(String theKey) {
		itsKey = theKey;
	}

	@Column(name = "namn")
	public String getNamn() {
		return itsNamn;
	}

	public void setNamn(String theNamn) {
		itsNamn = theNamn;
	}

	@Column(name = "start_number")
	public Integer getStartnumber() {
		return itsStartnumber;
	}

	public void setStartnumber(Integer theStartnumber) {
		itsStartnumber = theStartnumber;
	}

	@Column(name = "start_time")
	public String getStarttime() {
		return itsStarttime;
	}

	public void setStarttime(String theStarttime) {
		itsStarttime = theStarttime;
	}
}

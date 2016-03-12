package se.berkar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;


@MappedSuperclass
public class Item {

	private Integer itsDid;
	private String itsFirstname;
	private String itsLastname;
	private String itsGender;
	private String itsClazz;
	private Integer itsStartnumber;
	private Date itsStarttime;
	private Boolean itsDidNotStart;
	private Boolean itsDidNotFinish;

	@Id
	@Column(name = "did")
	@GeneratedValue(generator = "SQ_CMA", strategy = GenerationType.SEQUENCE)
	public Integer getDid() {
		return itsDid;
	}

	public void setDid(Integer theDid) {
		itsDid = theDid;
	}

	@NotNull
	@Column(name = "first_name", nullable = false)
	public String getFirstname() {
		return itsFirstname;
	}

	public void setFirstname(String theFirstname) {
		itsFirstname = theFirstname;
	}

	@Column(name = "last_name", nullable = true)
	public String getLastname() {
		return itsLastname;
	}

	public void setLastname(String theLastname) {
		itsLastname = theLastname;
	}

	@Column(name = "gender", nullable = true)
	public String getGender() {
		return itsGender;
	}

	public void setGender(String theGender) {
		itsGender = theGender;
	}

	@Column(name = "class", nullable = true)
	public String getClazz() {
		return itsClazz;
	}

	public void setClazz(String theClazz) {
		itsClazz = theClazz;
	}

	@Column(name = "start_number")
	public Integer getStartnumber() {
		return itsStartnumber;
	}

	public void setStartnumber(Integer theStartnumber) {
		itsStartnumber = theStartnumber;
	}

	@Column(name = "start_time")
	public Date getStarttime() {
		return itsStarttime;
	}

	public void setStarttime(Date theStarttime) {
		itsStarttime = theStarttime;
	}

	@Column(name = "did_not_start")
	public Boolean getDidNotStart() {
		return itsDidNotStart;
	}

	public void setDidNotStart(Boolean theDidNotStart) {
		itsDidNotStart = theDidNotStart;
	}

	@Column(name = "did_not_finish")
	public Boolean getDidNotFinish() {
		return itsDidNotFinish;
	}

	public void setDidNotFinish(Boolean theDidNotFinish) {
		itsDidNotFinish = theDidNotFinish;
	}
}


package se.berkar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "START")
@SequenceGenerator(name = "SQ_CMA", sequenceName = "sq_cma", allocationSize = 1)
public class Start {

	private Integer itsDid;
	private String itsName;
	private String itsGender;
	private Clazz itsClazz;
	private Integer itsStartnumber;
	private Integer itsStarttime; // In seconds after start of competition clock

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
	@Column(name = "name", nullable = false)
	public String getName() {
		return itsName;
	}

	public void setName(String theName) {
		itsName = theName;
	}

	@Column(name = "gender", nullable = true)
	public String getGender() {
		return itsGender;
	}

	public void setGender(String theGender) {
		itsGender = theGender;
	}

	@ManyToOne
	@JoinColumn(name = "class", nullable = true)
	public Clazz getClazz() {
		return itsClazz;
	}

	public void setClazz(Clazz theClazz) {
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
	public Integer getStarttime() {
		return itsStarttime;
	}

	public void setStarttime(Integer theStarttime) {
		itsStarttime = theStarttime;
	}
}


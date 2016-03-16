package se.berkar.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;


@MappedSuperclass
public class Item {

	private Integer itsDid;
	private String itsName;
	private String itsGender;
	private Clazz itsClazz;

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
	@JoinColumn(name = "class", nullable = false)
	public Clazz getClazz() {
		return itsClazz;
	}

	public void setClazz(Clazz theClazz) {
		itsClazz = theClazz;
	}

}


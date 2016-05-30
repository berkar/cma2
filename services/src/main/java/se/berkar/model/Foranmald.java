package se.berkar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "foranmald")
@SequenceGenerator(name = "SQ_CMA", sequenceName = "sq_cma", allocationSize = 1)
public class Foranmald {

	@Id
	@Column(name = "did")
	@GeneratedValue(generator = "SQ_CMA", strategy = GenerationType.SEQUENCE)
	private Integer itsDid;

	@NotNull
	@Column(name = "name", nullable = false)
	private String itsName;

	public Foranmald() {
	}

	public Foranmald(String theName) {
		itsName = theName;
	}

	public Integer getDid() {
		return itsDid;
	}

	public void setDid(Integer theDid) {
		itsDid = theDid;
	}

	public String getName() {
		return itsName;
	}

	public void setName(String theName) {
		itsName = theName;
	}
}


package se.berkar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RESULTAT")
@SequenceGenerator(name = "SQ_CMA", sequenceName = "sq_cma", allocationSize = 1)
public class Resultat {

	private Integer itsDid;
	private Integer itsStartnumber;
	private Double itsFinishtime;

	public Resultat() {
	}

	public Resultat(Integer theStartnumber, Double theFinishtime) {
		itsStartnumber = theStartnumber;
		itsFinishtime = theFinishtime;
	}

	@Id
	@Column(name = "DID")
	@GeneratedValue(generator = "SQ_CMA", strategy = GenerationType.SEQUENCE)
	public Integer getDid() {
		return itsDid;
	}

	public void setDid(Integer theDid) {
		itsDid = theDid;
	}

	@Column(name = "START_NUMBER")
	public Integer getStartnumber() {
		return itsStartnumber;
	}

	public void setStartnumber(Integer theStartnumber) {
		itsStartnumber = theStartnumber;
	}

	@Column(name = "FINISH_TIME")
	public Double getFinishtime() {
		return itsFinishtime;
	}

	public void setFinishtime(Double theFinishtime) {
		itsFinishtime = theFinishtime;
	}
}


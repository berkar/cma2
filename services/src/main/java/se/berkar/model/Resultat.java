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
	private Integer itsPlaceringTotal;
	private Integer itsPlaceringClazz;
	private Integer itsPlaceringGender;
	private Integer itsFinishOrder;
	private Integer itsFinishtime;
	private Integer itsTotaltime;
	private String itsMedalj;
	private Boolean itsDidNotStart;
	private Boolean itsDidNotFinish;

	public Resultat() {
	}

	public Resultat(Integer theStartnumber) {
		itsStartnumber = theStartnumber;
	}

	public Resultat(Integer theStartnumber, Integer theFinishtime, Integer theFinishOrder) {
		itsStartnumber = theStartnumber;
		itsFinishtime = theFinishtime;
		itsFinishOrder = theFinishOrder;
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

	@Column(name = "PLACERING_TOTAL")
	public Integer getPlaceringTotal() {
		return itsPlaceringTotal;
	}

	public void setPlaceringTotal(Integer thePlaceringTotal) {
		itsPlaceringTotal = thePlaceringTotal;
	}

	@Column(name = "PLACERING_CLASS")
	public Integer getPlaceringClazz() {
		return itsPlaceringClazz;
	}

	public void setPlaceringClazz(Integer thePlaceringClazz) {
		itsPlaceringClazz = thePlaceringClazz;
	}

	@Column(name = "PLACERING_GENDER")
	public Integer getPlaceringGender() {
		return itsPlaceringGender;
	}

	public void setPlaceringGender(Integer thePlaceringGender) {
		itsPlaceringGender = thePlaceringGender;
	}

	@Column(name = "FINISH_ORDER")
	public Integer getFinishOrder() {
		return itsFinishOrder;
	}

	public void setFinishOrder(Integer theFinishOrder) {
		itsFinishOrder = theFinishOrder;
	}

	@Column(name = "FINISH_TIME")
	public Integer getFinishtime() {
		return itsFinishtime;
	}

	public void setFinishtime(Integer theFinishtime) {
		itsFinishtime = theFinishtime;
	}

	@Column(name = "TOTAL_TIME")
	public Integer getTotaltime() {
		return itsTotaltime;
	}

	public void setTotaltime(Integer theTotaltime) {
		itsTotaltime = theTotaltime;
	}

	@Column(name = "MEDALJ")
	public String getMedalj() {
		return itsMedalj;
	}

	public void setMedalj(String theMedalj) {
		itsMedalj = theMedalj;
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


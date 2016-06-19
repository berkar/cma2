package se.berkar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RESULTATLISTA_GV")
public class Resultatlista {

	private Integer itsDid;
	private String itsName;
	private String itsClazz;
	private String itsGender;
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

	@Id
	@Column(name = "DID")
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

	@Column(name = "class", nullable = false)
	public String getClazz() {
		return itsClazz;
	}

	public void setClazz(String theClazz) {
		itsClazz = theClazz;
	}

	@Column(name = "gender", nullable = false)
	public String getGender() {
		return itsGender;
	}

	public void setGender(String theGender) {
		itsGender = theGender;
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


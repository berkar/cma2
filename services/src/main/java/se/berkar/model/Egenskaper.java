package se.berkar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "egenskaper")
public class Egenskaper {

	@Id
	@Column(name = "did")
	private Integer itsDid;

	@NotNull
	@Column(name = "starttid", nullable = false)
	private String itsStarttid;

	@NotNull
	@Column(name = "antal_per_grupp", nullable = false)
	private Integer itsAntalPerGrupp;

	@NotNull
	@Column(name = "forsta_startnummer", nullable = false)
	private Integer itsForstaStartnummer;

	@NotNull
	@Column(name = "tid_mellan_grupper_minuter", nullable = false)
	private Integer itsTidMellanGrupper;

	@NotNull
	@Column(name = "antal_varv", nullable = false)
	private Integer itsAntalVarv;

	public Integer getDid() {
		return itsDid;
	}

	public void setDid(Integer theDid) {
		itsDid = theDid;
	}

	public String getStarttid() {
		return itsStarttid;
	}

	public void setStarttid(String theStarttid) {
		itsStarttid = theStarttid;
	}

	public Integer getAntalPerGrupp() {
		return itsAntalPerGrupp;
	}

	public void setAntalPerGrupp(Integer theAntalPerGrupp) {
		itsAntalPerGrupp = theAntalPerGrupp;
	}

	public Integer getForstaStartnummer() {
		return itsForstaStartnummer;
	}

	public void setForstaStartnummer(Integer theForstaStartnummer) {
		itsForstaStartnummer = theForstaStartnummer;
	}

	public Integer getTidMellanGrupper() {
		return itsTidMellanGrupper;
	}

	public void setTidMellanGrupper(Integer theTidMellanGrupper) {
		itsTidMellanGrupper = theTidMellanGrupper;
	}

	public Integer getAntalVarv() {
		return itsAntalVarv;
	}

	public void setAntalVarv(Integer theAntalVarv) {
		itsAntalVarv = theAntalVarv;
	}
}

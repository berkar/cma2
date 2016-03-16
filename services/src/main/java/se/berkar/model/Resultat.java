package se.berkar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESULTAT")
public class Resultat {

	private Integer itsDid;
	private Integer itsStartnumber;
	private Date itsFinishtime;

	@Id
	@Column(name = "did")
	public Integer getDid() {
		return itsDid;
	}

	public void setDid(Integer theDid) {
		itsDid = theDid;
	}

	@Column(name = "start_number")
	public Integer getStartnumber() {
		return itsStartnumber;
	}

	public void setStartnumber(Integer theStartnumber) {
		itsStartnumber = theStartnumber;
	}

	@Column(name = "finish_time")
	public Date getFinishtime() {
		return itsFinishtime;
	}

	public void setFinishtime(Date theFinishtime) {
		itsFinishtime = theFinishtime;
	}
}


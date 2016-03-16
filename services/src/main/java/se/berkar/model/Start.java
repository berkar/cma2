package se.berkar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "START")
@SequenceGenerator(name = "SQ_CMA", sequenceName = "sq_cma", allocationSize = 1)
public class Start extends Item {

	private Integer itsStartnumber;
	private Date itsStarttime;
	private Boolean itsDidNotStart;
	private Boolean itsDidNotFinish;

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


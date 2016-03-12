package se.berkar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "resultat_gv")
@SequenceGenerator(name = "SQ_CMA", sequenceName = "sq_cma", allocationSize = 1)
public class Resultat extends Item {

	private Integer itsFinishorder;
	private Date itsFinishtime;

	@Column(name = "finish_order")
	public Integer getFinishorder() {
		return itsFinishorder;
	}

	public void setFinishorder(Integer theFinishorder) {
		itsFinishorder = theFinishorder;
	}

	@Column(name = "finish_time")
	public Date getFinishtime() {
		return itsFinishtime;
	}

	public void setFinishtime(Date theFinishtime) {
		itsFinishtime = theFinishtime;
	}
}


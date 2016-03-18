package se.berkar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RESULTATLISTA_GV")
@SequenceGenerator(name = "SQ_CMA", sequenceName = "sq_cma", allocationSize = 1)
public class Resultatlista extends Item {

	private Integer itsFinishOrder;
	private Double itsFinishtime;

	@Column(name = "FINISH_ORDER")
	public Integer getFinishOrder() {
		return itsFinishOrder;
	}

	public void setFinishOrder(Integer theFinishOrder) {
		itsFinishOrder = theFinishOrder;
	}

	@Column(name = "FINISH_TIME")
	public Double getFinishtime() {
		return itsFinishtime;
	}

	public void setFinishtime(Double theFinishtime) {
		itsFinishtime = theFinishtime;
	}
}


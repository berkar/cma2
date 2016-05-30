package se.berkar.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import se.berkar.model.Foranmald;
import se.berkar.services.ForanmaldServiceBean;

@ManagedBean
public class ForanmaldBean {

	private String itsName;

	public String getName() {
		return itsName;
	}

	public void setName(String theTheName) {
		itsName = theTheName;
	}

	@Inject
	private ForanmaldServiceBean itsForanmaldService;

	public List<Foranmald> getForanmalda() {
		return itsForanmaldService.getNative(itsName);
	}

}

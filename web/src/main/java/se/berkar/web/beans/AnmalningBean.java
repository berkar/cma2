package se.berkar.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import se.berkar.model.Anmalning;
import se.berkar.services.AnmalningServiceBean;

@ManagedBean
public class AnmalningBean {

	private String itsName;

	public String getName() {
		return itsName;
	}

	public void setName(String theTheName) {
		itsName = theTheName;
	}

	private String itsGender;

	public String getGender() {
		return itsGender;
	}

	public void setGender(String theGender) {
		itsGender = theGender;
	}

	private String itsClazz;

	public String getClazz() {
		return itsClazz;
	}

	public void setClazz(String theTheClazz) {
		itsClazz = theTheClazz;
	}

	@Inject
	private AnmalningServiceBean itsAnmalningService;

	public List<Anmalning> getAnmalningar() {
		return itsAnmalningService.getNative(itsName, itsGender, itsClazz);
	}

}

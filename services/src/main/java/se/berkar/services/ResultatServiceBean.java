package se.berkar.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;

import se.berkar.CmaConfiguration;
import se.berkar.model.Resultat;
import se.berkar.qualifiers.CmaLogger;
import se.berkar.qualifiers.CmaServiceDB;

import org.jboss.logging.Logger;

@Stateless
@Path("resultat")
public class ResultatServiceBean {

	@Inject
	@CmaLogger
	private transient Logger itsLog;

	@Inject
	@CmaServiceDB
	private EntityManager itsEntityManager;

	@Inject
	private CmaConfiguration itsConfiguration;

	@HEAD
	@Path("/ping")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean getStatus() {
		itsLog.trace("Status Ping received!");
		return true;
	}

	public void upload(List<Resultat> theResultatList) {
		// Delete all in Resultat, and add content from List
		itsEntityManager.createQuery("DELETE FROM Resultat").executeUpdate();
		theResultatList.stream().forEach(theResultat -> itsEntityManager.persist(theResultat));

	}
}

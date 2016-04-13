package se.berkar.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.berkar.CmaConfiguration;
import se.berkar.common.helpers.EmptyHandler;
import se.berkar.model.Anmalning;
import se.berkar.qualifiers.CmaLogger;
import se.berkar.qualifiers.CmaServiceDB;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;

@Stateless
@Path("anmalning")
public class AnmalningServiceBean {

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

	/**
	 * Get the list of anmälningar, based on optional params
	 * @param theName
	 * @param theGender
	 * @param theClass
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response get(@QueryParam("name") String theName, @QueryParam("gender") String theGender, @QueryParam("class") String theClass) throws Exception {
		return Response.ok(getNative(theName, theGender, theClass)).build();
	}

	@SuppressWarnings("unchecked")
	public List<Anmalning> getNative(String theName, String theGender, String theClass) {
		Criteria aCriteria = ((Session) itsEntityManager.getDelegate()).createCriteria(Anmalning.class);
		if (EmptyHandler.isNotEmpty(theName)) {
			aCriteria.add(Restrictions.ilike("name", theName.trim().replaceAll(" ", "%"), MatchMode.START));
		}
		if (EmptyHandler.isNotEmpty(theGender)) {
			aCriteria.add(Restrictions.eq("gender", theGender.trim()));
		}
		if (EmptyHandler.isNotEmpty(theClass)) {
			aCriteria.add(Restrictions.eq("class", theClass.trim()));
		}
		return aCriteria.list();
	}

	@GET
	@Path("/{did}")
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response get(@PathParam("did") Integer theDid) throws Exception {
		Anmalning aObject = getNative(theDid);
		if (aObject != null) {
			return Response.ok(aObject).build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	public Anmalning getNative(Integer theDid) {
		return itsEntityManager.find(Anmalning.class, theDid);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void upload(List<Anmalning> theAnmalningsList) {
		// Delete all in Anmalning, and add content from List
		itsEntityManager.createQuery("DELETE FROM Anmalning").executeUpdate();
		theAnmalningsList.stream().forEach(theAnmalning -> itsEntityManager.persist(theAnmalning));
	}

}

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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.berkar.CmaConfiguration;
import se.berkar.common.helpers.EmptyHandler;
import se.berkar.model.Foranmald;
import se.berkar.qualifiers.CmaLogger;
import se.berkar.qualifiers.CmaServiceDB;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;

@Stateless
@Path("foranmald")
public class ForanmaldServiceBean {

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
	 * Get the list of föranmälda, based on optional params, name
	 * @param theName .
	 * @return .
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response get(@QueryParam("name") String theName) throws Exception {
		return Response.ok(getNative(theName)).build();
	}

	@SuppressWarnings("unchecked")
	public List<Foranmald> getNative(String theName) {
		Criteria aCriteria = ((Session) itsEntityManager.getDelegate()).createCriteria(Foranmald.class);
		if (EmptyHandler.isNotEmpty(theName)) {
			aCriteria.add(Restrictions.ilike("name", theName.trim().replaceAll(" ", "%"), MatchMode.START));
		}
		return aCriteria.list();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void upload(List<Foranmald> theAnmalningsList) {
		// Delete all in Anmalning, and add content from List
		itsEntityManager.createQuery("DELETE FROM Foranmald").executeUpdate();
		theAnmalningsList.stream().forEach(theAnmalning -> itsEntityManager.persist(theAnmalning));
	}

}

package se.berkar.services;

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
import se.berkar.model.Resultatlista;
import se.berkar.qualifiers.CmaLogger;
import se.berkar.qualifiers.CmaServiceDB;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;

@Stateless
@Path("resultatlista")
public class ResultatlistaServiceBean {

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
	 * Get the resultlist, based on optional params
	 * @param theGender
	 * @param theClass
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response get(@QueryParam("gender") String theGender, @QueryParam("class") String theClass) throws Exception {
		Criteria aCriteria = ((Session) itsEntityManager.getDelegate()).createCriteria(Resultatlista.class);
		if (EmptyHandler.isNotEmpty(theGender)) {
			aCriteria.add(Restrictions.eq("gender", theGender.trim()));
		}
		if (EmptyHandler.isNotEmpty(theClass)) {
			aCriteria.add(Restrictions.eq("class", theClass.trim()));
		}
		aCriteria.add(Restrictions.isNotNull("finishtime"));
		aCriteria.addOrder(Order.asc("finishtime"));
		return Response.ok(aCriteria.list()).build();
	}
}

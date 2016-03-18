package se.berkar.services;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.berkar.CmaConfiguration;
import se.berkar.common.helpers.EmptyHandler;
import se.berkar.model.Start;
import se.berkar.qualifiers.CmaLogger;
import se.berkar.qualifiers.CmaServiceDB;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;

@Stateless
@Path("start")
public class StartServiceBean {

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
	 * Get the list of starts, based on optional params
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
		Criteria aCriteria = ((Session) itsEntityManager.getDelegate()).createCriteria(Start.class);
		if (EmptyHandler.isNotEmpty(theName)) {
			aCriteria.add(Restrictions.ilike("name", theName.trim().replaceAll(" ", "%"), MatchMode.START));
		}
		if (EmptyHandler.isNotEmpty(theGender)) {
			aCriteria.add(Restrictions.eq("gender", theGender.trim()));
		}
		if (EmptyHandler.isNotEmpty(theClass)) {
			aCriteria.add(Restrictions.eq("class", theClass.trim()));
		}
		return Response.ok(aCriteria.list()).build();
	}

	@GET
	@Path("/{did}")
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response get(@PathParam("did") Integer theDid) throws Exception {
		Start aObject = itsEntityManager.find(Start.class, theDid);
		if (aObject != null) {
			return Response.ok(aObject).build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response create(Start theStart) throws Exception {
		itsEntityManager.persist(theStart);
		return Response.ok(theStart).build();
	}

	@PUT
	@Path("/{did}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response update(@PathParam("did") Integer theDid, Start theStart) throws Exception {
		Start aObject = itsEntityManager.merge(theStart);
		if (aObject != null) {
			return Response.ok(aObject).build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	@DELETE
	@Path("/{did}")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response delete(@PathParam("did") Integer theDid) throws Exception {
		Start aObject = itsEntityManager.find(Start.class, theDid);
		if (aObject != null) {
			itsEntityManager.remove(aObject);
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

}

package se.berkar.services;

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

import se.berkar.common.helpers.EmptyHandler;
import se.berkar.model.Result;
import se.berkar.qualifiers.CmaLogger;
import se.berkar.qualifiers.CmaServiceDB;

import org.jboss.logging.Logger;

@Stateless
@Path("result")
public class ResultatServiceBean {

	@Inject
	@CmaLogger
	private transient Logger itsLog;

	@Inject
	@CmaServiceDB
	private EntityManager itsEntityManager;

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
		if (EmptyHandler.isNotEmpty(theGender) && EmptyHandler.isNotEmpty(theClass)) {
			return Response.ok(itsEntityManager.createQuery("SELECT res FROM Result res WHERE res.gender=:gender AND res.clazz=:class")
					.setParameter("gender", theGender)
					.setParameter("class", theClass)
					.getResultList()).build();
		} else if (EmptyHandler.isNotEmpty(theGender)) {
			return Response.ok(itsEntityManager.createQuery("SELECT res FROM Result res WHERE res.gender=:gender")
					.setParameter("gender", theGender)
					.getResultList()).build();

		} else if (EmptyHandler.isNotEmpty(theClass)) {
			return Response.ok(itsEntityManager.createQuery("SELECT res FROM Result res WHERE res.clazz=:class")
					.setParameter("class", theClass)
					.getResultList()).build();
		}
		return Response.ok(itsEntityManager.createQuery("SELECT res FROM Result res ORDER BY res.finishtime").getResultList()).build();
	}

	@GET
	@Path("/{did}")
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response get(@PathParam("did") Integer theDid) throws Exception {
		Result aObject = itsEntityManager.find(Result.class, theDid);
		if (aObject != null) {
			return Response.ok(aObject).build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	@GET
	@Path("/{startnumber}/startnumber")
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response getByStartNumber(@PathParam("startnumber") Integer theStartnumber) throws Exception {
		Result aObject = itsEntityManager.createQuery("SELECT reg FROM Result reg WHERE reg.startnumber=:startnumber", Result.class)
				.setParameter("startnumber", theStartnumber)
				.getSingleResult();
		if (aObject != null) {
			return Response.ok(aObject).build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

}

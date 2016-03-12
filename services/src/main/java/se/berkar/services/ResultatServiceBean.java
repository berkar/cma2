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
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	@Path("/{did}")
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response get(@PathParam("did") Integer theDid) throws Exception {
		Resultat aObject = itsEntityManager.find(Resultat.class, theDid);
		if (aObject != null) {
			return Response.ok(aObject).build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	@GET
	@Path("/{startnumber}/startnummer")
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response getByStartNumber(@PathParam("startnumber") Integer theStartnumber) throws Exception {
		Resultat aObject = itsEntityManager.createQuery("SELECT reg FROM Resultat reg WHERE reg.startnumber=:startnumber", Resultat.class)
				.setParameter("startnumber", theStartnumber)
				.getSingleResult();
		if (aObject != null) {
			return Response.ok(aObject).build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

}

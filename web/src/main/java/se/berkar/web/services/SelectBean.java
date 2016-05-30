package se.berkar.web.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.berkar.services.ForanmaldServiceBean;
import se.berkar.services.StartServiceBean;
import se.berkar.web.model.Option;

@Path("/select")
public class SelectBean {

	@Inject
	private ForanmaldServiceBean itsForanmaldService;

	@Inject
	private StartServiceBean itsStartService;

	@GET
	@Path("/foranmalda")
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Option> getAnmalningar() throws Exception {
		return itsForanmaldService.getNative(null).stream().map(theAnmalan -> new Option(theAnmalan.getDid(), theAnmalan.getName())).collect(Collectors.toList());
	}

	@GET
	@Path("/starter")
	@Produces(MediaType.APPLICATION_JSON)
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Option> getStarter() throws Exception {
//		return itsStartService.getNative(null, null, null).stream().map(theAnmalan -> new Option(theAnmalan.getDid(), theAnmalan.getName())).collect(Collectors.toList());
		return null;
	}
}

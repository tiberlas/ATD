package rest;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import agents.HostAgentLocal;

@Stateless
@Path("/")
@LocalBean
public class IdentifyController {

	@EJB
	private HostAgentLocal host;
	

	@GET
	@Path("identify")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMessages() {
		
		return Response.ok(host.getHost().getAlias()).build();
	}
}

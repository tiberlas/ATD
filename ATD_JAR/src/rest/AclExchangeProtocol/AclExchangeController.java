package rest.AclExchangeProtocol;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import agents.HostAgentLocal;
import model.ACL;

@Stateless
@Path("/")
@LocalBean
public class AclExchangeController {

	@EJB
	private HostAgentLocal host;
	
	@POST
	@Path("exchange/acl")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendACL(ACL acl) {
		host.handleMessage(acl);

		return Response.ok().build(); 
	}
}

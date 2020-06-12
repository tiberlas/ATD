package rest.AgentExchangeProtocol;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import agentManager.OnLineAgentManagerlocal;
import model.AID;

@Stateless
@Path("/")
@LocalBean
public class AgentExchangeController {
	
	@EJB
	private OnLineAgentManagerlocal onLineManager;
	
	@POST
	@Path("exchange/agent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response gotAID(AID aid) {
		onLineManager.addAgent(aid);

		return Response.ok().build(); 
	}

}

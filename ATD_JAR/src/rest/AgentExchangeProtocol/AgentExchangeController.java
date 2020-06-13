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
	@Path("exchange/agent/start")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response startAID(AID aid) {
		onLineManager.addAgent(aid);

		System.out.println("=> REQUEST: POST: exchange/agent/start: "+ aid);
		return Response.ok().build(); 
	}
	
	@POST
	@Path("exchange/agent/stop")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response stopAID(AID aid) {
		onLineManager.removeAgent(aid);

		System.out.println("=> REQUEST: POST: exchange/agent/stop: "+ aid);
		return Response.ok().build(); 
	}

}

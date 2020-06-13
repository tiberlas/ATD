package rest.handShakeProtocol;

import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import agentManager.OnLineAgentManagerlocal;
import config.HandShakeProtocol;
import model.AID;
import model.AgentType;
import model.Host;

@Stateless
@Path("/hand-shake")
@LocalBean
public class HandShakeController {

	@EJB
	private OnLineAgentManagerlocal onLineAgentManager;
	@EJB
	private HandShakeProtocol protocol;
	
	@POST
	@Path("node")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getNewHostNode(Host newHostNode) {
		onLineAgentManager.addHost(newHostNode);
		
		if(onLineAgentManager.getMaster() == null) {
			protocol.masterRecivedANewNode(newHostNode);
		}
		
		System.out.println("=> REQUEST: POST: hand-shake/node (45) "+ newHostNode);
		return Response.ok().build();
	}
	
	@POST
	@Path("nodes")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getNewHostNodes(Set<Host> newHostNodes) {
		onLineAgentManager.addHosts(newHostNodes);
		
		System.out.println("=> REQUEST: POST: hand-shake/nodes (55) "+ newHostNodes);
		return Response.ok().build();
	}
	
	@GET
	@Path("agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<AgentType> getAllAgentTypes() {
		System.out.println("=> REQUEST: GET: hand-shake/agent/classes");
		return onLineAgentManager.getAllTypes();
	}
	
	@POST
	@Path("agents/classes")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setAllAgentTypes(Map<String, Set<AgentType>> allTypes) {
		
		if(allTypes!=null && !allTypes.isEmpty()) {
			allTypes.forEach((alias, types) -> {
				onLineAgentManager.addTypes(types, alias);
			});
		}
		
		System.out.println("=> REQUEST: POST: hand-shake/agent/classes (78): " + allTypes);
		return Response.ok().build();
	}
	
	@POST
	@Path("agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAgents(Set<AID> aids) {
		onLineAgentManager.addAgents(aids);
		
		System.out.println("=> REQUEST: POST: hand-shake/agent/running: " + aids);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("node/{alias}")
	public Response removeNode(@PathParam("alias") String alias) {
		onLineAgentManager.removeHost(alias);
		
		System.out.println("=> REQUEST: DELETE: hand-shake/node: " + alias);
		return Response.ok().build();
	}

}
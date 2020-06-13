package controller;

import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.RunningAgentDTO;
import dto.TypeDTO;
import service.AgentService;
import service.TypeService;

@Stateless
@LocalBean
@Path("/agents")
public class AgentController {

	@EJB
	private TypeService typeService;
	
	@EJB
	private AgentService agentService;
	
	@GET
	@Path("/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<TypeDTO> getAllAgentTypes() {
		
		return typeService.getAllTypes();
	}
	
	@GET
	@Path("/running")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<RunningAgentDTO> getAllRunningAgents() {
		
		return agentService.getAllRunningAgents();
	}
	
	@PUT
	@Path("/running/{type}/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response startAgent(
			@PathParam("type") String type, 
			@PathParam("name") String name) {
		
		agentService.create(name, type);
		return Response.ok("AGENT CREATED").build();
	}
	
	@DELETE
	@Path("/running/{host}/{type}/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response stopAgent(
			@PathParam("host") String host, 
			@PathParam("type") String type, 
			@PathParam("name") String name) {
		
		agentService.stop(name, type, host);
		return Response.ok("AGENT STOPED").build();
	}
}

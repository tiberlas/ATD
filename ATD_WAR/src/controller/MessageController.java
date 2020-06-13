package controller;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.AclDTO;
import service.MessageService;

@Stateless
@LocalBean
@Path("/messages")
public class MessageController {

	@EJB
	private MessageService messageService;
	
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createACL(AclDTO message) {
		
		messageService.sendACL(message);
		return Response.ok("ACL SENT").build();
	}
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public String[] getAllPerformativs() {
		
		return messageService.getAllPerfomativs();
	}
	
	@GET
	@Path("/cn")
	public void startCN() {
		
		messageService.startCN();
	}
}

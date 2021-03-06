package rest.hartBeatProtocol;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Stateless
@Path("/")
@LocalBean
public class HartBeatController {

    @GET
	@Path("node")
	public Response getAllRegisteredUsers() {
    	System.out.println("=> REQUEST: GET:node  hartbeat");
		return Response.ok().build();
	}

}
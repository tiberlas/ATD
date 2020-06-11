package rest.handShakeProtocol;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import java.util.Map;
import java.util.Set;

import model.AID;
import model.AgentType;
import model.Host;

public abstract class HandShakeSender {
	
	public static boolean addNewNode(Host forHost, Host newHostNode) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/node");
		Response res = target.request().post(Entity.entity(newHostNode, MediaType.APPLICATION_JSON));
		
		return (res.getStatus() == 200);
	}
	
	public static boolean addNewNodes(Host forHost, Set<Host> newHostNodes) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/nodes");
		Response res = target.request().post(Entity.entity(newHostNodes, MediaType.APPLICATION_JSON));
		
		return (res.getStatus() == 200);
	}
	
	@SuppressWarnings("unchecked")
	public static Set<AgentType> getAllAgentTypes(Host forHost) {
		
		Set<AgentType> agents = null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/agents/classes");
		Response res = target.request().get();
		
		if(res.getStatus() == 200) {
			agents = (Set<AgentType>) res.readEntity((GenericType<AgentType>) agents);
			
			return agents;
		} else {

			return null;
		}
	}
	
	public static boolean addAgentTypes(Host forHost, Map<String, Set<AgentType>> allTypes) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/agents/classes");
		Response res = target.request().post(Entity.entity(allTypes, MediaType.APPLICATION_JSON));
		
		return (res.getStatus() == 200);
	}
	
	public static boolean addRunningAgenta(Host forHost, Set<AID> aids) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/agents/running");
		Response res = target.request().post(Entity.entity(aids, MediaType.APPLICATION_JSON));
		
		return (res.getStatus() == 200);
	}
	
	public static boolean remove(Host forHost, String removedNode) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/node/"+removedNode);
		Response res = target.request().delete();
		
		return (res.getStatus() == 200);
	}
}
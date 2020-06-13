package rest.handShakeProtocol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.google.gson.Gson;

import model.AID;
import model.AgentType;
import model.Host;

public abstract class HandShakeSender {
	
	public static boolean addNewNode(Host forHost, Host newHostNode) {
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
			System.out.println("=> POST" + "http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/node");
			ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/node");
			Response res = target.request().post(Entity.entity(newHostNode, MediaType.APPLICATION_JSON));
			
			return (res.getStatus() == 200);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean addNewNodes(Host forHost, Set<Host> newHostNodes) {
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
			System.out.println("=> POST" + "http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/nodes :" + newHostNodes);
			ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/nodes");
			Response res = target.request().post(Entity.entity(newHostNodes, MediaType.APPLICATION_JSON));
			
			return (res.getStatus() == 200);
		} catch(Exception e) {
			return false;
		}
	}
	
	public static Set<AgentType> getAllAgentTypes(Host forHost) {
		
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
			System.out.println("=> GET" + "http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/agents/classes");
			ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/agents/classes");
			Response res = target.request().get();
			
			if(res.getStatus() == 200) {
				String r = res.readEntity((String.class));
				System.out.println(r);
				Gson gson = new Gson();
				
				AgentType[] converted = gson.fromJson(r, AgentType[].class);
				return new HashSet<AgentType>(Arrays.asList(converted));
			} else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean addAgentTypes(Host forHost, Map<String, Set<AgentType>> allTypes) {
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
			System.out.println("=> POST" + "http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/agents/classes");
			ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/agents/classes");
			Response res = target.request().post(Entity.entity(allTypes, MediaType.APPLICATION_JSON));
			
			return (res.getStatus() == 200);
		} catch(Exception e) {
			return false;
		}
	}
	
	public static boolean addRunningAgenta(Host forHost, Set<AID> aids) {
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
			System.out.println("=> POST" + "http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/agents/running");
			ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/agents/running");
			Response res = target.request().post(Entity.entity(aids, MediaType.APPLICATION_JSON));
			
			return (res.getStatus() == 200);
		} catch(Exception e) {
			return false;
		}
	}
	
	public static boolean remove(Host forHost, String removedNode) {
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
			System.out.println("=> DELETE" + "http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/node/"+removedNode);
			ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/hand-shake/node/"+removedNode);
			Response res = target.request().delete();
			
			return (res.getStatus() == 200);
		} catch(Exception e) {
			return false;
		}
	}
}
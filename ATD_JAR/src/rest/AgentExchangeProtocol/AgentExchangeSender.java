package rest.AgentExchangeProtocol;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import model.AID;
import model.Host;

public abstract class AgentExchangeSender {

	public static boolean sendAgent(Host forHost, AID aid) {
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
			System.out.println("=> POST" + "http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/exchange/agent");
			ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/exchange/agent");
			Response res = target.request().post(Entity.entity(aid, MediaType.APPLICATION_JSON));
		
			return (res.getStatus() == 200);
		} catch(Exception e) {
			return false;
		}
	}
}

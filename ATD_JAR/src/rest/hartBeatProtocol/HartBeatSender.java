package rest.hartBeatProtocol;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import model.Host;


public class HartBeatSender {

	public static boolean checkNode(Host forNode) {
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
			System.out.println("=> GET" + "http://"+forNode.getAddress()+":"+forNode.getPort()+"/ATD_WAR/ATD/node");
			ResteasyWebTarget target = client.target("http://"+forNode.getAddress()+":"+forNode.getPort()+"/ATD_WAR/ATD/node");
			Response res = target.request().get();
		
			return (res.getStatus() == 200);
		} catch(Exception e) {
			return false;
		}
	}

}
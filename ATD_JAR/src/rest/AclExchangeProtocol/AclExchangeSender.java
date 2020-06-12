package rest.AclExchangeProtocol;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import model.ACL;
import model.Host;

public abstract class AclExchangeSender {

	public static boolean sendACL(Host forHost, ACL acl) {
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/exchange/acl");
			Response res = target.request().post(Entity.entity(acl, MediaType.APPLICATION_JSON));
		
			return (res.getStatus() == 200);
		} catch(Exception e) {
			return false;
		}
	}
}

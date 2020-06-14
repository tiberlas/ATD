package rest.AclExchangeProtocol;

import java.util.concurrent.TimeUnit;

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
			ResteasyClient client = new ResteasyClientBuilder()
					.establishConnectionTimeout(100, TimeUnit.SECONDS)
	                .socketTimeout(10, TimeUnit.SECONDS)
					.build();
			System.out.println("=> POST" + "http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/exchange/acl");
			ResteasyWebTarget target = client.target("http://"+forHost.getAddress()+":"+forHost.getPort()+"/ATD_WAR/ATD/exchange/acl");
			Response res = target.request().post(Entity.entity(acl, MediaType.APPLICATION_JSON));
		
			return (res.getStatus() == 200);
		} catch(Exception e) {
			return false;
		}
	}
}

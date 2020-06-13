package config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import model.Host;

@Singleton
@LocalBean
public class ServerDiscovery {

	public List<Host> findAllActiveNodes(String nodeIp) {
        
        ResteasyClient client = new ResteasyClientBuilder().build();
        List<Host> foundHost = new ArrayList<Host>();
        	
        String[] vnetParts = nodeIp.split("\\.");
    	//int me = Integer.parseInt(vnetParts[3]);
    	
    	String potentialHostAddress = vnetParts[0]+"."+vnetParts[1]+"."+vnetParts[2]+".";
    	List<String> lastPartOfIp = new ArrayList<String>(Arrays.asList("1", "101", "102", "103"));
    	
    	lastPartOfIp.forEach(p -> {
    		if(!p.equals(vnetParts[3])) {
    			try {
                	String adr = "http://"+potentialHostAddress+p+":8080/ATD_WAR/ATD/identify";
                	System.out.println("try to reach: "+adr);
                	ResteasyWebTarget target = client.target(adr);
            		Response response = target.request().get();
            		if(response.getStatus() == 200) {
            			String alias = response.readEntity(String.class);
            			
            			System.out.println("got response: "+ alias + " from "+potentialHostAddress+p);
            			foundHost.add(new Host(alias, (potentialHostAddress+p), 8080));
            		}
    			}catch(Exception e) {
    				//no response
    			}
    		}
    		
    	});
        
        return foundHost;
	}
	
}

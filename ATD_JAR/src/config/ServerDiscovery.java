package config;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

	public List<Host> findHosts() {
		
		List<String> potentialAddresses = listIPAddresses();
		
		List<Host> findedHostAgents = new ArrayList<Host>();
		for(String address: potentialAddresses) {
			findedHostAgents.addAll(checkIfMasterIsRunning(address));
		}

		return findedHostAgents;
	}
	
	private List<String> listIPAddresses() {
		/**
		 * hardcoded search for ip addresses on my local machine that might have ATD server 
		 * */
		List<String> findedIp = new ArrayList<String>();
		final byte[] myIp;
		final byte[] searchIp;
        
		try {
            myIp = Inet4Address.getLocalHost().getAddress();
            searchIp = myIp;
            searchIp[2] = 1;
        } catch (Exception e) {
            return null;
        }

		ExecutorService es = Executors.newCachedThreadPool();
        for(int i=3;i<=254;++i) {
        	
            final int j = i;  // i as non-final variable cannot be referenced from inner class
            
            es.execute(new Runnable() {
                
            	@Override
            	public void run() {
                    try {
                        searchIp[3] = (byte)j;
                        InetAddress address = InetAddress.getByAddress(searchIp);
                        String output = address.toString().substring(1);
                        if (address.isReachable(5000)) {
                            System.out.println(output + " is on the network");
                            findedIp.add(output);
                        } 
                    } catch (Exception e) {
                       //thread stopped
                    }
                }
            });
        }
        
        es.shutdown();
		try {
			es.awaitTermination(7, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        return findedIp;
    }
	
	private List<Host> checkIfMasterIsRunning(String potentialHostAddress) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		List<Host> findedHost = new ArrayList<Host>();
		
		int[] posiblePorts = new int[] {8080, 8081, 8082, 8083, 8084, 9090, 9091, 9092, 9093, 9094};
		
		ExecutorService es = Executors.newCachedThreadPool();
		for(int portNumber : posiblePorts) {
  
			es.execute(new Runnable() {
                
            	@Override
            	public void run() {
            		try {
                    	System.out.println("try: "+potentialHostAddress+":"+portNumber);
                    	ResteasyWebTarget target = client.target("http://"+potentialHostAddress+":"+portNumber+"/ATD_WAR/ATD/identify");
                		Response response = target.request().get();
                		if(response.getStatus() == 200) {
                			String alias = response.readEntity(String.class);
                			
                			System.out.println("got response: "+ alias + " from "+potentialHostAddress + ":" +portNumber);
                			findedHost.add(new Host(alias, potentialHostAddress, portNumber));
                		}
            			} catch(Exception e) {
            				System.out.println("no response: "+potentialHostAddress + ":" +portNumber);
            			}
                }
            });
        }
		
		//ceka dok se sve niti ne zavrsi tj maksimalno 3 sekunde
		es.shutdown();
		try {
			es.awaitTermination(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return findedHost;
	}
}

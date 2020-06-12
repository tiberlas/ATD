package config;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
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
		listIPAddresses2();
		System.out.println("----------------");
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

		if(myIp[0] == 127 && myIp[1] == 0) {
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
		
		int[] posiblePorts = new int[] {8080, 8081, 8082, 8083};
		
		for(int portNumber : posiblePorts) {
			try {
            	String adr = "http://"+potentialHostAddress+":"+portNumber+"/ATD_WAR/ATD/identify";
            	System.out.println(adr);
            	ResteasyWebTarget target = client.target(adr);
        		Response response = target.request().get();
        		if(response.getStatus() == 200) {
        			String alias = response.readEntity(String.class);
        			
        			System.out.println("got response: "+ alias + " from "+potentialHostAddress + ":" +portNumber);
        			findedHost.add(new Host(alias, potentialHostAddress, portNumber));
        		}
			}catch(Exception e) {
				//no response
			}
        }

		return findedHost;
	}
	
	
	
	//TEST
	private void listIPAddresses2(){
        /**
         * ispise sve ip na koje sam zakacen za likalnu mrezu
         * enp2s0f1 - 192.168.1.11
         * vrtaio preko cega sam povezan i ip; teorijski mogu da proban da izvrtim sve ip uspomoc bazne
         * */
        Enumeration<NetworkInterface> net = null;
        try { // get all interfaces; ethernet, wifi, virtual... etc
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        if (net == null){
            throw new RuntimeException("No network interfaces found.");
        }

        while(net.hasMoreElements()){
            NetworkInterface element = net.nextElement();
            try {
                if (element.isVirtual() || element.isLoopback()){
                    // discard virtual and loopback interface (127.0.0.1)
                    continue;
                }

                // rest are either Wifi or ethernet interfaces
                // loop through and print the IPs
                Enumeration<InetAddress> addresses = element.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = addresses.nextElement();
                    if (ip instanceof Inet4Address){
                        if (ip.isSiteLocalAddress()){
                            System.out.println(element.getDisplayName() + " - " + ip.getHostAddress());
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
	}
}

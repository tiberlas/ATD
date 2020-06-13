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
            			
            			System.out.println("got response: "+ alias + " from "+potentialHostAddress);
            			foundHost.add(new Host(alias, potentialHostAddress, 8080));
            		}
    			}catch(Exception e) {
    				//no response
    			}
    		}
    		
    	});
        
        return foundHost;
	}
	
//	public List<Host> findHosts() {
//
//		List<String> potentialAddresses = listIPAddresses();
//		
//		List<Host> findedHostAgents = new ArrayList<Host>();
//		
//		if(!potentialAddresses.isEmpty()) {
//			for(String address: potentialAddresses) {
//				findedHostAgents.addAll(checkIfMasterIsRunning(address));
//			}
//			
//			findedHostAgents.addAll(checkIfMasterIsRunning(null));
//		}
//		
//
//		return findedHostAgents;
//	}
//	
//	private List<String> listIPAddresses() {
//		List<String> found = new ArrayList<String>();
//		List<String> localhosts = new ArrayList<String>();
//		Enumeration<NetworkInterface> net = null;
//        try { // get all interfaces; ethernet, wifi, virtual... etc
//            net = NetworkInterface.getNetworkInterfaces();
//        } catch (SocketException e) {
//        	return found;
//        }
//
//        if (net == null){
//        	return found;
//        }
//
//        while(net.hasMoreElements()){
//            NetworkInterface element = net.nextElement();
//            try {
//                if (element.isLoopback()){
//                    // discard virtual and loopback interface (127.0.0.1)
//                    continue;
//                }
//
//                // rest are either Wifi or ethernet interfaces
//                // loop through and print the IPs
//                Enumeration<InetAddress> addresses = element.getInetAddresses();
//                while (addresses.hasMoreElements()){
//                    InetAddress ip = addresses.nextElement();
//                    if (ip instanceof Inet4Address){
//                        if (ip.isSiteLocalAddress()){
//                            System.out.println(element.getDisplayName() + " - " + ip.getHostAddress());
//                            localhosts.add(ip.getHostAddress());
//                        }
//                    }
//                }
//            } catch (SocketException e) {
//            	return found;
//            }
//        }
//        
//        String localhost = null;
//        for(String l : localhosts) {
//        	if(l.contains("192.168.56")) {
//        		localhost = l;
//        	}
//        }
//        if(localhost==null)  {
//        	return found;
//        }
//        
//        byte[] myIp = new byte[4];
//        String[] parts = localhost.split("\\.");
//        
//        
//        for(int i=0; i<4; ++i) {
//        	myIp[i] = (byte) Integer.parseInt(parts[i]);
//        }
//        
//        System.out.println("MY IP: " + myIp);
//        byte[] tryIp = myIp;
//        for(int i=1;i<=254;++i) {
//            if(myIp[3] == i) {
//            	continue;
//            }
//        	
//        	try {
//        		tryIp[3] = (byte) i;
//                InetAddress address = InetAddress.getByAddress(tryIp);
//                String output = address.toString();
//                if (address.isReachable(5000)) {
//                    System.out.println(output + " is on the network");
//                    found.add(output);
//                } 
//            } catch (Exception e) {
//               //thread stopped
//            };
//        }
//        
//        return found;
//    }
//	
//	private List<String> listIPAddresse2s() {
//		/**
//		 * hardcoded search for ip addresses on my local machine that might have ATD server 
//		 * */
//		List<String> findedIp = new ArrayList<String>();
//		final byte[] myIp;
//		final byte[] searchIp;
//
//		try {
//            myIp = Inet4Address.getLocalHost().getAddress();
//            searchIp = myIp;
//            searchIp[2] = 1;
//        } catch (Exception e) {
//            return null;
//        }
//
//        for(int i=3;i<=254;++i) {
//
//            final int j = i;  // i as non-final variable cannot be referenced from inner class
//                    try {
//                        searchIp[3] = (byte)j;
//                        InetAddress address = InetAddress.getByAddress(searchIp);
//                        String output = address.toString().substring(1);
//                        if (address.isReachable(5000)) {
//                            System.out.println(output + " is on the network");
//                            findedIp.add(output);
//                        } 
//                    } catch (Exception e) {
//                       //thread stopped
//                    };
//        }
//
//        return findedIp;
//    }
//	
//	private List<Host> checkIfMasterIsRunning(String potentialHostAddress) {
//		ResteasyClient client = new ResteasyClientBuilder().build();
//		List<Host> findedHost = new ArrayList<Host>();
//		
//		int[] posiblePorts = new int[] {8080, 8081, 8082, 8083, 8084};
//		
//		for(int portNumber : posiblePorts) {
//			try {
//            	String adr = "http://"+potentialHostAddress+":"+portNumber+"/ATD_WAR/ATD/identify";
//            	System.out.println(adr);
//            	ResteasyWebTarget target = client.target(adr);
//        		Response response = target.request().get();
//        		if(response.getStatus() == 200) {
//        			String alias = response.readEntity(String.class);
//        			
//        			System.out.println("got response: "+ alias + " from "+potentialHostAddress + ":" +portNumber);
//        			findedHost.add(new Host(alias, potentialHostAddress, portNumber));
//        		}
//			}catch(Exception e) {
//				//no response
//			}
//        }
//
//		return findedHost;
//	}
	
}

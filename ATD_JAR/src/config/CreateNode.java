package config;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import model.Host;

public abstract class CreateNode {

	public static Host createMasterNode() {	
		Host m = new Host("master", "127.0.0.1", 8080);
		
		Enumeration<NetworkInterface> net = null;
        try { // get all interfaces; ethernet, wifi, virtual... etc
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
        	return m;
        }
        if (net == null){
        	return m;
        }

        while(net.hasMoreElements()){
            NetworkInterface element = net.nextElement();
            // loop through and print the IPs
			Enumeration<InetAddress> addresses = element.getInetAddresses();
			while (addresses.hasMoreElements()){
			    InetAddress ip = addresses.nextElement();
			    if (ip instanceof Inet4Address){
			        if (ip.isSiteLocalAddress()){
			            System.out.println(element.getDisplayName() + " - " + ip.getHostAddress());
			            //hardcoded net mask of virtual box bridge adapter
			            if(ip.getHostAddress().contains("192.168.56")) {
			            	m.setAddress(ip.getHostAddress());
			            }
			        }
			    }
			}
        }
        
        return m;
	}
	
	public static String createAlias(List<Host> findedHostAgents) {

        if(findedHostAgents == null || findedHostAgents.isEmpty()) {
            return "master";
		} else {
			
			int maxAgentNumber = -1;
			for(Host hostAgent: findedHostAgents) {
				int agentNumber;
				if(hostAgent.getAlias() != null && !hostAgent.getAlias().trim().equals("")) {
					if(!hostAgent.getAlias().equals("master")) {
						try {
							agentNumber = Integer.parseInt(hostAgent.getAlias().substring(9));
						} catch (Exception e) {
							agentNumber = -1;
						}
							if(agentNumber > maxAgentNumber) {
							maxAgentNumber = agentNumber;
						}
					}
				}
			}
			
			return ("hostAgent"+ (++maxAgentNumber));
		}
	}

}
package config;

import java.util.List;
import java.net.InetAddress;
import java.net.UnknownHostException;

import model.Host;

public abstract class CreateHostName {

	public static Host create(List<Host> findedHostAgents) {

        String ip;
		try {
			ip = InetAddress.getLocalHost().toString();
			if(ip.contains("/")) {
				ip = ip.split("/")[1];
			}
		} catch (UnknownHostException e1) {
			ip = "127.0.0.1";
		}

        if(findedHostAgents == null || findedHostAgents.isEmpty()) {

            return new Host("master", ip, 8080);
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
			
			String alias = "hostAgent"+ (++maxAgentNumber);

            return new Host(alias, ip, 8080);
		}
	}

}
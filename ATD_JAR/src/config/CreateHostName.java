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
		} catch (UnknownHostException e1) {
			ip = "localhost";
		}

        if(findedHostAgents == null || findedHostAgents.isEmpty()) {

            return new Host("master", ip, 0);
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

            return new Host(alias, ip, 0);
		}
	}

}
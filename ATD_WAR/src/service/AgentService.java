package service;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import agents.HostAgentLocal;
import dto.RunningAgentDTO;
import model.AID;
import model.AgentType;

@Stateless
@LocalBean
public class AgentService {

	@EJB
	private HostAgentLocal host;
	
	public Set<RunningAgentDTO> getAllRunningAgents() {
		Set<RunningAgentDTO> ret = new HashSet<>();
		
		host.getAllAgents().forEach(a -> {
			ret.add(new RunningAgentDTO(a.getName(), a.getHostAlias(), a.getType().getName(), a.getType().getModule()));
		});
		
		return ret;
	}
	
	public void create(String name, String type) {
		String[] typeParts = type.split("@");
		
		if(typeParts.length != 2) {
			return;
		}
		
		AID aid = new AID(
				name, 
				host.getHost().getAlias(), 
				new AgentType(
						typeParts[0], 
						typeParts[1]));
		
		host.startAgent(aid);
	}
	
	public void stop(String name, String type, String hostAlias) {
		String[] typeParts = type.split("@");
		
		if(typeParts.length != 2) {
			return;
		}
		
		AID aid = new AID(
				name, 
				hostAlias, 
				new AgentType(
						typeParts[0], 
						typeParts[1]));
		
		host.stopAgent(aid);
	}
}

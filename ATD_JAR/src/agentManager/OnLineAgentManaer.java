package agentManager;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dataBaseService.onLineAgents.OnLineAgentsDataBaseLocal;
import dataBaseService.onLineHosts.OnLineHostsDataBaseLocal;
import dataBaseService.onLineTypes.OnLineTypesDataBaseLocal;
import model.AID;
import model.AgentType;
import model.Host;

@Stateless
@LocalBean
public class OnLineAgentManaer implements OnLineAgentManagerlocal {

	@EJB
	private OnLineHostsDataBaseLocal onLineHosts;
	
	@EJB
	private OnLineAgentsDataBaseLocal onLineAgents;
	
	@EJB
	private OnLineTypesDataBaseLocal onLineTypes;

	@Override
	public Set<Host> getAllHosts() {
		// TODO Auto-generated method stub
		return new HashSet<Host>();
	}
	
	@Override
	public Set<AID> getAllOnLineAgents() {
		return new HashSet<AID>();
	}
	
	@Override
	public Set<AID> getAllOnLineAgentsFromHost(Host host) {
		return new HashSet<AID>();
	}
	
	@Override
	public Set<AgentType> getAllTypes() {
		return new HashSet<AgentType>();
	}
	
	@Override
	public Set<AgentType> getAllTypesFromHost(Host host) {
		return new HashSet<AgentType>();
	}
	

	@Override
	public void addHost(Host newHost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTypes(Set<AgentType> types) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAgents(Set<AID> agentAIDs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeHost(String hostAlias) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAgent(AID aid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

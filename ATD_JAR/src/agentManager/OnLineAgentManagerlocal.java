package agentManager;

import java.util.Set;

import javax.ejb.Local;

import model.AID;
import model.AgentType;
import model.Host;

@Local
public interface OnLineAgentManagerlocal {

	Set<Host> getAllHosts();
	Set<AID> getAllOnLineAgents();
	Set<AID> getAllOnLineAgentsFromHost(Host host);
	Set<AgentType> getAllTypes();
	Set<AgentType> getAllTypesFromHost(Host host);
	
	void addHost(Host newHost);
	void addTypes(Set<AgentType> types);
	void addAgents(Set<AID> agentAIDs);
	
	void removeHost(String hostAlias);
	void removeAgent(AID aid);
	
	void cleanUp();
}

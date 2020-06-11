package agentManager;

import java.util.Map;
import java.util.Set;

import javax.ejb.Local;

import model.AID;
import model.AgentType;
import model.Host;

@Local
public interface OnLineAgentManagerlocal {

	Host getMaster();
	Set<Host> getAllHosts();
	Host getHost(String alias);
	Set<AID> getAllOnLineAgents();
	Set<AID> getAllOnLineAgentsFromHost(Host host);
	Set<AgentType> getAllTypes();
	Set<AgentType> getAllTypesFromHost(Host host);
	Map<String, Set<AgentType>> getAllTypesWithHost();
	
	void setMaster(Host master);
	void addHost(Host newHost);
	void addHosts(Set<Host> hosts);
	void addTypes(Set<AgentType> types, String hostAlias);
	void addType(AgentType type, String hostAlias);
	void addAgent(AID agentAID);
	void addAgents(Set<AID> agentAIDs);
	
	
	void removeHost(String hostAlias);
	void removeAgent(AID aid);
	
	void cleanUp();
}

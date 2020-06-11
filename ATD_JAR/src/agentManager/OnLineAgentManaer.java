package agentManager;

import java.util.Map;
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
	public Host getMaster() {
		return onLineHosts.getMasterNode();
	}

	@Override
	public void setMaster(Host master) {
		onLineHosts.setMasterNode(master);
	}
	
	@Override
	public Set<Host> getAllHosts() {
		return onLineHosts.getAllHostNodes();
	}
	
	@Override
	public Host getHost(String alias) {
		return onLineHosts.getHostNode(alias);
	}
	
	@Override
	public Set<AID> getAllOnLineAgents() {
		return onLineAgents.getAll();
	}
	
	@Override
	public Set<AID> getAllOnLineAgentsFromHost(Host host) {
		return onLineAgents.getAllByHostAlias(host.getAlias());
	}
	
	@Override
	public Set<AgentType> getAllTypes() {
		return onLineTypes.getAllUniqueTypes();
	}
	
	@Override
	public Set<AgentType> getAllTypesFromHost(Host host) {
		return onLineTypes.getAllByHostAlias(host.getAlias());
	}
	
	@Override
	public Map<String, Set<AgentType>> getAllTypesWithHost() {
		return onLineTypes.getAll();
	}
	
	@Override
	public void addHost(Host newHost) {
		if(!onLineHosts.cheskIfExist(newHost.getAlias())) {
			onLineHosts.addHostNode(newHost);
		}
		
	}
	
	@Override
	public void addHosts(Set<Host> hosts) {
		onLineHosts.addHostNodes(hosts);
	}

	@Override
	public void addTypes(Set<AgentType> types, String hostAlias) {
		onLineTypes.addSetOfOnLIneTypes(types, hostAlias);
	}
	
	@Override
	public void addType(AgentType type, String hostAlias) {
		onLineTypes.addOnLineType(type, hostAlias);
	}

	@Override
	public void addAgents(Set<AID> agentAIDs) {
		agentAIDs.forEach(aid -> {
			addAgent(aid);
		});
	}
	
	@Override
	public void addAgent(AID aid) {
		if(onLineHosts.cheskIfExist(aid.getHostAlias())) {
			
			if(onLineTypes.checkIfExistsOnHost(aid.getType(), aid.getHostAlias())) {
				onLineAgents.addOnLineAgent(aid);
			}
		}
	}

	@Override
	public void removeHost(String hostAlias) {
		onLineHosts.removeHostNode(hostAlias);
		onLineAgents.removeAllOnHost(hostAlias);
		onLineTypes.removeAllOnHost(hostAlias);
	}

	@Override
	public void removeAgent(AID aid) {
		onLineAgents.removeAgent(aid);
	}

	@Override
	public void cleanUp() {
		onLineHosts.cleanUp();
		onLineAgents.cleanUp();
		onLineTypes.cleanUp();
	}
	
}

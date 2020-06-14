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
import ws.RunningAgentsWS;

@Stateless
@LocalBean
public class OnLineAgentManaer implements OnLineAgentManagerlocal {

	@EJB
	private OnLineHostsDataBaseLocal onLineHosts;
	@EJB
	private OnLineAgentsDataBaseLocal onLineAgents;
	@EJB
	private OnLineTypesDataBaseLocal onLineTypes;
	@EJB
	private RunningAgentsWS runningAgentsWS;
	
	@Override
	public Host getMaster() {
		return onLineHosts.getMasterNode();
	}

	@Override
	public void setMaster(Host master) {
		System.out.println("MASTER IS: " + master);
		onLineHosts.setMasterNode(master);
		try {
			onLineAgents.removeAllOnHost("master");
		} catch(Exception e) {
			System.out.println("Init master");
		}
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
			System.out.println("ADDED NODE: "+ newHost);
			onLineHosts.addHostNode(newHost);
		}
		
	}
	
	@Override
	public void addHosts(Set<Host> hosts) {
		onLineHosts.addHostNodes(hosts);
		hosts.forEach(h -> {
			System.out.println("ADDED NODE: " + h);
		});
	}

	@Override
	public void addTypes(Set<AgentType> types, String hostAlias) {
		onLineTypes.addSetOfOnLIneTypes(types, hostAlias);
		types.forEach(t -> {
			System.out.println("ADDED TYPE: "+ t + " to node: "+ hostAlias);
		});
	}
	
	@Override
	public void addType(AgentType type, String hostAlias) {
		onLineTypes.addOnLineType(type, hostAlias);
		System.out.println("ADDED TYPE: "+ type + " to node: "+ hostAlias);
	}

	@Override
	public void addAgents(Set<AID> agentAIDs) {
		agentAIDs.forEach(aid -> {
			addAgent(aid);
		});
	}
	
	@Override
	public void addAgent(AID aid) {
		onLineAgents.addOnLineAgent(aid);
		System.out.println("ADDED ON LINE AGENT: "+ aid);
		runningAgentsWS.sendActiveAgent(aid);
	}

	@Override
	public void removeHost(String hostAlias) {
		runningAgentsWS.sendInactiveAgentSet(onLineAgents.getAllByHostAlias(hostAlias));
		
		onLineHosts.removeHostNode(hostAlias);
		onLineAgents.removeAllOnHost(hostAlias);
		onLineTypes.removeAllOnHost(hostAlias);
		System.out.println("REMOVED NODE: " + hostAlias);
	}

	@Override
	public void removeAgent(AID aid) {
		if(onLineAgents.getAll().contains(aid)) {
			onLineAgents.removeAgent(aid);
			System.out.println("REMOVED ON LINE AGENT: "+aid);
			runningAgentsWS.sendInactiveAgent(aid);
		}
	}

	@Override
	public void cleanUp() {
		onLineHosts.cleanUp();
		onLineAgents.cleanUp();
		onLineTypes.cleanUp();
	}
	
}

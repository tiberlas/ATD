package agents;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import ContractNet.IniatorAgentRemote;
import agentManager.ActiveAgentManagerLocal;
import agentManager.OnLineAgentManagerlocal;
import common.JsonObjMapper;
import messageManager.ACLSenderLocal;
import model.ACL;
import model.AID;
import model.AgentType;
import model.Host;
import model.PerformativeENUM;
import rest.AclExchangeProtocol.AclExchangeSender;
import rest.AgentExchangeProtocol.AgentExchangeSender;
import ws.RunningAgentsWS;
import ws.TypeWS;

@Singleton
@LocalBean
public class HostAgent implements HostAgentLocal {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private ActiveAgentManagerLocal activeManager;
	@EJB
	private OnLineAgentManagerlocal onLineManager;
	@EJB
	private ACLSenderLocal aclSender;
	@EJB
	private JsonObjMapper jsonMapper;
	@EJB
	private TypeWS typeWS;
	@EJB
	private RunningAgentsWS runningAgentsWS;
	
	private Host host;
	
	@Override
	public void setUp(Host thisNode) {
		host = thisNode;
	}
	
	@Override
	public void handleMessage(ACL message) {
		
		//deep copy
		String aclStringify = jsonMapper.objToJson(message);
		ACL msg = (ACL) jsonMapper.JsonToObj(aclStringify, ACL.class);

		System.out.println(msg);
		message.getReceiverAIDs().forEach(reciverAID -> {
			//salji samo 1 agentu
			Set<AID> forAgent = new HashSet<>();
			forAgent.add(reciverAID);
			msg.setReceiverAIDs(forAgent);
			
			if(reciverAID.getHostAlias().equals(host.getAlias())) {
				//acl je za lokalnog agenta
				aclSender.sendACL(msg);
			} else {
				//acl mora da se posalje odgovarajucem cvoru
				Host node = onLineManager.getHost(reciverAID.getHostAlias());
				AclExchangeSender.sendACL(node, msg);
			}
		});
	}

	@Override
	public Set<AID> getAllAgents() {
		Set<AID> aids = new HashSet<>();
		
		aids.addAll(activeManager.getAllActiveAgents());
		aids.addAll(onLineManager.getAllOnLineAgents());
		
		return aids;
	}
	
	@Override
	public Set<AgentType> getAllTypes() {
		Set<AgentType> types = new HashSet<>();
		
		types.addAll(activeManager.getAllActiveTypes());
		types.addAll(onLineManager.getAllTypes());
		
		return types;
	}
	
	@Override
	public String[] getAllPerfomativs() {
		List<String> ret = new ArrayList<String>();
		
		for(PerformativeENUM e : PerformativeENUM.values()) {
			ret.add(e.toString());
		}
		
		return ret.toArray(new String[ret.size()]);
	}
	
	@Override
	public void startAgent(AID aid) {
		if(!activeManager.checkIfAgentExist(aid)) {
			System.out.println("Started agent" + aid);
			activeManager.startAgent(aid);
			runningAgentsWS.sendActiveAgent(aid);
			
			if(onLineManager.getAllHosts().size() != 0) {
				onLineManager.getAllHosts().forEach(node -> {
					AgentExchangeSender.startAgent(node, aid);
				});
			}
		}
	}
	
	@Override
	public void stopAgent(AID aid) {
		if(activeManager.checkIfAgentExist(aid)) {
			System.out.println("Stopped agent" + aid);
			activeManager.stopAgent(aid);
			runningAgentsWS.sendInactiveAgent(aid);
			
			if(onLineManager.getAllHosts().size() != 0) {
				onLineManager.getAllHosts().forEach(node -> {
					AgentExchangeSender.stopAgent(node, aid);
				});
			}
		}
	}
	
	@Override
	public Host getHost() {
		return host;
	}
	
	@Override
	public void startCN() {
		final AgentType iniatorType = new AgentType("iniator", "contract-net-module");
		final AgentType participantType = new AgentType("participant", "contract-net-module");
		
		Set<AID> all = activeManager.getAllActiveAgents();
		List<AID> iniators = new ArrayList<AID>();
		Set<AID> participants = new HashSet<AID>();
		
		all.forEach(a -> {
			if(a.getType().equals(iniatorType)) {
				iniators.add(a);
			} else if(a.getType().equals(participantType)) {
				participants.add(a);
			}
		});
		
		if(iniators.size() > 0 && participants.size() > 0) {
			IniatorAgentRemote iag = (IniatorAgentRemote) activeManager.getAgent(iniators.get(0));
			iag.startedCFP(participants);
		}
	}
	
	@Override
	public void cleanUp() {
		activeManager.stopAll();
		onLineManager.cleanUp();
	}
	
}

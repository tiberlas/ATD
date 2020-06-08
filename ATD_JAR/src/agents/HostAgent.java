package agents;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import agentManager.ActiveAgentManagerLocal;
import agentManager.OnLineAgentManagerlocal;
import common.JsonObjMapper;
import messageManager.ACLSenderLocal;
import model.ACL;
import model.AID;
import model.AgentType;
import model.Host;
import model.PerformativeENUM;
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
	
	@PostConstruct
	public void setUp() {
		host = new Host("master", "localhost", 8080);
	}
	
	@Override
	public void handleMessage(ACL message) {
		
		//deep copy
		String aclStringify = jsonMapper.objToJson(message);
		ACL msg = (ACL) jsonMapper.JsonToObj(aclStringify, ACL.class);

		System.out.println(msg);
		message.getReceiverAIDs().forEach(reciverAID -> {
			if(activeManager.checkIfAgentExist(reciverAID)) {
				Set<AID> forAgent = new HashSet<>();
				forAgent.add(reciverAID);
				msg.setReceiverAIDs(forAgent);
				aclSender.sendACL(msg);
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
		}
	}
	
	@Override
	public void stopAgent(AID aid) {
		if(activeManager.checkIfAgentExist(aid)) {
			System.out.println("Stopped agent" + aid);
			activeManager.stopAgent(aid);
			runningAgentsWS.sendInactiveAgent(aid);
		}
	}
	
	@Override
	public Host getHost() {
		return host;
	}
	
	@Override
	public void cleanUp() {
		activeManager.stopAll();
		onLineManager.cleanUp();
	}
	
}

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
				
				if(message.getLanguage()!=null && message.getLanguage().equals("contract-net") && 
						message.getProtocol()!=null && message.getProtocol().equals("init contract-net") &&
						message.getPerformative()!=null && message.getPerformative().equals(PerformativeENUM.CFP) &&
						message.getSenderAID() == null) {
					//pokreni CN
					startCN();
				} else {
					//acl je za lokalnog agenta
					System.out.println("ACL FOR AGENT:"+ activeManager.checkIfAgentExist(reciverAID));
					aclSender.sendACL(msg);
				}
			} else if(reciverAID.getHostAlias().equals("master")) {
				//acl je za mastera
				if(onLineManager.getMaster() != null) {
					AclExchangeSender.sendACL(onLineManager.getMaster(), msg);
				}
			} else{
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
		System.out.println("Started agent" + aid);
		if(aid.getHostAlias().equals(host.getAlias())) {
			//agent je kreiran na ovom cvoru
			if(!activeManager.checkIfAgentExist(aid)) {
				activeManager.startAgent(aid);
				
				//posalji svim cvorovima
				if(onLineManager.getAllHosts().size() != 0) {
					onLineManager.getAllHosts().forEach(node -> {
						AgentExchangeSender.startAgent(node, aid);
					});
				}
				//posalji masteru
				if(onLineManager.getMaster() != null) {
					AgentExchangeSender.startAgent(onLineManager.getMaster(), aid);
				}
			}			
		} else {
			onLineManager.addAgent(aid);
		}
		
		runningAgentsWS.sendActiveAgent(aid);
	}
	
	@Override
	public void stopAgent(AID aid) {
		try {
			if(aid.getHostAlias().equals(host.getAlias())) {
				//agent je zaustavljen na ovom cvoru
				activeManager.stopAgent(aid);
			} else {
				onLineManager.removeAgent(aid);
			}
			
			System.out.println("Stopped agent" + aid);
			runningAgentsWS.sendInactiveAgent(aid);
			//javi ostalim cvorovima
			if(onLineManager.getAllHosts().size() != 0) {
				onLineManager.getAllHosts().forEach(node -> {
					AgentExchangeSender.stopAgent(node, aid);
				});
			}
			//posalji masteru
			if(onLineManager.getMaster() != null) {
				AgentExchangeSender.stopAgent(onLineManager.getMaster(), aid);
			}
		} catch(Exception e) {
			System.out.println("stopt on line agent");
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
		all.addAll(onLineManager.getAllOnLineAgents());
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
			AID iniatorAID = iniators.get(0);
			if(iniatorAID.getHostAlias().equals(host.getAlias())) {
				IniatorAgentRemote iag = (IniatorAgentRemote) activeManager.getAgent(iniatorAID);
				iag.startedCFP(participants);
				
				ACL response = new ACL();
				
				response.setReceiverAIDs(participants);
				response.setSenderAID(iniatorAID);
				response.setLanguage("contract-net");
				response.setPerformative(PerformativeENUM.CFP);
				
				System.out.println("INIATOR CFP");
				System.out.println(response);
				handleMessage(response);
			} else {
				ACL startCN = new ACL();
				
				Set<AID> p = new HashSet<AID>();
				p.add(iniatorAID);
				startCN.setReceiverAIDs(p);
				startCN.setSenderAID(null);
				startCN.setLanguage("contract-net");
				startCN.setProtocol("init contract-net");
				startCN.setPerformative(PerformativeENUM.CFP);
				handleMessage(startCN);
			}
			
		}
	}
	
	@Override
	public void cleanUp() {
		activeManager.stopAll();
		onLineManager.cleanUp();
	}
	
}

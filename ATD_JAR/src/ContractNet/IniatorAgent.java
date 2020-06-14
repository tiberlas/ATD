package ContractNet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import agents.HostAgentLocal;
import model.ACL;
import model.AID;
import model.PerformativeENUM;
import ws.ACLWS;

@Stateful
@LocalBean
@Remote(IniatorAgentRemote.class)
public class IniatorAgent implements IniatorAgentRemote{

	private static final long serialVersionUID = 1L;

	private AID aid;
	private Set<AID> participants = new HashSet<AID>();
	private Map<AID, Integer> accepted = new HashMap<AID, Integer>();
	
	@EJB
	private HostAgentLocal host;
	@EJB
	private ACLWS aclws;
	
	@Override
	public void startUp(AID agentAID) {
		this.aid = agentAID;
	}

	@Override
	public AID getAID() {
		return aid;
	}

	@Override
	public void handleMessage(ACL msg) {
		
		AID sender = msg.getSenderAID();
		if(msg.getPerformative().equals(PerformativeENUM.REFUSE)) {
			
			if(participants.contains(sender)) {
				participants.remove(sender);				
			
				logMsg("I was rejected by" + sender);
				System.out.println(aid + ": was rejected by" + sender);
				
				AcceptBest();
			} else {
				System.out.println(aid + " to late " + sender);
				return;
			}
		} else if(msg.getPerformative().equals(PerformativeENUM.PROPOSE)) {
			
			if(participants.contains(sender)) {
				participants.remove(sender);
				String bitStr[] = msg.getContent().split(":");
				int bit = Integer.parseInt(bitStr[1]);
				accepted.put(sender, new Integer(bit));
			
				System.out.println(aid + ": got poposal from " + sender);
				
				AcceptBest();
			} else {
				System.out.println(aid + " to late " + sender);
				return;
			}
		} if(msg.getPerformative().equals(PerformativeENUM.INFORM_DONE)) {
			
			logMsg("JOB SUCESSFULLY DONE BY" + sender);
			System.out.println(aid + ": SUCESSFULLY DONE");
		} if(msg.getPerformative().equals(PerformativeENUM.FAILURE)) {
		
			logMsg("JOB FAILD BY " + sender);
			System.out.println(aid + ": FAILD");
		} else {
			
			System.out.println(aid + ": NOT VALID PERFORMATIVE " + msg.getPerformative());
		}
		
		aclws.sendACL(msg);
		
	}
	
	@Override
	public void startedCFP(Set<AID> aids) {
		if(aids != null && aids.size() > 0) {
			this.participants = aids;
			this.accepted.clear();
		}
	}
	
	@Override
	public void AcceptBest() {
		
		if(!participants.isEmpty()) {
			return;
		}
		
		if(accepted.isEmpty()) {
			
			logMsg("NO ONE ACCEPTED");
			System.out.println("NO ONE ACCEPTED");
		} else {
			
			Integer min = null;
			AID minAid = null;
			for(AID biter : accepted.keySet()) {
				if(min == null) {
					min = accepted.get(biter);
					minAid = biter;
					continue;
				}
				if(accepted.get(biter) < min) {
					min = accepted.get(biter);
					minAid = biter;
				}
			}
			
			ACL response = new ACL();
			Set<AID> recivers = new HashSet<AID>();
			recivers.add(minAid);
			
			response.setReceiverAIDs(recivers);
			response.setSenderAID(aid);
			response.setLanguage("contract-net");
			response.setPerformative(PerformativeENUM.ACCEPT_PROPOSAL);
			host.handleMessage(response);
			
			recivers = accepted.keySet();
			recivers.remove(minAid);
			response.setReceiverAIDs(recivers);
			response.setPerformative(PerformativeENUM.REJECT_PROPOSAL);
			host.handleMessage(response);
		}
	}

	@Override
	public void cleanUp() {
		this.aid = null;
	}
	
	private void logMsg(String content) {
		ACL response = new ACL();
		Set<AID> recivers = new HashSet<AID>();
		recivers.add(aid);
		
		response.setReceiverAIDs(recivers);
		response.setSenderAID(aid);
		response.setLanguage("contract-net");
		response.setPerformative(PerformativeENUM.INFORM);
		response.setContent(content);
		host.handleMessage(response);
	}

}

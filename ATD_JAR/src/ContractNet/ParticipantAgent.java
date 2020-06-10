package ContractNet;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import messageManager.ACLSenderLocal;
import model.ACL;
import model.AID;
import model.PerformativeENUM;
import ws.ACLWS;

@Stateful
@LocalBean
@Remote(ParticipantAgentRemote.class)
public class ParticipantAgent implements ParticipantAgentRemote {

	private static final long serialVersionUID = 1L;
	
	private AID aid;
	
	@EJB
	private ACLSenderLocal aclSender;
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

		ACL response = new ACL();
		Set<AID> iniator = new HashSet<AID>();
		iniator.add(msg.getSenderAID());
		
		response.setReceiverAIDs(iniator);
		response.setSenderAID(aid);
		response.setLanguage("contract-net");
		
		if(msg.getPerformative().equals(PerformativeENUM.CFP)) {
			
			int rndNum = getRandomNumber();
			
			if(rndNum < 10) {
				response.setContent("Im not bidding");
				response.setPerformative(PerformativeENUM.REFUSE);
				
				System.out.println(aid + ": Im not bidding");
			} else {
				response.setContent("My bid is:"+rndNum);
				response.setPerformative(PerformativeENUM.PROPOSE);
				
				System.out.println(aid + ": My bid is " + rndNum);
			}
			
			aclSender.sendACL(response);
			
		} else if(msg.getPerformative().equals(PerformativeENUM.ACCEPT_PROPOSAL)) {
			
			int rndNum = getRandomNumber();
			
			if(rndNum < 10) {
				response.setContent("I failed");
				response.setPerformative(PerformativeENUM.FAILURE);
				
				System.out.println(aid + ": I failed");
			} else {
				response.setContent("Successfuly done");
				response.setPerformative(PerformativeENUM.INFORM_DONE);
				
				System.out.println(aid + ": Successfuly done");
			}
			
			aclSender.sendACL(response);
			
		} else if(msg.getPerformative().equals(PerformativeENUM.REJECT_PROPOSAL)) {
			
			iniator.clear();
			iniator.add(aid);
			response.setReceiverAIDs(iniator);
			response.setContent("I was rejected");
			response.setPerformative(PerformativeENUM.INFORM);
			
			System.out.println(aid +": REJECTED");
		} else {
			
			System.out.println(aid+ ": NOT VALID PERFORMATIVE");
		}
		
		aclws.sendACL(msg);
		
	}

	@Override
	public void cleanUp() {
		this.aid = null;
	}
	
	private int getRandomNumber() {
		Random rnd = new Random();
		return rnd.nextInt(20 - 1 + 1) + 1;
	}

}

package agents;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import model.ACL;
import model.AID;
import ws.ACLWS;

@Stateful
@LocalBean
public class TestAgent implements TestAgentLocal {

	private static final long serialVersionUID = 1L;

	@EJB
	private ACLWS aclws;
	
	private AID aid;
	
	@Override
	public void startUp(AID agentAID) {
		this.aid = agentAID;
	}

	@Override
	public AID getAID() {
		return aid;
	}

	@Override
	public void handleMessage(ACL message) {
		System.out.println("TEST AGENT ACL:");
		System.out.println(message);
		aclws.sendACL(message);
	}

	@Override
	public void cleanUp() {
		this.aid = null;
	}

}

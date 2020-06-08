package messageManager;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import agentManager.ActiveAgentManagerLocal;
import agents.Agent;
import common.JsonObjMapper;
import model.ACL;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/topic/publicTopic") })
public class ACLConsumer implements MessageListener {

	@EJB
	private ActiveAgentManagerLocal agents;
	
	@EJB
	private JsonObjMapper jsonMapper;
	
	public ACLConsumer() {
		super();
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			String json = message.getStringProperty("ACL");
			ACL acl = (ACL) jsonMapper.JsonToObj(json, ACL.class);
			
			acl.getReceiverAIDs().forEach(aid -> {
			
				Agent agent = (Agent) agents.getAgent(aid);
				if(agent != null) {
					agent.handleMessage(acl);
				} else {
					System.out.println("FATAL ERROR AGENT IS NOT IN THE LIST");
				}
			});
			
		} catch (JMSException e) {
			System.out.println("JMS EXCEPTION");
			//e.printStackTrace();
		}
	}
}

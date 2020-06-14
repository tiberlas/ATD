package messageManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import common.JsonObjMapper;
import model.ACL;

@Stateless
@LocalBean
public class ACLSender implements ACLSenderLocal {

	@EJB
	private JMSFactory factory;
	
	@EJB
	private JsonObjMapper jsonMapper;
	
	private Session session;
	private MessageProducer defaultProducer;

	public ACLSender() {
		super();
	}

	@PostConstruct
	public void postConstruct() {
		session = factory.getSession();
		defaultProducer = factory.getProducer(session);
	}

	@PreDestroy
	public void preDestroy() {
		try {
			session.close();
		} catch (JMSException e) {
		}
	}
	
	
	@Override
	public void sendACL(ACL acl) {
		try {
			Message msg = session.createTextMessage();
			msg.setStringProperty("ACL", jsonMapper.objToJson(acl));
			
			defaultProducer.send(msg);
		} catch(JMSException e) {
			System.out.println("COULD NOT SEND ACL TO MDB");
			//e.printStackTrace();
		}
	}

}

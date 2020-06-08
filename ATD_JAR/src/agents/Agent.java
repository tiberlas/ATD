package agents;

import java.io.Serializable;

import javax.annotation.PreDestroy;

import model.ACL;
import model.AID;

public interface Agent extends Serializable{

	void startUp(AID agentAID);
	AID getAID(); 
	void handleMessage(ACL message);
	
	@PreDestroy
	void cleanUp();
}

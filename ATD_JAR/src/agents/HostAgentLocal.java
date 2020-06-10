package agents;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.ejb.Local;

import model.ACL;
import model.AID;
import model.AgentType;
import model.Host;

@Local
public interface HostAgentLocal extends Serializable {

	Host getHost();
	void handleMessage(ACL message);
	
	Set<AID> getAllAgents();
	Set<AgentType> getAllTypes();
	String[] getAllPerfomativs();
	
	void startAgent(AID aid);
	void stopAgent(AID aid);
	
	@PreDestroy
	void cleanUp();
	
	void startCN();
}

package agents;

import java.io.Serializable;
import java.util.Set;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.Local;

import model.ACL;
import model.AID;
import model.AgentType;
import model.Host;

@Local
public interface HostAgentLocal extends Serializable {

	void setUp(List<Host> host);
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

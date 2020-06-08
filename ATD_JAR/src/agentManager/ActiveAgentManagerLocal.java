package agentManager;

import java.util.Set;

import javax.ejb.Local;

import agents.Agent;
import model.AID;
import model.AgentType;

@Local
public interface ActiveAgentManagerLocal {

	Set<AID> getAllActiveAgents();
	Set<AgentType> getAllActiveTypes();
	Agent getAgent(AID agentAID);
	
	void startAgent(AID agentAID);	
	void stopAgent(AID agentAID);
	boolean checkIfAgentExist(AID aid);
	void stopAll();
}

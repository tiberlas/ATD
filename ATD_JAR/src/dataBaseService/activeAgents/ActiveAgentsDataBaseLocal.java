package dataBaseService.activeAgents;

import java.util.Set;

import javax.ejb.Local;

import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;

import agents.Agent;
import model.AID;
import model.AgentType;

@Local
public interface ActiveAgentsDataBaseLocal {

	/**
	 * Active agents on this host
	 * */
	
	Set<Agent> getAllAgents();
	Set<Agent> getAllAgentsByType(AgentType agentType);
	Set<AID> getAllAgentAIDs();
 	Agent getRunningAgent(AID agentAID);
	void addRunningAgent(Agent agent) throws ObjectAlreadyActive;
	void removeAgent(AID agentAID);
	boolean checkIfAgentIsRunning(AID agentAID);
	int numberOfRunningAgents();
	void cleanUp();

}
package dataBaseService.activeAgents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;

import agents.Agent;
import model.AID;
import model.AgentType;

@Singleton
@LocalBean
public class ActiveAgentsDataBase implements ActiveAgentsDataBaseLocal {

	private Map<AID, Agent> runningAgents = new HashMap<AID, Agent>();
	
	@Override
	public int numberOfRunningAgents() {
		return runningAgents.size();
	}
	
	@Override
	public Set<Agent> getAllAgents() {
		return new HashSet<Agent>(runningAgents.values());
	}
	
	@Override
	public Set<AID> getAllAgentAIDs() {
		Set<AID> foundAID = new HashSet<>();
		
		runningAgents.values().forEach(agent -> {
			foundAID.add(agent.getAID());
		});
		
		return foundAID;
	}

	@Override
	public Set<Agent> getAllAgentsByType(AgentType agentType) {
		Set<Agent> foundAgents = new HashSet<>();
		
		runningAgents.values().forEach(agent -> {
			if(agent.getAID().getType().equals(agentType)) {
				foundAgents.add(agent);
			}
		});
		
		return foundAgents;
	}

	@Override
	public Agent getRunningAgent(AID agentAID) {
		if(runningAgents.containsKey(agentAID)) {
			return runningAgents.get(agentAID);
		} else {
			return null;
		}
	}
	
	@Override
	public void addRunningAgent(Agent agent) throws ObjectAlreadyActive {
		if(!runningAgents.containsKey(agent.getAID())) {
			System.out.println("ADDDED AGENT");
			runningAgents.put(agent.getAID(), agent);
		} else {
			throw new ObjectAlreadyActive();
		}	
	}

	@Override
	public void removeAgent(AID agentAID) {
		if(!runningAgents.containsKey(agentAID)) {
			runningAgents.remove(agentAID);
		}
	}

	@Override
	public boolean checkIfAgentIsRunning(AID agentAID) {
		return runningAgents.containsKey(agentAID);
	}
	
	@PreDestroy
	@Override
	public void cleanUp() {
		runningAgents.clear();
	}
}

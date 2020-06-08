package agentManager;

import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;

import agents.Agent;
import agents.TestAgentLocal;
import dataBaseService.activeAgents.ActiveAgentsDataBaseLocal;
import dataBaseService.activeTypes.ActiveTypesDataBaseLocal;
import model.AID;
import model.AgentType;

@Stateless
@LocalBean
public class ActiveAgentManager implements ActiveAgentManagerLocal{

	@EJB
	private ActiveAgentsDataBaseLocal activeAgents;
	
	@EJB
	private ActiveTypesDataBaseLocal activeTypes;
	
	@EJB
	private TestAgentLocal testAgent;
	
	@Override
	public Set<AID> getAllActiveAgents() {
		return this.activeAgents.getAllAgentAIDs();
	}

	@Override
	public Set<AgentType> getAllActiveTypes() {
		return this.activeTypes.getAllTypes();
	}

	@Override
	public Agent getAgent(AID agentAID) {
		return activeAgents.getRunningAgent(agentAID);
	}

	@Override
	public boolean checkIfAgentExist(AID aid) {
		return activeAgents.checkIfAgentIsRunning(aid);
	}	

	@Override
	public void startAgent(AID agentAID) {
		
		if(this.activeTypes.checkIfActiveType(agentAID.getType())) {
			if(!this.activeAgents.checkIfAgentIsRunning(agentAID)) {
				
				if(agentAID.getType().getModule().equals("test-module")) {
					testAgent.startUp(agentAID);
					
					try {
						this.activeAgents.addRunningAgent(testAgent);
					} catch (ObjectAlreadyActive e) {
						e.printStackTrace();
					}
				}
			}			
		}
	}

	@Override
	public void stopAgent(AID agentAID) {
		this.activeAgents.removeAgent(agentAID);
		
		if(this.activeAgents.getAllAgentsByType(agentAID.getType()).isEmpty()) {
			this.activeTypes.removeType(agentAID.getType());
		}
	}

	@Override
	public void stopAll() {
		this.activeAgents.cleanUp();
		this.activeTypes.removeAll();
	}

}

package agentManager;

import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;

import ContractNet.IniatorAgent;
import ContractNet.IniatorAgentRemote;
import ContractNet.ParticipantAgent;
import ContractNet.ParticipantAgentRemote;
import agents.Agent;
import agents.TestAgentRemote;
import common.JNDILookup;
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
					//TestAgentLocal testAgent = new TestAgent();
					TestAgentRemote testAgent = JNDILookup.lookUp(JNDILookup.TestAgentLookup, TestAgentRemote.class);
					
					testAgent.startUp(agentAID);
					
					try {
						this.activeAgents.addRunningAgent(testAgent);
					} catch (ObjectAlreadyActive e) {
						e.printStackTrace();
					}
				} else if(agentAID.getType().getModule().equals("contract-net-module")) {
					
					if(agentAID.getType().getName().equals("iniator")) {
						
						IniatorAgentRemote iniatorAgent = JNDILookup.lookUp(JNDILookup.IniatorAgentLookup, IniatorAgentRemote.class);
						iniatorAgent.startUp(agentAID);
						
						try {
							this.activeAgents.addRunningAgent(iniatorAgent);
						} catch (ObjectAlreadyActive e) {
							e.printStackTrace();
						}
					} else {
						
						ParticipantAgentRemote participantAgent = JNDILookup.lookUp(JNDILookup.ParticipantAgentLookup, ParticipantAgentRemote.class);
						participantAgent.startUp(agentAID);
						
						try {
							this.activeAgents.addRunningAgent(participantAgent);
						} catch (ObjectAlreadyActive e) {
							e.printStackTrace();
						}
					}
					
				} else {
					
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

package dataBaseService.onLineAgents;

import java.util.Set;

import javax.ejb.Local;

import model.AID;
import model.AgentType;

@Local
public interface OnLineAgentsDataBaseLocal {

	Set<AID> getAll();
	Set<AID> getAllByHostAlias(String hostAlias);
	Set<AID> getAllByType(AgentType type);
	Set<AID> getAllByName(String name);
	
	void addOnLineAgent(AID newAID);
	void addSetOfOnLIneAgents(Set<AID> newSetOfAIDs);

	void removeAllOnHost(String hostAlias);
	void removeAgent(AID agentAID);
	
	int getNumberOfOnLineAgents();
	void cleanUp();

}
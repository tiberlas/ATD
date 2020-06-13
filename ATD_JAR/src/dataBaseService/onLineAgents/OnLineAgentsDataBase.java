package dataBaseService.onLineAgents;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import model.AID;
import model.AgentType;

@Singleton
@LocalBean
public class OnLineAgentsDataBase implements OnLineAgentsDataBaseLocal {

	private Set<AID> onLineAgents = new HashSet<AID>();
	
	@Override
	public Set<AID> getAll() {
		return onLineAgents;
	}
	
	@Override
	public Set<AID> getAllByHostAlias(String hostAlias) {
		Set<AID> foundAgents = new HashSet<AID>();
		
		onLineAgents.forEach(aid -> {
			if(aid.getHostAlias().equals(hostAlias)) {
				foundAgents.add(aid);
			}
		});
		
		return foundAgents;
	}
	
	@Override
	public Set<AID> getAllByType(AgentType type) {
		Set<AID> foundAgents = new HashSet<AID>();
		
		onLineAgents.forEach(aid -> {
			if(aid.getType().equals(type)) {
				foundAgents.add(aid);
			}
		});
		
		return foundAgents;
	}
	
	@Override
	public Set<AID> getAllByName(String name) {
		Set<AID> foundAgents = new HashSet<AID>();
		
		onLineAgents.forEach(aid -> {
			if(aid.getName().equals(name)) {
				foundAgents.add(aid);
			}
		});
		
		return foundAgents;
	}
	
	@Override
	public void addOnLineAgent(AID newAID) {
		onLineAgents.add(newAID);
	}
	
	@Override
	public void addSetOfOnLIneAgents(Set<AID> newSetOfAIDs) {
		onLineAgents.addAll(newSetOfAIDs);
	}
	
	@Override
	public int getNumberOfOnLineAgents() {
		return onLineAgents.size();
	}
	
	@Override
	public void removeAllOnHost(String hostAlias) {
		getAllByHostAlias(hostAlias).forEach(aid -> {
			removeAgent(aid);
		});
	}
	
	@Override
	public void removeAgent(AID agentAID) {
		if(onLineAgents.contains(agentAID)) {
			onLineAgents.remove(agentAID);
		}
	}
	
	@Override
	@PreDestroy
	public void cleanUp() {
		onLineAgents.clear();
	}
}

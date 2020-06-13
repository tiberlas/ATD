package dataBaseService.activeTypes;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import model.AgentType;

@Singleton
@LocalBean
public class ActiveTypesDataBase implements ActiveTypesDataBaseLocal {

	private Set<AgentType> activeTypes = new HashSet<AgentType>();
	
	@Override
	public Set<AgentType> getAllTypes() {
		return activeTypes;
	}
	
	@Override
	public Set<AgentType> getAllByModule(String module) {
		Set<AgentType> foundTypes = new HashSet<AgentType>();
		
		activeTypes.forEach(type -> {
			if(type.getModule().equals(module)) {
				foundTypes.add(type);
			}
		});
		
		return foundTypes;
	}
	
	@Override
	public Set<AgentType> getAllByName(String name) {
		Set<AgentType> foundTypes = new HashSet<AgentType>();
		
		activeTypes.forEach(type -> {
			if(type.getName().equals(name)) {
				foundTypes.add(type);
			}
		});
		
		return foundTypes;
	}
	
	@Override
	public void addType(AgentType newType) {
		activeTypes.add(newType);
	}
	
	@Override
	public void removeType(AgentType typeForRemoval) {
		activeTypes.remove(typeForRemoval);
	}
	
	@Override
	public boolean checkIfActiveType(AgentType type) {
		return activeTypes.contains(type);
	}
	
	@Override
	public int getNumberOfTypes() {
		return activeTypes.size();
	}
	
	@Override
	@PreDestroy
	public void removeAll() {
		activeTypes.clear();
	}
}

package dataBaseService.activeTypes;

import java.util.Set;

import javax.ejb.Local;

import model.AgentType;

@Local
public interface ActiveTypesDataBaseLocal {

	Set<AgentType> getAllTypes();
	Set<AgentType> getAllByModule(String module);
	Set<AgentType> getAllByName(String name);
	void addType(AgentType newType);
	void removeType(AgentType typeForRemoval);
	boolean checkIfActiveType(AgentType type);
	int getNumberOfTypes();
	void removeAll();

}
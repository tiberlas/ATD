package dataBaseService.onLineTypes;

import java.util.Map;
import java.util.Set;

import javax.ejb.Local;

import model.AgentType;

@Local
public interface OnLineTypesDataBaseLocal {

	/**
	 * DB of on line(remote) agent types;
	 * */
	
	Map<String, Set<AgentType>> getAll();
	Set<AgentType> getAllByHostAlias(String hostAlias);
	Set<AgentType> getAllUniqueTypes();
	
	void addOnLineType(AgentType newType, String hostAlias);
	void addSetOfOnLIneTypes(Set<AgentType> newSetOfTypes, String hostAlias);
	
	boolean checkIfExistsOnHost(AgentType type, String hostAlias);
	
	void removeAllOnHost(String hostAlias);
	void removeOnLineType(AgentType type, String hostAlias);
	void cleanUp();
}

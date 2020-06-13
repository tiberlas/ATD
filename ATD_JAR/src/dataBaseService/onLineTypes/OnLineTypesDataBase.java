package dataBaseService.onLineTypes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import model.AgentType;

@Singleton
@LocalBean
public class OnLineTypesDataBase implements OnLineTypesDataBaseLocal{

	private Map<String, Set<AgentType>> onLineTypes = new HashMap<String, Set<AgentType>>();

	@Override
	public Map<String, Set<AgentType>> getAll() {
		return onLineTypes;
	}

	@Override
	public Set<AgentType> getAllByHostAlias(String hostAlias) {
		if(!onLineTypes.containsKey(hostAlias)) {
			return onLineTypes.get(hostAlias);
		} else {
			return null;
		}
	}
	
	@Override
	public Set<AgentType> getAllUniqueTypes() {
		Set<AgentType> allTypes = new HashSet<AgentType>();
		
		onLineTypes.forEach((hostAlias, types) -> {
			allTypes.addAll(types);
		});
		
		return allTypes;
	}

	@Override
	public void addOnLineType(AgentType newType, String hostAlias) {
		if(!onLineTypes.containsKey(hostAlias)) {
			Set<AgentType> newSet = new HashSet<AgentType>();
			newSet.add(newType);
			
			onLineTypes.put(hostAlias, newSet);
		} else {
			onLineTypes.get(hostAlias).add(newType);
		}
	}

	@Override
	public void addSetOfOnLIneTypes(Set<AgentType> newSetOfTypes, String hostAlias) {
		if(!onLineTypes.containsKey(hostAlias)) {
			onLineTypes.put(hostAlias, newSetOfTypes);
		} else {
			onLineTypes.get(hostAlias).addAll(newSetOfTypes);
		}
	}

	@Override
	public void removeAllOnHost(String hostAlias) {
		if(!onLineTypes.containsKey(hostAlias)) {
//			onLineTypes.get(hostAlias).clear();
			onLineTypes.remove(hostAlias);
		}
	}

	@Override
	public void removeOnLineType(AgentType type, String hostAlias) {
		if(!onLineTypes.containsKey(hostAlias)) {
			onLineTypes.get(hostAlias).remove(type);
		}
	}

	@PreDestroy
	@Override
	public void cleanUp() {
		onLineTypes.clear();
	}

	@Override
	public boolean checkIfExistsOnHost(AgentType type, String hostAlias) {
		return onLineTypes.get(hostAlias).contains(type);
	}
}

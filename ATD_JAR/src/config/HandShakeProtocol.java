package config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import agentManager.ActiveAgentManagerLocal;
import agentManager.OnLineAgentManagerlocal;
import model.AID;
import model.AgentType;
import model.Host;
import rest.handShakeProtocol.HandShakeSender;

@Singleton
@LocalBean
public class HandShakeProtocol {

	@EJB
	private OnLineAgentManagerlocal onLineAgentManager;
	@EJB
	private ActiveAgentManagerLocal activeAgents;
	
	public void masterRecivedANewNode(Host newNode) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					
					System.out.println("ALL NODES "+onLineAgentManager.getAllHosts());
					
					//uzmi spisak tipova sa novog cvora
					System.out.println("GET ALL TYPES");
					if(!gotTypesFromNewNode(true, newNode)) {
						handShakeFaild(newNode);
						return;
					}
					
					System.out.println("ALERT OTHER NODES THAT NEW NODE IS ADDED");
					//salji ostalim cvorovima da je novi cvor dosao
					Map<String, Set<AgentType>> types = new HashMap<String, Set<AgentType>>();
					types.put(newNode.getAlias(), onLineAgentManager.getAllTypesFromHost(newNode));
					onLineAgentManager.getAllHosts().forEach(node -> {
						if(!node.equals(newNode)) {
							HandShakeSender.addNewNode(node, newNode);
							HandShakeSender.addAgentTypes(node, types);
						}
					});
					System.out.println("SEND NEW NODE CLUSTER INFO");
					//salji informacije o clusteru novom cvoru
					if(!sendInfoAboutClusterToNewNode(true, newNode)) {
						handShakeFaild(newNode);
						return;
					}
					System.out.println("SEND NEW NODE TYPES INFO");
					if(!sendInfoAboutTypesToNewNode(true, newNode)) {
						handShakeFaild(newNode);
						return;
					}
					System.out.println("SEND NEW NODE RUNNING AGENTS INFO");
					if(!sendInfoAboutRunningAgentsToNewNode(true, newNode)) {
						handShakeFaild(newNode);
						return;
					}
					System.out.println("HAND SHAKE SUCCESS");
				}catch(Exception e) {
					System.out.println("HAND SHAKE FAILD BECOUSE EXCEPTION");
				}
			}
		}).start();
	}
	
	private boolean gotTypesFromNewNode(boolean firstAttempt, Host node) {
		
		Set<AgentType> newTypes = HandShakeSender.getAllAgentTypes(node);
		
		if(newTypes != null) {
			System.out.println("GOT TYPES FOR NODE: "+ node);
			onLineAgentManager.addTypes(newTypes, node.getAlias());
			
			return true;
		} else if(firstAttempt == true) {
			
			return gotTypesFromNewNode(false, node);
		} else {
			
			return false;
		}
	}
	
	private boolean sendInfoAboutClusterToNewNode(boolean firstAttempt, Host node) {
		
		Set<Host> nodes = onLineAgentManager.getAllHosts();
		nodes.remove(node);
		
		if(HandShakeSender.addNewNodes(node, nodes)) {
			System.out.println("SEND LIST OF NODES TO NODE: "+ node);
			return true;
		} else if(firstAttempt == true) {
			return sendInfoAboutClusterToNewNode(false, node);
		} else {
			return false;
		}
	}
	
	private boolean sendInfoAboutTypesToNewNode(boolean firstAttempt, Host node) {
		
		Map<String, Set<AgentType>> allTypes = onLineAgentManager.getAllTypesWithHost();
		allTypes.remove(node.getAlias());

		Set<AgentType> mytypes = activeAgents.getAllActiveTypes();
		allTypes.put("master", mytypes);
		
		if(HandShakeSender.addAgentTypes(node, allTypes)) {
			System.out.println("SEND LIST OF TYPES INFO TO NODE: "+ node);
			return true;
		} else if(firstAttempt == true) {
			return sendInfoAboutTypesToNewNode(false, node);
		} else {
			return false;
		}
	}
	
	private boolean sendInfoAboutRunningAgentsToNewNode(boolean firstAttempt, Host node) {
		
		Set<AID> aids = onLineAgentManager.getAllOnLineAgents();
		if(activeAgents.getAllActiveAgents().size() != 0) {
			aids.addAll(activeAgents.getAllActiveAgents());			
		}
		
		if(HandShakeSender.addRunningAgenta(node, aids)) {
			System.out.println("SEND LIST OF RUNNING AGENTS TO NODE: "+ node);
			return true;
		} else if(firstAttempt == true) {
			return sendInfoAboutRunningAgentsToNewNode(false, node);
		} else {
			return false;
		}
	}
	
	private void handShakeFaild(Host newNode) {
		onLineAgentManager.removeHost(newNode.getAlias());
		
		System.out.println("HAND SHAKE FALED");
	}
}

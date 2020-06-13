package config;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import agentManager.OnLineAgentManagerlocal;
import agents.HostAgentLocal;
import dataBaseService.activeTypes.ActiveTypesDataBaseLocal;
import model.AgentType;
import model.Host;
import rest.handShakeProtocol.HandShakeSender;

@Startup
@Singleton
public class StartUp {

	@EJB
	private ActiveTypesDataBaseLocal types;
	@EJB
	private HostAgentLocal host;
	@EJB
	private ServerDiscovery serverDiscovery;
	@EJB
	private OnLineAgentManagerlocal onLineAgentManager;

	@PostConstruct
	public void setUpAgentTypes() {
		System.out.println("SETING UP AGENT TYPES___________________________________________________________");
		
		//HARDCODED TYPES
		types.addType(new AgentType("test", "test-module"));
		types.addType(new AgentType("iniator", "contract-net-module"));
		types.addType(new AgentType("participant", "contract-net-module"));

		Host master = CreateNode.createMasterNode();
		List<Host> findedHosts = serverDiscovery.findAllActiveNodes(master.getAddress());
		master.setAlias(CreateNode.createAlias(findedHosts));
		host.setUp(master);
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("I am: " + host.getHost());
		System.out.println("found "+ findedHosts);
		
		//ovaj host nije master pa pocinje handshake
		if(findedHosts!=null && !findedHosts.isEmpty()) {
			findedHosts.forEach(h -> {
				if(h.getAlias().equals("master")) {
					onLineAgentManager.setMaster(h);
				} else {
					onLineAgentManager.addHost(h);
				}
			});
			
			if(!registerThisNodeToMaster(true)) {
				master.setAlias("master");
				host.setUp(master);
				onLineAgentManager.cleanUp();
				System.out.println("HAND SHAKE FALED");
				System.out.println("I am: " + host.getHost());
			}
		} else {
			onLineAgentManager.setMaster(null);
		}
	}
	
	private boolean registerThisNodeToMaster(boolean firstAttempt) {
		
		if(HandShakeSender.addNewNode(onLineAgentManager.getMaster(), host.getHost())) {
			return true;
		} else if(firstAttempt) {
			return registerThisNodeToMaster(false);
		} else {
			return false;
		}
	}


}

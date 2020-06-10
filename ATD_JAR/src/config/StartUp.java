package config;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import dataBaseService.activeTypes.ActiveTypesDataBaseLocal;
import model.AgentType;

@Startup
@Singleton
public class StartUp {

	@EJB
	private ActiveTypesDataBaseLocal types;
	
	@PostConstruct
	public void setUpAgentTypes() {
		System.out.println("SETING UP AGENT TYPES___________________________________________________________");
		
		AgentType test = new AgentType("test", "test-module");
		AgentType iniator = new AgentType("iniator", "contract-net-module");
		AgentType participant = new AgentType("participant", "contract-net-module");
		
		types.addType(test);
		types.addType(iniator);
		types.addType(participant);
		
	}
}

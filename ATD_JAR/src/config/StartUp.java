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
		
		types.addType(test);
		
	}
}

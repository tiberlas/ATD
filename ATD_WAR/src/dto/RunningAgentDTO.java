package dto;

import java.io.Serializable;

import model.AID;

public class RunningAgentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String agentName;
	private String hostAlias;
	private String agentTypeName;
	private String agentTypeModule;
	
	public RunningAgentDTO() {
		super();
	}
	
	public RunningAgentDTO(String agentName, String hostAlias, String agentTypeName, String agentTypeModule) {
		super();
		this.agentName = agentName;
		this.hostAlias = hostAlias;
		this.agentTypeName = agentTypeName;
		this.agentTypeModule = agentTypeModule;
	}
	
	public RunningAgentDTO(AID aid) {
		super();
		this.agentName = aid.getName();
		this.hostAlias = aid.getHostAlias();
		this.agentTypeName = aid.getType().getName();
		this.agentTypeModule = aid.getType().getModule();
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getHostAlias() {
		return hostAlias;
	}

	public void setHostAlias(String hostAlias) {
		this.hostAlias = hostAlias;
	}

	public String getAgentTypeName() {
		return agentTypeName;
	}

	public void setAgentTypeName(String agentTypeName) {
		this.agentTypeName = agentTypeName;
	}

	public String getAgentTypeModule() {
		return agentTypeModule;
	}

	public void setAgentTypeModule(String agentTypeModule) {
		this.agentTypeModule = agentTypeModule;
	}
	
}

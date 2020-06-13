package dto;

import java.io.Serializable;

import model.AgentType;

public class TypeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;
	private String module;

	public TypeDTO() {
		super();
	}
	
	public TypeDTO(AgentType type) {
		super();
		this.type = type.getName();
		this.module = type.getModule();
	}
	
	public TypeDTO(String type, String module) {
		super();
		this.type = type;
		this.module = module;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
}

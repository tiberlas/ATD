package model;

import java.io.Serializable;

public class AID implements Serializable {
	
	/**
	 * Defines the agent ID 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String hostAlias;
	private AgentType type;

	public AID() {
		super();
	}
	
	public AID(String name, String hostAlias, AgentType type) {
		super();
		this.name = name;
		this.hostAlias = hostAlias;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHostAlias() {
		return hostAlias;
	}
	public void setHostAlias(String host) {
		this.hostAlias = host;
	}
	public AgentType getType() {
		return type;
	}
	public void setType(AgentType type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hostAlias == null) ? 0 : hostAlias.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AID other = (AID) obj;
		if (hostAlias == null) {
			if (other.hostAlias != null)
				return false;
		} else if (!hostAlias.equals(other.hostAlias))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AID [name=" + name + ", host=" + hostAlias + ", type=" + type + "]";
	}
	
}

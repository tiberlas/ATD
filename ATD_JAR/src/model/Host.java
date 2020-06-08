package model;

import java.io.Serializable;

public class Host implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String alias; 
	private String address;
	private int port;
	
	public Host() {
		super();
	}

	public Host(String alias, String address, int port) {
		super();
		this.alias = alias;
		this.address = address;
		this.port = port;
	}
	
 	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "Host [alias=" + alias + ", address=" + address + ", port=" + port + "]";
	}

}

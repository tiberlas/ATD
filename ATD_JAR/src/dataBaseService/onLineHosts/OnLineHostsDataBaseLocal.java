package dataBaseService.onLineHosts;

import java.util.Set;

import javax.ejb.Local;

import model.Host;

@Local
public interface OnLineHostsDataBaseLocal {

	/**
	 * DB of on line(remote) host nodes;
	 * */
	
	//for master node
	Host getMasterNode();
	void setMasterNode(Host masterNode);
	
	//set of host nodes
	Host getHostNode(String hostAlias);
	Set<Host> getAllHostNodes();
	
	void addHostNode(Host newHostNode);
	void addHostNodes(Set<Host> hostNodes);

	void removeHostNode(Host hostNode);
	void removeHostNode(String hostAlias);
	void removeHostNodes(Set<Host> hostNodes);
	
	boolean cheskIfExist(String hostAlias);
	
	void cleanUp();

}
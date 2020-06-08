package dataBaseService.onLineHosts;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import model.Host;

@Singleton
@LocalBean
public class OnLineHostsDataBase implements OnLineHostsDataBaseLocal {

	/**
	 * spisak svih host cvorova sa adresom tog cvora;
	 * master cvor je odvojen i ne nalazi se na spisku(listi)
	 * */
	private Host masterNode = null;
	private Set<Host> hostNodes = new HashSet<Host>();
	
	@Override
	public Host getMasterNode() {
		return masterNode;
	}
	
	@Override
	public void setMasterNode(Host masterNode) {
		this.masterNode = masterNode;
	}
	
	@Override
	public Set<Host> getAllHostNodes() {
		return hostNodes;
	}
	
	@Override
	public Host getHostNode(String hostAlias) {
		Optional<Host> h = hostNodes.stream().
				filter(node -> node.getAlias().equals(hostAlias))
				.findFirst();
		
		return h.orElse(null);
	}
	
	@Override
	public void addHostNode(Host newHostNode) {
		this.hostNodes.add(newHostNode);
	}
	
	@Override
	public void addHostNodes(Set<Host> hostNodes) {
		this.hostNodes.addAll(hostNodes);
	}

	@Override
	public void removeHostNode(Host hostNode) {
		this.hostNodes.remove(hostNode);
	}
	
	@Override
	public void removeHostNode(String hostAlias) {
		Host foundHost = getHostNode(hostAlias);
		if(foundHost != null) {
			removeHostNode(foundHost);
		}
	}
	
	@Override
	public void removeHostNodes(Set<Host> hostNodes) {
		
	}
	
	@Override
	public boolean cheskIfExist(String hostAlias) {
		
		Optional<Host> h = hostNodes.stream().
				filter(node -> node.getAlias().equals(hostAlias))
				.findFirst();
		
		return h.isPresent();
	}
	
	@PreDestroy
	@Override
	public void cleanUp() {
		this.hostNodes.clear();
		this.masterNode = null;
	}
}

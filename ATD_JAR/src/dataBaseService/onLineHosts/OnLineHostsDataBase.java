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
		Set<Host> hosts = new HashSet<Host>();
		hostNodes.forEach(h -> {
			hosts.add(h);
		});
		
		return hosts;
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
		if(newHostNode != null) {
			this.hostNodes.add(newHostNode);
		}
	}
	
	@Override
	public void addHostNodes(Set<Host> hostNodes) {
		if(hostNodes != null && !hostNodes.isEmpty()) {
			hostNodes.forEach(h -> {
				addHostNode(h);
			});
		}
	}

	@Override
	public void removeHostNode(Host hostNode) {
		if(hostNodes.contains(hostNode)) {
			this.hostNodes.remove(hostNode);
		}
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
		hostNodes.forEach(node -> {
			removeHostNode(node);
		});
	}
	
	@Override
	public boolean cheskIfExist(String hostAlias) {
		
		boolean ret = false;
		
		for(Host h : hostNodes) {
			if(h.getAlias().equals(hostAlias)) {
				ret = true;
			}
		}
		return ret;
	}
	
	@PreDestroy
	@Override
	public void cleanUp() {
		this.hostNodes.clear();
		this.masterNode = null;
	}
}

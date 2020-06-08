package service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import agents.HostAgentLocal;
import common.AclAdapter;
import dto.AclDTO;

@Stateless
@LocalBean
public class MessageService {

	@EJB
	private HostAgentLocal host;
	
	public void sendACL(AclDTO acl) {
		
		host.handleMessage(AclAdapter.fromDTOtoModel(acl));
	}
	
	public String[] getAllPerfomativs() {
		
		return host.getAllPerfomativs();
	}
}

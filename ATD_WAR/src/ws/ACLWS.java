package ws;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import common.AclAdapter;
import common.JsonObjMapper;
import dto.AclDTO;
import model.ACL;

@Stateless
@ServerEndpoint(value = "/ws/acl")
@LocalBean
public class ACLWS {

	@EJB
	private JsonObjMapper jsonMapper;
	
	private static Set<Session> activeSessions = new HashSet<Session>();
	
	public ACLWS() {
		super();
	}
	
	@OnOpen
	public void onOpen(Session session) {
		activeSessions.add(session);
	}
	
	public void sendACL(ACL acl) {
		AclDTO dto = AclAdapter.fromModeltoDTO(acl);
		
		activeSessions.forEach(session -> {
			if(session.isOpen()) {
				try {
					session.getBasicRemote().sendText(jsonMapper.objToJson(dto));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@OnClose
	public void close(Session session) {
		activeSessions.remove(session);
	}
	
	@OnError
	public void error(Session session, Throwable t) {
		close(session);
		t.printStackTrace();
	}
	
}
